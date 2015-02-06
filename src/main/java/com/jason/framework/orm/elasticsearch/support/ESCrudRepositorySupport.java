package com.jason.framework.orm.elasticsearch.support;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BaseFilterBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.jason.framework.orm.Page;
import com.jason.framework.util.Collections3;

/**
 * elasticsearch 数据支撑类 	CRUD支撑类 
 * @author Jason
 * @data 2014-10-10
 */
public abstract class ESCrudRepositorySupport<T> extends ESRepositorySupport implements InitializingBean{
	

	/**
	 * 进行索引，新增/更新
	 * @param index 索引名称:_index
	 * @param type 索引类型:_type
	 * @param domain 
	 * @param indexId 主键
	 * @return 
	 */
	public IndexResponse index(String index,String type,T domain,String indexId){
		Assert.notNull(domain, "domain must not null");
		Assert.hasText(indexId, "indexId must not be empty");
		
		XContentBuilder builder = getXContentBuilder(domain);
		IndexResponse indexResponse = super.getTransportClient().prepareIndex(index, type,indexId)
			.setSource(builder)
			.execute().actionGet();
		return  indexResponse;
	}
	
	/**
	 * 批量索引,批量更新
	 * @param index 索引名称:_index
	 * @param type 索引类型:_type
	 * @param collection
	 */
	public boolean index(String index,String type,List<T> collection) {
		Assert.notEmpty(collection, "Collection must have elements");
		
		BulkRequestBuilder bulkRequest = super.getTransportClient().prepareBulk();
		
		for (T domain:collection) {
			XContentBuilder builder = getXContentBuilder(domain);
			String indexId = getIndexId(domain);
			Assert.hasText(indexId, "indexId must not be empty");
			
			bulkRequest.add(super.getTransportClient().prepareIndex(index, type,indexId).setSource(builder));
		}
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		
		if (bulkResponse.hasFailures()) {
			super.getLogger().error("bulkResponse.hasFailures()...");
			return false;
		}else{
			super.getLogger().debug("bulk index ok！Millis："+bulkResponse.getTookInMillis());
			return true;
		}
	}

	/**
	 * 根据id获取对象
	 * @param index 索引名称:_index
	 * @param type 索引类型:_type
	 * @param indexId 主键
	 * @return 
	 */
	public T get(String index, String type, String indexId) {
		Assert.notNull(indexId, "indexId must not null");
		
		GetResponse getResponse = super.getTransportClient()
				.prepareGet(index, type, indexId).execute().actionGet();
		if (getResponse.isExists()) {
			Map<String, Object> map = getResponse.getSource();

			T entity = map2obj(map);
			setIndexId(entity,getResponse.getId());
			return entity;
		} else {
			return null;
		}
	}
	
	/**
	 * 根据id删除数据
	 * @param index 索引名称:_index
	 * @param type 索引类型:_type
	 * @param indexId
	 * @return
	 */
	public DeleteResponse  delete(String index, String type, String indexId) {
		Assert.notNull(indexId, "indexId must not null");
		DeleteResponse deleteResponse = super.getTransportClient().prepareDelete(index, type, indexId)
	        .execute()
	        .actionGet();
		return deleteResponse;
	}
	
	
	/**
	 * 根据索引和类型删除所有数据
	 * @param index 索引名称:_index
	 * @param type 索引类型:_type
	 * @return 
	 */
	public  DeleteByQueryResponse deleteAll(String index, String type) {
		MatchAllQueryBuilder allQueryBuilder = QueryBuilders.matchAllQuery();
		DeleteByQueryResponse deleteByQueryResponse = super.getTransportClient().prepareDeleteByQuery(index)
						.setTypes(type)
						.setQuery(allQueryBuilder)
						.execute()
						.actionGet();
		return deleteByQueryResponse;
	}

	/**
	 * 搜索
	 * @param index 索引名称:_index
	 * @param type 索引类型:_type
	 * @param queryString 关键字
	 * @param size 条数
	 * @return
	 */
	public List<T> query(String index, String type,String queryString,int size) {
		Assert.hasText(queryString, "queryString must not be empty!");

		if(StringUtils.isBlank(queryString)){
			queryString = "*";
		}
		
		SearchResponse searchResponse = super.getTransportClient().prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.queryString(queryString))
                .setFrom(0).setSize(size).setExplain(true)
                .execute()
                .actionGet();
		
		SearchHits hits = searchResponse.getHits(); 
        
        List<T> list = new ArrayList<T>();
        for (SearchHit hit : hits) {
        	String indexId = hit.getId();
        	Map<String, Object> map = hit.getSource();
        	T entity = map2obj(map);
        	setIndexId(entity,indexId);
            list.add(entity);
        } 
		return list;
	}
	public Page<T> queryPage(String index, String type,Page<T> page, String queryString,BaseFilterBuilder filter,SortBuilder sort) {
		List<SortBuilder> sorts = new ArrayList<SortBuilder>();
		if(sort!=null){
			sorts.add(sort);
		}
		return queryPage(index, type, page, queryString, filter, sorts);
	}
	/**
	 * 分页搜索
	 * @param index  索引名称:_index
	 * @param type 索引类型:_type
	 * @param page 分页对象
	 * @param queryString 关键字
	 * @param filter 过滤器
	 * @param sorts 排序器集合
	 * @return
	 */
	public Page<T> queryPage(String index, String type,Page<T> page, String queryString,BaseFilterBuilder filter,List<SortBuilder> sorts) {

		if(StringUtils.isBlank(queryString)){
			queryString = "*";
		}
		QueryStringQueryBuilder query = QueryBuilders.queryString(queryString);
		
		return queryPage(index, type, page, query, filter, sorts);
	}
	
	/**
	 * 分页搜索
	 * @param index 索引名称:_index
	 * @param type 索引类型:_type
	 * @param page 分页对象
	 * @param query 查询器
	 * @param filter 过滤器
	 * @param sort 排序器
	 * @return
	 */
	public Page<T> queryPage(String index, String type,Page<T> page, QueryBuilder query,BaseFilterBuilder filter,SortBuilder sort) {
		List<SortBuilder> sorts = new ArrayList<SortBuilder>();
		if(sort!=null){
			sorts.add(sort);
		}
		return queryPage(index, type, page, query, filter, sorts);
	}
	/**
	 * 分页搜索
	 * @param index  索引名称:_index
	 * @param type 索引类型:_type
	 * @param page 分页对象
	 * @param query 查询器
	 * @param filter 过滤器
	 * @param sorts 排序器 集合
	 * @return
	 */
	public Page<T> queryPage(String index, String type,Page<T> page, QueryBuilder query,BaseFilterBuilder filter,List<SortBuilder> sorts) {
		Assert.notNull(page, "page must not null");
		
		SearchRequestBuilder searchRequestBuilder = super.getTransportClient().prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(query)
                .setPostFilter(filter)
                .setFrom(page.getFirst()-1).setSize(page.getPageSize()).setExplain(true);
		if(Collections3.isNotEmpty(sorts)){
			for (SortBuilder sort:sorts) {
				searchRequestBuilder.addSort(sort);
			}
		}

		super.getLogger().debug("SearchRequestBuilder Json :{}",searchRequestBuilder.toString());
		
		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
		
		SearchHits hits = searchResponse.getHits(); 
        long total = hits.getTotalHits();
        long millis = searchResponse.getTookInMillis();
        super.getLogger().debug("SearchRequest total:{},millis:{}",total,millis);
        
        List<T> list = new ArrayList<T>();
        for (SearchHit hit : hits) {
        	Map<String, Object> map = hit.getSource();
            
        	T domain = map2obj(map);
        	setIndexId(domain,hit.getId());
        	
            list.add(domain);
        } 
        page.setTotalCount(total);
        page.setResult(list);
		return page;
	}
	
	
	/**
	 * 初始化Mapping映射
	 * @param index 索引名称:_index
	 * @param type 索引类型:_type
	 */
	public void initIndexMapping(String index,String type) {
		boolean isExists = ESHelper.isExistsIndex(super.getTransportClient(), index);
		if(!isExists){
			//create index
			createIndex(index);
			
		    //create mapping
			XContentBuilder mapping = getMapping();
			
			try {
				super.getLogger().debug(String.format("mapping is %s", mapping.string()));
			} catch (IOException e) {
				super.getLogger().debug("mapping to string error!",e);
			}
			PutMappingRequest mappingRequest = Requests.putMappingRequest(index).type(type).source(mapping);
			super.getTransportClient().admin().indices().putMapping(mappingRequest).actionGet();
		    
		    super.getLogger().debug(String.format("create index and mapping are success!index=%s type=%s", index,type));
		}else{
			super.getLogger().debug("Index already exists!");
		}
	}
	
	
	/** 获取对应的Mapping */
	protected abstract XContentBuilder getMapping();
	
	/** 根据对象 建立 XContentBuilder */
	protected abstract XContentBuilder getXContentBuilder(T domain);
	
	/** ES查询的数据Map集合  转化为  对象 */
	protected abstract T map2obj(Map<String, Object> map);
	
	/** 获取 domain 对象的 indexId */
	protected abstract String getIndexId(T domain);
	
	/** 设置 domain 对象的 indexId */
	protected abstract void setIndexId(T domain,String indexId);

	
}
