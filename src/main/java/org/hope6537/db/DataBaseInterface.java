package org.hope6537.db;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface DataBaseInterface {

    /**
     * <p>Describe: 驱动类名称</p>
     */
    public static final String DBDRIVER = "com.mysql.jdbc.Driver";
    /**
     * <p>Describe: URL</p>
     */
    public static final String DBURL = "jdbc:mysql://localhost:3306/project_1_testing";
    /**
     * <p>Describe: 用户名</p>
     */
    public static final String DBUSER = "root";
    /**
     * <p>Describe: 密码</p>
     */
    public static final String DBPASS = "root";

    /**
     * <p>Describe: 获取到连接 </p>
     * <p>Using: </p>
     * <p>How To Work: </p>
     * <p>DevelopedTime: 2014年9月9日下午7:09:06 </p>
     * <p>Author:Hope6537</p>
     *
     * @return
     * @see
     */
    public abstract Connection getConnections();

    /**
     * @Descirbe 通过经典数据库语句进行增删改
     * @Author Hope6537(赵鹏)
     * @Params @param sql
     * @Params @return
     * @SignDate 2014-4-27下午07:26:50
     * @Version 0.9
     */
    public abstract int update(String sql);

    /**
     * @Descirbe 完成对数据库的表的数据预处理更新操作
     * @Author Hope6537(赵鹏)
     * @Params @param sql
     * @Params @param params
     * @Params @return
     * @Params @throws SQLException
     * @SignDate 2014-4-27下午07:23:12
     * @Version 0.9
     */
    public abstract int updateByPrepared(String sql, List<Object> params);

    /**
     * @Descirbe 查询返回单条记录
     * @Author Hope6537(赵鹏)
     * @Params @param sql
     * @Params @param params
     * @Params @return
     * @Params @throws SQLException
     * @SignDate 2014-4-27下午07:30:17
     * @Version 0.9
     */
    public abstract Map<String, String> getSimpleResult(String sql,
                                                        List<Object> params);

    /**
     * @Descirbe 得到预处理查询（List）
     * @Author Hope6537(赵鹏)
     * @Params @param sql
     * @Params @param params
     * @Params @return
     * @Params @throws SQLException
     * @SignDate 2014-4-27下午07:37:54
     * @Version 0.9
     */
    public abstract List<Map<String, String>> getResult(String sql,
                                                        List<Object> params);

    /**
     * @Descirbe 反射机制进行封装
     * @Author Hope6537(赵鹏)
     * @Params @param <T>
     * @Params @param sql
     * @Params @param params
     * @Params @param c
     * @Params @return
     * @Params @throws SQLException
     * @Params @throws IllegalAccessException
     * @Params @throws SecurityException
     * @Params @throws NoSuchFieldException
     * @Params @throws InstantiationException
     * @SignDate 2014-4-27下午07:44:04
     * @Version 0.9
     */
    public abstract <T> T findSimpleRef(String sql, List<Object> params,
                                        Class<T> c);

    /**
     * @Descirbe 使用反射机制进行预处理语句查询进行List<T>封装
     * @Author Hope6537(赵鹏)
     * @Params @param <T>
     * @Params @param sql
     * @Params @param params
     * @Params @param c
     * @Params @return
     * @Params @throws SQLException
     * @Params @throws IllegalAccessException
     * @Params @throws SecurityException
     * @Params @throws NoSuchFieldException
     * @Params @throws InstantiationException
     * @SignDate 2014-4-27下午07:45:40
     * @Version 0.9
     */
    public abstract <T> List<T> findRef(String sql, List<Object> params,
                                        Class<T> c);

    /**
     * @Descirbe 通过经典数据库语句进行查询
     * @Author Hope6537(赵鹏)
     * @Params @param sql
     * @Params @return
     * @SignDate 2014-4-27下午07:26:31
     * @Version 0.9
     */
    public abstract ArrayList<Map<String, String>> query(String sql);

    public abstract void close();

}