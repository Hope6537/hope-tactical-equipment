package org.hope6537.hadoop.recommend;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.hope6537.context.ApplicationConstant;
import org.hope6537.hadoop.ConfigurationFactory;
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

    public void run(RecommendConfiguration conf) throws Exception {

        Recommend_1_BuildRatingMatrix step1 = new Recommend_1_BuildRatingMatrix();
        Recommend_2_BuildSourceMatrix step2 = new Recommend_2_BuildSourceMatrix();
        Recommend_3_RegroupMatrix step3 = new Recommend_3_RegroupMatrix();
        Recommend_4_MultipleMatrix step4 = new Recommend_4_MultipleMatrix();
        Recommend_5_FilterResult step5 = new Recommend_5_FilterResult();
        System.err.println("Execute Step1");
        if (step1.run(conf)) {
            System.err.println("Execute Step2");
            if (step2.run(conf)) {
                System.err.println("Execute Step3");
                if (step3.run(conf)) {
                    System.err.println("Execute Step4");
                    if(step4.run(conf)) {
                        step5.run(conf);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        HashMap<String, String> paths = new HashMap<>();
        Configuration configuration = ConfigurationFactory.getConfigurationOfPseudoDistributed();
        //configuration.set("mapred.child.java.opts", "-Xmx200m -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=16888");
        RecommendConfiguration recommendConfiguration = new RecommendConfiguration(paths, configuration);
        HdfsUtils hdfsUtils = HdfsUtils.getInstanceOfPseudoDistributed(configuration);
       /* args = new String[2];
        args[0] = "hdfs://hadoop2master:9000/recommend";
        args[1] = "hdfs://hadoop2master:9000/recommend/output";*/
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
            paths.put("step1OutPut", paths.get("input") + "/s1o/");
            paths.put("step2OutPut", paths.get("input") + "/s2o/");
            paths.put("step3OutPut", paths.get("input") + "/s3o/");
            paths.put("step4OutPut", paths.get("input") + "/s4o/");
            paths.put("filter_output", paths.get("input") + "/filter_output/");
            paths.put("output", args[1]);

            System.err.println("removing and making");
            /*hdfsUtils.rmrShowInConsole(paths.get("step1OutPut"));
            hdfsUtils.rmrShowInConsole(paths.get("step2OutPut"));
            hdfsUtils.rmrShowInConsole(paths.get("step3OutPut"));*/
            //hdfsUtils.rmrShowInConsole(paths.get("step4OutPut"));
            //hdfsUtils.rmrShowInConsole(paths.get("filter_output"));
            hdfsUtils.rmrShowInConsole(paths.get("output"));
            hdfsUtils.closeFileSystem();

            System.err.println("Job Path is here");
            System.err.println("================================");
            for (String key : paths.keySet()) {
                System.err.println(key + " -> " + paths.get(key));
            }
            System.err.println("================================");
            RecommendDriver recommendDriver = new RecommendDriver();
            recommendDriver.run(recommendConfiguration);
        }
    }

}

class RecommendConfiguration {

    private Map<String, String> paths;
    private Configuration configuration;

    public Map<String, String> getPaths() {
        return paths;
    }

    public void setPaths(Map<String, String> paths) {
        this.paths = paths;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public RecommendConfiguration(Map<String, String> paths, Configuration configuration) {
        this.paths = paths;
        this.configuration = configuration;
    }
}

class JobHandler extends AbstractHandler {

    private RecommendConfiguration configuration;

    public static JobHandler getInstance(int level, RecommendConfiguration configuration) {
        return new JobHandler(level, configuration);
    }

    public JobHandler(int level, RecommendConfiguration configuration) {
        super(level);
        this.configuration = configuration;
    }

    @Override
    protected void response(UserJob job) {
        try {
            job.run(configuration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

abstract class AbstractHandler {

    private int level = 0;

    private AbstractHandler nextHandler;

    public AbstractHandler(int level) {
        this.level = level;
    }

    public AbstractHandler setNextHandler(AbstractHandler nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }

    public final void handleJob(UserJob job) {
        boolean result = false;
        if (this.level == job.getLevel()) {
            this.response(job);
        } else {
            if (ApplicationConstant.isNull(this.nextHandler)) {
                System.err.println("===Mission End==");
            } else {
                this.nextHandler.handleJob(job);
            }
        }
    }

    protected abstract void response(UserJob job);
}

interface UserJob {

    public boolean run(RecommendConfiguration conf) throws Exception;

    public int getLevel();
}


/**
 * 步骤1 建立评分矩阵 Rating Matrix
 */
class Recommend_1_BuildRatingMatrix implements UserJob {


    public boolean run(RecommendConfiguration conf) throws Exception {

        Job job = Job.getInstance(conf.getConfiguration());

        job.setJarByClass(Recommend_1_BuildRatingMatrix.class);
        job.setJobName("Recommend_1_BuildRatingMatrix");

        FileInputFormat.setInputPaths(job, new Path(conf.getPaths().get("input")));
        FileOutputFormat.setOutputPath(job, new Path(conf.getPaths().get("step1OutPut")));

        job.setMapperClass(RatingMatrixMapper.class);
        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);

        job.setReducerClass(RatingMatrixReducer.class);
        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(Text.class);

        job.waitForCompletion(true);

        return job.isSuccessful();
    }

    private int level = 1;

    @Override
    public int getLevel() {
        return level;
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
class Recommend_2_BuildSourceMatrix implements UserJob {

    private int level = 1;

    @Override
    public int getLevel() {
        return level;
    }

    public boolean run(RecommendConfiguration conf) throws Exception {

        Job job = Job.getInstance(conf.getConfiguration());

        job.setJarByClass(Recommend_2_BuildSourceMatrix.class);
        job.setJobName("Recommend_2_BuildSourceMatrix");

        FileInputFormat.setInputPaths(job, new Path(conf.getPaths().get("step1OutPut")));
        FileOutputFormat.setOutputPath(job, new Path(conf.getPaths().get("step2OutPut")));

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
                        resultKey.set(key1 + "->" + key2);
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
class Recommend_3_RegroupMatrix implements UserJob {

    private int level = 3;

    @Override
    public int getLevel() {
        return level;
    }

    public boolean run(RecommendConfiguration conf) throws Exception {
        Job ratingJob = Job.getInstance(conf.getConfiguration());
        ratingJob.setJarByClass(Recommend_3_RegroupMatrix.class);
        ratingJob.setJobName("Recommend_3_RegroupMatrix");
        FileInputFormat.setInputPaths(ratingJob, new Path(conf.getPaths().get("step1OutPut")));
        FileOutputFormat.setOutputPath(ratingJob, new Path(conf.getPaths().get("step3OutPut") + "/rating"));
        ratingJob.setMapperClass(FormatRatingMapper.class);
        ratingJob.setMapOutputKeyClass(LongWritable.class);
        ratingJob.setMapOutputValueClass(Text.class);
        ratingJob.waitForCompletion(true);

        Job sourceJob = Job.getInstance(conf.getConfiguration());
        sourceJob.setJarByClass(Recommend_3_RegroupMatrix.class);
        sourceJob.setJobName("Recommend_3_RegroupMatrix");
        FileInputFormat.setInputPaths(sourceJob, new Path(conf.getPaths().get("step2OutPut")));
        FileOutputFormat.setOutputPath(sourceJob, new Path(conf.getPaths().get("step3OutPut") + "/source"));
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
                    /*System.err.println(data.toString());*/
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

/**
 * 步骤4 矩阵相乘
 */
class Recommend_4_MultipleMatrix implements UserJob {

    private int level = 4;

    @Override
    public int getLevel() {
        return level;
    }


    public boolean run(RecommendConfiguration conf) throws Exception {
        Job multiJob = Job.getInstance(conf.getConfiguration());
        multiJob.setJarByClass(Recommend_4_MultipleMatrix.class);
        multiJob.setJobName("Recommend_4_MultipleMatrix");
        FileInputFormat.setInputPaths(multiJob, new Path(conf.getPaths().get("step3OutPut") + "/rating"), new Path(conf.getPaths().get("step3OutPut") + "/source"));
        FileOutputFormat.setOutputPath(multiJob, new Path(conf.getPaths().get("step4OutPut")));
        multiJob.setMapperClass(MultiMapper.class);
        multiJob.setMapOutputKeyClass(LongWritable.class);
        multiJob.setMapOutputValueClass(Text.class);
        multiJob.setReducerClass(MultiReducer.class);
        multiJob.setOutputKeyClass(Text.class);
        multiJob.setOutputValueClass(Text.class);
        multiJob.waitForCompletion(true);

        Job sumJob = Job.getInstance(conf.getConfiguration());
        sumJob.setJarByClass(Recommend_4_MultipleMatrix.class);
        sumJob.setJobName("Recommend_4_MultipleMatrix");
        FileInputFormat.setInputPaths(sumJob, new Path(conf.getPaths().get("step4OutPut")));
        FileOutputFormat.setOutputPath(sumJob, new Path(conf.getPaths().get("filter_output")));
        sumJob.setMapperClass(SumMapper.class);
        sumJob.setMapOutputKeyClass(Text.class);
        sumJob.setMapOutputValueClass(Text.class);
        sumJob.setReducerClass(SumReducer.class);
        sumJob.setOutputKeyClass(Text.class);
        sumJob.setOutputValueClass(Text.class);
        sumJob.waitForCompletion(true);

        return multiJob.isSuccessful() && sumJob.isSuccessful();
    }


    public static class MultiMapper extends Mapper<LongWritable, Text, LongWritable, Text> {

        private String fileType;
        private LongWritable resultKey = new LongWritable();
        private Text resultValue = new Text();

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            FileSplit fileSplit = (FileSplit) context.getInputSplit();
            fileType = fileSplit.getPath().getParent().getName();
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String[] tokens = RecommendDriver.DELIMITER.split(value.toString());

            if (fileType.startsWith("source")) {
                //同源
                String[] data = tokens[0].split("->");
                String itemId1 = data[0];
                String itemId2 = data[1];
                String times = tokens[1];

                resultKey.set(Long.parseLong(itemId1));
                resultValue.set("A>>" + itemId2 + "->" + times);
                context.write(resultKey, resultValue);
            }

            if (fileType.startsWith("rating")) {
                //评分
                String[] data = tokens[1].split("->");
                String itemId = tokens[0];
                String userId = data[0];
                String rating = data[1];
                resultKey.set(Long.parseLong(itemId));
                resultValue.set("B>>" + userId + "->" + rating);
                context.write(resultKey, resultValue);
            }
        }

    }

    public static class MultiReducer extends Reducer<LongWritable, Text, Text, Text> {

        private Text resultKey = new Text();
        private Text resultValue = new Text();

        @Override
        protected void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            //同源
            HashMap<String, String> matrixMapA = new HashMap<>();
            //评分
            HashMap<String, String> matrixMapB = new HashMap<>();

            for (Text text : values) {
                String value = text.toString();
                if (value.startsWith("A>>")) {
                    String[] kv = value.substring(3).split("->");
                    matrixMapA.put(kv[0], kv[1]);
                } else if (value.startsWith("B>>")) {
                    String[] kv = value.substring(3).split("->");
                    matrixMapB.put(kv[0], kv[1]);
                } else {
                    context.getCounter("MultiReducer", "SkipLine").increment(1L);
                }
            }

            double result;
           /* Iterator<String> stringIteratorA = matrixMapA.keySet().iterator();
            while (stringIteratorA.hasNext()) {
                String itemId = stringIteratorA.next();
                int num = Integer.parseInt(matrixMapA.get(itemId));
                Iterator<String> stringIteratorB = matrixMapB.keySet().iterator();
                while (stringIteratorB.hasNext()) {
                    String userId = stringIteratorB.next();
                    double rating = Double.parseDouble(matrixMapB.get(userId));
                    result = num * rating;
                    resultKey.set(userId);
                    resultValue.set(itemId + "->" + result);
                    context.write(resultKey, resultValue);
                }
            }*/

            for (String itemId : matrixMapA.keySet()) {
                int num = Integer.parseInt(matrixMapA.get(itemId));
                for (String userId : matrixMapB.keySet()) {
                    double rating = Double.parseDouble(matrixMapB.get(userId));
                    result = num * rating;
                    resultKey.set(userId);
                    resultValue.set(itemId + "->" + result);
                    context.write(resultKey, resultValue);
                }
            }
        }
    }

    public static class SumMapper extends Mapper<LongWritable, Text, Text, Text> {

        private Text resultKey = new Text();
        private Text resultValue = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] tokens = RecommendDriver.DELIMITER.split(value.toString());
            resultKey.set(tokens[0]);
            resultValue.set(tokens[1]);
            context.write(resultKey, resultValue);
        }
    }

    public static class SumReducer extends Reducer<Text, Text, Text, Text> {
        private Text resultValue = new Text();

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

            Map<String, Double> doubleMap = new HashMap<>();
            for (Text line : values) {
                String[] tokens = line.toString().split("->");
                String itemId = tokens[0];
                double score = Double.parseDouble(tokens[1]);
                if (!doubleMap.containsKey(itemId)) {
                    doubleMap.put(itemId, 0.0);
                }
                doubleMap.put(itemId, doubleMap.get(itemId) + score);
            }
            for (String itemId : doubleMap.keySet()) {
                double score = doubleMap.get(itemId);
                resultValue.set(itemId + "->" + score);
                context.write(key, resultValue);
            }
        }
    }

}

class Recommend_5_FilterResult implements UserJob {

    @Override
    public boolean run(RecommendConfiguration conf) throws Exception {

        Job fitlerJob = Job.getInstance(conf.getConfiguration());
        fitlerJob.setJarByClass(Recommend_4_MultipleMatrix.class);
        fitlerJob.setJobName("Recommend_4_MultipleMatrix");
        FileInputFormat.setInputPaths(fitlerJob, new Path(conf.getPaths().get("filter_output")), new Path(conf.getPaths().get("step1OutPut")));
        FileOutputFormat.setOutputPath(fitlerJob, new Path(conf.getPaths().get("output")));
        fitlerJob.setMapperClass(FilterMapper.class);
        fitlerJob.setMapOutputKeyClass(Text.class);
        fitlerJob.setMapOutputValueClass(Text.class);
        fitlerJob.setReducerClass(FilterReducer.class);
        fitlerJob.setOutputKeyClass(Text.class);
        fitlerJob.setOutputValueClass(Text.class);
        fitlerJob.waitForCompletion(true);
        return fitlerJob.isSuccessful();

    }

    public static class FilterMapper extends Mapper<LongWritable, Text, Text, Text> {
        private Text resultKey = new Text();
        private Text resultValue = new Text();
        private String fileType;

        /**
         * 有两个输入路径
         * 一个是评分矩阵，一个是第四步得出来的推荐矩阵
         */
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            FileSplit fileSplit = (FileSplit) context.getInputSplit();
            fileType = fileSplit.getPath().getParent().getName();
        }

        /**
         * 输出数据将以 itemId -> userId 为key
         * ratingScore 和 recommendScore为value
         * 在Reduce中，把ratingScore不为0的去掉
         */
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] token = RecommendDriver.DELIMITER.split(value.toString());
            String userId = token[0];
            if (fileType.startsWith("s1o")) {
                for (int i = 1; i < token.length; i++) {
                    String[] data = token[i].split("->");
                    String itemId = data[0];
                    String rating = data[1];
                    resultKey.set(itemId + "->" + userId);
                    resultValue.set("Rating>>" + rating);
                    context.write(resultKey, resultValue);
                }
            } else if (fileType.startsWith("filter_output")) {
                String[] data = token[1].split("->");
                String itemId = data[0];
                String score = data[1];
                resultKey.set(itemId + "->" + userId);
                resultValue.set("Score>>" + score);
                context.write(resultKey, resultValue);
            } else {
                context.getCounter("FilterMapper", "SkipLine").increment(1L);
            }


        }
    }

    public static class FilterReducer extends Reducer<Text, Text, Text, Text> {
        private Text resultKey = new Text();
        private Text resultValue = new Text();

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            String[] token = key.toString().split("->");
            String itemId = token[0];
            String userId = token[1];
            Double score = 0.0;
            boolean isEnabled = false;
            for (Text text : values) {
                String line = text.toString();
                if (line.startsWith("Score>>")) {
                    score = Double.parseDouble(line.substring(7));
                    isEnabled = true;
                } else if (line.startsWith("Rating>>")) {
                    Double rating = Double.parseDouble(line.substring(8));
                    if (rating > 0) {
                        isEnabled = false;
                        break;
                    }
                }
            }
            if (isEnabled) {
                resultKey.set(userId);
                resultValue.set(itemId + "\t" + score);
                context.write(resultKey, resultValue);
            }
        }
    }

    private int level = 5;

    @Override
    public int getLevel() {
        return level;
    }
}