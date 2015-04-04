package org.hope6537.hadoop.peoplerank;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.hope6537.hadoop.ConfigurationFactory;
import org.hope6537.hadoop.hdfs.HdfsUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Driver extends Configured implements Tool {

    public static final int nums = 25;
    public static final double d = 0.85;

    public static void main(String[] args) throws Exception {
        ToolRunner.run(ConfigurationFactory.getConfigurationOfPseudoDistributed(), new Driver(), args);
        System.exit(0);
    }


    @Override
    public int run(String[] args) throws Exception {
        int step1 = 0;
        getConf().set("io.compression.codecs", "org.apache.hadoop.io.compress.DefaultCodec,org.apache.hadoop.io.compress.GzipCodec,com.hadoop.compression.lzo.LzopCodec");
        getConf().set("io.compression.codec.lzo.class", "com.hadoop.compression.lzo.LzoCodec");
        if (args.length < 2) {
            System.err.println("参数错误");
            System.err.println("确认输入和输出路径");
            return 0;
        }
        HdfsUtils hdfsUtils = HdfsUtils.getInstanceOfPseudoDistributed(ConfigurationFactory.getConfigurationOfPseudoDistributed());
        final String input = args[0];
        final String output = args[1];
        System.err.println("the input path is " + input);
        System.err.println("the output path is " + output);

        Map<String, String> jobPathMap = new ConcurrentHashMap<>();
        jobPathMap.put("input", input);
        jobPathMap.put("input_people", input + "/people");
        jobPathMap.put("input_pr", input + "/pr");
        jobPathMap.put("tmp1", input + "/tmp1");
        jobPathMap.put("tmp2", input + "/tmp2");
        jobPathMap.put("result", output);
        System.err.println("remove directories");
        jobPathMap.keySet().stream().
                filter(key -> !(key.startsWith("input") || key.startsWith("tmp1"))).forEach(key -> hdfsUtils.rmrShowInConsole(jobPathMap.get(key)));
        hdfsUtils.closeFileSystem();
        //int step1 = new AdjacencyMatrix().run(jobPathMap);
        if (step1 == 0) {
            int step2 = new PeopleRank().run(jobPathMap);
            return step2;
        }
        return 1;
    }
}
