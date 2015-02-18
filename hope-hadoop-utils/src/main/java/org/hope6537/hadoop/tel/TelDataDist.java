package org.hope6537.hadoop.tel;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.hope6537.date.DateFormatCalculate;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Hope6537 on 2015/1/30.
 */
public class TelDataDist {

    public static void main(String[] args) throws Exception {

        Job job = Job.getInstance(new Configuration());

        job.setJarByClass(TelDataDist.class);

        job.setMapperClass(TelMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(DataBean.class);
        FileInputFormat.setInputPaths(job, new Path(args[0] == null ? "/test.dat" : args[0]));

        job.setReducerClass(TelReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DataBean.class);
        FileOutputFormat.setOutputPath(job, new Path(args[1] == null ? "/" + DateFormatCalculate.createNowTime() + "output" : args[1]));

        job.waitForCompletion(true);


    }

    public static class TelMapper extends Mapper<LongWritable, Text, Text, DataBean> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            System.out.println(line);
            String[] datas = line.split("\t");
            System.out.println(Arrays.toString(datas));
            String tel = datas[1];
            System.out.println(tel);
            String uploadData = datas[8];
            System.out.println(uploadData);
            String downloadData = datas[9];
            System.out.println(downloadData);
            context.write(new Text(tel), new DataBean(tel, uploadData, downloadData));
        }
    }

    public static class TelReducer extends Reducer<Text, DataBean, Text, DataBean> {
        @Override
        protected void reduce(Text key, Iterable<DataBean> values, Context context) throws IOException, InterruptedException {
            Long uploadTotal = 0L;
            Long downloadTotal = 0L;
            for (DataBean dataBean : values) {
                uploadTotal += dataBean.getUploadData();
                downloadTotal += dataBean.getDownloadData();
            }
            context.write(key, new DataBean(key.toString(), uploadTotal, downloadTotal));
        }
    }

}
