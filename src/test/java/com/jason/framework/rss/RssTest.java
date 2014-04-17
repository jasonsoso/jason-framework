package com.jason.framework.rss;


import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Content;
import com.sun.syndication.feed.rss.Description;
import com.sun.syndication.feed.rss.Item;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;
import com.sun.syndication.io.WireFeedOutput;
import com.sun.syndication.io.XmlReader;

public class RssTest {
    
    @Test
    public void testRssReader() throws Exception {
        /*System.setProperty("http.proxyHost", "10.191.131.13"); 
        System.setProperty("http.proxyPort", "3128"); 
        String authStr = "account:password"; 
        String auth = "Basic " + new BASE64Encoder().encode(authStr.getBytes()); 
        URL feedurl = new URL("http://rss.sina.com.cn/news/marquee/ddt.xml"); //指定rss位置 
        URLConnection uc = feedurl.openConnection(); 
        //设定代理 
        uc.setRequestProperty("Proxy-Authorization", auth); 
        uc.addRequestProperty("Referer", "localhost");
        
        */
		SyndFeedInput input = new SyndFeedInput(); 
		SyndFeed feed = input.build(new XmlReader(new URL("http://rss.sina.com.cn/news/marquee/ddt.xml")));
        //SyndFeed feed = input.build(new XmlReader(uc));
		
        @SuppressWarnings("unchecked")
		List<SyndEntry> entries = feed.getEntries(); 
        for (SyndEntry entry:entries) 
        { 
            System.out.print(entry.getTitle()); 
            System.out.print(entry.getLink()); 
        } 
    }
    @Test
    public void testRssPublish () throws Exception{
    	//DateFormat dateParse = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Channel channel = new Channel("rss_2.0"); // 该type参数为固定格式
        channel.setTitle("jason test");
        channel.setDescription("jason  描述");
        channel.setLink("http://qing.jasonsoso.com/rss");
        channel.setPubDate(date);
        channel.setEncoding("UTF-8");
        
        List<Item> items = new ArrayList<Item>();
        Item item = new Item();
        item.setAuthor("jason tan");
        item.setTitle("item 1");
        Description desc = new Description();
        desc.setType("item desc type");
        desc.setValue("item desc value");
        item.setDescription(desc);
        items.add(item);// 添加一个item
        
        Item item2 = new Item();
        item2.setAuthor("zhangwei");
        item2.setTitle("use rome to read rss");
        Description desc2 = new Description();
        desc2.setValue("you must import rome_1.0.jar & jdom.jar");
        item2.setDescription(desc2);
        Content content = new Content();
        content.setValue("rome是用来发布读取rss的工具，遵循rss标准的XML");
        item2.setContent(content);
        items.add(item2);// 添加一个item
        
        channel.setItems(items);
        WireFeedOutput out = new WireFeedOutput();
        try {
            // Channel继承与WireFeed
        	System.out.println("-------testRssPublish------");
            System.out.println(out.outputString(channel));
        } catch (FeedException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * 使用接口SyndFeed & SyndEntry
     */
    @Test
    public void testSyndFeedXml () throws Exception{
        Date date = new Date();
        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_2.0"); // 该type参数为固定格式
        feed.setTitle("test rome channel title");
        feed.setDescription("channel的描述");
        feed.setLink("http://hi.baidu.com/openj/rss");
        feed.setPublishedDate(date);
        
        List<SyndEntry> items = new ArrayList<SyndEntry>();
        SyndEntry entry = new SyndEntryImpl();
        entry.setAuthor("zhangwei");
        entry.setTitle("item title");
        SyndContent desc = new SyndContentImpl();
        desc.setType("item desc type");
        desc.setValue("item desc value");
        entry.setDescription(desc);
        items.add(entry);// 添加一个entry
        
        entry = new SyndEntryImpl();
        entry.setAuthor("zhangwei");
        entry.setTitle("use rome to read rss");
        desc = new SyndContentImpl();
        desc.setType("plain/text");
        desc.setValue("you must import rome_1.0.jar & jdom.jar");
        entry.setDescription(desc);
        items.add(entry);// 添加一个entry
        
        feed.setEntries(items);
        SyndFeedOutput out = new SyndFeedOutput();
        try {
        	System.out.println("-------testSyndFeedXml------");
            System.out.println(out.outputString(feed));
        } catch (FeedException ex) {
            ex.printStackTrace();
        }
    }
}

