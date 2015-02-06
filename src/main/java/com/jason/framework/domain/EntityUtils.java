package com.jason.framework.domain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.jason.framework.util.DateHelper;
import com.jason.framework.util.ExceptionUtils;
import com.jason.framework.util.IOUtils;

/**
 * 实体类 辅助类
 * @author Jason
 * @date 2013-5-25 下午08:28:41
 */
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


	/**
	 * 对象转换为字符串
	 * @param actual
	 * @return
	 * @throws Exception
	 */
	public static String toString(Object actual) {
		return (actual==null) ? "" : actual.toString();
	}
	
	/**
	 * 对象转换为Long对象
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Long toLong(Object obj){
		Long l = 0L;
		if(obj==null){
			return l;
		}
		String str = toString(obj);
		try {
			l = Long.parseLong(str);
		} catch (Exception e) {
			l=0L;
		}
		return l;
	}
	/**
	 * 对象转换为long
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static int toint(Object obj){
		int i = 0;
		try {
			i = Integer.valueOf(String.valueOf(obj)).intValue();
		} catch (Exception e) {
			i=0;
		}
		return i;
	}
	/**
	 * 对象转换为long
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static long tolong(Object obj){
		long l = 0;
		try {
			l = Long.valueOf(String.valueOf(obj)).longValue();
		} catch (Exception e) {
			l=0;
		}
		return l;
	}
	/**
	 * 对象转换为double
	 * @param obj
	 * @return
	 */
	public static double todouble(Object obj){
		double d = 0;
		try {
			d = Double.valueOf(String.valueOf(obj)).doubleValue();
		} catch (Exception e) {
			d=0;
		}
		return d;
	}
	/**
	 * 对象转换为Date
	 * @param obj
	 * @return
	 */
	public static Date toDate(Object actual){
		String dateStr = toString(actual);
		return DateHelper.toDate(dateStr, DateHelper.YYYYMMDD_HHMMSS);
	}
	/**
	 * long 转换为 Date
	 * @param actual
	 * @return
	 */
	public static Date toDate(long actual){
		Date date = new Date(actual);
		return date;
	}
	
	
	private EntityUtils() {
	}

}
