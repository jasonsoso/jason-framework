package com.jason.framework.orm.redis;

import java.util.Map;

/**
 * HashMap 持久层
 * @author Jason
 * @date 2015-1-4
 */
public interface HashMapRepository {
	/**
	 * 插入Key-HashMap
	 * @param key
	 * @param mapKey
	 * @param mapVal
	 */
	void set(String key, String mapKey, String mapVal);

	/**
	 * 批量 插入Key-HashMap
	 * @param key
	 * @param map
	 */
	void set(String key, Map<String, String> map);

	/**
	 * 根据key获取HashMap
	 * @param key
	 * @return
	 */
	Map<String, String> get(String key);

	/**
	 * 根据key和mapKey获取value
	 * @param key
	 * @param mapKey
	 * @return
	 */
	String get(String key, String mapKey);

	/**
	 * 根据key和mapKey进行删除
	 * @param key
	 * @param mapKey
	 */
	void del(String key, String mapKey);

	/**
	 * 根据key和mapKey[] 批量删除
	 * @param key
	 * @param mapKey
	 */
	void del(String key, String[] mapKey);

	/**
	 * 根据key 和 mapKey 判断是否存在
	 * @param key
	 * @param mapKey
	 * @return
	 */
	boolean exists(String key, String mapKey);
}
