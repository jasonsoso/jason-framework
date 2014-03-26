package com.jason.framework.util.html;

import org.junit.Test;
import org.springframework.web.util.HtmlUtils;

public class HtmlTest {

	String html = "<ul class=\"nav\"><li><a href=\"http://www.mkfree.com\">首 页</a></li>"
		+ "<li class=\"active\"><a href=\"http://blog.mkfree.com\">博客</a></li>"
		+ "<li><a href=\"#\">RSS</a></li></ul>";

	/**
	 * 把html的标签特殊字符转换成普通字符 
	 */
	@Test
	public void testhtmlEscape() {
		String value = HtmlUtils.htmlEscape(html);
		System.out.println(value);
	}

	/**
	 * 把html的特殊字符转换成普通数字
	 */
	@Test
	public void testhtmlEscapeDecimal() {
		String value = HtmlUtils.htmlEscapeDecimal(html);
		System.out.println(value);
	}

	/**
	 * 把html的特殊字符转换成符合Intel HEX文件的字符串
	 */
	@Test
	public void htmlEscapeHex() {
		String value = HtmlUtils.htmlEscapeHex(html);
		System.out.println(value);
	}

	/**
	 * 把html的特殊字符反转换成html标签 以上三种方法都可以反转换
	 */
	@Test
	public void htmlUnescape() {
		String tmp = HtmlUtils.htmlEscapeDecimal(html);
		System.out.println(tmp);

		String value = HtmlUtils.htmlUnescape(tmp);
		System.out.println(value);
	}
	/**
	 * 刪除html
	 */
	@Test
	public void testHtml(){
		String input="<p>欢迎使用ueditor</p><p><span style='font-family:consolas, &#39;lucida console&#39;, monospace;font-size:12px;-webkit-text-size-adjust:none;background-color:#ffffff;'>getContentTxt</span></p><p>你好</p><p><img src='http://img.baidu.com/hi/jx2/j_0005.gif' /></p><p><img src='http://www.sqrb.com.cn/sqnews/attachement/jpg/site2/20130130/1078d2c5c9a71272adff1b.jpg' /></p>";
		int length = 50;
        // 去掉所有html元素,  
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "");
        str = str.replaceAll( "<[^>]*>", "");  
        str = str.replaceAll("[(/>)<]", "");  
        int len = str.length();  
        if (len >= length) {  
        	str = str.substring(0, length);  
            str += "......";  
        }
        System.out.println(str);
        
        String str2 = HtmlHelper.filterHtml(input);
        System.out.println(str2);
	}
	@Test
	public void testHtml2(){
		String text="<p>欢迎使用ueditor</p><p><span style='font-family:consolas, &#39;lucida console&#39;, monospace;font-size:12px;-webkit-text-size-adjust:none;background-color:#ffffff;'>getContentTxt</span></p><p>你好</p><p><img src='http://img.baidu.com/hi/jx2/j_0005.gif' /></p><p><img src='http://www.sqrb.com.cn/sqnews/attachement/jpg/site2/20130130/1078d2c5c9a71272adff1b.jpg' /></p>";
		
		//去除html标签  
        String str = text.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "")     
                         .replaceAll("</[a-zA-Z]+[1-9]?>", "");
        System.out.println(str);
        
	}

}
