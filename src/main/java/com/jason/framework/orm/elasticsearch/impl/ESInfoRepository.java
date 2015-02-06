package com.jason.framework.orm.elasticsearch.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.mlt.MoreLikeThisRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.AndFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jason.framework.domain.EntityUtils;
import com.jason.framework.orm.Page;
import com.jason.framework.orm.elasticsearch.InfoRepository;
import com.jason.framework.orm.elasticsearch.constant.IndexConstant.Index;
import com.jason.framework.orm.elasticsearch.constant.IndexConstant.Type;
import com.jason.framework.orm.elasticsearch.domain.Info;
import com.jason.framework.orm.elasticsearch.support.ESCrudRepositorySupport;


@Repository
public class ESInfoRepository extends ESCrudRepositorySupport<Info> implements InfoRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(ESInfoRepository.class);
	
	//定义字段常量
	private final static String ID = "id";
	private final static String SSID = "ssid";
	
	private final static String CHANNEL = "channel";
	private final static String TITLE = "title";
	private final static String AUTHOR = "author";
	private final static String SOURCE = "source";
	private final static String CONTENT = "content";
	private final static String STATUS = "status";
	private final static String TAG = "tag";
	private final static String COUNTRY = "country";
	private final static String CREATE_TIME = "create_time";
	private final static String PUBLISH_TIME = "publish_time";
	private final static String EXPIRATION_DATE = "expiration_date";
	private final static String PICTURE = "picture";


	@Override
	public Info get(String id) {
		return super.get(Index.INFO.toString(), Type.INFO_INFO.toString(), id);
	}

	@Override
	public void index(Info domain) {
		super.index(Index.INFO.toString(), Type.INFO_INFO.toString(), domain, domain.getIndexId());
	}

	@Override
	public void index(List<Info> list) {
		super.index(Index.INFO.toString(), Type.INFO_INFO.toString(), list);
	}

	@Override
	public void delete(String id) {
		super.delete(Index.INFO.toString(), Type.INFO_INFO.toString(), id);
	}

	@Override
	public void deleteAll() {
		super.deleteAll(Index.INFO.toString(), Type.INFO_INFO.toString());
	}

	@Override
	public List<Info> query(String queryString, int size) {
		return super.query(Index.INFO.toString(), Type.INFO_INFO.toString(), queryString, size);
	}

	@Override
	public Page<Info> queryPage(Page<Info> page, String queryString) {
		
		Map<String, Object> params = page.getParams();
		long country = EntityUtils.tolong(params.get("country"));
		long channel = EntityUtils.tolong(params.get("channel"));
		//过滤参数
		AndFilterBuilder andFilter = FilterBuilders.andFilter();
		if(country>0){
			andFilter.add(FilterBuilders.termFilter(COUNTRY, country));//过滤参数 国家 country
		}
		if(channel>0){
			andFilter.add(FilterBuilders.termFilter(CHANNEL, channel));//过滤参数 频道 channel
		}
		//排序参数
		String order = page.getOrder();
		String orderBy = page.getOrderBy();
		SortBuilder sort = null;
		if(StringUtils.isNotBlank(orderBy)&&StringUtils.isNotBlank(order)){
			SortOrder sortOrder = StringUtils.equalsIgnoreCase(order, "desc") ? SortOrder.DESC : SortOrder.ASC;
			sort = SortBuilders.fieldSort(orderBy).order(sortOrder);
		}
		
		/*QueryBuilder query = null;
		if(StringUtils.isNotBlank(queryString)){
			 query = QueryBuilders
		        .boolQuery()
				.should(QueryBuilders.queryString(queryString).field(TITLE).field(CONTENT));
		}*/
		return super.queryPage(Index.INFO.toString(), Type.INFO_INFO.toString(), page, queryString, andFilter, sort);
	}

	
	
	@Override
	public List<Info> moreLikeThis(String indexId,int size) {
		
		MoreLikeThisRequest moreLikeThisRequest = Requests.moreLikeThisRequest(Index.INFO.toString())
			.type( Type.INFO_INFO.toString())
			.id(indexId)
			.fields(TITLE,CONTENT)
			.searchFrom(0)
			.searchSize(size)
			.minTermFreq(1)	//一篇文档中一个词语至少出现次数，小于这个值的词将被忽略，默认是2
			.minDocFreq(1);	//一个词语最少在多少篇文档中出现，小于这个值的词会将被忽略，默认是5
		
		SearchResponse mltResponse = super.getTransportClient()
			.moreLikeThis(moreLikeThisRequest)
			.actionGet();
		
		SearchHits hits = mltResponse.getHits(); 
        long total = hits.getTotalHits();
        long millis = mltResponse.getTookInMillis();
        super.getLogger().debug("SearchRequest total:{},millis:{}",total,millis);
        
        List<Info> list = new ArrayList<Info>();
        for (SearchHit hit : hits) {
        	Map<String, Object> map = hit.getSource();
            
        	Info domain = map2obj(map);
        	setIndexId(domain,hit.getId());
            list.add(domain);
        } 
		return list;
	}

	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		//初始化Mapping
		initIndexMapping();
	}
	@Override
	public void initIndexMapping() {
		super.initIndexMapping(Index.INFO.toString(), Type.INFO_INFO.toString());
		
	}
	@Override
	protected XContentBuilder getMapping() {
		XContentBuilder mapping = null;
		try {
			mapping = XContentFactory.jsonBuilder()
				.startObject()
					.startObject(Type.INFO_INFO.toString())
						.startObject("_all")      
					    	.field("indexAnalyzer", "ik")
					        .field("searchAnalyzer", "ik")
					    .endObject()
					    .startObject("properties")
					    	.startObject(ID)
					      	  .field("type", "long")
					      	  .field("store", "yes")
					        .endObject()
					    	.startObject(SSID)
					      	  .field("type", "integer")
					      	  .field("store", "yes")
					        .endObject()
					        .startObject(CHANNEL)
					      	  .field("type", "long")
					      	  .field("store", "yes")
					        .endObject()
					      	.startObject(TITLE)
					      	  .field("type", "string")
					      	  .field("store", "yes")
					          .field("indexAnalyzer", "ik")
					          .field("searchAnalyzer", "ik")
					        .endObject()
					        .startObject(AUTHOR)
					      	  .field("type", "string")
					      	  .field("store", "yes")
					        .endObject()
					        .startObject(SOURCE)
					      	  .field("type", "string")
					      	  .field("store", "yes")
					        .endObject()
					        .startObject(CONTENT)
					      	  .field("type", "string")
					      	  .field("store", "yes")
					          .field("indexAnalyzer", "ik")
					          .field("searchAnalyzer", "ik")
					        .endObject()
					        .startObject(STATUS)
					      	  .field("type", "long")
					      	  .field("store", "yes")
					        .endObject() 
					        .startObject(TAG)
					      	  .field("type", "string")
					      	  .field("store", "yes")
					          .field("indexAnalyzer", "whitespace")
					          .field("searchAnalyzer", "whitespace")
					        .endObject()
					        .startObject(COUNTRY)
					      	  .field("type", "long")
					      	  .field("store", "yes")
					        .endObject() 
					        .startObject(CREATE_TIME)
					      	  .field("type", "long")
					      	  .field("store", "yes")
					        .endObject()
					        .startObject(PUBLISH_TIME)
					      	  .field("type", "long")
					      	  .field("store", "yes")
					        .endObject()
					        .startObject(EXPIRATION_DATE)
					      	  .field("type", "long")
					      	  .field("store", "yes")
					        .endObject()
					        .startObject(PICTURE)
					      	  .field("type", "string")
					      	  .field("store", "yes")
					      	  .field("index", "not_analyzed")
					        .endObject()
					    .endObject()
				    .endObject()
		        .endObject();
		} catch (IOException e) {
			LOGGER.error("getMapping is error!",e);
		}
		return mapping;
	}

	@Override
	protected XContentBuilder getXContentBuilder(Info domain) {

		XContentBuilder builder = null;
		try {
			builder = XContentFactory.jsonBuilder().startObject()
				.field(ID, domain.getId())
				.field(SSID, domain.getSsid())
				.field(CHANNEL, domain.getChannel())
				.field(TITLE, domain.getTitle())
				.field(AUTHOR, domain.getAuthor())
				.field(SOURCE, domain.getSource())
				.field(CONTENT, domain.getContent())
				.field(STATUS, domain.getStatus())
				.field(TAG, domain.getTag())
				.field(COUNTRY, domain.getCountry())
				.field(CREATE_TIME, domain.getCreateTime())
				.field(PUBLISH_TIME, domain.getPublishTime())
				.field(EXPIRATION_DATE, domain.getExpirationDate())
				.field(PICTURE, domain.getPicture())
			.endObject();
		} catch (IOException e) {
			LOGGER.error("Listings getXContentBuilder is error! ",e);
		}
		return builder;
	}

	@Override
	protected Info map2obj(Map<String, Object> map) {
		Info entity = new Info();
		entity.setId(EntityUtils.tolong(map.get(ID)));
		entity.setSsid(EntityUtils.toint(map.get(SSID)));
		
		entity.setChannel(EntityUtils.tolong(map.get(CHANNEL)));
		entity.setTitle(EntityUtils.toString(map.get(TITLE)));
		entity.setAuthor(EntityUtils.toString(map.get(AUTHOR)));
		entity.setSource(EntityUtils.toString(map.get(SOURCE)));
		entity.setContent(EntityUtils.toString(map.get(CONTENT)));
		entity.setStatus(EntityUtils.tolong(map.get(STATUS)));
		entity.setTag(EntityUtils.toString(map.get(TAG)));
		entity.setCountry(EntityUtils.tolong(map.get(COUNTRY)));
		entity.setCreateTime(EntityUtils.tolong(map.get(CREATE_TIME)));
		entity.setPublishTime(EntityUtils.tolong(map.get(PUBLISH_TIME)));
		entity.setExpirationDate(EntityUtils.tolong(map.get(EXPIRATION_DATE)));
		entity.setPicture(EntityUtils.toString(map.get(PICTURE)));
		
		return entity;
	}

	@Override
	protected String getIndexId(Info domain) {
		return domain.getIndexId();
	}

	@Override
	protected void setIndexId(Info domain, String indexId) {
		domain.setIndexId(indexId);
	}

	
}
