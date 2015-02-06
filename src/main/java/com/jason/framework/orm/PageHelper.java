package com.jason.framework.orm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;



/**
 * 分页帮助类 
 * @author Jason
 * @data 2014-6-30 下午02:53:49
 */
public class PageHelper {
	private PageHelper(){
	}

	private static final Pattern DISTINCT_PATTERN = Pattern.compile(
																		"select?(\\s+distinct{1}\\s{1}(.+)\\s{1})from{1}\\s+.+",
																		Pattern.CASE_INSENSITIVE
																	);
	private static final int SELECT_ITEMS_GROUP = 2;

	/**
	 * 
	 * @param sql
	 * @return
	 */
	public static String buildCountSQL(String sql) {
		if (isContainsDistinct(sql)) {
			return buildDistinctCountSql(sql);
		}
		return buildCountSql(sql);
	}

	/**
	 * 
	 * @param page
	 * @param sql
	 * @return
	 */
	public static String buildOrderByString(Page<?> page, String sql) {
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

			if (sql.toLowerCase().indexOf("order by") < 0) {
				orderBy.insert(0, " order by ");
			}else {
				orderBy.insert(0, ",");
			}
		}

		return orderBy.toString();
	}

	/**
	 * @param sql
	 * @return
	 */
	private static boolean isContainsDistinct(String sql) {

		if (sql.indexOf("distinct") == -1) {
			return sql.contains("DISTINCT");
		}

		return true;
	}

	/**
	 * @param sql
	 * @return
	 */
	private static String buildDistinctCountSql(String sql) {
		String selectItems = findSelectItems(sql);
		String fromHql = buildFromHql(sql);
		return String.format("select count(distinct %s) %s", selectItems, fromHql);
	}

	/**
	 * @param sql
	 * @return
	 */
	private static String findSelectItems(String sql) {
		Matcher m = DISTINCT_PATTERN.matcher(sql);
		if (m.matches()) {
			return m.group(SELECT_ITEMS_GROUP);
		}
		throw new UnsupportedOperationException("can't count this distintc hql");
	}

	/**
	 * @param sql
	 * @return
	 */
	private static String buildCountSql(String sql) {
		String fromHql = buildFromHql(sql);
		return String.format("select count(*) %s", fromHql);
	}

	/**
	 * @param sql
	 * @return
	 */
	private static String buildFromHql(String sql) {
		String fromHql = sql;
		if (fromHql.indexOf("from") == -1) {
			fromHql = StringUtils.substringAfter(fromHql, "FROM");
		}else{
			fromHql = StringUtils.substringAfter(fromHql, "from");
		}

		if (fromHql.indexOf("order by") == -1) {
			fromHql = StringUtils.substringBefore(fromHql, "ORDER BY");
		}else {
			fromHql = StringUtils.substringBefore(fromHql, "order by");
		}

		return "from" + fromHql;
	}

}
