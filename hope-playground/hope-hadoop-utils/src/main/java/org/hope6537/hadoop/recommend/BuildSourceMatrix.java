package org.hope6537.hadoop.recommend;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by Hope6537 on 2015/4/5.
 */
public class BuildSourceMatrix {
    private int level = 1;

    public boolean run(RecommendConfiguration conf) throws Exception {

        Job job = Job.getInstance(conf.getConfiguration());

        job.setJarByClass(BuildSourceMatrix.class);
        job.setJobName("BuildSourceMatrix");

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
