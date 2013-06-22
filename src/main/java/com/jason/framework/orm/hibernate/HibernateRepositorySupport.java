package com.jason.framework.orm.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.util.Assert;

import com.jason.framework.orm.Page;


/**
 * Hibernate Repository Support
 * 
 * @param <K>
 * @param <T>
 */
public class HibernateRepositorySupport<K extends Serializable, T> extends HibernateRepository {

	private Class<T> clazz;

	public HibernateRepositorySupport() {
		clazz = getClazz();
	}

	@SuppressWarnings("unchecked")
	private Class<T> getClazz() {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<T>) type.getActualTypeArguments()[1];
	}

	/**
	 * 
	 * @param domain
	 */
	public void store(T domain) {
		Assert.notNull(domain, "domain must not null");
		getSession().saveOrUpdate(domain);
	}

	/**
	 * 
	 * @param domain
	 */
	public void delete(T domain) {
		Assert.notNull(domain, "domain must not null");
		getSession().delete(domain);
	}
	/**
	 * @param id
	 */
	public void delete (K id){
		Assert.notNull(id, "id must not null");
		delete(this.get(id));
	}

	/**
	 * 
	 * @param hql
	 * @param values
	 * @return List<T>
	 */
	@SuppressWarnings("unchecked")
	public List<T> query(String hql, Map<String, Object> values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 
	 * @param hql
	 * @param values
	 * @return List<T>
	 */
	@SuppressWarnings("unchecked")
	public List<T> query(String hql, Object... values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 
	 * @param page
	 * @param queryString
	 * @param values
	 * @return Page<T>
	 */
	@SuppressWarnings("unchecked")
	public Page<T> queryPage(Page<T> page, String queryString, Map<String, Object> values) {
		setPageTotalCount(page, queryString, values);

		String orderHql = createOrderBy(page, queryString);
		Query query = createQuery(String.format("%s%s", queryString, orderHql), values);

		page.setResult(
						setPageParameter(query, page).list()
					);
		return page;
	}

	/**
	 * 
	 * @param page
	 * @param hql
	 * @param values
	 * @return Page<T>
	 */
	@SuppressWarnings("unchecked")
	public Page<T> queryPage(Page<T> page, String hql, Object... values) {
		setPageTotalCount(page, hql, values);

		String orderHql = createOrderBy(page, hql);
		Query query = createQuery(String.format("%s%s", hql, orderHql), values);
		page.setResult(setPageParameter(query, page).list());
		return page;
	}

	/**
	 * 
	 * @param hql
	 * @param values
	 * @return Object
	 */
	public Object queryUnique(String hql, Map<String, Object> values) {
		return createQuery(hql, values).uniqueResult();
	}

	/**
	 * 
	 * @param hql
	 * @param values
	 * @return Object
	 */
	public Object queryUnique(String hql, Object... values) {
		return createQuery(hql, values).uniqueResult();
	}
	
	/**
	 * @param hql
	 * @param values
	 * @return int
	 */
	public int createHqlQuery(String hql, Object... values){
		return createQuery(hql, values).executeUpdate();
	}
	/**
	 * @param hql
	 * @param values
	 * @return int
	 */
	public int createHqlQuery(String hql, Map<String, Object> values){
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 
	 * @param top
	 * @param queryString
	 * @param values
	 * @return List<T>
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryTop(int top, String queryString, Object... values) {
		return createQuery(queryString, values).setMaxResults(top).list();
	}

	/**
	 * 
	 * @param top
	 * @param queryString
	 * @param values
	 * @return List<T>
	 */
	@SuppressWarnings("unchecked")
	public List<T> queryTop(int top, String queryString, Map<String, Object> values) {
		return createQuery(queryString, values).setMaxResults(top).list();
	}

	/**
	 * load
	 * @param id
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T lazyGet(K id) {
		Assert.notNull(id, "id must not be null");
		Assert.notNull(clazz, "clazz must not be null");

		return (T) getSession().load(clazz, id);
	}

	/**
	 * get
	 * @param id
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public T get(K id) {

		Assert.notNull(id, "id must not be null");

		return (T) getSession().get(clazz, id);
	}

	/**
	 * @param entity
	 * @return Object
	 */
	public Object reload(Object entity) {
		Assert.notNull(entity, "entity must not be null");
		return getSession().merge(entity);
	}

	public void emptyCache() {
		getHibernateTemplate().getSessionFactory().evict(clazz);
	}

	public void emptyCache(K id) {
		getHibernateTemplate().getSessionFactory().evict(clazz, id);
	}

	/**
	 * @param <T>
	 * @param query
	 * @param page
	 * @return Query
	 */
	protected Query setPageParameter(Query query, Page<T> page) {

		query.setFirstResult(page.getFirst() - 1);

		if (page.getPageSize() != -1) {
			query.setMaxResults(page.getPageSize());
			return query;
		}

		query.setMaxResults((int) page.getTotalCount());
		return query;
	}

	/**
	 * @param <T>
	 * @param page
	 * @param hql
	 * @param values
	 */
	protected void setPageTotalCount(Page<T> page, String hql, Object... values) {

		if (page.isAutoCount()) {
			long count = countHqlResult(hql, values);
			page.setTotalCount(count);
		}
	}

	/**
	 * @param <T>
	 * @param page
	 * @param hql
	 * @param values
	 */
	protected void setPageTotalCount(Page<T> page, String hql, Map<String, Object> values) {
		if (page.isAutoCount()) {
			long count = countHqlResult(hql, values);
			page.setTotalCount(count);
		}
	}

	/**
	 * @param hql
	 * @param values
	 * @return
	 */
	protected Long countHqlResult(String hql, Object... values) {
		if (isContainsDistinct(hql)) {
			String countHql = buildDistinctCountHql(hql);
			return (Long) createQuery(countHql, values).uniqueResult();
		}

		String countHql = buildCountHql(hql);
		return (Long) createQuery(countHql, values).uniqueResult();
	}

	/**
	 * @param hql
	 * @param values
	 * @return
	 */
	protected Long countHqlResult(String hql, Map<String, Object> values) {
		if (isContainsDistinct(hql)) {
			String countHql = buildDistinctCountHql(hql);
			return (Long) createQuery(countHql, values).uniqueResult();
		}

		String countHql = buildCountHql(hql);
		return (Long) createQuery(countHql, values).uniqueResult();
	}

	/**
	 * @param hql
	 * @return
	 */
	private boolean isContainsDistinct(String hql) {

		if (hql.indexOf("distinct") == -1) {
			return hql.contains("DISTINCT");
		}

		return true;
	}

	/**
	 * @param hql
	 * @return
	 */
	private String buildDistinctCountHql(String hql) {
		String selectItems = findSelectItems(hql);
		String fromHql = buildFromHql(hql);
		return String.format("SELECT COUNT(DISTINCT %s) %s", selectItems, fromHql);
	}

	/**
	 * @param hql
	 * @return
	 */
	private String findSelectItems(String hql) {
		Matcher m = DISTINCT_PATTERN.matcher(hql);
		if (m.matches()) {
			return m.group(SELECT_ITEMS_GROUP);
		}
		throw new UnsupportedOperationException("can't count this distintc hql");
	}

	/**
	 * @param hql
	 * @return
	 */
	private String buildCountHql(String hql) {
		String fromHql = buildFromHql(hql);
		return String.format("SELECT COUNT(*) %s", fromHql);
	}

	/**
	 * @param hql
	 * @return
	 */
	private String buildFromHql(String hql) {
		String fromHql = hql;
		if (fromHql.indexOf("from") == -1) {
			fromHql = StringUtils.substringAfter(fromHql, "FROM");
		}
		else {
			fromHql = StringUtils.substringAfter(fromHql, "from");
		}

		if (fromHql.indexOf("order by") == -1) {
			fromHql = StringUtils.substringBefore(fromHql, "ORDER BY");
		}
		else {
			fromHql = StringUtils.substringBefore(fromHql, "order by");
		}

		return "FROM" + fromHql;
	}

	protected String createOrderBy(final Page<T> page, String queryString) {
		Assert.notNull(page, "page must not null");

		StringBuilder orderBy = new StringBuilder();
		if (page.isOrderBySetted()) {
			String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
			String[] orderArray = StringUtils.split(page.getOrder(), ',');

			Assert.isTrue(orderByArray.length == orderArray.length, "orderBy.length must equal order.length");

			for (int i = 0; i < orderByArray.length; i++) {
				if (i > 0){
					orderBy.append(",");
				}
				orderBy.append(String.format("%s %s", orderByArray[i], orderArray[i]));
			}

			if (queryString.toLowerCase().indexOf("order by") < 0) {
				orderBy.insert(0, " order by ");
			}
			else {
				orderBy.insert(0, ",");
			}
		}

		return orderBy.toString();
	}

	private static final Pattern DISTINCT_PATTERN = Pattern.compile("select?(\\s+distinct{1}\\s{1}(.+)\\s{1})from{1}\\s+.+",
			Pattern.CASE_INSENSITIVE);
	private static final int SELECT_ITEMS_GROUP = 2;

}
