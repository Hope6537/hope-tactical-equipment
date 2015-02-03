package org.hope6537.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.io.*;
import java.net.URI;

import static org.junit.Assert.assertTrue;

/**
 * Created by Hope6537 on 2015/1/28.
 */
public class HdfsDemo {

    FileSystem fileSystem;
    Logger logger;

    public static Configuration getConfigurationByHA() {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://ns1");
        configuration.set("dfs.nameservices", "ns1");
        configuration.set("dfs.ha.namenodes.ns1", "nn1,nn2");
        configuration.set("dfs.namenode.rpc-address.ns1.nn1", "itcast01:9000");
        configuration.set("dfs.namenode.rpc-address.ns1.nn2", "itcast02:9000");
        configuration.set("dfs.client.failover.proxy.provider.ns1", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
        return configuration;
    }

    @Before
    public void init() throws Exception {
        System.setProperty("hadoop.home.dir", "C:\\CoderDocuments\\hadoop-2.2.0");
        //fileSystem = FileSystem.get(new URI("hdfs://hadoop2master:9000"), new Configuration(), "hope6537");
        fileSystem = FileSystem.get(new URI("hdfs://ns1"), getConfigurationByHA(), "hope6537");
    }

    @Test
    public void testAddItem() throws Exception {
        OutputStream out = fileSystem.create(new Path("/test1.dat"));
        InputStream in = new FileInputStream(new File("D:/[Lukool][Desktop][20150127_20-57-11].avi"));
        IOUtils.copyBytes(in, out, 4096, true);
    }

    @Test
    public void testGetItem() throws Exception {
        FSDataInputStream in = fileSystem.open(new Path("/test1.dat"));
        OutputStream out = new FileOutputStream(new File("D:/test1.avi"));
        IOUtils.copyBytes(in, out, 4096, true);
    }

    @Test
    public void testDeleteItemList() throws Exception {
        boolean flag = fileSystem.delete(new Path("/hope6537"), true);
        assertTrue(flag);
    }

    @Test
    public void testMakedir() throws Exception {
        boolean flag = fileSystem.mkdirs(new Path("/hope6537(test)"));
        assertTrue(flag);
    }
}


