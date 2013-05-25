package com.jason.framework.orm.hibernate;


/**
 * SQL查询工厂类
 * @author Jason
 * @date 2013-5-25 下午09:17:58
 */
public interface SQLQueryFactory {
	
	/**
	 * 
	 * @param sql
	 * @return the sql query.e.g:SQLQuery or prepareStatement.
	 */
	Object createSQLQuery(String sql);
}
