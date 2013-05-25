package com.jason.framework.orm.hibernate.query;

import static java.lang.String.format;
import static java.util.Collections.unmodifiableMap;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.springframework.util.Assert.isTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Assembly  HQL
 * @author Jason
 *
 */
public class HQLQuery {

	public enum AndOr {
		AND("and"), OR("or");
		private final String meta;

		private AndOr(final String meta) {
			this.meta = meta;
		}
		public String asMeta() {
			return meta;
		}
	}

	public enum MatchType {
		EQ("="), NE("<>"), LIKE("like"), GT(">"), LT("<"), GE(">="), LE("<="), IN("in"), NI("not in");

		private final String meta;

		private MatchType(final String meta) {
			this.meta = meta;
		}
		public String asMeta() {
			return meta;
		}
	}
	/**
	 * hql
	 */
	private final StringBuilder query = new StringBuilder();
	/**
	 * values
	 */
	private final Map<String, Object> values = new HashMap<String, Object>();

	private int lengthOfTableName = 0;
	private boolean insertedWhere = false;

	/**
	 * hql
	 * @return String 
	 */
	public String hql() {
		if (!values.isEmpty() && !insertedWhere) {
			query.insert(lengthOfTableName, "where ");
			insertedWhere = true;
		}
		return query.toString();
	}

	/**
	 * values
	 * @return Map<String, Object>
	 */
	public Map<String, Object> values() {
		return unmodifiableMap(values);
	}

	/**
	 * from xxx
	 * @param table
	 * @return
	 */
	public HQLQuery table(String table) {
		String fromTable = table;

		if (fromTable.toLowerCase().indexOf("from") == -1) {
			fromTable = format("from %s ", fromTable);
		}
		if (!fromTable.endsWith(" ")) {
			fromTable = format("%s ", fromTable);
		}
		query.insert(0, fromTable);
		lengthOfTableName = fromTable.length();
		return this;
	}

	/**
	 * xxx is null or xxx not null
	 * 
	 * @param hql
	 * @return
	 */
	public HQLQuery more(final String hql) {
		query.append(hql);
		return this;
	}

	/**
	 * @param property
	 * @param propertyKey
	 * @param type
	 * @param propertyValue
	 * @param andOr
	 * @return
	 */
	public HQLQuery criteria(String property, String propertyKey, MatchType type, Object propertyValue, AndOr andOr) {
		if (null != propertyValue && isNotBlank(propertyValue.toString())) {

			String propertyKeyStr = transformKey(type, propertyKey);

			if (!values.isEmpty()) {
				query.append(format("%s ", andOr.asMeta()));
			}
			if (type == MatchType.IN || type == MatchType.NI) {
				query.append(format("%s %s (:%s) ", property, type.asMeta(), propertyKeyStr));
			}
			else {
				query.append(format("%s %s :%s ", property, type.asMeta(), propertyKeyStr));
			}

			values.put(propertyKeyStr, transformValue(type, propertyValue));
		}
		return this;
	}

	/**
	 * @param matchType
	 * @param key
	 * @return
	 */
	private String transformKey(MatchType matchType, String key) {
		String keyStr = key;
		keyStr = keyStr.replaceAll("\\.", "_");
		if (values.containsKey(keyStr)) {
			keyStr = format("%s_%s", keyStr, randomstring(4));
		}
		return keyStr;
	}

	/**
	 * like '%xxx%'
	 * @param matchType
	 * @param value
	 * @return
	 */
	private Object transformValue(MatchType matchType, Object value) {

		switch (matchType) {
		case LIKE:
			if (value.toString().indexOf("%") == -1) {
				return format("%%%s%%", value.toString());
			}
		default:
			return value;
		}
	}

	private static String RANDOM_SOURCE = "0123456789ABCDEF";

	/**
	 * @param length
	 * @return
	 */
	private String randomstring(int length) {
		Random random = new Random();
		StringBuilder buf = new StringBuilder();

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(RANDOM_SOURCE.length());
			buf.append(RANDOM_SOURCE.charAt(index));
		}

		return buf.toString();
	}

	/**
	 * equal( = )
	 * @param property
	 * @param propertyValue
	 * @return
	 */
	public HQLQuery eq(String property, Object propertyValue) {
		return criteria(property, property, MatchType.EQ, propertyValue, AndOr.AND);
	}

	/**
	 * not equal ( <> )
	 * @param property
	 * @param propertyValue
	 * @return
	 */
	public HQLQuery ne(String property, Object propertyValue) {
		return criteria(property, property, MatchType.NE, propertyValue, AndOr.AND);
	}

	/**
	 * Greater than ( > )
	 * @param property
	 * @param propertyValue
	 * @return
	 */
	public HQLQuery gt(String property, Object propertyValue) {
		return criteria(property, property, MatchType.GT, propertyValue, AndOr.AND);
	}

	/**
	 * Less than ( < )
	 * @param property
	 * @param propertyValue
	 * @param andOr
	 * @return
	 */
	public HQLQuery lt(String property, Object propertyValue) {
		return criteria(property, property, MatchType.LT, propertyValue, AndOr.AND);
	}

	/**
	 * Greater equal ( >= )
	 * @param property
	 * @param propertyValue
	 * @param andOr
	 * @return
	 */
	public HQLQuery ge(String property, Object propertyValue) {
		return criteria(property, property, MatchType.GE, propertyValue, AndOr.AND);
	}

	/**
	 * Less equal ( <= )
	 * @param property
	 * @param propertyValue
	 * @param andOr
	 * @return
	 */
	public HQLQuery le(String property, Object propertyValue) {
		return criteria(property, property, MatchType.LE, propertyValue, AndOr.AND);
	}

	/**
	 * like '%xxx%'
	 * @param property
	 * @param propertyValue
	 * @return
	 */
	public HQLQuery like(String property, String propertyValue) {
		return criteria(property, property, MatchType.LIKE, propertyValue, AndOr.AND);
	}

	/**
	 * in (xxx)
	 * @param property
	 * @param propertyValue
	 * @param andOr
	 * @return
	 */
	public HQLQuery in(String property, Object propertyValue) {
		return criteria(property, property, MatchType.IN, propertyValue, AndOr.AND);
	}

	/**
	 * not in (xxx)
	 * @param property
	 * @param propertyValue
	 * @param andOr
	 * @return
	 */
	public HQLQuery ni(String property, Object propertyValue) {
		return criteria(property, property, MatchType.NI, propertyValue, AndOr.AND);
	}

	/**
	 * @param property
	 * @param type
	 * @param value
	 * @return
	 */
	public HQLQuery or(String property, MatchType type, Object value) {
		return or(new String[] { property }, new MatchType[] { type }, new Object[] { value });
	}

	/**
	 * @param properties
	 * @param matchTypes
	 * @param values
	 * @return
	 */
	public HQLQuery or(String[] properties, MatchType[] matchTypes, Object[] values) {

		isTrue(properties.length == matchTypes.length ? matchTypes.length == values.length : false);

		List<Integer> avaliablePropertyValues = new ArrayList<Integer>();

		for (int i = 0; i < properties.length; i++) {
			Object value = values[i];

			if (null != value && isNotBlank(value.toString())) {
				avaliablePropertyValues.add(i);
			}
		}

		if (avaliablePropertyValues.isEmpty()) {
			return this;
		}

		if (avaliablePropertyValues.size() == 1) {
			int index = avaliablePropertyValues.get(0);
			Object value = transformValue(matchTypes[index], values[index]);
			return criteria(properties[index], properties[index], matchTypes[index], value, AndOr.OR);
		}

		if (!this.values.isEmpty()) {
			query.append(format("%s ", AndOr.AND.asMeta()));
		}

		StringBuilder buf = new StringBuilder();

		int cursor = 0;
		for (int index : avaliablePropertyValues) {

			String key = transformKey(matchTypes[index], properties[index]);
			Object value = transformValue(matchTypes[index], values[index]);

			if (matchTypes[index] == MatchType.IN || matchTypes[index] == MatchType.NI) {
				buf.append(format("(%s %s (:%s)) ", properties[index], matchTypes[index].asMeta(), key));
			}
			else {
				buf.append(format("(%s %s :%s) ", properties[index], matchTypes[index].asMeta(), key));
			}

			this.values.put(key, value);

			if (cursor < avaliablePropertyValues.size() - 1) {
				buf.append(format("%s ", AndOr.OR.asMeta()));
			}

			cursor++;
		}

		query.append(format("(%s) ", buf.substring(0, buf.length() - 1)));
		return this;
	}

	/**
	 * order by xxx
	 * @param orderBy
	 * @return
	 */
	public HQLQuery orderBy(final String orderBy) {
		String orderByString = orderBy;

		if (orderByString.toLowerCase().indexOf("order by") == -1) {
			orderByString = format("order by %s ", orderByString);
		}

		query.append(orderByString);
		return this;
	}

	/**
	 * group by xxx
	 * @param groupBy
	 * @return
	 */
	public HQLQuery groupBy(final String groupBy) {
		String groupByString = groupBy;

		if (groupByString.toLowerCase().indexOf("group by") == -1) {
			groupByString = format("group by %s ", groupByString);
		}

		query.append(groupByString);
		return this;
	}

}
