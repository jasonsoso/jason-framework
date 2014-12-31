package com.jason.framework.orm.redis.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * Jedis 持久层支撑类
 * @author Jason
 * @date 2014年12月31日
 */
public abstract class JedisRepositorySupport {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private JedisPool jedisPool;
	
	/**
	 * 获取Logger
	 * @return Logger
	 */
	protected  Logger getLogger() {
		return logger;
	}
	/**
	 * 获取连接池
	 * @return
	 */
	protected JedisPool getJedisPool() {
		return jedisPool;
	}
	
	

	/**
	 * 获取redis对象
	 * @return
	 */
	protected Jedis getJedis() {
		Jedis jedis = null;
        try {
            jedis = getJedisPool().getResource();
        } catch (Exception e) {
            throw new JedisConnectionException(e);
        }
        return jedis;
	}
	/**
	 * 释放Jedis对象到池
	 * 
	 * @param jedis Jedis对象
	 * @param isBroken 是否破坏的
	 */
	protected void returnResource(Jedis jedis, boolean isBroken) {
        if (jedis != null){
        	if (isBroken){
                getJedisPool().returnBrokenResource(jedis);
            }else{
                getJedisPool().returnResource(jedis);
            }
        }
    }
	

}
