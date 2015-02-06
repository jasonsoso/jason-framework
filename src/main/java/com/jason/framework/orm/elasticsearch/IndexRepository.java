package com.jason.framework.orm.elasticsearch;

import java.util.List;

import com.jason.framework.orm.elasticsearch.domain.IndexData;

/**
 * 索引 管理
 * @author Jason
 * @date 2014-10-10
 */
public interface IndexRepository {
	
	/**
	 * 获取es中总共的索引
	 * @return
	 */
	List<IndexData>  findIndexAlias();
	
	/** 
	 * 针对一个索引，进行删除
	 * @param indexName 索引名称
	 * @return true 删除成功，false 删除失败
	 */
	boolean deleteIndex(String indexName);
	
}
