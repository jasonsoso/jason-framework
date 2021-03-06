package com.jason.framework.orm.hibernate;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.Assert;

import com.jason.framework.util.ExceptionUtils;


/**
 * Hibernate 相关辅助类
 * @author Jason
 * @date 2013-5-25 下午09:18:47
 */
public final class HibernateHelper {

	/**
	 * 
	 * @param <T>
	 * @param <V>
	 * @param srcObjects
	 * @param propertyValues
	 * @param clazz
	 */
	public static <T, V> void mergeByIds(Collection<T> srcObjects, Collection<V> propertyValues, Class<T> clazz) {
		merge(srcObjects, propertyValues, "id", clazz);
	}

	/**
	 * 
	 * @param <T>
	 * @param <V>
	 * @param srcObjects
	 * @param propertyValues
	 * @param propertyName
	 * @param clazz
	 * 
	 * 
	 * cannot use on hibernate cascade=save-or-update
	 */
	public static <T, V> void merge(Collection<T> srcObjects, Collection<V> propertyValues, String propertyName, Class<T> clazz) {
		Assert.notNull(srcObjects, "srcObjects must not null");
		Assert.notNull(propertyValues, "propertyValues must not null");
		Assert.notNull(clazz, "clazz must not null");

		if (propertyValues.isEmpty()) {
			srcObjects.clear();
			return;
		}

		Iterator<T> it = srcObjects.iterator();

		try {
			while (it.hasNext()) {
				T element = it.next();
				Object propertyValue = BeanUtils.getProperty(element, propertyName);

				// if contains,remove from propertyValues
				if (propertyValues.contains(propertyValue)) {
					propertyValues.remove(propertyValue);
				}

				// remove from srcObjects
				else {
					it.remove();
				}
			}

			for (V property : propertyValues) {
				if (property != null) {
					T element = clazz.newInstance();
					BeanUtils.setProperty(element, propertyName, property);
					srcObjects.add(element);
				}
			}

		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}

	}

	private HibernateHelper() {
	}
}
