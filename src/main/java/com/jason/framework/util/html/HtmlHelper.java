package com.jason.framework.util.html;

import org.apache.commons.lang.StringUtils;


/**
 * html 工具类
 * @author Jason
 * @date 2014-3-16 下午03:26:55
 */
public class HtmlHelper extends org.springframework.web.util.HtmlUtils {

	/**
	 * 过虑html代码,获取内容
	 * @param input html内容
	 * @return
	 */
	public static String filterHtml(String input) {
		if(StringUtils.isBlank(input)){
			return "";
		}
		// 去掉所有html元素,
		String str = input
						.replaceAll("\\&[a-zA-Z]{1,10};", "")
						.replaceAll("<[^>]*>", "")
						.replaceAll("[(/>)<]", "")
						.replaceAll("\r", "")
						.replaceAll("\n", "")
						.replaceAll("\t", "");
		return str;
	}
}
