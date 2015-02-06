package com.jason.framework.orm.elasticsearch;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse.AnalyzeToken;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jason.framework.AbstractTestBase;
import com.jason.framework.orm.Page;
import com.jason.framework.orm.elasticsearch.constant.IndexConstant.Index;
import com.jason.framework.orm.elasticsearch.constant.IndexConstant.Type;
import com.jason.framework.orm.elasticsearch.domain.Info;
import com.jason.framework.orm.elasticsearch.support.ESHelper;



public class InfoRepositoryTest  extends AbstractTestBase {
	
	private static Logger logger = LoggerFactory.getLogger(InfoRepositoryTest.class);

    @Autowired
    private InfoRepository infoRepository;
    
    @Autowired
	private TransportClient transportClient;

    @Before
    public void deleteAll(){
    	
    }
    
    /*@Test
	public void testSuggeust() throws IOException {
    	List<String> suggestions = new SuggestRequestBuilder(transportClient)
		.field("title")
		.term("纽")
		.size(10)
		.similarity(2.0f)
		.execute().actionGet().suggestions();
    	
    	for (String str:suggestions) {
			System.out.println("str:"+str);
		}
    }*/
    
    
    /**
     * 测试高亮显示搜素结果
     * @throws IOException
     */
    @Test
	public void testHighlight() throws IOException {

    	String queryString = "测试";
		SearchResponse searchResponse = transportClient.prepareSearch(Index.INFO.toString())
                .setTypes(Type.INFO_INFO.toString())
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.queryString(queryString))
                .addHighlightedField("content")
                //.setHighlighterPreTags("<tag1>", "<tag2>")
				//.setHighlighterPostTags("</tag3>", "</tag4>")
                .setFrom(0).setSize(20).setExplain(true)
                .execute()
                .actionGet();
		
		SearchHits hits = searchResponse.getHits(); 
        
		for (SearchHit hit : hits) {
			
			Map<String, HighlightField> result = hit.highlightFields();
			//logger.info("A map of highlighted fields:{}", result);

        	//Map<String, Object> map = hit.getSource();
        	String id = hit.getId();
        	
        	logger.info("---------------------id:{}", id);
        	//logger.info("A map of map fields:{}", map);
        	
			HighlightField titleField = result.get("content");
			if(null != titleField){
				Text[] titleTexts =  titleField.fragments();  
	            for(Text text : titleTexts){  
	            	logger.info("title text: :{}",text);
	            }  
			}
		}
    }
    @Test
	public void testPinyinQuery() throws IOException {
		AnalyzeResponse analyzeResponse = transportClient.admin().indices()
				.prepareAnalyze(Index.INFO.toString(), "阳光天使")
				.setAnalyzer("pinyin")
				.execute().actionGet();

		logger.info("size:{}", analyzeResponse.getTokens().size());
		List<AnalyzeToken> list = analyzeResponse.getTokens();
		for (AnalyzeToken token : list) {
			logger.info("Term:{}", token.getTerm());
		}
	}
    @Test
	public void testIKQuery() throws IOException {
		AnalyzeResponse analyzeResponse = transportClient.admin().indices()
				.prepareAnalyze(Index.INFO.toString(), "中国投资者在英国买房像乐购购物 当地人的购房梦想难实现")
				.setAnalyzer("ik")
				.execute().actionGet();

		logger.info("size:{}", analyzeResponse.getTokens().size());
		List<AnalyzeToken> list = analyzeResponse.getTokens();
		for (AnalyzeToken token : list) {
			logger.info("Term:{}", token.getTerm());
		}
		
	}
    
    @Test
    public void testIndex(){
    	
    	Info entity = new Info();
    	entity.setId(100);
		entity.setSsid(0);
		entity.setIndexId(ESHelper.setIndexIdfromIdAndSsid(entity.getSsid(), entity.getId()));
		
		entity.setChannel(2401);
		entity.setTitle("纽约的公寓和他们的缴税方式,纽约不错的现在地方");
		entity.setAuthor("Jason");
		entity.setSource("IREE网");
		entity.setContent("Condo（产权公寓）购买的是特定公寓单位的独立产权，和一般商品房一样。业主需要承担公寓每年的房产税(real estate tax)以及物业管理税(common Charge)。购买者需要向管理委员会提交申请，并且提供财务状况。一般这个申请时间比co-op要短很多，并且允许外国人买。可以随便出租，非常方便。新建的公寓楼为了方便买卖都是condo。Sponsor unit是指新房（一手房交易），开发商卖的一手房。除了sponsor unit, 其他都是resale二手房交易。因为sponsor unit是从开发商手里购买，所以转让费和双方的律师费都会由买房者承担。买新楼盘者，往往会要求多支付Working Capital Contribution, 大楼会让新住户多交付2~3个月的物业费作为集体开支和支付大楼管理员的房间。由于co-op 和condo的产权性质不同，co-op的产权属于它的公司，condo的产权属于个人，那么，属于个人产权的房子condo，为了不动产产权保险需自己购买产权保险（title insurance）。Co-op的地税和物业税都是个人交给co-op，并由coop代缴。codon物业费交给物业公司，税需有买家交给政府，税可以3月一交，6月一交，或者1年一交。如果中国投资者不居住在美国，可以直接通过美国政府网站付地税。");
		entity.setStatus(0);
		entity.setTag("中国投资者 房产税 物业税 产权");
		entity.setCountry(0);
		entity.setCreateTime(new Date().getTime());
		entity.setPublishTime(new Date().getTime());
		entity.setExpirationDate(new Date().getTime());
		entity.setPicture("");
		
		infoRepository.index(entity);
		
		
		Info entity2 = infoRepository.get(entity.getIndexId());
    	Assert.assertNotNull("新增则不为空！", entity2);
    	System.out.println("新增数据为："+entity2.toString());
    	
        
    }
    @Test
    public void testIndexList(){
    	List<Info> list = new ArrayList<Info>();
    	
    	Info entity2 = new Info();
    	entity2.setId(2);
    	entity2.setSsid(0);
    	entity2.setIndexId(ESHelper.setIndexIdfromIdAndSsid(entity2.getSsid(), entity2.getId()));
    	
    	entity2.setChannel(2401);
    	entity2.setTitle("政策松绑 香港房地产再度升温 ");
    	entity2.setAuthor("Jason");
    	entity2.setSource("IREE网");
    	entity2.setContent("房市政策松绑后，尖沙咀的奥斯汀住宅项目、大埔区的逸珑湾、位于荃湾的环宇海湾和大角咀的浪澄湾四个楼盘共有642套房屋在售。截至晚上7点，已经售出464套。房屋中介机构说，投资购买房产的人多于去年。   经济学家关焯照说，政府决定放松第二套房双倍印花税，促进了新房和二手房的销量。   会德丰和新世界开发的奥斯汀住宅项目的209套房子只剩下五套，房屋价格在1259万港币到4100万港币之间，总共进账37.5亿港币。   美联物业布少明说：“我们的客户中，有约40%的人购买奥斯汀住宅项目是为了投资，其他人是自住。购房者中十分之一是内地人。”   他说，购买逸珑湾楼盘的人中20%是为了投资，去年夏天购买新房的人中仅有10%是为了投资。   他说：“奥斯汀住宅项目紧邻香港地铁柯士甸站和九龙站，投资者看好这里的租赁市场。他们看到即使在执行房地产降温政策的时候，这里的房价也没有大幅下降，他们认为这里的房子会有很好的回报率，这使投资者又回到房地产市场。”   一位政府房地产政策顾问说，在六个月内出售自己以前的住房的人可以免征双倍印花税，这一放松政策刺激了房地产市场。   他说：“购房者不再担心一些让房地产降温的政策，因为他们看到房价在受到打压时期都没有大跌。政府的这一举措刺激了人们被抑制的购买热情。”");
    	entity2.setStatus(0);
    	entity2.setTag("香港房地产 香港楼价 香港楼市");
    	entity2.setCountry(0);
    	entity2.setCreateTime(new Date().getTime());
    	entity2.setPublishTime(new Date().getTime());
    	entity2.setExpirationDate(new Date().getTime());
    	entity2.setPicture("/proprty/2014/0801/32258efab81c4709815f503bb40bb963_.jpg");
		
		list.add(entity2);
		
		

		Info entity3 = new Info();
		entity3.setId(3);
		entity3.setSsid(0);
		entity3.setIndexId(ESHelper.setIndexIdfromIdAndSsid(entity3.getSsid(), entity3.getId()));
		
		entity3.setChannel(2402);
		entity3.setTitle("2015年4月起温哥华新房税将降低 房产税收计算一目了然");
		entity3.setAuthor("Jason");
		entity3.setSource("IREE网");
		entity3.setContent("2013年4月1日开始，BC省引用新的税项，将原来的12% GST/PST合并销售税HST转回HST/PST过渡税。买卖新房，按照不同的签合同- 如果买卖合同和成交日期都是2012年4月1日之前，你已付12%的HST，购买新房价格在$525,000以内，按比率最高退税$26,250。新房价格超过$525,000，统一退税$26,250。- 如果买卖合同是2012年4月1日之前，成交日期是2013年4月1日之前，你需要付12%的HST，购买新房价格在$850,000以内，按比率最高退税$42,500。新房价格超过$850,000， 统一退税$42,500。- 如果买卖合同是2012年4月1日之前， 成交日期是2013年4月1日之后至2015年4月1日之前，你不需要付HST的7%省税部份。你需要付5%的省税加2%的过渡税，这2%也包含建筑商需 要交的建材税。如果成交日期是2015年4月1日之后，2%的税就不需要付。");
		entity3.setStatus(0);
		entity3.setTag("温哥华买房 加拿大房产税");
		entity3.setCountry(0);
		entity3.setCreateTime(new Date().getTime());
		entity3.setPublishTime(new Date().getTime());
		entity3.setExpirationDate(new Date().getTime());
		entity3.setPicture("/proprty/2014/0801/1a5f0179d3854f98ab960ff17a7add53_.jpg");
    	
		
		list.add(entity3);
		
		Info entity4 = new Info();
		entity4.setId(4);
		entity4.setSsid(0);
		entity4.setIndexId(ESHelper.setIndexIdfromIdAndSsid(entity4.getSsid(), entity4.getId()));
		
		entity4.setChannel(2403);
		entity4.setTitle("中国投资者在英国买房像乐购购物 当地人的购房梦想难实现");
		entity4.setAuthor("Jason");
		entity4.setSource("IREE网");
		entity4.setContent("英国《每日邮报》6月13日发表题为《你的孩子们买不起房的真正原因：从利物浦到克罗伊登，房子甚至在建成前就被中国的中产阶级抢购了》的文章，作者为戴维·琼斯。文章称，在无数年轻英国人充满渴望地凝视着房地产中介的窗口中显示的他们永远没有希望买得起的房产时，作者近日在6000英里之遥的香港参加了一个盛大的英国房产展，而这里上演的情形无情地嘲弄了这些英国年轻人的困境。");
		entity4.setStatus(0);
		entity4.setTag("中国投资者 英国房产投资 英国房产 曼切斯特房价 伦敦大学");
		entity4.setCountry(0);
		entity4.setCreateTime(new Date().getTime());
		entity4.setPublishTime(new Date().getTime());
		entity4.setExpirationDate(new Date().getTime());
		entity4.setPicture("/proprty/2014/0801/31bc94fccd5944888f0046454656cba9_.jpg");
		
		
		list.add(entity4);
		
		infoRepository.index(list);
    	
    }
    
    //@Test
    public void testDelete(){
    }
    
    //@Test
    public void testDeleteAll(){
    }
    @Test
    public void testQuery(){
    	List<Info> list = infoRepository.query("*",10);
    	for (Info entity:list) {
			System.out.println(entity.toString());
		}
    }
    @Test
    public void testQueryPage(){
    	Map<String, Object> params = new HashMap<String, Object>();
    	//params.put("channel", 1);
    	
		
    	Page<Info> page = new Page<Info>()
    		//.setOrderBy("publish_time")
    		.setOrder("desc")
    		.setParams(params);
    	page = infoRepository.queryPage(page, "");
    	
    	long totalCount = page.getTotalCount();
    	List<Info> list = page.getResult();
    	System.out.println("totalCount:"+totalCount);
    	for (Info entity:list) {
			System.out.println("---------Info:"+entity.toString());
		}
    }
    @Test
    public void testMoreLikeThis(){
    	List<Info> list = infoRepository.moreLikeThis("1-340", 5);
    	for (Info entity:list) {
			System.out.println("---------Info:"+entity.toString());
		}
    }

}
