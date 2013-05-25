package com.jason.framework.domain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.jason.framework.util.ExceptionUtils;
import com.jason.framework.util.IOUtils;


public final class EntityUtils {

	/**
	 * 
	 * @param <T>
	 * @param actual
	 * @param safe
	 * @return
	 */
	public static <T> T nullSafe(T actual, T safe) {
		return null == actual ? safe : actual;
	}

	/**
	 * 
	 * @param actual
	 * @param notEmpty
	 * @return
	 */
	public static String emptySafe(String actual, String notEmpty) {
		return StringUtils.isNotBlank(actual) ? actual : notEmpty;
	}

	/**
	 * 
	 * @param entity
	 * @return
	 */
	public static Object copyProperties(Object entity) {
		try {
			return BeanUtils.cloneBean(entity);
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}
	}

	/**
	 * 
	 * @param <T>
	 * @param entity
	 * @return
	 */
	public static <T extends Serializable> Object clone(T entity) {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;

		try {
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bao);
			oos.writeObject(entity);
			oos.flush();

			ByteArrayInputStream bai = new ByteArrayInputStream(bao.toByteArray());
			ois = new ObjectInputStream(bai);
			Object clone = ois.readObject();

			return clone;
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		} finally {
			IOUtils.freeQuietly(oos, ois);
		}
	}

	/**
	 * 
	 * @param <T>
	 * @param obj
	 * @param out
	 */
	public static <T extends Serializable> void serialize(T obj, OutputStream out) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(out);
			oos.writeObject(obj);
			oos.flush();
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		} finally {
			IOUtils.freeQuietly(oos, out);
		}
	}

	/**
	 * 
	 * @param in
	 * @return
	 */
	public static Object deserialize(InputStream in) {
		ObjectInputStream ooi = null;
		try {
			ooi = new ObjectInputStream(in);
			return ooi.readObject();
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		} finally {
			IOUtils.freeQuietly(ooi, in);
		}
	}

	private EntityUtils() {
	}

}
