package com.jason.framework.orm.mybatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.util.Assert;

import com.jason.framework.orm.Page;
import com.jason.framework.orm.PageHelper;
import com.jason.framework.orm.dialect.Dialect;
import com.jason.framework.util.ExceptionUtils;
import com.jason.framework.util.ReflectionUtils;

/**
 * Mybatis 分页插件
 * @author Jason
 * @data 2014-6-30 
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class MybatisPagePlugin implements Interceptor {

	private Dialect dialect;
	private String interceptPattern;
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		Object target = invocation.getTarget();
		if (target instanceof RoutingStatementHandler) {
			RoutingStatementHandler handler = (RoutingStatementHandler) target;
			StatementHandler delegate = (StatementHandler) ReflectionUtils.getFieldValue(handler, "delegate");
			interceptIfNecessary(invocation, delegate);
		}
		
		return invocation.proceed();
	}

	private void interceptIfNecessary(Invocation invocation, StatementHandler delegete) throws Exception {

		MappedStatement stmt = (MappedStatement) ReflectionUtils.getFieldValue(delegete, "mappedStatement");
		if (!isSantisfiedByPattern(stmt)) {
			return;
		}

		BoundSql boundSql = delegete.getBoundSql();
		Object paramObject = boundSql.getParameterObject();

		if (!isSantisfiedByParam(paramObject, Page.class)) {
			return;
		}

		// paramObject instanceOf page,we intercept it.
		Page<?> page = (Page<?>) paramObject;
		if(!page.isAutoCount()){
			return;
		}

		// create count sql
		String countSql = PageHelper.buildCountSQL(boundSql.getSql());
		long count = executeCountSql(invocation, stmt, boundSql, countSql, page);
		page.setTotalCount(count);

		// create pageSql like select table_field from table where table_field=? order by table_field limit ?,?
		String pageSql = createPageSql(page, boundSql.getSql());
		ReflectionUtils.setFieldValue(boundSql, "sql", pageSql);
	}

	private boolean isSantisfiedByPattern(MappedStatement stmt) {

		String stmtId = stmt.getId();
		
		// if user did not define the interceptPattern,always return true
		if (StringUtils.isBlank(this.interceptPattern)) {
			return true;
		}

		return stmtId.matches(this.interceptPattern);
	}

	private boolean isSantisfiedByParam(Object paramObject, Class<?> paramClazz) {
		if (null == paramObject) {
			return false;
		}
		
		// verify the paramClazz is superClass of the paramObject
		return paramClazz.isAssignableFrom(paramObject.getClass());
	}

	private long executeCountSql(Invocation invocation, MappedStatement stmt,
			BoundSql sql, String countSql, Object paramObject) throws Exception {

		long result = -1;
		BoundSql countBoundSql = new BoundSql(
												stmt.getConfiguration(),
												countSql,
												sql.getParameterMappings(),
												paramObject
											);

		Connection conn = getConnection(invocation);
		PreparedStatement pstmt = conn.prepareStatement(countSql);
		ParameterHandler paramHandler = new DefaultParameterHandler(stmt, paramObject, countBoundSql);
		paramHandler.setParameters(pstmt);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			result = rs.getLong(1);
		}

		freeQuietly(rs, pstmt);
		return result;
	}

	private Connection getConnection(Invocation invocation) {
		return (Connection) invocation.getArgs()[0];
	}

	private void freeQuietly(ResultSet rs, PreparedStatement pstmt) {
		try {
			if (null != rs) {
				rs.close();
			}
		} catch (Exception e) {
		}

		try {
			if (null != pstmt) {
				pstmt.close();
			}
		} catch (Exception e) {
		}
	}

	private String createPageSql(Page<?> page, String sql) {

		sql = String.format("%s%s", sql, PageHelper.buildOrderByString(page, sql));
		Dialect dialect = getDialect();
		sql = dialect.getLimitString(sql, page.getFirst() - 1, page.getPageSize());
		return sql;
	}

	protected Dialect getDialect() {
		return this.dialect;
	}

	@SuppressWarnings("unchecked")
	private Dialect getDialect(String dialectClazz) {

		try {

			Class<Dialect> clazz = (Class<Dialect>) Class.forName(dialectClazz);
			return clazz.newInstance();
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties props) {
		Assert.hasText(props.getProperty("dialect"), "dialect property not found!");

		this.dialect = getDialect(props.getProperty("dialect"));
		this.interceptPattern = props.getProperty("interceptPattern");
	}

}
