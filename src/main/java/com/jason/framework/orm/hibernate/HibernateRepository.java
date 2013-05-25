package com.jason.framework.orm.hibernate;

import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;


public class HibernateRepository extends HibernateDaoSupport{
	
	@Autowired
	public void setHibernateTemplateInternal(HibernateTemplate hibernateTemplate){
		super.setHibernateTemplate(hibernateTemplate);
	}	

	/**
	 * @param queryString
	 * @param values
	 * @return
	 */
	protected Query createQuery(String queryString, Object... values) {

		Assert.hasText(queryString, "queryString must has text");

		Query query = getSession().createQuery(queryString);
		if (null != values) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * @param queryString
	 * @param values
	 * @return
	 */
	protected final Query createQuery(String queryString, Map<String, Object> values) {

		Assert.hasText(queryString, "queryString must has text");

		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

}
