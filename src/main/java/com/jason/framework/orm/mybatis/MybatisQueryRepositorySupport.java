package com.jason.framework.orm.mybatis;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * 查询
 * @author Jason
 * @date 2014-7-26
 */
public class MybatisQueryRepositorySupport extends SqlSessionDaoSupport {

	public <T> List<T> query(String key, Object params) {
		return super.getSqlSession().selectList(key, params);
	}
}
