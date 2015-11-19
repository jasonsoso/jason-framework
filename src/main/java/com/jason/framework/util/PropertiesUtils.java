package com.jason.framework.util;

import java.util.Properties;

import org.springframework.util.Assert;

/**
 * 在初始化Spring，读取配置文件
 * @see SpringPropertyPlaceholderConfigurer
 * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
 * 读取 配置文件  辅助类
 * @author Jason
 */
public final class PropertiesUtils {
	
	private PropertiesUtils(){
	}
	
	/**
	 * 缓存配置文件中的属性
	 */
	private static Properties props = new Properties();
	
	
	
	public static Properties getProps() {
		return props;
	}
	/**
	 * 在spring初始化的时候，去设置静态文件属性
	 * @param props
	 */
	public static void setProps(Properties props) {
		Assert.notEmpty(props, "Properties(Map) must have entries");
		PropertiesUtils.props = props;
	}
	
	/**
	 * 根据key获取value
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getEntryValue(String key, String defaultValue) {
		Properties prop = getProps();
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
	public static String getEntryValue(String key) {
		Properties prop = getProps();
		if (prop != null) {
			return prop.getProperty(key);
		}
		return null;
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getEntryDefaultValue(String key, String defaultValue) {
		return getEntryValue(key, defaultValue);
	}

	/**
	 * 
	 * @param key
	 * @return int
	 */
	public static int getIntEntryValue(String key) {
		String value = getEntryValue(key);
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
		String value = getEntryValue(key);
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
