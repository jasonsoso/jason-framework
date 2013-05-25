package com.jason.framework.application;

import java.util.List;
import java.util.Map;

import com.jason.framework.domain.authority.Authority;
import com.jason.framework.orm.Page;

public interface AuthorityService {


	Authority get(String id);

	void store(Authority entity);

	void delete(String id);

	List<Authority> query(String queryString, Object... values);

	Page<Authority> queryPage(Page<Authority> page, String hql, Map<String, Object> values);
	
	Authority getfromSQL(String id);
}
