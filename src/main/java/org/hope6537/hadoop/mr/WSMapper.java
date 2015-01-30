package org.hope6537.hadoop.mr;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by Hope6537 on 2015/1/30.
 */
public class WSMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String data = value.toString();
        String[] lines = data.split(" ");
        for (String str : lines) {
            context.write(new Text(str), new LongWritable(1));
        }
    }
}
