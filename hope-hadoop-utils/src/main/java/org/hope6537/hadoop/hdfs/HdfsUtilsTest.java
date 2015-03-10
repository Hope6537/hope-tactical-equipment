package org.hope6537.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Created by Hope6537 on 2015/2/13.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HdfsUtilsTest {


    private HdfsUtils hdfsUtils;

    @Before
    public void init() {
        hdfsUtils = HdfsUtils.getInstanceOfPseudoDistributed(new Configuration());
    }

    @Test
    public void test_01_Mkdir() throws IOException {
        boolean res = hdfsUtils.mkdirs("/hdfs_client_test");
        hdfsUtils.closeFileSystem();
        assertTrue(res);
    }

    @Test
    public void test_02_Ls() throws IOException {
        hdfsUtils.lsShowInConsole("/");
        hdfsUtils.lsShowInConsole("/hdfs_client_test");
        hdfsUtils.closeFileSystem();
    }

    @Test
    public void test_03_Put() throws IOException {
        boolean res = hdfsUtils.put("D:/[Lukool][DBXV][20150306_15-04-46].jpg", "/hdfs_client_test/hdfs_put");
        hdfsUtils.lsShowInConsole("/hdfs_client_test");
        hdfsUtils.closeFileSystem();
        assertTrue(res);
    }

    @Test
    public void test_04_Get() throws IOException {
        boolean res = hdfsUtils.get("/hdfs_client_test/hdfs_put", "D:/hdfs_get");
        hdfsUtils.lsShowInConsole("/hdfs_client_test");
        hdfsUtils.closeFileSystem();
        assertTrue(res);
    }

    @Test
    public void test_05_Rmr() throws IOException {
        boolean res = hdfsUtils.rmr("/hdfs_client_test");
        hdfsUtils.lsShowInConsole("/");
        hdfsUtils.closeFileSystem();
        assertTrue(res);
    }
}

