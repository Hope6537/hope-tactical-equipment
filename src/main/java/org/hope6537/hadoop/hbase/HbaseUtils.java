package org.hope6537.hadoop.hbase;

import com.sun.istack.NotNull;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.hope6537.hadoop.ConfigurationUtils;
import org.hope6537.hadoop.HadoopConstants;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hope6537 on 2015/2/4.
 */
public class HbaseUtils {

    @NotNull
    private Configuration hbaseConfiguration;
    private HConnection connection;

    public HbaseUtils() {
        this(ConfigurationUtils.getConfiguration(HadoopConstants.HBASE));
    }

    public HbaseUtils(Configuration hbaseConfiguration) {
        this.hbaseConfiguration = hbaseConfiguration;
        try {
            connection = HConnectionManager.createConnection(hbaseConfiguration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertData(String rowKey, String tableName, String columnFamily, String column, String data) throws IOException {
        HTable table = new HTable(hbaseConfiguration, tableName);
        Put put = new Put(Bytes.toBytes(rowKey));
        put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(data));
        table.put(put);
        table.close();
    }

    public void insertData(String tableName, HBaseModel HBaseModel) throws IOException {
        HTable table = new HTable(hbaseConfiguration, tableName);
        table.put(HBaseModel.toPut());
        table.close();
    }

    @Before
    public void init() {
        hbaseConfiguration = ConfigurationUtils.getConfiguration(HadoopConstants.HBASE);
        //指定用户和用户组
        //user = User.createUserForTesting(hbaseConfiguration, "hope6537", new String[]{"adm"});
    }

    @Test
    public void testPut() throws IOException {
        HTable table = new HTable(hbaseConfiguration, "test");
        Put put = new Put(Bytes.toBytes("row1"));
        put.add(Bytes.toBytes("cf"), Bytes.toBytes("a"), Bytes.toBytes("hope6537"));
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
            byte[] value = r.getValue(Bytes.toBytes("cf"), Bytes.toBytes("a"));
            System.out.println(new String(value));
        }
        connection.close();
    }

    public List<HBaseModel> getModelListFromHBase(String tableName, Get filter) {
        try {
            HTableInterface table = connection.getTable(tableName);
            Scan scan = new Scan(filter);
            ResultScanner scanner = table.getScanner(scan);
            List<HBaseModel> resList = new ArrayList<>();
            for (Result r : scanner) {
                resList.add(HBaseModel.getModelFromHBase(r));
            }
            return resList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public HBaseModel getModelFromHBase(String tableName, Integer rowKey) {
        HTable table = null;
        try {
            table = new HTable(hbaseConfiguration, tableName);
            Get get = new Get(Bytes.toBytes(rowKey));
            Result result = table.get(get);
            HBaseModel model = HBaseModel.getModelFromHBase(result);
            table.close();
            return model;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void testGet() throws Exception {
        HTable table = new HTable(hbaseConfiguration, "test");
        Get get = new Get(Bytes.toBytes("row1"));
        get.setMaxVersions(5);
        Result result = table.get(get);
        for (Cell kv : result.listCells()) {
            String family = new String(kv.getFamily());
            System.out.println(family);
            String qualifier = new String(kv.getQualifier());
            System.out.println(qualifier);
            System.out.println(new String(kv.getValue()));
        }
        table.close();
    }

    @Test
    public void testDrop() throws Exception {
        HBaseAdmin admin = new HBaseAdmin(hbaseConfiguration);
        admin.disableTable("test");
        admin.deleteTable("test");
        admin.close();
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

    @Test
    public void testDel() throws Exception {
        HTable table = new HTable(hbaseConfiguration, "test");
        Delete del = new Delete(Bytes.toBytes("row1"));
        del.deleteColumn(Bytes.toBytes("cf"), Bytes.toBytes("b"));
        table.delete(del);
        table.close();
    }


}
