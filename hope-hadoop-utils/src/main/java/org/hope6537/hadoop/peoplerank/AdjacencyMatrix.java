package org.hope6537.hadoop.peoplerank;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.hope6537.context.ApplicationConstant;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Hope6537 on 2015/3/31.
 */
public class AdjacencyMatrix {

    public static final int nums = 25;
    public static final double d = 0.85;


    public int run(final String input, final String output) throws Exception {

        Job job = Job.getInstance();
        job.setJarByClass(AdjacencyMatrix.class);

        job.setMapperClass(StepMapper.class);
        job.setReducerClass(StepReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, new Path(input));
        FileOutputFormat.setOutputPath(job, new Path(output));

        return job.waitForCompletion(true) ? 0 : 1;
    }

    private static class StepMapper extends Mapper<LongWritable, Text, Text, Text> {

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            System.err.println(value.toString());
            String[] tokens = ApplicationConstant.DELIMITER.split(value.toString());
            Text resultKey = new Text(tokens[0]);
            Text resultValue = new Text(tokens[1]);
            context.write(resultKey, resultValue);
        }
    }

    private static class StepReducer extends Reducer<Text, Text, Text, Text> {

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            float[] G = new float[nums];
            Arrays.fill(G, (float) (1 - d));
            float[] A = new float[nums];
            int sum = 0;
            for (Text value : values) {
                int index = Integer.parseInt(value.toString());
                A[index - 1] = 1;
                sum++;
            }

            if (sum == 0) {
                sum = 1;
            }

            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < nums; i++) {
                buffer.append(",").append(G[i] + d * A[i] / sum);
            }
            Text value = new Text(buffer.toString().substring(1));
            System.err.println(key + "->" + value);
            context.write(key, value);
        }
    }


}
