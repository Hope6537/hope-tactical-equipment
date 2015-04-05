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

/**
 * Created by Hope6537 on 2015/4/5.
 */
public class FilterResult {


    private int level = 5;

    public boolean run(RecommendConfiguration conf) throws Exception {

        Job fitlerJob = Job.getInstance(conf.getConfiguration());
        fitlerJob.setJarByClass(FilterResult.class);
        fitlerJob.setJobName("FilterResult");
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

    public int getLevel() {
        return level;
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
}
