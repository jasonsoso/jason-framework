package com.jason.framework.search;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse.AnalyzeToken;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElasticsearchPluginsTests {
    private Logger logger = LoggerFactory.getLogger(getClass());


    Client client = null;
    
    @Before
    public void before() {
        client = new TransportClient()
        	.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
    }
    
    @After
    public void after() {
        client.close();
    }

    
    
    //--------------------------测试 analysis-ik分词器  analysis-mmseg分词器也类似----------------------------------
    
    @Test
    public void testIndex() throws IOException {
    	//1.create a index
    	client.admin().indices().prepareCreate("index_ik").execute().actionGet();
    }
    
    @Test
    public void testMapping() throws IOException {
    	//2.create a mapping
    	XContentBuilder mapping = XContentFactory.jsonBuilder().startObject()
							        .startObject("fulltext")
							        	.startObject("_all")      
								        	.field("indexAnalyzer", "ik")
								            .field("searchAnalyzer", "ik")
								            .field("term_vector","no")
								            .field("store","false")
								        .endObject()
								        .startObject("properties")      
							              	.startObject("content")
							              	  .field("type", "string")      
								              .field("store","no")
								              .field("term_vector","with_positions_offsets")
								              .field("indexAnalyzer", "ik")
								              .field("searchAnalyzer", "ik")
								              .field("include_in_all","true")
								              .field("boost", 8)
								            .endObject()  
							             .endObject()
							          .endObject()
							       .endObject();
    	
    	PutMappingRequest mappingRequest = Requests.putMappingRequest("index_ik").type("fulltext").source(mapping);  
    	client.admin().indices().putMapping(mappingRequest).actionGet();
    }
    @Test
    public void testIndexDoc() throws IOException{
    	//3.index some docs
        XContentBuilder builder1 = XContentFactory.jsonBuilder()
                                    .startObject()
                                    .field("content","美国留给伊拉克的是个烂摊子吗")
                                    .endObject();
        XContentBuilder builder2 = XContentFactory.jsonBuilder()
							        .startObject()
							        .field("content","公安部：各地校车将享最高路权")
							        .endObject();
        XContentBuilder builder3 = XContentFactory.jsonBuilder()
							        .startObject()
							        .field("content","中韩渔警冲突调查：韩警平均每天扣1艘中国渔船")
							        .endObject();
        XContentBuilder builder4 = XContentFactory.jsonBuilder()
							        .startObject()
							        .field("content","中国驻洛杉矶领事馆遭亚裔男子枪击 嫌犯已自首")
							        .endObject();
        XContentBuilder builder5 = XContentFactory.jsonBuilder()
							        .startObject()
							        .field("content","我爱我的中国 我的中国也爱我")
							        .endObject();
        BulkRequestBuilder bulkRequest = client.prepareBulk();
         bulkRequest.add(client.prepareIndex("index_ik", "fulltext", "1").setSource(builder1))
                    .add(client.prepareIndex("index_ik", "fulltext", "2").setSource(builder2))
                    .add(client.prepareIndex("index_ik", "fulltext", "3").setSource(builder3))
                    .add(client.prepareIndex("index_ik", "fulltext", "4").setSource(builder4))
        			.add(client.prepareIndex("index_ik", "fulltext", "5").setSource(builder5));
        
        BulkResponse bulkResponse =  bulkRequest.execute().actionGet();
        
        if(bulkResponse.hasFailures()){
        }
        logger.info("bulk state : {}",bulkResponse.hashCode());
    }


    @Test
    public void testQuery() throws IOException {
    	//4.query with highlighting
        SearchResponse searchResponse = client.prepareSearch("index_ik")
                                            .setTypes("fulltext")
                                            .setQuery(QueryBuilders.queryString("中国"))
                                            .addHighlightedField("content")
                                            //.setHighlighterPreTags("<tag1>", "<tag2>")
                                            //.setHighlighterPostTags("</tag1>", "</tag2>")
                                            .setFrom(0).setSize(10).setExplain(true)                        //Page  
                                            .execute()
                                            .actionGet();
        
        SearchHits hits = searchResponse.getHits(); 
        long total = hits.getTotalHits();
        logger.info("search result total:{}",total);
        
        for (SearchHit hit : hits) {
            Map<String, HighlightField> result = hit.highlightFields(); 
            logger.info("A map of highlighted fields:{}",result);
            
            HighlightField titleField = result.get("content");  
            Text[] titleTexts =  titleField.fragments();  
            for(Text text : titleTexts){  
            	logger.info("title text: :{}",text);
            }  
            
        }  
    }
    @Test
	public void testIKQuery() throws IOException {
		AnalyzeResponse analyzeResponse = client.admin().indices()
				.prepareAnalyze("twitter", "测试elasticsearch分词器的效果")
				.setAnalyzer("ik")
				.execute().actionGet();

		logger.info("size:{}", analyzeResponse.getTokens().size());
		List<AnalyzeToken> list = analyzeResponse.getTokens();
		for (AnalyzeToken token : list) {
			logger.info("Term:{}", token.getTerm());
		}
	}
    
    //---------------------测试 analysis-pinyin ------------------------------
    
    @Test
    public void testPinyinIndex() throws IOException {
    	//1.create a index for doing some tests
    	XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
							        .startObject("index")
							        	.startObject("analysis")
							        		.startObject("analyzer")
							        			.startObject("pinyin_analyzer")
								        			.field("tokenizer", "my_pinyin")
										            .field("filter", new String[]{"standard"})
									            .endObject()
							        		.endObject()
							        		.startObject("tokenizer")
							        			.startObject("my_pinyin")
								        			.field("type", "pinyin")
								        			.field("first_letter", "none")
								        			.field("padding_char", " ")
									            .endObject()
							        		.endObject()
								        .endObject()
							          .endObject()
							       .endObject();
    	  
    	  
    	
    	client.admin().indices().prepareCreate("jasontan")
	        //.setSettings(ImmutableSettings.settingsBuilder().loadFromSource(builder.string()))
	        .setSource(builder)
    		.execute().actionGet();
    	
    }
    
    @Test
	public void testPinyinQuery() throws IOException {
		AnalyzeResponse analyzeResponse = client.admin().indices()
				.prepareAnalyze("twitter", "阳光天使")
				.setAnalyzer("pinyin")
				.execute().actionGet();

		logger.info("size:{}", analyzeResponse.getTokens().size());
		List<AnalyzeToken> list = analyzeResponse.getTokens();
		for (AnalyzeToken token : list) {
			logger.info("Term:{}", token.getTerm());
		}
	}
    @Test
    public void testIndexPinyin() throws IOException {
    	 XContentBuilder builder = XContentFactory.jsonBuilder()
						         .startObject()
						         .field("name", "刘德华")
						         .endObject();
     
     client.prepareIndex("medcl", "folks", "andy")
                                 .setSource(builder)
                                 .execute()
                                 .actionGet();
    }
    //-----------------------------------------------------------------
   
}

