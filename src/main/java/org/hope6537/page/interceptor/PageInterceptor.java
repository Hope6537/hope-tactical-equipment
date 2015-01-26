package org.hope6537.page.interceptor;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.hope6537.context.ApplicationConstant;
import org.hope6537.page.model.Page;
import org.hope6537.reflect.ReflectUtil;
import org.hope6537.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.PropertyException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author hope6537
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PageInterceptor implements Interceptor {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private static String dialect = "";    //数据库方言
    private static String pageSqlId = ""; //mapper.xml中需要拦截的ID(正则匹配)

    public Object intercept(Invocation ivk) throws Throwable {
        if (ivk.getTarget() instanceof RoutingStatementHandler) {
            RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk.getTarget();
            BaseStatementHandler delegate = ReflectUtil.getValueByFieldName(statementHandler, "delegate");
            MappedStatement mappedStatement = ReflectUtil.getValueByFieldName(delegate, "mappedStatement");

            if (mappedStatement.getId().matches(pageSqlId)) { //拦截需要分页的SQL
                BoundSql boundSql = delegate.getBoundSql();
                Object parameterObject = boundSql.getParameterObject();//分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
                if (parameterObject == null) {
                    throw new NullPointerException("parameterObject尚未实例化！");
                } else {
                    Connection connection = (Connection) ivk.getArgs()[0];
                    Page page = getPage(parameterObject);
                    page.setTotalResult(getTotalResult(statementHandler, connection));
                    if (page.hasSumColumns() && page.lessThanMaxSumLimit()) {
                        page.setSumResult(getSumResult(statementHandler, connection, page));
                    }
                    String pageSql = generatePageSql(boundSql.getSql(), page);
                    ReflectUtil.setValueByFieldName(boundSql, "sql", pageSql); //将分页sql语句反射回BoundSql.
                }
            }
        }
        return ivk.proceed();
    }

    private Page getPage(Object parameterObject) throws NoSuchFieldException {
        Page page = null;//参数就是Page实体
        if (parameterObject instanceof Page) {
            page = (Page) parameterObject;
        } else if (parameterObject instanceof Map) {
            Map map = (Map) parameterObject;
            page = (Page) map.get("page");
            if (page == null) {
                page = new Page();
            }
        }
        return page;
    }

    private int getTotalResult(RoutingStatementHandler statementHandler, Connection connection) {
        int totalResult = 0;
        try {
            BaseStatementHandler delegate = ReflectUtil.getValueByFieldName(statementHandler, "delegate");
            MappedStatement mappedStatement = ReflectUtil.getValueByFieldName(delegate, "mappedStatement");
            BoundSql boundSql = delegate.getBoundSql();
            Object parameterObject = boundSql.getParameterObject();
            String sql = boundSql.getSql();
            String countSql = "select count(0) from (" + sql + ") tmp_count"; //记录统计
            PreparedStatement countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql, boundSql.getParameterMappings(), parameterObject);
            setParameters(countStmt, mappedStatement, countBS, parameterObject);
            ResultSet rs = countStmt.executeQuery();
            if (rs.next()) {
                totalResult = rs.getInt(1);
            }
            rs.close();
            countStmt.close();
        } catch (SQLException e) {
            logger.error(ApplicationConstant.ERROR, e);
        }
        return totalResult;
    }

    private Map<String, String> getSumResult(RoutingStatementHandler statementHandler, Connection connection, Page page) throws SQLException {
        Map<String, String> sumResultMap = new HashMap<String, String>();
        try {
            BaseStatementHandler delegate = ReflectUtil.getValueByFieldName(statementHandler, "delegate");
            MappedStatement mappedStatement = ReflectUtil.getValueByFieldName(delegate, "mappedStatement");
            BoundSql boundSql = delegate.getBoundSql();
            Object parameterObject = boundSql.getParameterObject();
            String sql = boundSql.getSql();
            String sumSql = getSumSql(sql, page);
            PreparedStatement sumStatement = connection.prepareStatement(sumSql);
            BoundSql sumBoundSql = new BoundSql(mappedStatement.getConfiguration(), sumSql, boundSql.getParameterMappings(), parameterObject);
            setParameters(sumStatement, mappedStatement, sumBoundSql, parameterObject);
            ResultSet rs = sumStatement.executeQuery();
            if (rs.next()) {
                for (String sumColumn : page.getSumColumns()) {
                    sumResultMap.put(sumColumn, rs.getString(sumColumn));
                }
            }
            rs.close();
            sumStatement.close();
            page.setSumResult(sumResultMap);
        } catch (SQLException e) {
            logger.error(ApplicationConstant.ERROR, e);
        }
        return sumResultMap;
    }

    private String getSumSql(String sql, Page page) {
        String sumSql = "SELECT ";
        for (String column : page.getSumColumns()) {
            sumSql += " IFNULL(SUM(" + column + "),0) AS " + column + ",";
        }
        sql = sumSql.substring(0, sumSql.length() - 1) + " FROM (" + sql + ") a";
        return sql;
    }


    /**
     * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler
     *
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws java.sql.SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null) {
                            value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
                        }
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if (typeHandler == null) {
                        throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName + " of statement " + mappedStatement.getId());
                    }
                    typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
                }
            }
        }
    }

    /**
     * 根据数据库方言，生成特定的分页sql
     *
     * @param sql
     * @param page
     * @return
     */
    private String generatePageSql(String sql, Page page) {
        if (page != null && StringUtil.notEmpty(dialect)) {
            StringBuilder pageSql = new StringBuilder();
            if ("mysql".equals(dialect)) {
                pageSql.append(sql)
                        .append(page.getSortField())
                        .append(page.getSortOrder())
                        .append(" limit ")
                        .append(page.getCurrentResult())
                        .append(",")
                        .append(page.getPageSize());
            } else if ("oracle".equals(dialect)) {
                pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (")
                        .append(sql)
                        .append(page.getSortField())
                        .append(page.getSortOrder())
                        .append(") tmp_tb where ROWNUM<=")
                        .append(page.getCurrentResult() + page.getPageSize())
                        .append(") where row_id>")
                        .append(page.getCurrentResult());
            }
            return pageSql.toString();
        } else {
            return sql;
        }
    }

    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, this);
    }

    public void setProperties(Properties p) {
        dialect = p.getProperty("dialect");
        if (StringUtil.isEmpty(dialect)) {
            try {
                throw new PropertyException("dialect property is not found!");
            } catch (PropertyException e) {
                logger.error(ApplicationConstant.ERROR, e);
            }
        }
        pageSqlId = p.getProperty("pageSqlId");
        if (StringUtil.isEmpty(pageSqlId)) {
            try {
                throw new PropertyException("pageSqlId property is not found!");
            } catch (PropertyException e) {
                logger.error(ApplicationConstant.ERROR, e);
            }
        }
    }

}