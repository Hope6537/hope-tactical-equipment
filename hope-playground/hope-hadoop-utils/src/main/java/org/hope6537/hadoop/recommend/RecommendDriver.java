package org.hope6537.hadoop.recommend;

import org.apache.hadoop.conf.Configuration;
import org.hope6537.hadoop.ConfigurationFactory;
import org.hope6537.hadoop.hdfs.HdfsUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

interface UserJob {

    public boolean run(RecommendConfiguration conf) throws Exception;

    public int getLevel();
}

/**
 * Created by Hope6537 on 2015/2/14.
 * 网站评分推荐MapReduce实现
 * <pre>
 * ===============================================================
 * 设计一下思路
 * 1、将元数据转化为同源矩阵 即一个用户存在[选择101、102]值对后，该矩阵的内容+1
 * /   101 102 103
 * 101  5   2   2
 * 102  2   5   3
 * 103  2   3   4
 * 实际是我们可以看出这个所谓的同源矩阵就是个三角矩阵
 * 具体的我们先这么算，等到成功后我们再优化三角矩阵
 * 2、然后我们得出得分矩阵
 * 对于User u1 他对于所有产品的打分
 * [user u1]
 * 101 2.5
 * 102 0.0
 * 103 4.0
 * 实际上这个矩阵很好理解
 * 3、然后我们对于User u1 我们将同源矩阵乘以他的评分矩阵
 * 将会获得一个 n*1的矩阵
 * 上面的得数，就是对于user u1的推荐值
 * 去掉评分矩阵中不为零的数据，也就是他已经看过的东西，最后按照得数进行排序返回推荐
 * =================================================================
 * MapReduce阶段将分为4个步骤
 * 步骤1、按照User进行Group by 来计算矩阵单位[k1,k2]出现的频数，同时计算用户的评分矩阵
 * 步骤2、对第一步产生的矩阵单位频数进行计数，并从中得出同源矩阵
 * 步骤3、合并同源矩阵和评分矩阵(重點)
 * 步骤4、得出结果并筛选
 * </pre>
 */
public class RecommendDriver {

    public static final Pattern DELIMITER = Pattern.compile("[\t,]");

    public static void main(String[] args) throws Exception {
        HashMap<String, String> paths = new HashMap<>();
        Configuration configuration = ConfigurationFactory.getConfigurationOfPseudoDistributed();
        //configuration.set("mapred.child.java.opts", "-Xmx200m -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=16888");
        RecommendConfiguration recommendConfiguration = new RecommendConfiguration(paths, configuration);
        HdfsUtils hdfsUtils = HdfsUtils.getInstanceOfPseudoDistributed(configuration);
       /* args = new String[2];
        args[0] = "hdfs://hadoop2master:9000/recommend";
        args[1] = "hdfs://hadoop2master:9000/recommend/output";*/
        if (args.length < 2) {
            System.err.println("args error");
            System.err.println("--input 文件初始输入路径");
            System.err.println("--output 文件最终输出路径");
        } else {
            System.err.println("System init");
            System.err.println("input -" + args[0] + ", output -" + args[1]);
            System.err.println("looking for input");

            hdfsUtils.lsShowInConsole(args[0]);

            paths.put("input", args[0]);
            paths.put("step1OutPut", paths.get("input") + "/s1o/");
            paths.put("step2OutPut", paths.get("input") + "/s2o/");
            paths.put("step3OutPut", paths.get("input") + "/s3o/");
            paths.put("step4OutPut", paths.get("input") + "/s4o/");
            paths.put("filter_output", paths.get("input") + "/filter_output/");
            paths.put("output", args[1]);

            System.err.println("removing and making");
            /*hdfsUtils.rmrShowInConsole(paths.get("step1OutPut"));
            hdfsUtils.rmrShowInConsole(paths.get("step2OutPut"));
            hdfsUtils.rmrShowInConsole(paths.get("step3OutPut"));*/
            //hdfsUtils.rmrShowInConsole(paths.get("step4OutPut"));
            //hdfsUtils.rmrShowInConsole(paths.get("filter_output"));
            hdfsUtils.rmrShowInConsole(paths.get("output"));
            hdfsUtils.closeFileSystem();

            System.err.println("Job Path is here");
            System.err.println("================================");
            for (String key : paths.keySet()) {
                System.err.println(key + " -> " + paths.get(key));
            }
            System.err.println("================================");
            RecommendDriver recommendDriver = new RecommendDriver();
            recommendDriver.run(recommendConfiguration);
        }
    }

    public void run(RecommendConfiguration conf) throws Exception {

        BuildRatingMatrix step1 = new BuildRatingMatrix();
        BuildSourceMatrix step2 = new BuildSourceMatrix();
        RegroupMatrix step3 = new RegroupMatrix();
        MultipleMatrix step4 = new MultipleMatrix();
        FilterResult step5 = new FilterResult();
        System.err.println("Execute Step1");
        if (step1.run(conf)) {
            System.err.println("Execute Step2");
            if (step2.run(conf)) {
                System.err.println("Execute Step3");
                if (step3.run(conf)) {
                    System.err.println("Execute Step4");
                    if (step4.run(conf)) {
                        step5.run(conf);
                    }
                }
            }
        }
    }

}

class RecommendConfiguration {

    private Map<String, String> paths;
    private Configuration configuration;

    public RecommendConfiguration(Map<String, String> paths, Configuration configuration) {
        this.paths = paths;
        this.configuration = configuration;
    }

    public Map<String, String> getPaths() {
        return paths;
    }

    public void setPaths(Map<String, String> paths) {
        this.paths = paths;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}

