package org.hope6537.db;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @version 0.9
 * @Describe 经典数据库的工具类
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-4-27下午01:32:09
 * @company Changchun University&SHXT
 */
public class DataBaseUtils implements DataBaseInterface {

    /**
     * @Describe 连接对象
     */
    static Connection conn = null;
    /**
     * @Describe 处理对象
     */
    static Statement stmt = null;
    /**
     * @Describe 预处理对象
     */
    static PreparedStatement pstmt = null;
    /**
     * @Describe 结果集对象
     */
    static ResultSet rs = null;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't Find DriverClass");
        }
    }

    public DataBaseUtils() {
        conn = getConnections();// TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {

    }

    /*
     * (non-Javadoc)
     *
     * @see cn.com.ccdx.zhao.utils.DbInter#getConnections()
     *
     * @Author:Hope6537(赵鹏)
     */
    public Connection getConnections() {
        try {
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Connection Failed");
            e.printStackTrace();
        }
        return conn;
    }

    /*
     * (non-Javadoc)
     *
     * @see cn.com.ccdx.zhao.utils.DbInter#update(java.lang.String)
     *
     * @Author:Hope6537(赵鹏)
     */
    public int update(String sql) {

        try {
            stmt = conn.createStatement();
            return stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println("Update Failed");
            e.printStackTrace();
            return -1;
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see cn.com.ccdx.zhao.utils.DbInter#updateByPrepared(java.lang.String,
     * java.util.List)
     *
     * @Author:Hope6537(赵鹏)
     */
    public int updateByPrepared(String sql, List<Object> params) {
        // 所影响的数据库的行数
        int result = -1;
        try {
            pstmt = conn.prepareStatement(sql);
            // 占位符的第一个位置
            int index = 1;
            if (params != null && !params.isEmpty()) {
                for (int i = 0; i < params.size(); i++) {
                    pstmt.setObject(index++, params.get(i));
                }
            }
            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see cn.com.ccdx.zhao.utils.DbInter#getSimpleResult(java.lang.String,
     * java.util.List)
     *
     * @Author:Hope6537(赵鹏)
     */
    public Map<String, String> getSimpleResult(String sql, List<Object> params) {
        Map<String, String> map = new HashMap<String, String>();
        int index = 1;
        try {

            pstmt = conn.prepareStatement(sql);
            if (params != null && !params.isEmpty()) {
                for (int i = 0; i < params.size(); i++) {
                    pstmt.setObject(index++, params.get(i));
                }
            }
            rs = pstmt.executeQuery();
            ResultSetMetaData rsmt = rs.getMetaData();
            int collength = rsmt.getColumnCount();
            while (rs.next()) {
                for (int i = 0; i < collength; i++) {
                    String colName = rsmt.getColumnName(i + 1);
                    String colValue = rs.getObject(colName).toString();
                    if (colValue == null) {
                        colValue = "";
                    }
                    map.put(colName, colValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /*
     * (non-Javadoc)
     *
     * @see cn.com.ccdx.zhao.utils.DbInter#getResult(java.lang.String,
     * java.util.List)
     *
     * @Author:Hope6537(赵鹏)
     */
    public List<Map<String, String>> getResult(String sql, List<Object> params) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {

            int index = 1;
            pstmt = conn.prepareStatement(sql);
            if (params != null && !params.isEmpty()) {
                for (int i = 0; i < params.size(); i++) {
                    pstmt.setObject(index++, params.get(i));
                }
            }
            rs = pstmt.executeQuery();
            ResultSetMetaData rsmt = rs.getMetaData();
            int collength = rsmt.getColumnCount();
            while (rs.next()) {
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < collength; i++) {
                    String colName = rsmt.getColumnName(i + 1);
                    String colValue = rs.getObject(colName).toString();
                    if (colValue == null) {
                        colValue = "";
                    }
                    map.put(colName, colValue);
                }
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /*
     * (non-Javadoc)
     *
     * @see cn.com.ccdx.zhao.utils.DbInter#findSimpleRef(java.lang.String,
     * java.util.List, java.lang.Class)
     *
     * @Author:Hope6537(赵鹏)
     */
    public <T> T findSimpleRef(String sql, List<Object> params, Class<T> c) {
        T result = null;
        try {
            int index = 1;
            pstmt = conn.prepareStatement(sql);
            if (params != null && !params.isEmpty()) {
                for (int i = 0; i < params.size(); i++) {
                    pstmt.setObject(index++, params.get(i));
                }
            }
            rs = pstmt.executeQuery();
            ResultSetMetaData rsmt = rs.getMetaData();
            int collength = rsmt.getColumnCount();
            while (rs.next()) {
                result = c.newInstance();
                for (int i = 0; i < collength; i++) {
                    String colName = rsmt.getColumnName(i + 1);
                    Object colValue = rs.getObject(colName);
                    if (colValue == null) {
                        colValue = "";
                    }
                    // java的对象的访问权限
                    Field field = c.getDeclaredField(colName);
                    field.setAccessible(true);
                    field.set(result, colValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see cn.com.ccdx.zhao.utils.DbInter#findRef(java.lang.String,
     * java.util.List, java.lang.Class)
     *
     * @Author:Hope6537(赵鹏)
     */
    public <T> List<T> findRef(String sql, List<Object> params, Class<T> c) {
        List<T> list = new ArrayList<T>();
        try {
            int index = 1;
            pstmt = conn.prepareStatement(sql);
            if (params != null && !params.isEmpty()) {
                for (int i = 0; i < params.size(); i++) {
                    pstmt.setObject(index++, params.get(i));
                }
            }
            rs = pstmt.executeQuery();
            ResultSetMetaData rsmt = rs.getMetaData();
            int collength = rsmt.getColumnCount();
            while (rs.next()) {
                T result = c.newInstance();
                for (int i = 0; i < collength; i++) {
                    String colName = rsmt.getColumnName(i + 1);
                    Object colValue = rs.getObject(colName);
                    if (colValue == null) {
                        colValue = "";
                    }
                    // java的对象的访问权限
                    Field field = c.getDeclaredField(colName);
                    field.setAccessible(true);
                    field.set(result, colValue);
                }
                list.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /*
     * (non-Javadoc)
     *
     * @see cn.com.ccdx.zhao.utils.DbInter#query(java.lang.String)
     *
     * @Author:Hope6537(赵鹏)
     */
    public ArrayList<Map<String, String>> query(String sql) {
        try {
            ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

            stmt = conn.createStatement();

            rs = stmt.executeQuery(sql);

            ResultSetMetaData rsmd = rs.getMetaData();

            int count = rsmd.getColumnCount();// 获得个数

            while (rs.next()) {
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 1; i <= count; i++) {
                    String columnName = rsmd.getColumnName(i);
                    String columnValue = rs.getString(columnName);
                    map.put(columnName, columnValue);
                }
                list.add(map);

            }

            return list;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see cn.com.ccdx.zhao.utils.DbInter#close()
     *
     * @Author:Hope6537(赵鹏)
     */
    public void close() {
        try {

            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error:Close Connection");
        }

    }

}
