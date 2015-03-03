package org.hope6537.hadoop.textfitler;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by Hope6537 on 2015/3/3.
 */
public class CastSeqFileDriver {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("需要两个参数");
            System.err.println("-input 指定文件输入");
            System.err.println("-out 指定文件输出");
        }
    }
}

class CastSeqFileMapper extends Mapper<LongWritable, Text, Text, Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

    }
}
