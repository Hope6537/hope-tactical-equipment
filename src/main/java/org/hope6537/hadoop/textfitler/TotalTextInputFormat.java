package org.hope6537.hadoop.textfitler;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.CombineFileRecordReader;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;

import java.io.IOException;

/**
 * Created by Hope6537 on 2015/2/18.
 * 自定义输入格式
 * 让每个分片持有完整的文本文件
 * key -> 文件所属的类别名
 * value -> 文件所属的文本内容
 */
public class TotalTextInputFormat extends CombineFileInputFormat<Text, Text> {


    /**
     * 确保所有文件不可分割
     */
    @Override
    protected boolean isSplitable(JobContext context, Path file) {
        return false;
    }

    /**
     * 返回一个CombineFileRecordReader对象
     * CombineFileRecordReader的构造函数中，指定RecordReader
     */
    @Override
    public RecordReader<Text, Text> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException {
        CombineFileRecordReader<Text, Text> recordReader = new CombineFileRecordReader<Text, Text>(
                (CombineFileSplit) split, context, TotalTextRecordReader.class);
        return recordReader;
    }

}
