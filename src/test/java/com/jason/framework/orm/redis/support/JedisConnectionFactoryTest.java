package com.jason.framework.orm.redis.support;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.jason.framework.AbstractTestBase;

public class JedisConnectionFactoryTest extends AbstractTestBase{
	@Autowired
	private JedisPool jedisPool;
	
	@Test
	public void testIsNull(){
		Jedis jedis = jedisPool.getResource();
		
		System.out.println("jedisPool"+jedisPool);
		System.out.println("jedis:"+jedis);
		jedis.set("key", "value");
		String v = jedis.get("key");
		Assert.assertEquals(v, "value");
		
	}
}
