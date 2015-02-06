package com.jason.framework.orm.dialect.impl;

import com.jason.framework.orm.dialect.Dialect;

/**
 * MySql 分页方言
 * @author Jason
 * @data 2014-6-30
 */
public class MySQLDialect implements Dialect {

	@Override
	public String getLimitString(String sql, boolean hasOffset) {
		StringBuilder builder = new StringBuilder();
		return builder.append(sql).append(hasOffset ? " limit ?, ?" : " limit ?").toString();
	}

	@Override
	public String getLimitString(String sql, int offset, int offsetSize) {
		StringBuilder builder = new StringBuilder();
		return builder.append(sql).append(" limit ").append(offset).append(",").append(offsetSize).toString();
	}

}
