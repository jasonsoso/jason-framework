package com.jason.framework.search;


import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.datehistogram.DateHistogramFacet;
import org.elasticsearch.search.facet.datehistogram.DateHistogramFacetBuilder;
import org.elasticsearch.search.facet.filter.FilterFacet;
import org.elasticsearch.search.facet.filter.FilterFacetBuilder;
import org.elasticsearch.search.facet.geodistance.GeoDistanceFacet;
import org.elasticsearch.search.facet.geodistance.GeoDistanceFacetBuilder;
import org.elasticsearch.search.facet.histogram.HistogramFacet;
import org.elasticsearch.search.facet.histogram.HistogramFacetBuilder;
import org.elasticsearch.search.facet.query.QueryFacet;
import org.elasticsearch.search.facet.query.QueryFacetBuilder;
import org.elasticsearch.search.facet.range.RangeFacet;
import org.elasticsearch.search.facet.range.RangeFacetBuilder;
import org.elasticsearch.search.facet.statistical.StatisticalFacet;
import org.elasticsearch.search.facet.statistical.StatisticalFacetBuilder;
import org.elasticsearch.search.facet.terms.TermsFacet;
import org.elasticsearch.search.facet.termsstats.TermsStatsFacet;
import org.elasticsearch.search.facet.termsstats.TermsStatsFacetBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElasticsearchTests {
    private Logger logger = LoggerFactory.getLogger(getClass());


    Client client = null;
    
    @Before
    public void before() {
        client = new TransportClient()
        	.addTransportAddress(new InetSocketTransportAddress("192.168.1.108", 9300));
    }
    
    @After
    public void after() {
        client.close();
    }

    @Test
    public void testClient() {
        
    	
    	//Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch").build();
		//Client client = new TransportClient(settings)
		//		.addTransportAddress(new InetSocketTransportAddress("192.168.1.108", 9300));
		
		
        //Node node = NodeBuilder.nodeBuilder().node();
        //Client client = node.client();
        
        //Node node = NodeBuilder.nodeBuilder().clusterName("").client(true).node();
        
        //Node node = NodeBuilder.nodeBuilder().local(true).node();
        
        
        //on shutdown
        //node.close();
        
        
        Client client = new TransportClient()
           .addTransportAddress(new InetSocketTransportAddress("192.168.1.108", 9300));

        
        client.close();
    }
   
    
    @Test
    public void testIndex() throws IOException {
        String jsonstr = "{\"user\":\"jetty\",\"postDate\":\"2013-01-30\",\"message\":\"trying out Elastic Search\",\"age\":28,\"location\":{\"lat\" : 40.12,\"lon\":-71.34}}";
        
        client.prepareIndex("twitter", "tweet", "13")
                                    .setSource(jsonstr)
                                    .execute()
                                    .actionGet();
        
        //---------------------------------------------
        
        XContentBuilder builder = XContentFactory.jsonBuilder()
            .startObject()
            .field("user", "tom")
            .field("postDate", new Date())
            .field("message", "hello tom!")
            .field("age", 24)
            .endObject();
        
        String json = builder.string();
        logger.info("the json is {}",json);
        
        IndexResponse response = client.prepareIndex("twitter", "tweet", "13")
                                    .setSource(builder)
                                    .execute()
                                    .actionGet();
        
        logger.info("the index name is {}",response.getIndex());
        logger.info("the index id is {}",response.getId() );
    }
    
    @Test
    public void testGet() throws IOException {
        
        GetResponse getResponse = client.prepareGet("twitter", "tweet", "13")
                                    .execute()
                                    .actionGet();
        
        Map<String, Object> map = getResponse.getSource();
        
        Iterator<Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Object> e = it.next();
            logger.info("Key:{}; Value:{}", e.getKey(),e.getValue());
        }
    }
    
    @Test
    public void testDelete() throws IOException {
        
        DeleteResponse deleteResponse = client.prepareDelete("twitter", "tweet", "1")
                                    .execute()
                                    .actionGet();
        logger.info("delete id : {}",deleteResponse.getId());
    }
    
   
    
    
    @Test
    public void testBulk() throws IOException {
        
        XContentBuilder builder0 = XContentFactory.jsonBuilder()
                                    .startObject()
                                    .field("user", "jason ling")
                                    .field("postDate", new Date())
                                    .field("message", "hello jason ling!")
                                    .field("age", 30)
                                    .endObject();
        XContentBuilder builder1 = XContentFactory.jsonBuilder()
                                    .startObject()
                                    .field("user", "kaven chen")
                                    .field("postDate", new Date())
                                    .field("message", "hello kaven chen!")
                                    .field("age", 26)
                                    .endObject();
        
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        bulkRequest.add(client.prepareIndex("twitter", "tweet", "7").setSource(builder0))
                    .add(client.prepareIndex("twitter", "tweet", "8").setSource(builder1));
        
        BulkResponse bulkResponse =  bulkRequest.execute().actionGet();
        
        if(bulkResponse.hasFailures()){
            
        }
        logger.info("bulk state : {}",bulkResponse.hashCode());
    }


    @Test
    public void testSearch() throws IOException {
        SearchResponse searchResponse = client.prepareSearch("twitter")
                                            .setTypes("tweet")
                                            .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                                            //.setQuery(QueryBuilders.termQuery("user", "jason"))             //Query 
                                            //.setQuery(QueryBuilders.fieldQuery("user", "jason"))
                                            .setQuery(QueryBuilders.queryString("jason"))
                                            .setFilter(FilterBuilders.rangeFilter("age").from(24).to(30))   //Filter    
                                            .setFrom(0).setSize(10).setExplain(true)                        //Page  
                                            .execute()
                                            .actionGet();
        
        //SearchResponse searchResponse = client.prepareSearch().execute().actionGet();
            
        SearchHits hits = searchResponse.getHits(); 
        long total = hits.getTotalHits();
        logger.info("search result total:{}",total);
        
        for (SearchHit hit : hits) {
            Object user = hit.getSource().get("user");
            Object postDate = hit.getSource().get("postDate");
            Object message = hit.getSource().get("message");
            Object age = hit.getSource().get("age");
            logger.info("user:{},postDate:{},message:{},age:{}",user,postDate,message,age);
        }  
    }
    
    @Test
    public void testMultiSearch() throws IOException {
        
        SearchRequestBuilder srb1 = client
            .prepareSearch().setQuery(QueryBuilders.queryString("jason")).setSize(1);
        SearchRequestBuilder srb2 = client
            .prepareSearch().setQuery(QueryBuilders.matchQuery("user", "kaven chen")).setSize(1);

    
        MultiSearchResponse sr = client.prepareMultiSearch()
                                    .add(srb1)
                                    .add(srb2)
                                    .execute().actionGet();
        long nbHits = 0;
        for (MultiSearchResponse.Item item : sr.getResponses()) {
            SearchResponse response = item.getResponse();
            nbHits += response.getHits().totalHits();
        }
        logger.info("nbHits total is {}",nbHits);
    }
    
    @Test
    public void testFacets() throws IOException {
        
        SearchResponse sr = client.prepareSearch()
            .setQuery(QueryBuilders.matchAllQuery())
            .addFacet(FacetBuilders.termsFacet("f1").field("age").size(10))
            .addFacet(FacetBuilders.dateHistogramFacet("f2").field("postDate").interval("year"))
            .execute().actionGet();
        
        TermsFacet f1 = (TermsFacet) sr.getFacets().facetsAsMap().get("f1");
        
        long totalCount = f1.getTotalCount();      // Total terms doc count
        long otherCount = f1.getOtherCount();      // Not shown terms doc count
        long missingCount = f1.getMissingCount();    // Without term doc count
        
        logger.info("totalCount is {}; otherCount is {}; missingCount is {}",totalCount,otherCount,missingCount);
        // For each entry
        for (TermsFacet.Entry entry : f1) {
            entry.getTerm();    // Term
            entry.getCount();   // Doc count
            logger.info("Term is {} ; Doc count is {}",entry.getTerm(),entry.getCount());
        }
        
        
        DateHistogramFacet f2 = (DateHistogramFacet) sr.getFacets().facetsAsMap().get("f2");
        String name = f2.getName();
        String type = f2.getType();
        logger.info("name is {} ; type is {}",name,type);
        for (DateHistogramFacet.Entry entry : f2.getEntries()) {
            logger.info("Count is {} ",entry.getCount());
        }
    }
    @Test
    public void testRangeFacets() throws IOException {
        RangeFacetBuilder rfb = FacetBuilders.rangeFacet("f")
                                .field("age")
                                //.addUnboundedFrom(70) //from -infinity to 3 (excluded)
                                //.addRange(25, 70)     //from 3 to 6 (excluded)
                                .addUnboundedTo(25);    // from 6 to +infinity
        
        SearchResponse sr = client.prepareSearch()
                            .setQuery(QueryBuilders.matchAllQuery())
                            .addFacet(rfb)
                            .execute().actionGet();
        
        
        RangeFacet f = (RangeFacet) sr.getFacets().facetsAsMap().get("f");
        for (RangeFacet.Entry entry : f) {
            entry.getFrom();    // Range from requested
            entry.getTo();      // Range to requested
            entry.getCount();   // Doc count
            entry.getMin();     // Min value
            entry.getMax();     // Max value
            entry.getMean();    // Mean
            entry.getTotal();   // Sum of values
            logger.info("count : {}",entry.getCount());
        }
        
    }
    
    @Test
    public void testHistogramFacets() throws IOException {
        HistogramFacetBuilder hfb = FacetBuilders.histogramFacet("f")
                                .field("age")
                                .interval(30);
        
        SearchResponse sr = client.prepareSearch()
                            .setQuery(QueryBuilders.matchAllQuery())
                            .addFacet(hfb)
                            .execute().actionGet();
        
        
        HistogramFacet f = (HistogramFacet) sr.getFacets().facetsAsMap().get("f");
        for (HistogramFacet.Entry entry : f) {

            logger.info("key:{}; count:{}",entry.getKey(),entry.getCount());
        }
        
    }
    @Test
    public void testDateHistogramFacets() throws IOException {
        DateHistogramFacetBuilder dhfb = FacetBuilders.dateHistogramFacet("f")
                                .field("postDate")
                                .interval("day");
        
        SearchResponse sr = client.prepareSearch()
                            .setQuery(QueryBuilders.matchAllQuery())
                            .addFacet(dhfb)
                            .execute().actionGet();
        
        
        DateHistogramFacet  f = (DateHistogramFacet) sr.getFacets().facetsAsMap().get("f");
        for (DateHistogramFacet.Entry entry : f) {

            logger.info("time:{}; count:{}",entry.getTime(),entry.getCount());
        }
        
    }
    
    @Test
    public void testFilterFacets() throws IOException {
        FilterFacetBuilder ffb = FacetBuilders.filterFacet("f",
            FilterBuilders.termFilter("age", 26));    // Your Filter here
        
        SearchResponse sr = client.prepareSearch()
                            .setQuery(QueryBuilders.matchAllQuery())
                            .addFacet(ffb)
                            .execute().actionGet();
        
        
        FilterFacet  f = (FilterFacet) sr.getFacets().facetsAsMap().get("f");

        logger.info("count:{}",f.getCount());
        
    }
    @Test
    public void testQueryFacets() throws IOException {
        QueryFacetBuilder qfb = FacetBuilders.queryFacet("f",
            QueryBuilders.matchQuery("user", "jason tan"));
        
        SearchResponse sr = client.prepareSearch()
                            .setQuery(QueryBuilders.matchAllQuery())
                            .addFacet(qfb)
                            .execute().actionGet();
        
        QueryFacet   f = (QueryFacet) sr.getFacets().facetsAsMap().get("f");

        logger.info("count:{}",f.getCount());
    }
    @Test
    public void testStatisticalFacets() throws IOException {
        StatisticalFacetBuilder sfb = FacetBuilders.statisticalFacet("f")
                                    .field("age");
        
        SearchResponse sr = client.prepareSearch()
                            .setQuery(QueryBuilders.matchAllQuery())
                            .addFacet(sfb)
                            .execute().actionGet();
        
        StatisticalFacet  f = (StatisticalFacet) sr.getFacets().facetsAsMap().get("f");

        f.getCount();           // Doc count
        f.getMin();             // Min value
        f.getMax();             // Max value
        f.getMean();            // Mean
        f.getTotal();           // Sum of values
        f.getStdDeviation();    // Standard Deviation
        f.getSumOfSquares();    // Sum of Squares
        f.getVariance();        // Variance
        
        logger.info("count:{}",f.getCount());
    }
    
    @Test
    public void testTermsStatsFacets() throws IOException {
        TermsStatsFacetBuilder tsfb = FacetBuilders.termsStatsFacet("f")
                                        .keyField("user")
                                        .valueField("age");
        
        SearchResponse sr = client.prepareSearch()
                            .setQuery(QueryBuilders.matchAllQuery())
                            .addFacet(tsfb)
                            .execute().actionGet();
        
        TermsStatsFacet f = (TermsStatsFacet) sr.getFacets().facetsAsMap().get("f");

        // For each entry
        for (TermsStatsFacet.Entry entry : f) {
            entry.getTerm();            // Term
            entry.getCount();           // Doc count
            entry.getMin();             // Min value
            entry.getMax();             // Max value
            entry.getMean();            // Mean
            entry.getTotal();           // Sum of values
            logger.info("Term:{};Doc count:{}",entry.getTerm(),entry.getCount());
        }
    }

    @Test
    public void testGeoDistanceFacets() throws IOException {
        
        client.prepareIndex("twitter", "tweet", "14").setSource(XContentFactory.jsonBuilder().startObject()
            .field("user", "tomy")
            .field("postDate", new Date())
            .field("message","hello!")
            .field("age",30)
            .startObject("location").field("lat", 40.65).field("lon", -73.95).endObject()
            .endObject()).execute().actionGet();
        
        
        GeoDistanceFacetBuilder gdfb = FacetBuilders.geoDistanceFacet("f")
                                        .field("location")              // Field containing coordinates we want to compare with
                                        .point(40, -70)                     // Point from where we start (0)
                                        .addUnboundedFrom(10)               // 0 to 10 km (excluded)
                                        .addRange(10, 20)                   // 10 to 20 km (excluded)
                                        .addRange(20, 100)                  // 20 to 100 km (excluded)
                                        .addUnboundedTo(100)                // from 100 km to infinity (and beyond ;-)  )
                                        .unit(DistanceUnit.KILOMETERS);     // All distances are in kilometers. Can be MILES
        
        SearchResponse sr = client.prepareSearch() // from NY
                            .setQuery(QueryBuilders.matchAllQuery())
                            .addFacet(gdfb)
                            .execute().actionGet();
        
        GeoDistanceFacet f = (GeoDistanceFacet) sr.getFacets().facetsAsMap().get("f");

        // For each entry
        for (GeoDistanceFacet.Entry entry : f) {
            entry.getFrom();            // Distance from requested
            entry.getTo();              // Distance to requested
            entry.getCount();           // Doc count
            entry.getMin();             // Min value
            entry.getMax();             // Max value
            entry.getTotal();           // Sum of values
            entry.getMean();            // Mean
            logger.info("Doc count is {}",entry.getCount());
        }
    }
   
}

