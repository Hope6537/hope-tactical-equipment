package org.hope6537.hadoop.recommend;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hope6537 on 2015/4/5.
 */
public class MultipleMatrix {

    private int level = 4;

    public int getLevel() {
        return level;
    }


    public boolean run(RecommendConfiguration conf) throws Exception {
        Job multiJob = Job.getInstance(conf.getConfiguration());
        multiJob.setJarByClass(MultipleMatrix.class);
        multiJob.setJobName("MultipleMatrix");
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
        sumJob.setJarByClass(MultipleMatrix.class);
        sumJob.setJobName("MultipleMatrix");
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
