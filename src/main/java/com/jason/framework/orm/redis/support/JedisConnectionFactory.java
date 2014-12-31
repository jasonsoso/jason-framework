
package com.jason.framework.orm.redis.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisConnectionException;
/**
 * redis 连接工厂类
 * Connection factory creating <a href="http://github.com/xetorthio/jedis">Jedis</a> based connections.
 * @author Jason
 * @date 2014年12月31日
 */
public class JedisConnectionFactory implements FactoryBean<JedisPool>,InitializingBean, DisposableBean {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(JedisConnectionFactory.class);

	private String hostName = "localhost";
	private int port = Protocol.DEFAULT_PORT;
	private int timeout = Protocol.DEFAULT_TIMEOUT;
	private String password;

	private JedisPool pool = null;
	private JedisPoolConfig poolConfig = new JedisPoolConfig();

	public JedisConnectionFactory() {}

	/**
	 * Constructs a new <code>JedisConnectionFactory</code> instance using the given pool configuration.
	 * 
	 * @param poolConfig pool configuration
	 */
	public JedisConnectionFactory(JedisPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}

	@Override
	public void afterPropertiesSet() {
		
		pool = new JedisPool(poolConfig, hostName, port, timeout,password);
	}
	@Override
	public void destroy() {
		if (pool != null) {
			try {
				pool.destroy();
			} catch (Exception ex) {
				LOGGER.warn("Cannot properly close Jedis pool", ex);
			}
			pool = null;
		}
	}
	

	@Override
	public JedisPool getObject() throws Exception {
		return pool;
	}

	@Override
	public Class<?> getObjectType() {
		return this.pool != null ? this.pool.getClass() : JedisPool.class;
		
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	
	
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPort() {
		return port;

	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public JedisPoolConfig getPoolConfig() {
		return poolConfig;
	}
	public void setPoolConfig(JedisPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}
}
