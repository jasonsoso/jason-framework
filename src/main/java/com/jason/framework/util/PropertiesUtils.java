package com.jason.framework.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取 配置文件  辅助类
 * @author Jason
 * @date 2013-2-11 下午07:45:45
 */
public final class PropertiesUtils {
	
	public PropertiesUtils(){}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtils.class);
	
	/**
	 * 缓存配置文件
	 * String:配置文件路径
	 * Properties:java.util.Properties
	 */
	private static Map<String,Properties> propMap = new HashMap<String,Properties>();

	
	/**
	 * 根据properties的属性文件路径 读取生成java.util.Properties对象
	 * @param propName 属性文件名 properties格式 默认文件名:META-INF/config/framework.properties
	 * @return java.util.Properties
	 */
	public static Properties getProperties(String propName) {
		String propNameStr = propName;
		if (propNameStr == null){
			propNameStr = "META-INF/config/framework.properties";
		}
		//判断是否包含key[propNameStr]，有则直接返回 ，否则读取
		if (propMap.containsKey(propNameStr)){
			return (Properties) propMap.get(propNameStr);
		}
		Properties props = new Properties();
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			props.load(loader.getResourceAsStream(propNameStr));
			propMap.put(propNameStr, props);
		} catch (Exception e) {
			LOGGER.error("getProperties(String) error",e);
		}
		return props;
	}

	/**
	 * 
	 * @param propName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getEntryValue(String propName, String key, String defaultValue) {
		Properties prop = getProperties(propName);
		if (prop != null) {
			return prop.getProperty(key, defaultValue);
		}
		return null;
	}

	/**
	 * 
	 * @param propName
	 * @param key
	 * @return
	 */
	public static String getEntryValue(String propName, String key) {
		Properties prop = getProperties(propName);
		if (prop != null) {
			return prop.getProperty(key);
		}
		return null;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public static String getEntryValue(String key) {
		return getEntryValue(null, key);
	}
	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getEntryDefaultValue(String key, String defaultValue) {
		return getEntryValue(null, key, defaultValue);
	}

	/**
	 * 
	 * @param key
	 * @return int
	 */
	public static int getIntEntryValue(String key) {
		String value = getEntryValue(null, key);
		int intValue = 0;
		if (value != null) {
			try {
				intValue = Integer.parseInt(value.trim());
			} catch (NumberFormatException ex) {
			}
		}
		return intValue;
	}

	/**
	 * 
	 * @param key
	 * @return long
	 */
	public static long getLongEntryValue(String key) {
		String value = getEntryValue(null, key);
		long longValue = 0;
		if (value != null) {
			try {
				longValue = Long.parseLong(value.trim());
			} catch (NumberFormatException ex) {
			}
		}
		return longValue;
	}
}
