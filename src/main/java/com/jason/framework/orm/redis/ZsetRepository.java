package com.jason.framework.orm.redis;

import java.util.Set;

/**
 * Key-Zset 有序集合 持久层
 * @author Jason
 * @date 2015-1-9
 */
public interface ZsetRepository {
	/**
	 * 添加Key-Zset记录
	 * @param key 
	 * @param val
	 * @param score
	 */
	void set(String key,String val,double score);
	
	/**
	 * 删除
	 * @param key
	 * @param val
	 */
	void del(String key,String val);
	
	/**
	 * 根据Key查询个数
	 * @param key
	 * @return
	 */
	Long size(String key);
	
	/**
	 * 根据key有序查询
	 * 降序，从大到小
	 * @param key
	 * @return
	 */
	Set<String> getListDesc(String key);
	
	/**
	 * 根据key有序查询
	 * 升序，从小到大
	 * @param key
	 * @return
	 */
	Set<String> getListAsc(String key);
}
