package com.jason.framework.orm.redis.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;

import com.jason.framework.orm.redis.QueueRepository;
import com.jason.framework.orm.redis.support.JedisRepositorySupport;

/**
 * Redis K-Queue 类型  队列的持久层
 * @author Jason
 * @date 2015-1-9
 */
@Repository
public class RedisQueueRepository extends JedisRepositorySupport implements QueueRepository {

	@Override
	public void pushFront(String key, String data) {
		Jedis jedis = super.getJedis();
        boolean isBroken = false;
        try {
            jedis.lpush(key, data);
        } catch (Exception e) {
            isBroken = true;
            super.getLogger().error("lpush key error!", e);
        } finally {
        	super.returnResource(jedis, isBroken);
        }
	}

	@Override
	public void pushFront(String key, String[] datas) {
		Jedis jedis = super.getJedis();
        boolean isBroken = false;
        try {
            jedis.lpush(key, datas);
        } catch (Exception e) {
            isBroken = true;
            super.getLogger().error("lpush key error!", e);
        } finally {
        	super.returnResource(jedis, isBroken);
        }
	}

	@Override
	public void pushBack(String key, String data) {
		Jedis jedis = super.getJedis();
        boolean isBroken = false;
        try {
            jedis.rpush(key, data);
        } catch (Exception e) {
            isBroken = true;
            super.getLogger().error("rpush key error!", e);
        } finally {
        	super.returnResource(jedis, isBroken);
        }
	}

	@Override
	public void pushBack(String key, String[] datas) {
		Jedis jedis = super.getJedis();
        boolean isBroken = false;
        try {
            jedis.rpush(key, datas);
        } catch (Exception e) {
            isBroken = true;
            super.getLogger().error("rpush key error!", e);
        } finally {
        	super.returnResource(jedis, isBroken);
        }
	}

	@Override
	public String popFront(String key) {
		String val = null;
		Jedis jedis = super.getJedis();
        boolean isBroken = false;
        try {
        	val = jedis.lpop(key);
        } catch (Exception e) {
            isBroken = true;
            super.getLogger().error("lpop key error!", e);
        } finally {
        	super.returnResource(jedis, isBroken);
        }
        return  val;
	}

	@Override
	public String popBack(String key) {
		String val = null;
		Jedis jedis = super.getJedis();
        boolean isBroken = false;
        try {
        	val = jedis.rpop(key);
        } catch (Exception e) {
            isBroken = true;
            super.getLogger().error("rpop key error!", e);
        } finally {
        	super.returnResource(jedis, isBroken);
        }
        return val;
	}

	@Override
	public List<String> getList(String key) {
		List<String> list = new ArrayList<String>();
        boolean isBroken = false;
        Jedis jedis = super.getJedis();
        try {
             list = jedis.lrange(key, 0, -1);
        } catch (Exception e) {
            isBroken = true;
            super.getLogger().error("lrange key error!", e);
        } finally {
        	super.returnResource(jedis, isBroken);
        }
        return list;
	}

	@Override
	public Long size(String key) {
		Long size = null;
        boolean isBroken = false;
        Jedis jedis = super.getJedis();
        try {
            size = jedis.llen(key);
        } catch (Exception e) {
            isBroken = true;
            super.getLogger().error("llen key error!", e);
        } finally {
        	super.returnResource(jedis, isBroken);
        }
		return size;
	}

}
