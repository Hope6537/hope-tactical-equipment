package org.hope6537.hadoop.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.security.User;
import org.apache.hadoop.hbase.util.Bytes;
import org.hope6537.hadoop.ConfigurationUtils;
import org.hope6537.hadoop.HadoopConstants;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Hope6537 on 2015/2/4.
 */
public class HbaseUtils {

    private Configuration hbaseConfiguration;
    private User user;

    public HbaseUtils() {
        hbaseConfiguration = ConfigurationUtils.getConfiguration(HadoopConstants.HBASE);
    }

    @Before
    public void init() {
        hbaseConfiguration = ConfigurationUtils.getConfiguration(HadoopConstants.HBASE);
        //指定用户和用户组
        user = User.createUserForTesting(hbaseConfiguration, "hope6537", new String[]{"adm"});
    }

    @Test
    public void testPut() throws IOException {
        HTable table = new HTable(hbaseConfiguration, "user");
        Put put = new Put(Bytes.toBytes("rk0003"));
        put.add(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes("liuyan"));
        table.put(put);
        table.close();
    }

    @Test
    public void testScan() throws Exception {
        //HTablePool pool = new HTablePool(hbaseConfiguration, 10);
        HConnection connection = HConnectionManager.createConnection(hbaseConfiguration);
        HTableInterface table = connection.getTable("test");
        Scan scan = new Scan(Bytes.toBytes("row1"));
        scan.addFamily(Bytes.toBytes("cf"));
        ResultScanner scanner = table.getScanner(scan);
        for (Result r : scanner) {
            /**
             for(KeyValue kv : r.list()){
             String family = new String(kv.getFamily());
             System.out.println(family);
             String qualifier = new String(kv.getQualifier());
             System.out.println(qualifier);
             System.out.println(new String(kv.getValue()));
             }
             */
            byte[] value = r.getValue(Bytes.toBytes("cf"), Bytes.toBytes("a"));
            System.out.println(new String(value));
        }
        connection.close();
    }

    @Test
    public void testCreate() throws IOException {
        HBaseAdmin admin = new HBaseAdmin(hbaseConfiguration);
        //TODO:最新的方法是什么？A:使用tablename类进行载入
        TableName newTableName = TableName.valueOf("userinfo");
        HTableDescriptor table = new HTableDescriptor(newTableName);
        HColumnDescriptor security = new HColumnDescriptor("security");
        HColumnDescriptor info = new HColumnDescriptor("info");
        security.setMaxVersions(5);
        info.setMaxVersions(3);
        table.addFamily(security);
        table.addFamily(info);
        admin.createTable(table);
        admin.close();
    }


}
