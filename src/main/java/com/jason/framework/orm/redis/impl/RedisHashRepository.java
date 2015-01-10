package com.jason.framework.orm.redis.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;

import com.jason.framework.orm.redis.HashRepository;
import com.jason.framework.orm.redis.support.JedisRepositorySupport;
import com.jason.framework.util.Collections3;

/**
 *  Redis K-Hash 类型  哈希的持久层
 * @author Jason
 * @date 2015-1-9
 */
@Repository
public class RedisHashRepository extends JedisRepositorySupport implements HashRepository {
	
	@Override
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
	@Override
	public void set(String key, Map<String, String> map) {
		boolean isBroken = false;
		Jedis jedis = super.getJedis();
		try {
			if (!Collections3.isEmpty(map)) {
				jedis.hmset(key, map);
			}else{
				super.getLogger().debug("The map can't be empty!");
			}
		} catch (Exception e) {
			isBroken = true;
			super.getLogger().error("hmset key error!", e);
		} finally {
			super.returnResource(jedis, isBroken);
		}
	}

	@Override
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

	@Override
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
			super.getLogger().error("hget key error!", e);
		} finally {
			super.returnResource(jedis, isBroken);
		}
		return value;
	}
	
	@Override
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
	@Override
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
	@Override
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
