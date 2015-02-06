package com.jason.framework.orm.mybatis;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.util.Assert;

import com.jason.framework.orm.Page;


/**
 * MyBatis 持久层 支持类
 * @author Jason
 * @data 2014-6-30 
 * @param <PK>
 * @param <T>
 */
public abstract class MybatisRepositorySupport<PK, T> extends SqlSessionDaoSupport {

	
	/**
	 * 根据id获取对象
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T get(PK id) {
		Assert.notNull(id, "id must not be null!");
		return (T) getSqlSession().selectOne(getNamespace() + ".get", id);
	}

	/**
	 * 保存
	 * @param entity
	 */
	public void save(T entity) {
		Assert.notNull(entity, "entity must not be null!");
		getSqlSession().insert(getNamespace() + ".save", entity);
	}

	/**
	 * 更新
	 * @param entity
	 */
	public void update(T entity) {
		Assert.notNull(entity, "entity must not be null!");
		getSqlSession().update(getNamespace() + ".update", entity);
	}

	/**
	 * 删除
	 * @param entity
	 */
	public void delete(T entity) {
		Assert.notNull(entity, "entity must not be null!");
		getSqlSession().delete(getNamespace() + ".delete", entity);
	}
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(PK id) {
		Assert.notNull(id, "id must not be null!");
		getSqlSession().delete(getNamespace() + ".deleteById", id);
	}

	/**
	 * 查询
	 * @param value
	 * @return
	 */
	public List<T> query(Object value) {
		return getSqlSession().selectList(getNamespace() + ".query", value);
	}

	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public Page<T> queryPage(Page<T> page) {
		Assert.notNull(page, "page must not be null!");
		List<T> result = getSqlSession().selectList(getNamespace() + ".queryPage", page);
		page.setResult(result);
		return page;
	}

	/**
	 * 获取对应的名字空间，对应xml的namespace
	 * @return
	 */
	protected abstract String getNamespace();
}
