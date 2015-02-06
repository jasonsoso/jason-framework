package com.jason.framework.orm.dialect;

/**
 * 方言
 * @author Jason
 * @data 2014-6-30 
 */
public interface Dialect {
	
	String getLimitString(String sql, boolean hasOffset);

	String getLimitString(String sql, int offset, int offsetSize);
}
