package org.hope6537.hadoop.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

/**
 * Created by Hope6537 on 2015/2/4.
 */
public class HbaseDemo {


    public static Configuration getConfiguration() {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "hadoop5,hadoop6,hadoop7");
        return configuration;
    }

}
