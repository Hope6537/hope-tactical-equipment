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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Hope6537 on 2015/4/6.
 */
public class NormalData {

    public int run(Map<String, String> jobPathMap) throws Exception {

        Job job = Job.getInstance();
        job.setJarByClass(NormalData.class);

        job.setMapperClass(StepMapper.class);
        job.setReducerClass(StepReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, new Path(jobPathMap.get("input_pr")));
        FileOutputFormat.setOutputPath(job, new Path(jobPathMap.get("result")));

        return job.waitForCompletion(true) ? 0 : 1;
    }

    private static class StepMapper extends Mapper<LongWritable, Text, Text, Text> {

        Text resultKey = new Text("1");

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            context.write(resultKey, value);
        }
    }

    private static class StepReducer extends Reducer<Text, Text, Text, Text> {

        private Text resultKey = new Text();
        private Text resultValue = new Text();

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            List<String> vList = new ArrayList<>();
            float sum = 0f;
            for (Text line : values) {
                vList.add(line.toString());
                String[] vals = ApplicationConstant.DELIMITER.split(line.toString());
                float f = Float.parseFloat(vals[1]);
                sum += f;
            }
            for (String line : vList) {
                String[] vals = ApplicationConstant.DELIMITER.split(line.toLowerCase());
                resultKey.set(vals[0]);
                resultValue.set(String.valueOf(Float.parseFloat(vals[1]) / sum));
                context.write(resultKey, resultValue);
            }
        }
    }
}
