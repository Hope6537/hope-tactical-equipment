package org.hope6537.hadoop.recommend;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.hope6537.hadoop.hdfs.HdfsUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Hope6537 on 2015/2/14.
 * 网站评分推荐MapReduce实现
 * <pre>
 * ===============================================================
 * 设计一下思路
 * 1、将元数据转化为同源矩阵 即一个用户存在[选择101、102]值对后，该矩阵的内容+1
 * /   101 102 103
 * 101  5   2   2
 * 102  2   5   3
 * 103  2   3   4
 * 实际是我们可以看出这个所谓的同源矩阵就是个三角矩阵
 * 具体的我们先这么算，等到成功后我们再优化三角矩阵
 * 2、然后我们得出得分矩阵
 * 对于User u1 他对于所有产品的打分
 * [user u1]
 * 101 2.5
 * 102 0.0
 * 103 4.0
 * 实际上这个矩阵很好理解
 * 3、然后我们对于User u1 我们将同源矩阵乘以他的评分矩阵
 * 将会获得一个 n*1的矩阵
 * 上面的得数，就是对于user u1的推荐值
 * 去掉评分矩阵中不为零的数据，也就是他已经看过的东西，最后按照得数进行排序返回推荐
 * =================================================================
 * MapReduce阶段将分为4个步骤
 * 步骤1、按照User进行Group by 来计算矩阵单位[k1,k2]出现的频数，同时计算用户的评分矩阵
 * 步骤2、对第一步产生的矩阵单位频数进行计数，并从中得出同源矩阵
 * 步骤3、合并同源矩阵和评分矩阵(重點)
 * 步骤4、得出结果并筛选
 * </pre>
 */
public class RecommendDriver {

    public static final Pattern DELIMITER = Pattern.compile("[\t,]");

    public void run(HashMap<String, String> paths, Configuration configuration) throws Exception {
        Recommend_1_BuildRatingMatrix step1 = new Recommend_1_BuildRatingMatrix();
        if (step1.run(paths, configuration)) {
            Recommend_2_BuildSourceMatrix step2 = new Recommend_2_BuildSourceMatrix();
            if (step2.run(paths, configuration)) {
                Recommend_3_RegroupMatrix step3 = new Recommend_3_RegroupMatrix();
                step3.run(paths, configuration);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        HashMap<String, String> paths = new HashMap<>();
        Configuration configuration = new Configuration();
        //configuration.set("mapred.child.java.opts", "-Xmx200m -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=16888");
        HdfsUtils hdfsUtils = HdfsUtils.getInstanceOfPseudoDistributed(configuration);
        if (args.length < 2) {
            System.err.println("args error");
            System.err.println("--input 文件初始输入路径");
            System.err.println("--output 文件最终输出路径");
        } else {
            System.err.println("System init");
            System.err.println("input -" + args[0] + ", output -" + args[1]);
            System.err.println("looking for input");

            hdfsUtils.lsShowInConsole(args[0]);

            paths.put("input", args[0]);
            paths.put("step1OutPut", paths.get("input") + "/s1o");
            paths.put("step2Input", paths.get("step1OutPut"));
            paths.put("step2OutPut", paths.get("input") + "/s2o");
            paths.put("step3Input", paths.get("step2OutPut"));
            paths.put("step3OutPut", paths.get("input") + "/s3o");
            paths.put("step4Input", paths.get("step3OutPut"));
            paths.put("output", args[1]);

            System.err.println("removing and making");
            hdfsUtils.rmrShowInConsole(paths.get("step1OutPut"));
            hdfsUtils.rmrShowInConsole(paths.get("step2OutPut"));
            hdfsUtils.rmrShowInConsole(paths.get("step3OutPut"));
            hdfsUtils.rmrShowInConsole(paths.get("output"));
            hdfsUtils.closeFileSystem();

            System.err.println(paths.toString());

            RecommendDriver recommendDriver = new RecommendDriver();
            recommendDriver.run(paths, configuration);
        }
    }

}

/**
 * 步骤1 建立评分矩阵 Rating Matrix
 */
class Recommend_1_BuildRatingMatrix {

    public boolean run(Map<String, String> paths, Configuration configuration) throws Exception {

        Job job = Job.getInstance(configuration);

        job.setJarByClass(Recommend_1_BuildRatingMatrix.class);
        job.setJobName("Recommend_1_BuildRatingMatrix");

        FileInputFormat.setInputPaths(job, new Path(paths.get("input").toString()));
        FileOutputFormat.setOutputPath(job, new Path(paths.get("step1OutPut").toString()));

        job.setMapperClass(RatingMatrixMapper.class);
        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(RatingMatrixReducer.class);
        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(Text.class);

        job.waitForCompletion(true);

        return job.isSuccessful();
    }

    /**
     * 将数据重排序
     */
    public static class RatingMatrixMapper extends Mapper<LongWritable, Text, LongWritable, Text> {
        private LongWritable userId = new LongWritable();
        private Text result = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] tokens = RecommendDriver.DELIMITER.split(value.toString());
            try {
                userId.set(Long.parseLong(tokens[0]));
                String itemId = tokens[1];
                String pref = tokens[2];
                String resultString = itemId + "->" + pref;
                result.set(resultString);
                context.write(userId, result);
            } catch (ArrayIndexOutOfBoundsException e) {
                context.getCounter("RatingMatrixMapper", "ArrayIndexOutOfBounds").increment(1L);
            }

        }
    }

    /**
     * 将相同的Reduce规约
     */
    public static class RatingMatrixReducer extends Reducer<LongWritable, Text, LongWritable, Text> {
        private Text result = new Text();

        @Override
        protected void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            StringBuilder stringBuilder = new StringBuilder();
            for (Text value : values) {
                stringBuilder.append(",");
                stringBuilder.append(value.toString());
            }
            result.set(stringBuilder.toString().replaceFirst(",", ""));
            context.write(key, result);
        }
    }
}

/**
 * 步骤2 建立同源矩阵 Source Matrix
 */
class Recommend_2_BuildSourceMatrix {

    public boolean run(Map<String, String> paths, Configuration configuration) throws Exception {

        Job job = Job.getInstance(configuration);

        job.setJarByClass(Recommend_2_BuildSourceMatrix.class);
        job.setJobName("Recommend_2_BuildSourceMatrix");

        FileInputFormat.setInputPaths(job, new Path(paths.get("step1OutPut").toString()));
        FileOutputFormat.setOutputPath(job, new Path(paths.get("step2OutPut").toString()));

        job.setMapperClass(SourceMatrixMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        job.setReducerClass(SourceMatrixReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        job.waitForCompletion(true);

        return job.isSuccessful();
    }

    private static class SourceMatrixMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
        private Text resultKey = new Text();
        private LongWritable resultValue = new LongWritable();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] tokens = RecommendDriver.DELIMITER.split(value.toString());
            try {
                for (int i = 1; i < tokens.length; i++) {
                    String key1 = tokens[i].split("->")[0];
                    for (int j = 1; j < tokens.length; j++) {
                        String key2 = tokens[j].split("->")[0];
                        resultKey.set(key1 + "<->" + key2);
                        resultValue.set(1L);
                        context.write(resultKey, resultValue);
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                context.getCounter("SourceMatrixMapper", "ArrayIndexOutOfBounds").increment(1L);
            }
        }
    }

    private static class SourceMatrixReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
        private LongWritable resultValue = new LongWritable();

        @Override
        protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
            long res = 0L;
            for (LongWritable longWritable : values) {
                res += longWritable.get();
            }
            resultValue.set(res);
            context.write(key, resultValue);
        }
    }

}

/**
 * 步骤3 合并矩阵 Regroup Matrix
 */
class Recommend_3_RegroupMatrix {

    public boolean run(Map<String, String> paths, Configuration configuration) throws Exception {
        Job ratingJob = Job.getInstance(configuration);
        ratingJob.setJarByClass(Recommend_3_RegroupMatrix.class);
        ratingJob.setJobName("Recommend_3_RegroupMatrix");
        FileInputFormat.setInputPaths(ratingJob, new Path(paths.get("step1OutPut").toString()));
        FileOutputFormat.setOutputPath(ratingJob, new Path(paths.get("step3OutPut") + "/rating"));
        ratingJob.setMapperClass(FormatRatingMapper.class);
        ratingJob.setMapOutputKeyClass(LongWritable.class);
        ratingJob.setMapOutputValueClass(Text.class);
        ratingJob.waitForCompletion(true);

        Job sourceJob = Job.getInstance(configuration);
        sourceJob.setJarByClass(Recommend_3_RegroupMatrix.class);
        sourceJob.setJobName("Recommend_3_RegroupMatrix");
        FileInputFormat.setInputPaths(sourceJob, new Path(paths.get("step2OutPut").toString()));
        FileOutputFormat.setOutputPath(sourceJob, new Path(paths.get("step3OutPut") + "/source"));
        sourceJob.setMapperClass(FormatSourceMapper.class);
        sourceJob.setMapOutputKeyClass(Text.class);
        sourceJob.setMapOutputValueClass(LongWritable.class);
        sourceJob.waitForCompletion(true);

        return ratingJob.isSuccessful() && sourceJob.isSuccessful();
    }

    public static class FormatRatingMapper extends Mapper<LongWritable, Text, LongWritable, Text> {

        private LongWritable resultKey = new LongWritable();
        private Text resultValue = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] tokens = RecommendDriver.DELIMITER.split(value.toString());
            try {
                String userId = tokens[0];
                for (int i = 1; i < tokens.length; i++) {
                    String[] data = tokens[i].split("->");
                    System.err.println(data.toString());
                    String itemId = data[0];
                    String rating = data[1];
                    resultKey.set(Long.parseLong(itemId));
                    resultValue.set(userId + "->" + rating);
                    context.write(resultKey, resultValue);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                context.getCounter("FormatRatingMapper", "ArrayIndexOutOfBounds").increment(1L);
            }
        }
    }

    /**
     * 在本例中，本源矩阵不需要重构key
     */
    public static class FormatSourceMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
        private Text resultKey = new Text();
        private LongWritable resultValue = new LongWritable();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] tokens = RecommendDriver.DELIMITER.split(value.toString());
            try {
                resultKey.set(tokens[0]);
                resultValue.set(Long.parseLong(tokens[1]));
                context.write(resultKey, resultValue);
            } catch (ArrayIndexOutOfBoundsException e) {
                context.getCounter("FormatSourceMapper", "ArrayIndexOutOfBounds").increment(1L);
            }
        }
    }

}