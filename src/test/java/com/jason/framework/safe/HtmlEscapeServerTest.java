package com.jason.framework.safe;

import org.junit.Test;

import com.jason.framework.safe.defaults.DefaultHtmlEscapeService;

public class HtmlEscapeServerTest {
	

	@Test
	public void testHtmlSafe() throws Exception {
		HtmlEscapeService htmlEscapeService = new DefaultHtmlEscapeService();
		
		String unsafe = "<script>alert('123')</script>"+
			  "<div>hello div</div>"+
			  "<img class='lazy' data-original='http://www.iree185.com/resources/proprty/2015/0310/ad9995ae2ce742f08902118e58b038c5_200_160.jpg'  alt='Sleek City Loft - Shepherdess Walk, London, N1' title='Sleek City Loft - Shepherdess Walk, London, N1' />"+
			  "<p><a href='http://example.com/' onclick='stealCookies()'>Link</a></p>";
		
		boolean isSafe = htmlEscapeService.isValidRelaxed(unsafe);
		System.out.println("是否安全的html："+isSafe);
		
		String safe = htmlEscapeService.cleanRelaxed(unsafe);
		System.out.println("过滤后的的html："+safe);
	}	
	
	@Test
	public void testHtmlSafe2() throws Exception {
		HtmlEscapeService htmlEscapeService = new DefaultHtmlEscapeService();
		
		String unsafe = "123测试<script>alert('123')</script>"+
			  "<div>hello div</div>"+
			  "<img class='lazy' data-original='http://www.iree185.com/resources/proprty/2015/0310/ad9995ae2ce742f08902118e58b038c5_200_160.jpg'  alt='Sleek City Loft - Shepherdess Walk, London, N1' title='Sleek City Loft - Shepherdess Walk, London, N1' />"+
			  "<p><a href='http://example.com/' onclick='stealCookies()'>Link</a></p>";
		
		System.out.println("testHtmlSafe2 过滤html："+htmlEscapeService.cleanNone(unsafe));
		
	}	
}
