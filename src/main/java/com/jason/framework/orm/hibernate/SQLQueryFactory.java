package com.jason.framework.orm.hibernate;

public interface SQLQueryFactory {
	
	/**
	 * 
	 * @param sql
	 * @return the sql query.e.g:SQLQuery or prepareStatement.
	 */
	Object createSQLQuery(String sql);
}
