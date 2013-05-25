package com.jason.framework.infrastruture.persist.hibernate.impl;

import org.springframework.stereotype.Repository;

import com.jason.framework.domain.authority.Authority;
import com.jason.framework.domain.authority.AuthorityRepository;
import com.jason.framework.orm.hibernate.HibernateRepositorySupport;

@Repository
public class HibernateAuthorityRepository extends HibernateRepositorySupport<String, Authority> implements AuthorityRepository{

	@Override
	public void delete(String id) {
		super.delete(super.get(id));
	}
	
}
