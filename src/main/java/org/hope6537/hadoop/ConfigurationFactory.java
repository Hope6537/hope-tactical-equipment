package org.hope6537.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.hope6537.context.ApplicationConstant;


/**
 * Created by Hope6537 on 2015/2/5.
 */
public class ConfigurationFactory {

    public static Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        //hdfs
        configuration.set("fs.defaultFS", "hdfs://ns1");
        configuration.set("dfs.nameservices", "ns1");
        configuration.set("dfs.ha.namenodes.ns1", "nn1,nn2");
        configuration.set("dfs.namenode.rpc-address.ns1.nn1", "hadoop1:9000");
        configuration.set("dfs.namenode.rpc-address.ns1.nn2", "hadoop2:9000");
        configuration.set("dfs.client.failover.proxy.provider.ns1", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
        configuration.set("hbase.zookeeper.quorum", "hadoop5,hadoop6,hadoop7");
        return configuration;
    }

    public static Configuration getConfigurationOfPseudoDistributed() {
        Configuration configuration = new Configuration();
//        configuration.set("mapred.child.java.opts", "-Xmx200m -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=16888");
       /* UserGroupInformation user = UserGroupInformation.createRemoteUser("hope6537");
        configuration.set("hadoop.job.ugi", "hope6537");
        configuration.set("mapreduce.jobhistory.address", "hadoop2master:10020");
        configuration.set("mapreduce.framework.name", "yarn");
        configuration.set("yarn.nodemanager.aux-services", "mapreduce_shuffle");
        configuration.set("dfs.replication", "1");

        configuration.set("fs.defaultFS", "hdfs://hadoop2master:9000");
        configuration.set("hadoop.tmp.dir", "/home/hope6537/hadoop/hadoop-2.6.0/tmp");
        configuration.set("hadoop.tmp.dir", "C:/CoderDocuments/hadoop/hadoop-2.6.0/tmp");
        configuration.set("dfs.permissions", "false");*/
        return configuration;
    }

    public static Configuration getConfiguration(String type) {
        if (ApplicationConstant.isNull(type)) {
            return getConfiguration();
        } else {
            switch (type) {
                case HadoopConstants.HBASE:
                    Configuration configuration = HBaseConfiguration.create();
                    configuration.set("hbase.cluster.distributed", "true");
                    configuration.set("hbase.zookeeper.master", "hadoop5:60000");
                    configuration.set("hbase.zookeeper.quorum", "hadoop5,hadoop6,hadoop7");
                    return configuration;
                case "test":
                    Configuration testConfiguration = new Configuration();
                    //hdfs
                    testConfiguration.set("fs.defaultFS", "hdfs://ns1");
                    testConfiguration.set("dfs.nameservices", "ns1");
                    testConfiguration.set("dfs.ha.namenodes.ns1", "nn1,nn2");
                    testConfiguration.set("dfs.namenode.rpc-address.ns1.nn1", "hadoop1:9000");
                    testConfiguration.set("dfs.namenode.rpc-address.ns1.nn2", "hadoop2:9000");
                    testConfiguration.set("dfs.client.failover.proxy.provider.ns1", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
                    testConfiguration.set("hbase.zookeeper.quorum", "hadoop5,hadoop6,hadoop7");
                    return testConfiguration;
                default:
                    return getConfiguration();
            }
        }
    }

}
