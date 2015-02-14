package org.hope6537.hadoop.inverseindex;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.hope6537.context.ApplicationConstant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 倒排索引驱动
 * 源自于WordCount
 * 源数据为
 * a.txt
 * Hello World
 * Hello Tom
 * Hello Jerry
 * b.txt
 * Hello Hope
 * Zhao Peng
 * Blog Message
 */

public class InverseIndexDriver {

    public static void main(String[] args) {

        Configuration configuration = new Configuration();

        Job countJob = null;
        Job indexJob = null;

        try {
            countJob = Job.getInstance(configuration);
            indexJob = Job.getInstance(configuration);

            countJob.setJarByClass(InverseIndexDriver.class);
            indexJob.setJarByClass(InverseIndexDriver.class);

            countJob.setMapperClass(CountMapper.class);
            countJob.setMapOutputKeyClass(Text.class);
            countJob.setMapOutputValueClass(LongWritable.class);
            FileInputFormat.setInputPaths(countJob, new Path(args[0]));

            countJob.setReducerClass(CountReducer.class);
            countJob.setOutputKeyClass(Text.class);
            countJob.setOutputKeyClass(LongWritable.class);
            FileOutputFormat.setOutputPath(countJob, new Path(args[1]));

            indexJob.setMapperClass(IndexMapper.class);
            indexJob.setMapOutputKeyClass(Text.class);
            indexJob.setMapOutputValueClass(Text.class);
            FileInputFormat.setInputPaths(indexJob, new Path(args[1]));

            indexJob.setReducerClass(IndexReducer.class);
            indexJob.setOutputKeyClass(Text.class);
            indexJob.setOutputValueClass(Text.class);
            FileOutputFormat.setOutputPath(indexJob, new Path(args[2]));

            boolean res = countJob.waitForCompletion(true);
            if (res) {
                indexJob.waitForCompletion(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 1、计数Mapper
     * Hello-->a.txt 1
     * Hello-->a.txt 1
     * Hello-->a.txt 1
     * Hello-->b.txt 1
     */
    private static class CountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

        private Text keyText = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            FileSplit split = (FileSplit) context.getInputSplit();
            String[] words = null;
            String path = null;
            Counter counter = context.getCounter("exceptionCounter", "arrayIndexOutOfBounds");
            Counter counterNull = context.getCounter("exceptionEmpty", "StringIsEmpty");
            try {
                path = split.getPath().toString().split(":")[2].substring(4);
                words = line.split(" ");
            } catch (ArrayIndexOutOfBoundsException e) {
                counter.increment(1L);
                return;
            }
            for (String str : words) {
                if (ApplicationConstant.isNull(str)) {
                    counterNull.increment(1L);
                    return;
                }
                keyText.set(str + "->" + path);
                context.write(keyText, new LongWritable(1));
            }
        }
    }

    /**
     * 2、计数Reducer
     * Hello-->a.txt 3
     * Hello-->b.txt 2
     */

    private static class CountReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
        @Override
        protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
            Long counter = 0L;
            for (LongWritable longWritable : values) {
                counter += longWritable.get();
            }
            context.write(key, new LongWritable(counter));
        }
    }

    /**
     * 3、规约Map 将路径对应到数量中
     * Hello a.txt->3
     * Hello b.txt->2
     */
    private static class IndexMapper extends Mapper<LongWritable, Text, Text, Text> {

        private Text keyText = new Text();
        private Text valueText = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] namePathAndCount = value.toString().split("\t");
            String[] nameAndPath = namePathAndCount[0].split("->");
            String count;
            String wordName;
            String newValue;
            Counter counter = context.getCounter("exceptionIndex", "arrayIndexOutOfBounds");
            try {
                count = namePathAndCount[1];
                wordName = nameAndPath[0];
                newValue = nameAndPath[1] + "->" + count;
            } catch (ArrayIndexOutOfBoundsException e) {
                counter.increment(1L);
                return;
            }
            keyText.set(wordName);
            valueText.set(newValue);
            context.write(keyText, valueText);
        }
    }

    /**
     * Hello {a.txt->3,b.txt->2}
     */
    private static class IndexReducer extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            List<String> valueList = new ArrayList<>();
            for (Text text : values) {
                valueList.add(text.toString());
            }
            context.write(key, new Text(valueList.toString()));
        }
    }
}
