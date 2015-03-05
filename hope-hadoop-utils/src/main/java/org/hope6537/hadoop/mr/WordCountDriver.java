package org.hope6537.hadoop.mr;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.hope6537.hadoop.ConfigurationFactory;

import java.io.IOException;

/**
 * Created by Hope6537 on 2015/2/19.
 */
public class WordCountDriver {

    public static void main(String[] args) throws Exception {
        System.exit(new WordCountDriver().run(args));
    }

    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(ConfigurationFactory.getConfigurationOfPseudoDistributed());
        job.setJarByClass(WordCountDriver.class);
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);
        job.setCombinerClass(WordCountCombiner.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String data = value.toString();
            String[] lines = data.split(" ");
            for (String str : lines) {
                context.write(new Text(str), new LongWritable(1));
            }
        }
    }

    public static class WordCountCombiner extends Reducer<Text, LongWritable, Text, LongWritable> {

        @Override
        protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {

            long counter = 0;
            for (LongWritable longWritable : values) {
                counter += longWritable.get();
            }
            context.write(key, new LongWritable(counter));

        }
    }


    public static class WordCountReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

        @Override
        protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
            long counter = 0;
            for (LongWritable longWritable : values) {
                counter += longWritable.get();
            }
            context.write(key, new LongWritable(counter));
        }
    }
}