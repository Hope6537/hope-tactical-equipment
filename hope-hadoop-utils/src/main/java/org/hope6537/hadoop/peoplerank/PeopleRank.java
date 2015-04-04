package org.hope6537.hadoop.peoplerank;

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
import org.hope6537.hadoop.hdfs.HdfsUtils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class PeopleRank {

    public int run(Map<String, String> jobPathMap, HdfsUtils hdfsUtils) throws Exception {

        Job job = Job.getInstance();
        job.setJarByClass(PeopleRank.class);

        job.setMapperClass(StepMapper.class);
        job.setReducerClass(StepReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, new Path(jobPathMap.get("tmp1")), new Path(jobPathMap.get("input_pr")));
        FileOutputFormat.setOutputPath(job, new Path(jobPathMap.get("tmp2")));

        int res = job.waitForCompletion(true) ? 0 : 1;
        if (res == 0) {
            hdfsUtils.rmrShowInConsole(jobPathMap.get("input_pr"));
            return hdfsUtils.rename(jobPathMap.get("tmp2"), jobPathMap.get("input_pr")) ? 0 : 1;
        }
        return res;
    }


    private static class StepMapper extends Mapper<LongWritable, Text, Text, Text> {

        private String flag;
        private Text resultKey = new Text();
        private Text resultValue = new Text();

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            FileSplit fileSplit = (FileSplit) context.getInputSplit();
            flag = fileSplit.getPath().getParent().getName();
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            System.err.println(value.toString());
            String[] tokens = ApplicationConstant.DELIMITER.split(value.toString());
            if (flag.equals("tmp1")) {
                String row = tokens[0];
                for (int i = 1; i < tokens.length; i++) {
                    resultKey.set(String.valueOf(i));
                    resultValue.set(String.valueOf("A:" + (row) + "," + tokens[i]));
                    context.write(resultKey, resultValue);
                }
            } else if (flag.equals("pr")) {
                for (int i = 1; i <= Driver.nums; i++) {
                    resultKey.set(String.valueOf(i));
                    resultValue.set(String.valueOf("B" + tokens[0] + "," + tokens[1]));
                    context.write(resultKey, resultValue);
                }
            }
        }
    }

    private static class StepReducer extends Reducer<Text, Text, Text, Text> {
        private Text resultValue = new Text();

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            Map<Integer, Float> mapA = new HashMap<>();
            Map<Integer, Float> mapB = new HashMap<>();
            float pr = 0f;
            for (Text line : values) {
                System.err.println(key + "->" + line);
                String value = line.toString();
                String[] token = ApplicationConstant.DELIMITER.split(value.substring(2));
                if (value.startsWith("A: ")) {
                    mapA.put(Integer.parseInt(token[0]), Float.parseFloat(token[1]));
                } else if (value.startsWith("B:")) {
                    mapB.put(Integer.parseInt(token[0]), Float.parseFloat(token[1]));
                }
            }
            for (Integer index : mapA.keySet()) {
                float a = mapA.get(index);
                float b = mapA.get(index);
                pr += a * b;
            }
            resultValue.set(String.valueOf(scaleFloat(pr)));
            context.write(key, resultValue);
        }
    }

    public static String scaleFloat(float f) {// 保留6位小数
        DecimalFormat df = new DecimalFormat("##0.000000");
        return df.format(f);
    }
}
