package org.hope6537.hadoop.textfitler;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.hope6537.hadoop.ConfigurationFactory;
import org.hope6537.hadoop.hdfs.HdfsUtils;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by Hope6537 on 2015/2/18.
 */
public class TotalTextDriver {
    public static void main(String[] args) throws Exception {
        Configuration configuration = ConfigurationFactory.getConfigurationOfPseudoDistributed();
        //设置分片大小上限
        configuration.setLong("mapreduce.input.fileinputformat.split.maxsize", 400000);

        Job job = Job.getInstance(configuration, "TotalTextTokenizer");
        job.setJarByClass(TotalTextDriver.class);

        job.setInputFormatClass(TotalTextInputFormat.class);
        job.setMapperClass(TotalTextTokenizerMapper.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        //但是要注意的是我们的输入路径是一个二级目录顶，所以我们需要迭代深化进每个目录，给到InputFormat中
        Path input = new Path(args[0]);
        Path output = new Path(args[1]);

        try {
            FileSystem fs = input.getFileSystem(configuration);
            FileStatus[] stats = fs.listStatus(input);
            for (FileStatus subPath : stats) {
                /**
                 * 这一步有疑惑，为何不直接用addInputPaths
                 */
                FileInputFormat.addInputPath(job, subPath.getPath());
            }
        } catch (Exception e) {
            System.err.println("Bulid Path failed Stop Job");
            return;
        }

        FileOutputFormat.setOutputPath(job, output);

        //如果输出目录已经存在了，那么将其删除

        HdfsUtils hdfsUtils = HdfsUtils.getInstanceOfPseudoDistributed(configuration);
        hdfsUtils.rmrShowInConsole(args[1]);
        hdfsUtils.closeFileSystem();


        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }
}

class TotalTextTokenizerMapper extends Mapper<Text, Text, Text, Text> {

    private Text resultKey = new Text();
    private Text resultValue = new Text();
    private Analyzer analyzer = new IKAnalyzer();

    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        resultKey.set(key);

        String line = value.toString();
        StringReader sr = new StringReader(line);
        TokenStream ts = analyzer.tokenStream("", sr);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while (ts.incrementToken()) {
                /**
                 * 这个是干啥的？
                 */
                CharTermAttribute termAttribute = ts.getAttribute(CharTermAttribute.class);
                stringBuilder.append(termAttribute.toString());
                stringBuilder.append(" ");
            }
        } catch (Exception e) {
            context.getCounter("FailDocs", "SkipLine").increment(1L);
        }
        resultValue.set(stringBuilder.toString());
        context.write(resultKey, resultValue);
    }
}
