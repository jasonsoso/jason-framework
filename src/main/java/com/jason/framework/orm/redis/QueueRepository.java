package com.jason.framework.orm.redis;

import java.util.List;

/**
 * queue 队列 持久层
 * @author Jason
 * @date 2015-1-9
 */
public interface QueueRepository {
	
	/**
	 * 统计kege的个数
	 * @param key
	 * @return
	 */
	Long size(String key);
	
	/**
	 * 添加到队列头
	 * @param key
	 * @param data
	 */
	void pushFront(String key,String data);
	
	/**
	 * 批量添加到队列头
	 * 按顺序排序，数组【0】为先插入，数组【1】后插入
	 * @param key
	 * @param datas
	 */
	void pushFront(String key,String[] datas);
	
	/**
	 * 添加到队列尾
	 * @param key
	 * @param data
	 */
	void pushBack(String key,String data);
	
	/**
	 * 批量添加到队列尾
	 * 按顺序排序，数组【0】为先插入，数组【1】后插入
	 * @param key
	 * @param datas
	 */
	void pushBack(String key,String[] datas);
	
	/**
	 * 移除队列头
	 * @param key
	 * @param str
	 */
	String popFront(String key);
	
	/**
	 * 移除队列尾
	 * @param key
	 */
	String popBack(String key);
	
	/**
	 * 根据key获取整一个队列List
	 * @param key
	 * @return
	 */
	List<String> getList(String key);
}
