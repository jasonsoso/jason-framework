package com.jason.framework.orm.elasticsearch.support;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.hppc.cursors.ObjectCursor;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jason.framework.orm.elasticsearch.domain.IndexData;
import com.jason.framework.util.PropertiesUtils;


/**
 * elasticsearch 索引支撑类
 * @author Jason
 * @date 2014-10-10
 */
public abstract class ESRepositorySupport {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TransportClient transportClient;
	
	/**
	 * 获取客户端Client
	 * @return TransportClient
	 */
	protected final TransportClient getTransportClient() {
		return transportClient;
	}
	/**
	 * 获取日志参数
	 * @return
	 */
	protected Logger getLogger() {
		return logger;
	}


	
	
	/**
	 * 获取es中的总共索引
	 * @return List<String>
	 */
	public List<IndexData> findIndexAlias(){
		List<IndexData> list = new ArrayList<IndexData>();
		ImmutableOpenMap<String, IndexMetaData> indices = transportClient.admin().cluster()
		    .prepareState().execute()
		    .actionGet().getState()
		    .getMetaData().getIndices();
	
		if (null != indices) {
			for(ObjectCursor<IndexMetaData> cursor : indices.values()){
				IndexMetaData indexMetaData = cursor.value;
				String index = indexMetaData.getIndex();
				StringBuilder mappings = new StringBuilder();
				try {
					ImmutableOpenMap<String, MappingMetaData> immutableOpenMap = indexMetaData.getMappings();
					for (ObjectCursor<MappingMetaData> typeEntry : immutableOpenMap.values()) {
						MappingMetaData a = typeEntry.value;
						String mapping = a.getSourceAsMap().toString();
						mappings.append(mapping);
					}
				} catch (IOException e) {
					logger.error("转换Map出错！", e);
				}
				IndexData indexData = new IndexData();
				indexData.setIndex(index);
				indexData.setMappings(mappings.toString());
				list.add(indexData);
			}
		}
		return list;
	}
	
	/**
	 * 根据索引名称 进行删除索引
	 * 
	 * @param indexName 索引名称
	 * @return true 删除成功，false 删除失败
	 */
	public boolean  deleteIndex(String indexName) {
		boolean isExists = ESHelper.isExistsIndex(transportClient, indexName);
		//是否存在，存在则删除
		if(isExists){
			DeleteIndexResponse deleteIndexResponse = transportClient.admin().indices()
				.prepareDelete(indexName)
				.execute().actionGet();
			return deleteIndexResponse.isAcknowledged();
		}else{
			logger.debug(indexName+"索引已经不存在");
			return true;
		}
	}

	
	/**
	 * 创建索引
	 * @param index 索引名称:_index
	 */
	public void createIndex(String indexName){
		int number_of_shards = PropertiesUtils.getIntEntryValue("index.number_of_shards");
		int number_of_replicas = PropertiesUtils.getIntEntryValue("index.number_of_replicas");
		
		Settings settings = ImmutableSettings.settingsBuilder()
       		.put("number_of_shards", number_of_shards)	//分片
       		.put("number_of_replicas", number_of_replicas) 	//副本
       		.build();
		
		//create index
		transportClient.admin().indices()
			.prepareCreate(indexName)
			.setSettings(settings)
			.execute().actionGet();
		logger.debug("Create an index success!");
	}
	
}
