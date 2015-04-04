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
import java.util.Map;

public class AdjacencyMatrix {


    public int run(Map<String, String> jobPathMap) throws Exception {

        Job job = Job.getInstance();
        job.setJarByClass(AdjacencyMatrix.class);

        job.setMapperClass(StepMapper.class);
        job.setReducerClass(StepReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, new Path(jobPathMap.get("input_people")));
        FileOutputFormat.setOutputPath(job, new Path(jobPathMap.get("tmp1")));

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
            float[] G = new float[Driver.nums];// 概率矩阵列
            Arrays.fill(G, (float) (1 - Driver.d) / G.length);

            float[] A = new float[Driver.nums];// 近邻矩阵列
            int sum = 0;// 链出数量
            for (Text val : values) {
                int idx = Integer.parseInt(val.toString());
                A[idx - 1] = 1;
                sum++;
            }

            if (sum == 0) {// 分母不能为0
                sum = 1;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < A.length; i++) {
                sb.append(",").append((float) (G[i] + Driver.d * A[i] / sum));
            }

            Text v = new Text(sb.toString().substring(1));
            System.out.println(key + ":" + v.toString());
            context.write(key, v);
        }
    }


}
