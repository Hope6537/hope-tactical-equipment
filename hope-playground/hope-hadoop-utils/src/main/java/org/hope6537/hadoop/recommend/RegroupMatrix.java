package org.hope6537.hadoop.recommend;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by Hope6537 on 2015/4/5.
 */
public class RegroupMatrix {

    private int level = 3;

    public int getLevel() {
        return level;
    }

    public boolean run(RecommendConfiguration conf) throws Exception {
        Job ratingJob = Job.getInstance(conf.getConfiguration());
        ratingJob.setJarByClass(RegroupMatrix.class);
        ratingJob.setJobName("RegroupMatrix");
        FileInputFormat.setInputPaths(ratingJob, new Path(conf.getPaths().get("step1OutPut")));
        FileOutputFormat.setOutputPath(ratingJob, new Path(conf.getPaths().get("step3OutPut") + "/rating"));
        ratingJob.setMapperClass(FormatRatingMapper.class);
        ratingJob.setMapOutputKeyClass(LongWritable.class);
        ratingJob.setMapOutputValueClass(Text.class);
        ratingJob.waitForCompletion(true);

        Job sourceJob = Job.getInstance(conf.getConfiguration());
        sourceJob.setJarByClass(RegroupMatrix.class);
        sourceJob.setJobName("RegroupMatrix");
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
