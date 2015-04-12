package com.jason.framework.safe;

/**
 * Html过滤服务
 * @author Jason
 * @date 2015-4-12 下午09:55:53
 */
public interface HtmlEscapeService {

	/**
	 * 比较宽松地过滤html，适用于富文本编辑器内容或其他html内容
	 * @param bodyHtml
	 * @return 
	 */
	String cleanRelaxed(String bodyHtml);
	
	/**
	 * 是否存在不安全的html（松散轻松模型）
	 * @param bodyHtml
	 * @return
	 */
	boolean isValidRelaxed(String bodyHtml);
	
	/**
	 * 去掉所有标签，返回纯文字.适用于textarea，input
	 * @param bodyHtml
	 * @return 
	 */
	String cleanNone(String bodyHtml);
	
	/**
	 * 是否存在html
	 * @param bodyHtml
	 * @return
	 */
	boolean isValidNone(String bodyHtml);
	
}
