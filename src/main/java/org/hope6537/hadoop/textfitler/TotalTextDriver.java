package org.hope6537.hadoop.textfitler;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
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
public class TotalTextDriver extends Configured implements Tool {
    public static void main(String[] args) throws Exception {
        int res = new TotalTextDriver().run(args);
        System.err.println(res);
    }
    /*
     * yarn-site.xml
     * <property>
     <name>yarn.nodemanager.resource.memory-mb</name>
     <value>10000</value>
     </property>
     <property>
     <name>yarn.scheduler.maximum-allocation-mb</name>
     <value>10000</value>
     </property>
     <property>
     <name>yarn.scheduler.minimum-allocation-mb</name>
     <value>3000</value>
     </property>
     <property>
     <name>mapreduce.reduce.memory.mb</name>
     <value>3000</value>
     </property>
     <property>
     <name>mapreduce.map.memory.mb</name>
     <value>3000</value>
     </property>
     */

    @Override
    public int run(String[] args) throws Exception {
        Configuration configuration = ConfigurationFactory.getConfigurationOfPseudoDistributed();
        //设置分片大小上限
        configuration.setLong("mapreduce.input.fileinputformat.split.maxsize", 2000000);
        //如果输出目录已经存在了，那么将其删除
        HdfsUtils hdfsUtils = HdfsUtils.getInstanceOfPseudoDistributed(configuration);
        hdfsUtils.rmrShowInConsole(args[1]);
        hdfsUtils.closeFileSystem();

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
            return 1;
        }

        FileOutputFormat.setOutputPath(job, output);

        return job.waitForCompletion(true) ? 0 : 1;
    }
}

class TotalTextTokenizerMapper extends Mapper<Text, Text, Text, Text> {

    private Text resultKey = new Text();
    private Text resultValue = new Text();

    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        resultKey.set(key);
        String line = value.toString();
        StringReader sr = new StringReader(line);
        StringBuilder stringBuilder = new StringBuilder();
        Analyzer analyzer = new IKAnalyzer();
        try {
            TokenStream ts = analyzer.tokenStream("", sr);
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
        analyzer.close();
        analyzer = null;
        resultValue.set(stringBuilder.toString());
        context.write(resultKey, resultValue);
    }
}
