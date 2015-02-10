package org.hope6537.db;

import org.hope6537.context.ApplicationConstant;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <pre>
 * 作为基本的实现工具类，将会作为具体模型或业务的父类来被继承
 * </pre>
 * <p>
 * Describe: 数据库的基本工具类
 * </p>
 * <p>
 * Using: 作为BaseUtils，不会被直接使用
 * </p>
 * <p>
 * DevelopedTime: 2014-4-27下午01:32:09
 * </p>
 * <p>
 * Company: ChangChun Unviersity JiChuang Team
 * </p>
 *
 * @author Hope6537
 * @version 1.0
 * @see
 */
public class BaseDaoUtilImpl<T> implements BaseDaoUtil<T> {

    /**
     * Describe: 驱动类名称
     */
    public static final String DBDRIVER = "com.mysql.jdbc.Driver";
    /**
     * Describe: 数据库Url
     */
    public static final String DBURL = "jdbc:mysql://localhost:3306/hive_source";
    /**
     * Describe: 用户名
     */
    public static final String DBUSER = "root";
    /**
     * Describe: 密码
     */
    public static final String DBPASS = "4236537";

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


    private String status = ApplicationConstant.STATUS_DIE;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't Find DriverClass");
        }
    }

    protected BaseDaoUtilImpl() {
        conn = getConnections();
        status = ApplicationConstant.STATUS_NORMAL;
    }

    public Connection getConnections() {
        try {
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
        } catch (SQLException e) {

            System.out.println("Connection Failed");
            e.printStackTrace();
        }
        return conn;
    }

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

        }
        return result;
    }

    public int saveByPrepared(T t) {
        // 所影响的数据库的行数
        int result = -1;
        try {
            // 这里使用了类反射机制
            List<String> params = new ArrayList<String>();
            ParameterizedType type = (ParameterizedType) this.getClass()
                    .getGenericSuperclass();
            Class<?> clz = (Class<?>) type.getActualTypeArguments()[0];
            String sql = "insert into " + clz.getSimpleName() + " values (?";
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().contains("serialVersionUID")) {
                    continue;
                }
                if (field.getName().contains("id") && !field.getName().contains("_id")) {
                    String idSql = "select max(" + field.getName()
                            + ") as i from " + clz.getSimpleName();
                    ArrayList<Map<String, String>> map = this.query(idSql);
                    int id = 0;
                    try {
                        id = (Integer.parseInt(map.get(0).get("i")));
                    } catch (NumberFormatException e) {
                        System.out.println("First Data In");
                        id = 0;
                    }
                    params.add(++id + "");
                    continue;
                }
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(),
                        clz);
                Method getMethod = pd.getReadMethod();
                getMethod.setAccessible(true);
                Object value = getMethod.invoke(t);
                sql += ",?";
                params.add(value.toString());
            }
            sql += ")";

            pstmt = conn.prepareStatement(sql);
            // 占位符的第一个位置
            int index = 1;
            if (params != null && !params.isEmpty()) {
                for (int i = 0; i < params.size(); i++) {
                    pstmt.setObject(index++, params.get(i));
                }
            }
            result = pstmt.executeUpdate();
            closeStmts();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return result;
    }

    public boolean saveMultiByPrepared(List<T> list) {
        return this.saveMultiByPrepared(list, list.size() - 1);
    }


    /**
     * 首先获取类的关系，其次查询出第一主键，接着依次加1
     * insert into table (fields) values (?,?,?) , (?,?,?)
     */
    public boolean saveMultiByPrepared(List<T> list, int saveTimes) {
        int result = 0;
        boolean gotId = false;
        int entryId = 0;
        try {
            // 这里使用了类反射机制
            List<String> params = new ArrayList<String>();
            ParameterizedType type = (ParameterizedType) this.getClass()
                    .getGenericSuperclass();
            Class<?> clz = (Class<?>) type.getActualTypeArguments()[0];
            StringBuilder sql = new StringBuilder()
                    .append("insert into ")
                    .append(clz.getSimpleName())
                    .append(" values (?");
            Field[] fields = clz.getDeclaredFields();

            int startIndex = ApplicationConstant.EFFECTIVE_LINE_ZERO;
            int endIndex = Math.min(startIndex + saveTimes, list.size() - 1);

            //假设是存在10 3
            //0 2 -> 3 5 -> 6 8 -> 9 9
            for (; endIndex < list.size(); startIndex = endIndex, endIndex = Math.min(startIndex + saveTimes, list.size() - 1)) {
                for (int i = startIndex; i <= endIndex; i++) {
                    T obj = list.get(i);
                    for (Field field : fields) {
                        if (field.getName().contains("serialVersionUID")) {
                            continue;
                        }
                        if (field.getName().contains("id") && !field.getName().contains("_id")) {
                            if (!gotId) {
                                String idSql = "select max(" + field.getName()
                                        + ") as i from " + clz.getSimpleName();
                                ArrayList<Map<String, String>> map = this.query(idSql);
                                int id = 0;
                                try {
                                    id = (Integer.parseInt(map.get(0).get("i")));
                                } catch (NumberFormatException e) {
                                    System.out.println("First Data In");
                                    id = 0;
                                }
                                gotId = true;
                                params.add(++id + "");
                                entryId = id;
                                continue;
                            } else {
                                params.add(++entryId + "");
                                continue;
                            }

                        }
                        PropertyDescriptor pd = new PropertyDescriptor(field.getName(),
                                clz);
                        Method getMethod = pd.getReadMethod();
                        getMethod.setAccessible(true);
                        Object value = getMethod.invoke(obj);
                        sql.append(",?");
                        params.add(value.toString());
                    }
                    sql.append(i < list.size() - 1 ? ")," : ")");
                }
                pstmt = conn.prepareStatement(sql.toString());
                // 占位符的第一个位置
                int index = 1;
                if (ApplicationConstant.notNull(params)) {
                    for (String item : params) {
                        pstmt.setObject(index++, item);
                    }
                }
                result += pstmt.executeUpdate();
                this.closeStmts();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return result == list.size();
    }

    public int deleteByPrepared(T t) {

        // 所影响的数据库的行数
        int result = -1;
        try {
            // 这里使用了类反射机制
            List<String> params = new ArrayList<String>();
            ParameterizedType type = (ParameterizedType) this.getClass()
                    .getGenericSuperclass();
            Class<?> clz = (Class<?>) type.getActualTypeArguments()[0];
            String sql = "delete from " + clz.getSimpleName() + " where ";
            Field[] fields = clz.getDeclaredFields();
            String idfieldName = "";
            String idValue = "";
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (field.getName().contains("id")) {
                    PropertyDescriptor pd = new PropertyDescriptor(
                            field.getName(), clz);
                    Method getMethod = pd.getReadMethod();
                    idfieldName = field.getName();
                    idValue = getMethod.invoke(t).toString();
                    sql += idfieldName + " = ?";
                    params.add(idValue);
                    break;
                } else {
                    continue;
                }

            }
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
        }
        return result;
    }

    public int updateByPrepared(T t) {

        // 所影响的数据库的行数
        int result = -1;
        try {
            // 这里使用了类反射机制
            List<String> params = new ArrayList<String>();
            ParameterizedType type = (ParameterizedType) this.getClass()
                    .getGenericSuperclass();
            Class<?> clz = (Class<?>) type.getActualTypeArguments()[0];
            String sql = "update " + clz.getSimpleName() + " set ";
            Field[] fields = clz.getDeclaredFields();
            String idfieldName = "";
            String idValue = "";
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (field.getName().contains("serialVersionUID")) {
                    continue;
                }
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(),
                        clz);
                Method getMethod = pd.getReadMethod();
                if (field.getName().contains("id")) {
                    idfieldName = field.getName();
                    idValue = getMethod.invoke(t).toString();
                    continue;
                }
                String fieldId = field.getName();
                Object value = getMethod.invoke(t);
                if (i == fields.length - 1) {
                    sql += fieldId + "= ? ";
                } else {
                    sql += fieldId + "= ?, ";
                }
                params.add(value.toString());

            }
            sql += " where " + idfieldName + " = ? ";
            params.add(idValue);
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

        }
        return result;
    }

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

    public T findSimpleRef(String sql, List<Object> params, Class<T> c) {
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

    @SuppressWarnings("unchecked")
    public T getEntryByPrimaryKey(int id) {
        try {
            List<Object> params = new ArrayList<Object>();
            ParameterizedType type = (ParameterizedType) this.getClass()
                    .getGenericSuperclass();
            Class<T> clz = (Class<T>) type.getActualTypeArguments()[0];
            String sql = "select * from " + clz.getSimpleName() + " where ";
            Field[] fields = clz.getDeclaredFields();
            String idfieldName = "";
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (field.getName().contains("id")) {
                    idfieldName = field.getName();
                    sql += idfieldName + " = ?";
                    params.add(id);
                    break;
                } else {
                    continue;
                }

            }
            return this.findSimpleRef(sql, params, clz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<T> findRef(String sql, List<Object> params, Class<T> c) {
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
                    String columnValue = null;
                    if (columnName.equals("i") && rs.getObject(columnName) == null) {
                        columnValue = "0";
                    } else {
                        columnValue = rs.getObject(columnName).toString();
                    }
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

    public void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error:Close Connection");
        }

    }

    public void closeStmts() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error:Close Connection");
        }
    }

}
