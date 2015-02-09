package org.hope6537.db;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface BaseDaoUtil<T> {

	

	/**
	 * <p>Describe: 获取到连接</p>
	 * <p>Using: DriverManager.getConnection(DBURL, DBUSER, DBPASS);</p>
	 * <p>How To Work: </p>
	 * <p>DevelopedTime: 2014-4-27下午07:27:05 </p>
	 * <p>Author:Hope6537</p>
	 * @return
	 * @see
	 */
	Connection getConnections();

	/**
	 * <p>Describe: 使用普通SQL，通过经典数据库语句进行增删改</p>
	 * <p>Using: </p>
	 * <p>How To Work: </p>
	 * <p>DevelopedTime:  2014-4-27下午07:26:50 </p>
	 * <p>Author:Hope6537</p>
	 * @param sql 普通的SQL语句
	 * @return 返回更改行数
	 * @see
	 */
	int update(String sql);

	/**
	 * <p>Describe: 使用预处理SQL，通过经典数据库语句进行增删改</p>
	 * <p>Using: </p>
	 * <p>How To Work: </p>
	 * <p>DevelopedTime:  2014-4-27下午07:26:50 </p>
	 * <p>Author:Hope6537</p>
	 * @param sql 预处理的SQL语句
	 * @return 返回更改行数
	 * @see
	 */
	int updateByPrepared(String sql, List<Object> params);

	/**
	*<pre>
	* 这里使用了非常残暴的类反射机制来添加数据
	* ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
	* 获得泛型的类型，并找到Class对象,注意因为有这个代码的存在
	* 所以在实例化BaseDaoUtilImpl的时候，不能使用这个方法
	* Field[] fields = clz.getDeclaredFields();得到属性
	* 一旦遇到serialVersionUID跳过
	* 遇到id将会使用java来控制主键
	* 即select max(" + field.getName()+ ") as i from " + clz.getSimpleName();
	* 来找到id的最大值并+1，没有使用sql+1是因为出现了Column '' not found.异常
	* 暂时不管。
	* 然后拼接预处理sql
	* 最后添加。
	*</pre>
	* <p>Describe: 使用类反射机制添加数据</p>
	* <p>Using: </p>
	* <p>How To Work: </p>
	* <p>DevelopedTime: 2014-4-27下午09:11:23 </p>
	* <p>Author:Hope6537</p>
	* @param t
	* @return
	* @see
	*/
	int saveByPrepared(T t);

	/**
	 * <p>Describe: 预处理对象删除</p>
	 * <p>Using: </p>
	 * <p>How To Work: </p>
	 * <p>DevelopedTime: 2014年10月22日上午9:20:21 </p>
	 * <p>Author:Hope6537</p>
	 * @param t
	 * @return
	 * @see
	 */
	int deleteByPrepared(T t);

	/**
	 *<pre>
	 *这里同样使用了非常残暴的类反射机制来更新数据
	 *要注意实体内的id必须存在
	 *</pre>
	 * <p>Describe: 实用类反射机制来获取数据</p>
	 * <p>Using: </p>
	 * <p>How To Work: </p>
	 * <p>DevelopedTime: 2014-4-27下午07:37:54 </p>
	 * <p>Author:Hope6537</p>
	 * @param t
	 * @return
	 * @see
	 */
	int updateByPrepared(T t);

	/**
	 * <p>Describe: 预处理对象查询</p>
	 * <p>Using: </p>
	 * <p>How To Work: </p>
	 * <p>DevelopedTime: 2014年10月22日上午9:25:45 </p>
	 * <p>Author:Hope6537</p>
	 * @param id
	 * @return
	 * @see
	 */
	T getEntryByPrimaryKey(int id);

	/**
	 * <p>Describe: 得到预处理查询（List）</p>
	 * <p>Using: </p>
	 * <p>How To Work: </p>
	 * <p>DevelopedTime: 2014-4-27下午09:22:15 </p>
	 * <p>Author:Hope6537</p>
	 * @param sql
	 * @param params
	 * @return
	 * @see
	 */
	List<Map<String, String>> getResult(String sql, List<Object> params);

	/**
	 * <p>Describe: 反射机制进行单体查询并封装</p>
	 * <p>Using: </p>
	 * <p>How To Work: </p>
	 * <p>DevelopedTime: 2014-4-27下午06:17:22 </p>
	 * <p>Author:Hope6537</p>
	 * @param sql
	 * @param params
	 * @param c
	 * @return
	 * @see
	 */
	T findSimpleRef(String sql, List<Object> params, Class<T> c);

	/**
	 *<pre>
	 *
	 *</pre>
	 * <p>Describe: 使用反射机制进行预处理语句查询进行List<T>封装</p>
	 * <p>Using: </p>
	 * <p>How To Work: </p>
	 * <p>DevelopedTime: 2014-4-27下午07:45:40 </p>
	 * <p>Author:Hope6537</p>
	 * @param sql
	 * @param params
	 * @param c
	 * @return
	 * @see
	 */
	List<T> findRef(String sql, List<Object> params, Class<T> c);

	/**
	 * <p>Describe: 通过经典数据库语句进行查询</p>
	 * <p>Using: </p>
	 * <p>How To Work: </p>
	 * <p>DevelopedTime: 2014-4-27下午07:26:31 </p>
	 * <p>Author:Hope6537</p>
	 * @param sql
	 * @return
	 * @see
	 */
	ArrayList<Map<String, String>> query(String sql);

	/**
	 *<pre>
	 * 在aop切面处理中，将会作为destroy-methods设定
	 *</pre>
	 * <p>Describe: 关闭流</p>
	 * <p>Using: </p>
	 * <p>How To Work: </p>
	 * <p>DevelopedTime:2014-4-27下午07:27:35 </p>
	 * <p>Author:Hope6537</p>
	 * @see
	 */
	void close();

}