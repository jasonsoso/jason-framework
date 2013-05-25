package com.jason.framework.orm.hibernate.impl;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.jason.framework.orm.hibernate.HibernateRepository;
import com.jason.framework.orm.hibernate.SQLQueryFactory;


@Repository
public class HibernateSQLQueryFactory extends HibernateRepository implements SQLQueryFactory {

	@Override
	public SQLQuery createSQLQuery(String sql) {
		return super.getSession().createSQLQuery(sql);
	}
}
