package com.jason.framework.orm.redis.impl;

import java.util.Set;

import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;

import com.jason.framework.orm.redis.ZsetRepository;
import com.jason.framework.orm.redis.support.JedisRepositorySupport;

/**
 *  Redis Key-Zset 有序集合持久层
 * @author Jason
 * @date 2015-1-9
 */
@Repository
public class RedisZsetRepository extends JedisRepositorySupport implements ZsetRepository {

	@Override
	public void set(String key, String val, double score) {
		Jedis jedis = super.getJedis();
		boolean isBroken = false;
		try {
			jedis.zadd(key, score, val);
		} catch (Exception e) {
			isBroken = true;
			super.getLogger().error("zadd key error!", e);
		} finally {
			super.returnResource(jedis, isBroken);
		}
	}

	@Override
	public Long size(String key) {
		Long l = null;
		Jedis jedis = super.getJedis();
		boolean isBroken = false;
		try {
			l = jedis.zcard(key);
		} catch (Exception e) {
			isBroken = true;
			super.getLogger().error("zcard key error!", e);
		} finally {
			super.returnResource(jedis, isBroken);
		}
		return l;
	}

	@Override
	public Set<String> getListDesc(String key) {
		Set<String> set = null;
		Jedis jedis = super.getJedis();
		boolean isBroken = false;
		try {
			set = jedis.zrevrange(key, 0, -1);
		} catch (Exception e) {
			isBroken = true;
			super.getLogger().error("zcard key error!", e);
		} finally {
			super.returnResource(jedis, isBroken);
		}
		return set;
	}

	@Override
	public Set<String> getListAsc(String key) {
		Set<String> set = null;
		Jedis jedis = super.getJedis();
		boolean isBroken = false;
		try {
			set = jedis.zrange(key, 0, -1);
		} catch (Exception e) {
			isBroken = true;
			super.getLogger().error("zcard key error!", e);
		} finally {
			super.returnResource(jedis, isBroken);
		}
		return set;
	}

	@Override
	public void del(String key, String val) {
		Jedis jedis = super.getJedis();
		boolean isBroken = false;
		try {
			jedis.zrem(key, val);
		} catch (Exception e) {
			isBroken = true;
			super.getLogger().error("zadd key error!", e);
		} finally {
			super.returnResource(jedis, isBroken);
		}
	}
}
