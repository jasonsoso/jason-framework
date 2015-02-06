package com.jason.framework.orm.elasticsearch.support;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.Client;
import org.nlpcn.commons.lang.pinyin.Pinyin;

/**
 * Elasticsearch 帮助类
 * @author Jason
 */
public class ESHelper {

	private ESHelper(){
	}
	/**
	 * 判断索引是否存在
	 * @param client 客户端
	 * @param index 索引名称
	 * @return true 存在索引，false 不存在索引
	 */
	public static boolean isExistsIndex(Client client, String index) {
		IndicesExistsRequest request = new IndicesExistsRequestBuilder(client
				.admin().indices(), index).request();
		IndicesExistsResponse response = client.admin().indices()
				.exists(request).actionGet();
		return response.isExists();
	}
	
	/**
	 * 由id和ssid组装IndexId
	 * @param id
	 * @param ssid
	 * @return
	 */
	public static String setIndexIdfromIdAndSsid(int ssid,long id){
		StringBuilder sb = new StringBuilder().append(ssid).append("-").append(id);
		return sb.toString();
	}
	
	/**
	 * 中文转变为拼音
	 * @param cnName
	 * @return
	 */
	public static String getPinyin(String cnName){
		StringBuilder sb = new StringBuilder();
		if(StringUtils.isNotBlank(cnName)){
			List<String> result = Pinyin.pinyinWithoutTone(cnName);
			for (String s:result ) {
				sb.append(s);
			}
		}
		return sb.toString();
	}
}
