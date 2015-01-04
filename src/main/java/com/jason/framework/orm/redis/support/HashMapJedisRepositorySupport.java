package com.jason.framework.orm.redis.support;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

import com.jason.framework.util.Collections3;

/**
 * Redis HashMap 哈希 的持久层 支撑类
 * @author Jason
 * @date 2014年12月31日
 */
public class HashMapJedisRepositorySupport extends JedisRepositorySupport{
	
	/**
	 * 插入Key-HashMap
	 * 
	 * @param key
	 * @param mapKey
	 * @param mapVal
	 */
	public void set(String key, String mapKey, String mapVal) {
		boolean isBroken = false;
		Jedis jedis = super.getJedis();
		try {
			jedis.hset(key, mapKey, mapVal);
		} catch (Exception e) {
			isBroken = true;
			super.getLogger().error("hset key error!", e);
		} finally {
			super.returnResource(jedis, isBroken);
		}
	}
	
	/**
	 * 插入Key-HashMap
	 * 
	 * @param key
	 * @param map
	 */
	public void set(String key, Map<String, String> map) {
		boolean isBroken = false;
		Jedis jedis = super.getJedis();
		try {
			if (!Collections3.isEmpty(map)) {
				jedis.hmset(key, map);
			}
		} catch (Exception e) {
			isBroken = true;
			super.getLogger().error("hmset key error!", e);
		} finally {
			super.returnResource(jedis, isBroken);
		}
	}

	/**
	 * 根据key获取HashMap
	 * @param key
	 * @return
	 */
	public Map<String, String> get(String key) {
		Map<String, String> map = new HashMap<String, String>();
		boolean isBroken = false;
		Jedis jedis = super.getJedis();
		try {
			map = jedis.hgetAll(key);
		} catch (Exception e) {
			isBroken = true;
			super.getLogger().error("hgetAll key error!", e);
		} finally {
			super.returnResource(jedis, isBroken);
		}
		return map;
	}

	/**
	 * 根据key和mapKey获取value
	 * 
	 * @param key
	 * @param mapKey
	 * @return
	 */
	public String get(String key, String mapKey) {
		String value = null;
		boolean isBroken = false;
		Jedis jedis = super.getJedis();
		try {
			if (exists(key,mapKey)) {
				value = jedis.hget(key, mapKey);
			}
		} catch (Exception e) {
			isBroken = true;
		} finally {
			super.returnResource(jedis, isBroken);
		}
		return value;
	}
	
	/**
	 * 根据key和mapKey进行删除
	 * 
	 * @param key
	 * @param mapKey
	 */
	public void del(String key, String mapKey) {
		boolean isBroken = false;
		Jedis jedis = super.getJedis();
		try {
			jedis.hdel(key, mapKey);
		} catch (Exception e) {
			isBroken = true;
			super.getLogger().error("hdel key error!", e);
		} finally {
			super.returnResource(jedis, isBroken);
		}
	}
	/**
	 * 根据key和mapKey[] 批量删除
	 * @param key
	 * @param mapKey
	 */
	public void del(String key, String[] mapKey) {
		boolean isBroken = false;
		Jedis jedis = super.getJedis();
		try {
			jedis.hdel(key, mapKey);
		} catch (Exception e) {
			isBroken = true;
			super.getLogger().error("hdel key error!", e);
		} finally {
			super.returnResource(jedis, isBroken);
		}
	}
	
	/**
	 * 根据key 和 mapKey 判断是否存在
	 * 
	 * @param key
	 * @param mapKey
	 * @return true 代表有此数据，false代表没有
	 */
	public boolean exists(String key, String mapKey) {
		Jedis jedis = super.getJedis();
		boolean isBroken = false;
		try {
			return jedis.hexists(key, mapKey);
		} catch (Exception e) {
			isBroken = true;
			super.getLogger().error("hexists key error!", e);
		} finally {
			super.returnResource(jedis, isBroken);
		}
		return false;
	}
}
