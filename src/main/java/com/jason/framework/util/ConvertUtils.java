package com.jason.framework.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

public final class ConvertUtils {

	/**
	 * 
	 * @param <T>
	 * @param beans
	 * @param keyPropertyName
	 * @param valuePropertyName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T, K, V> void convertPropertyToMap(Collection<T> beans,
														String keyPropertyName,
														String valuePropertyName,
														Map<K, V> target) {

		Assert.notNull(beans, "beans must not null");
		Assert.hasLength(keyPropertyName, "keyPropertyName must not blank");
		Assert.hasLength(valuePropertyName, "valuePropertyName must not blank");
		Assert.notNull(target, "target must not null");

		try {
			for (T bean : beans) {
				K pk = (K) BeanUtils.getProperty(bean, keyPropertyName);
				V pv = (V) BeanUtils.getProperty(bean, valuePropertyName);
				target.put(pk, pv);
			}

		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}
	}

	/**
	 * 
	 * @param <T>
	 * @param beans
	 * @param propertyName
	 * @param separator
	 * @return
	 */
	public static <T> String convertPropertyToString(Collection<T> beans,
														String propertyName,
														String separator) {
		Assert.notNull(beans, "beans must not null");
		Assert.hasLength(propertyName, "propertyName must not blank");

		List<String> target = new ArrayList<String>();
		convertPropertyToList(beans, propertyName, separator, target);
		return StringUtils.join(target, separator);
	}

	/**
	 * 
	 * @param <T>
	 * @param beans
	 * @param propertyName
	 * @param separator
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T, V> void convertPropertyToList(Collection<T> beans,
													String propertyName,
													String separator,
													List<V> target) {

		Assert.notNull(beans, "beans must not null");
		Assert.hasLength(propertyName, "propertyName must not blank");
		Assert.notNull(target, "target must not null");

		try {

			for (T bean : beans) {
				target.add((V) BeanUtils.getProperty(bean, propertyName));
			}

		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}
	}

	private ConvertUtils(){
	}
}
