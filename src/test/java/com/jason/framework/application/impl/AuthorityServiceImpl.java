package com.jason.framework.application.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jason.framework.application.AuthorityService;
import com.jason.framework.domain.authority.Authority;
import com.jason.framework.domain.authority.AuthorityRepository;
import com.jason.framework.orm.Page;
import com.jason.framework.orm.hibernate.SQLQueryFactory;



@Transactional
public class AuthorityServiceImpl implements AuthorityService {

	private AuthorityRepository authorityRepository;
	private SQLQueryFactory sqlQueryFactory;
	
	@Autowired
	public void setSqlQueryFactory(SQLQueryFactory sqlQueryFactory) {
		this.sqlQueryFactory = sqlQueryFactory;
	}

	@Autowired
	public void setAuthorityRepository(AuthorityRepository authorityRepository) {
		this.authorityRepository = authorityRepository;
	}

	
	
	@Override
	public Authority get(String id) {
		return authorityRepository.get(id);
	}

	@Override
	public void store(Authority entity) {
		authorityRepository.store(entity);
	}

	@Override
	public void delete(String id) {
		authorityRepository.delete(id);
	}

	@Override
	public List<Authority> query(String queryString, Object... values) {
		return authorityRepository.query(queryString,values);
	}

	@Override
	public Page<Authority> queryPage(Page<Authority> page, String hql, Map<String, Object> values) {
		return authorityRepository.queryPage(page,hql,values);
	}

	@Override
	public Authority getfromSQL(String id) {
		SQLQuery query = (SQLQuery) sqlQueryFactory.createSQLQuery("SELECT * FROM security_authority WHERE id=? ");
		Authority object = (Authority)query
			.setString(0, id)
			.setResultTransformer(Transformers.aliasToBean(Authority.class))
			.uniqueResult();
		return object;
	}

}
