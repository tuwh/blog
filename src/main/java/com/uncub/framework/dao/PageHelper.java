package com.uncub.framework.dao;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * Mybatis - 通用分页拦截器
 * Created by tuwh
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class}),
@Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class})})
public class PageHelper implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(PageHelper.class);

    private static DataBaseType dataBaseType = null;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof StatementHandler) {
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
            // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环
            // 可以分离出最原始的的目标类)
            while (metaStatementHandler.hasGetter("h")) {
                Object object = metaStatementHandler.getValue("h");
                metaStatementHandler = SystemMetaObject.forObject(object);
            }
            RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
            if (!(rowBounds instanceof Pagination)) {
                return invocation.proceed();
            }
            Pagination pagination = (Pagination) rowBounds;
            // 分离最后一个代理对象的目标类
            while (metaStatementHandler.hasGetter("target")) {
                Object object = metaStatementHandler.getValue("target");
                metaStatementHandler = SystemMetaObject.forObject(object);
            }
            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");

            if (dataBaseType == null){
                String driverClass = (String) metaStatementHandler.getValue("delegate.configuration.environment.dataSource.driverClass");
                if (driverClass.contains("mysql")) dataBaseType = DataBaseType.MYSQL;
                else if (driverClass.contains("oracle")) dataBaseType = DataBaseType.MYSQL;
            }
            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            // 分页参数作为参数对象parameterObject的一个属性
            String sql = boundSql.getSql();
            // 重写sql
            String pageSql = buildPageSql(sql, pagination);
            //重写分页sql
            metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
            Connection connection = (Connection) invocation.getArgs()[0];
            // 重设分页参数里的总页数等
            setPageParameter(sql, connection, mappedStatement, boundSql, pagination);
            // 将执行权交给下一个拦截器
            return invocation.proceed();
        } else if (invocation.getTarget() instanceof ResultSetHandler) {
            Object result = invocation.proceed();
            ResultSetHandler resultSetHandler = (ResultSetHandler) invocation.getTarget();
            MetaObject metaResultHandler = SystemMetaObject.forObject(resultSetHandler);
            //获取分页属性，如为分页查询，则在分页属性中返回查询结果
            if (metaResultHandler.hasGetter("rowBounds")){
                RowBounds rowBounds = (RowBounds) metaResultHandler.getValue("rowBounds");
                if (rowBounds instanceof Pagination){
                    Pagination pagination = (Pagination) rowBounds;
                    if (result instanceof List){
                        pagination.setResult((List) result);
                    }
                }
            }
            return result;
        } else if (invocation.getTarget() instanceof ParameterHandler) {
            return invocation.proceed();
        }
        return null;
    }

    /**
     * 只拦截这两种类型的
     * <br>StatementHandler
     * <br>ResultSetHandler
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler || target instanceof ResultSetHandler || target instanceof ParameterHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 修改原SQL为分页SQL
     * @param sql
     * @param page
     * @return
     */
    private String buildPageSql(String sql, Pagination page) throws SQLException {
        if (dataBaseType == DataBaseType.MYSQL){
            StringBuffer pageSql = new StringBuffer(sql)
                    .append(" limit ")
                    .append(page.getStartRow())
                    .append(",")
                    .append(page.getEndRow());
            return pageSql.toString();
        }else if (dataBaseType == DataBaseType.ORACLE){
            StringBuffer pageSql = new StringBuffer();
            pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
            pageSql.append(sql);
            pageSql.append(" ) temp where rownum <= ").append(page.getEndRow());
            pageSql.append(") where row_id > ").append(page.getStartRow());
            return pageSql.toString();
        }
        throw new SQLException("不支持的数据库类型！");
    }

    /**
     * 获取总记录数
     * @param sql
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param page
     */
    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement,
                                  BoundSql boundSql, Pagination page) {
        // 记录总记录数
        String countSql = "select count(0) as num from (" + sql + ") as temp";
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
                    boundSql.getParameterMappings(), boundSql.getParameterObject());
            for (ParameterMapping parameterMapping : boundSql.getParameterMappings()){
                if (null != boundSql.getAdditionalParameter(parameterMapping.getProperty()))
                    countBS.setAdditionalParameter(parameterMapping.getProperty(), boundSql.getAdditionalParameter(parameterMapping.getProperty()));
            }
            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt("num");
            }
            page.setTotal(totalCount);
            int totalPage = totalCount / page.getPageSize() + ((totalCount % page.getPageSize() == 0) ? 0 : 1);
            page.setPages(totalPage);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Ignore this exception", e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
            try {
                countStmt.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
        }
    }

    /**
     * 代入参数值
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
                               Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }

}
