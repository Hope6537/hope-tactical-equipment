package org.hope6537.hadoop.textfitler;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;

import java.io.IOException;

/**
 * Created by Hope6537 on 2015/2/18.
 */
public class TotalTextRecordReader extends RecordReader {

    /**
     * 当前处理的分片
     */
    private CombineFileSplit combineFileSplit;
    /**
     * 这个分片所包含的Text数量
     */
    private int totalLength;
    /**
     * 当前处理的文件的索引
     */
    private int currentIndex;
    /**
     * 当前处理进度
     */
    private float currentProgress = 0;
    /**
     * 当前文章的类别Text
     */
    private Text currentKey = new Text();
    /**
     * 当前文章的全文Text
     */
    private Text currentValue = new Text();
    /**
     * 配置信息
     */
    private Configuration configuration;
    /**
     * 当前文件是否已经被读取
     */
    private boolean isFinished;

    /**
     * @param combineFileSplit 待处理的分片器
     * @param context          保存任务和系统信息
     * @param currentIndex     当前文件在Split中的索引
     */
    public TotalTextRecordReader(CombineFileSplit combineFileSplit, TaskAttemptContext context, int currentIndex) {
        this.combineFileSplit = combineFileSplit;
        this.currentIndex = currentIndex;
        this.configuration = context.getConfiguration();
        this.totalLength = combineFileSplit.getPaths().length;
        this.isFinished = false;
    }

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {

    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        if (!isFinished) {
            //查看当前文件是否已经被处理
            //如果未处理那么转换为key-value
            Path file = combineFileSplit.getPath(currentIndex);
            //通过文件夹的所属名称得到类别
            currentKey.set(file.getParent().getName());
            FSDataInputStream fsDataInputStream = null;
            //将通过文件系统得到文件的整个字节流
            byte[] contents = new byte[(int) combineFileSplit.getLength(currentIndex)];
            try {
                FileSystem fileSystem = file.getFileSystem(configuration);
                fsDataInputStream = fileSystem.open(file);
                fsDataInputStream.readFully(contents);
                currentValue.set(contents);
            } catch (Exception e) {

            } finally {
                fsDataInputStream.close();
            }
            //读取完成返回true，将转换到下个key-value对
            isFinished = true;
            return true;
        }
        //如果都完成了就结束
        return false;
    }

    @Override
    public Object getCurrentKey() throws IOException, InterruptedException {
        return currentKey;
    }

    @Override
    public Object getCurrentValue() throws IOException, InterruptedException {
        return currentValue;
    }

    @Override
    public float getProgress() throws IOException, InterruptedException {
        if (currentIndex >= 0 && currentIndex < totalLength) {
            currentProgress = (float) currentIndex / totalLength;
            return currentProgress;
        }
        return currentProgress;
    }

    @Override
    public void close() throws IOException {
        System.err.println("-Bye!-");
    }
}
