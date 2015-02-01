package org.hope6537.hadoop.sort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.hope6537.context.ApplicationConstant;

import java.io.IOException;

/**
 * Created by Hope6537 on 2015/2/1.
 */
public class SortDriver {


    public static class SortMapper extends Mapper<LongWritable, Text, SortBean, NullWritable> {

        private SortBean sortBean = new SortBean();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            if (ApplicationConstant.notNull(line)) {
                String[] datas = line.split("\t");
                String username = datas[0];
                Long increase = Long.parseLong(datas[1]);
                Long decrease = Long.parseLong(datas[2]);
                String date = datas[3];
                SortBean.setBeanData(sortBean, username, increase, decrease, date);
                context.write(sortBean, NullWritable.get());
            }
        }
    }

    public static class SortReducer extends Reducer<SortBean, NullWritable, Text, SortBean> {

        private Text text = new Text();

        @Override
        protected void reduce(SortBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            String username = key.getUsername();
            text.set(username);
            context.write(text, key);
        }
    }


    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);
        job.setJarByClass(SortDriver.class);
        job.setMapperClass(SortMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(SortBean.class);
        FileInputFormat.setInputPaths(job, new Path(args[0]));

        job.setReducerClass(SortReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(SortBean.class);
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);

    }

}
