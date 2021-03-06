package org.hope6537.hadoop.sort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.hope6537.context.ApplicationConstant;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Hope6537 on 2015/2/1.
 */
public class SumDriver {


    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);
        job.setJarByClass(SumMapper.class);
        job.setMapperClass(SumMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(SortBean.class);
        FileInputFormat.setInputPaths(job, new Path(args[0]));

        job.setReducerClass(SumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(SortBean.class);
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);

        //SortDrvier.main(new String[]{args[1], args[2]});

    }

    public static class SumMapper extends Mapper<LongWritable, Text, Text, SortBean> {

        protected org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
        private SortBean sortBean = new SortBean();
        private Text keyText = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            if (ApplicationConstant.isNull(line)) {
                return;
            } else {
                String[] datas = line.split("\t");
                logger.info(Arrays.toString(datas));
                System.out.println(Arrays.toString(datas));
                String username = datas[0];
                Long increase = Long.parseLong(datas[1]);
                Long decrease = Long.parseLong(datas[2]);
                String date = datas[3];
                keyText.set(username + "->" + date);
                SortBean.setBeanData(sortBean, username, increase, decrease, date);
                context.write(keyText, sortBean);
            }
        }
    }

    public static class SumReducer extends Reducer<Text, SortBean, Text, SortBean> {

        private Text keyText = new Text();
        private SortBean total = new SortBean();

        @Override
        protected void reduce(Text key, Iterable<SortBean> values, Context context) throws IOException, InterruptedException {
            String keyString = key.toString();
            if (ApplicationConstant.isNull(keyString)) {
                return;
            } else {
                try {
                    String[] keys = keyString.split("->");
                    String username = keys[0];
                    String date = keys[1];
                    long totalIn = 0L;
                    long totalOut = 0L;
                    for (SortBean sortBean : values) {
                        totalIn += sortBean.getTheIncreaseNumber();
                        totalOut += sortBean.getTheDecreaseNumber();
                    }
                    SortBean.setBeanData(total, username, totalIn, totalOut, date);
                    keyText.set(username);
                    context.write(key, total);
                } catch (Exception e) {
                    return;
                }

            }


        }
    }


}
