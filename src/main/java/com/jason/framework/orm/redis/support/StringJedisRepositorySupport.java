package com.jason.framework.orm.redis.support;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * Redis 字符串的持久层 支撑类
 * @author Jason
 * @date 2014年12月31日
 */
public class StringJedisRepositorySupport extends JedisRepositorySupport{
	
	/**
	 * 插入key-value
	 * @param key
	 * @param value
	 */
	public void set(String key,String value){
		boolean isBroken = false;
		Jedis jedis = super.getJedis();
		try {
			jedis.set(key, value);
		} catch (Exception e) {
			isBroken = true;
			super.getLogger().error("set key error!", e);
		} finally {
			super.returnResource(jedis, isBroken);
		}
	}
	/**
	 * 批量插入
	 * @param batchData
	 */
	public void set(Map<String,String> batchData){
		boolean isBroken = false;
		Jedis jedis = super.getJedis();
		try {
			Pipeline pipeline = jedis.pipelined();
            for(Map.Entry<String,String> element:batchData.entrySet()){
            	pipeline.set(element.getKey(),element.getValue());
            }
            pipeline.sync();
		} catch (Exception e) {
			isBroken = true;
			super.getLogger().error("set keys error!", e);
		} finally {
			super.returnResource(jedis, isBroken);
		}
	}
	/**
	 * 根据key获取value
	 * @param key
	 * @return
	 */
	public String get(String key) {
		boolean isBroken = false;
		String value = null;
		Jedis jedis = super.getJedis();
		try {
			value = jedis.get(key);
		} catch (Exception e) {
			isBroken = true;
			super.getLogger().error("get key error!", e);
		} finally {
			super.returnResource(jedis, isBroken);
		}
		return value;
	}
	/**
	 * 批量获取
	 * @param keys
	 * @return
	 */
	public List<String> get(String[] keys) {
		boolean isBroken = false;
		List<String> value = null;
		Jedis jedis = super.getJedis();
		try {
			value = jedis.mget(keys);
		} catch (Exception e) {
			isBroken = true;
			super.getLogger().error("get keys error!", e);
		} finally {
			super.returnResource(jedis, isBroken);
		}
		return value;
	}
	
	/**
	 * 根据key进行删除
	 * @param key
	 */
	public void  del(String key) {
		boolean isBroken = false;
		Jedis jedis = super.getJedis();
		try {
			jedis.del(key);
		} catch (Exception e) {
			isBroken = true;
			super.getLogger().error("del key error!", e);
		} finally {
			super.returnResource(jedis, isBroken);
		}
	}
	
	/**
	 * 根据key判断是否存在
	 * @param key
	 * @return
	 */
	public boolean exists(String key) {
        Jedis jedis = super.getJedis();
        boolean isBroken = false;
        try {
            return jedis.exists(key);
        } catch (Exception e) {
            isBroken = true;
            super.getLogger().error("exists key error!", e);
        } finally {
        	super.returnResource(jedis, isBroken);
        }
        return false;
    }
}
