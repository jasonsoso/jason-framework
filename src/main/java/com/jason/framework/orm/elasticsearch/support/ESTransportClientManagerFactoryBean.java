package com.jason.framework.orm.elasticsearch.support;

import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Elasticsearch TransportClient 管理工厂类
 * @author Jason
 */
public class ESTransportClientManagerFactoryBean  implements FactoryBean<TransportClient>, InitializingBean, DisposableBean {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ESTransportClientManagerFactoryBean.class);

	/**
	 * 客户端
	 */
	private TransportClient transportClient;
	
	/**
	 * 参数注入：IP + Port
	 */
	private Map<String, Integer> transportAddresses;
	
	/**
	 * 参数注入：cluster.name
	 */
	private String clusterName = "elasticsearch";
	private String clientPingTimeout = "10s";
	private String clientNodesSamplerInterval = "10s";
	
	
	public void setTransportAddresses(Map<String, Integer> transportAddresses) {
		this.transportAddresses = transportAddresses;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public void setClientPingTimeout(String clientPingTimeout) {
		this.clientPingTimeout = clientPingTimeout;
	}
	public void setClientNodesSamplerInterval(String clientNodesSamplerInterval) {
		this.clientNodesSamplerInterval = clientNodesSamplerInterval;
	}
	
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		LOGGER.info("Initializing Elasticsearch TransportClient Manager");
		initElasticsearchTransport();
	}

	/**
	 * 初始化Transport通讯
	 */
	private void initElasticsearchTransport() {
		Settings settings = ImmutableSettings.settingsBuilder()
								.put("cluster.name", clusterName)
								.put("client.transport.ping_timeout", clientPingTimeout)
								.put("client.transport.nodes_sampler_interval", clientNodesSamplerInterval)
								.build();
		final TransportClient client = new TransportClient(settings);
		if (null != transportAddresses) {
			for (final Entry<String, Integer> address : transportAddresses.entrySet()) {
				client.addTransportAddress(new InetSocketTransportAddress(address.getKey(), address.getValue()));
				LOGGER.info(String.format("Adding transport clusterName:%s,clientPingTimeout:%s,clientNodesSamplerInterval:%s,address:%s,port:%s", clusterName,clientPingTimeout,clientNodesSamplerInterval,address.getKey(),address.getValue()));
			}
		}else{
			LOGGER.error("Please Add transport address and port!");
		}
		this.transportClient = client;
	}

	

	@Override
	public TransportClient getObject() throws Exception {
		return this.transportClient;
	}

	@Override
	public Class<? extends TransportClient> getObjectType() {
		return this.transportClient != null ? this.transportClient.getClass() : TransportClient.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
	
	@Override
	public void destroy() throws Exception {
		LOGGER.info("Shutting down Elasticsearch TransportClient Manager");
		transportClient.close();
	}

}