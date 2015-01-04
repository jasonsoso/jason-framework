package com.jason.framework.orm.redis;

import java.util.List;
import java.util.Map;


/**
 * KV 字符串 持久层
 * @author Jason
 * @date 2015-1-4
 */
public interface StringRepository {
	
	/**
	 * 插入key-value
	 * @param key
	 * @param value
	 */
	void set(String key, String value);
	
	/**
	 * 批量插入
	 * @param batchData
	 */
	void set(Map<String,String> batchData);

	/**
	 * 根据key获取value
	 * @param key
	 * @return
	 */
	String get(String key);
	
	/**
	 * 批量获取
	 * @param keys
	 * @return
	 */
	List<String> get(String[] keys);
	
	/**
	 * 根据key进行删除
	 * @param key
	 */
	void del(String key);

	/**
	 * 根据key判断是否存在
	 * @param key
	 * @return
	 */
	boolean exists(String key);
}
