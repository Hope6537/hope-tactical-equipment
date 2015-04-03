package org.hope6537.hadoop.peoplerank;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.hope6537.hadoop.ConfigurationFactory;
import org.hope6537.hadoop.hdfs.HdfsUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Hope6537 on 2015/3/31.
 */
public class Driver extends Configured implements Tool {

    public static void main(String[] args) throws Exception {
        ToolRunner.run(new Driver(), args);
        System.exit(0);
    }


    @Override
    public int run(String[] args) throws Exception {

        final String input = args[0];
        final String output = args[1];

        System.err.println("the input path is " + input);
        System.err.println("the output path is " + input);

        Map<Integer, String> jobPathMap = new ConcurrentHashMap<>();
        jobPathMap.put(1, input + "/pr ");
        jobPathMap.put(2, input + "/adj");
        jobPathMap.put(3, input + "/foreach");
        jobPathMap.put(4, output);


        HdfsUtils hdfsUtils = HdfsUtils.getInstanceOfPseudoDistributed(ConfigurationFactory.getConfigurationOfPseudoDistributed());
        jobPathMap.forEach((key, value) -> hdfsUtils.rmr(value));

        return new AdjacencyMatrix().run(input, jobPathMap.get(1));

    }
}
