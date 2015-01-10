package com.jason.framework.orm.redis.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import com.jason.framework.orm.redis.StringRepository;
import com.jason.framework.orm.redis.support.JedisRepositorySupport;

/**
 * Redis KV 类型  字符串的持久层
 * @author Jason
 * @date 2015-1-9
 */
@Repository
public class RedisStringRepository extends JedisRepositorySupport implements StringRepository {

	@Override
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
	@Override
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
	@Override
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
	@Override
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
	
	@Override
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
	
	@Override
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
