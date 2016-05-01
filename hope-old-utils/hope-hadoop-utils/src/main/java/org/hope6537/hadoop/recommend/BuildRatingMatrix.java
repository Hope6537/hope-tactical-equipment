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
public class BuildRatingMatrix {

    private int level = 1;

    public boolean run(RecommendConfiguration conf) throws Exception {

        Job job = Job.getInstance(conf.getConfiguration());

        job.setJarByClass(BuildRatingMatrix.class);
        job.setJobName("BuildRatingMatrix");

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

