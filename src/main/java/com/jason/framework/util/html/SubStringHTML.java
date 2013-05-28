package com.jason.framework.util.html;


/**
 * java截取带html标签的字符串,再把标签补全(保证页面显示效果)<br>
 * 一般是用在字符串中有html标签的截取.如: 后台发布用了在线编辑器, 前台显示内容要截取的情况.<br>
 * 
 * @author YangJunping
 * @date 2010-7-15
 */
public final class SubStringHTML {
	
	private SubStringHTML(){}

	/**
	 * 去除html标签  截字符串 
	 * @param input
	 * @param length
	 * @return
	 */
	public static String subStringWithoutHtml(String param,int length){
		String str = param.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "")
						  .replaceAll("</[a-zA-Z]+[1-9]?>", "");
		int len = str.length();
        if (len >= length) {
        	str = str.substring(0, length);  
            str += "...";  
        }
		return str;
	}
	
	/**
	 * http://blog.csdn.net/zdtwyjp/article/details/5736430
	 * 按子节长度截取字符串(支持截取带HTML代码样式的字符串)<br>
	 * 如：<span>中国人发在线</span> 当截取2个字节得到的结果是：<span>中国
	 * @param param
	 *            将要截取的含html代码的字符串参数
	 * @param length
	 *            截取的字节长度
	 * @return 返回截取后的字符串
	 * @author YangJunping
	 * @date 2010-7-15
	 */
	public static String subStringHTML(String param, int length) {
		StringBuffer result = new StringBuffer();
		int n = 0;
		char temp;
		// 是不是HTML代码
		boolean isCode = false; 
		// 是不是HTML特殊字符,如 
		boolean isHTML = false; 
		for(int i = 0; i < param.length(); i++) {
			temp = param.charAt(i);
			if(temp == '<') {
				isCode = true;
			}else if(temp == '&') {
				isHTML = true;
			}else if(temp == '>' && isCode) {
				n = n - 1;
				isCode = false;
			}else if(temp == ';' && isHTML) {
				isHTML = false;
			}
			if(!isCode && !isHTML) {
				n = n + 1;
				// UNICODE码字符占两个字节
				if((temp + "").getBytes().length > 1) {
					n = n + 1;
				}
			}
			result.append(temp);
			if(n >= length) {
				break;
			}
		}
		result.append("...");
		return fix(result.toString());
	}
	/**
	 * 补全HTML代码<br>
	 * 如：<span>中国 ---> <span>中国</span>
	 * 
	 * @param str
	 * @return
	 * @author YangJunping
	 * @date 2010-7-15
	 */
	private static String fix(String str) {
		// 存放修复后的字符串
		StringBuffer fixed = new StringBuffer(); 
		TagsList[] unclosedTags = getUnclosedTags(str);
		// 生成新字符串
		for(int i = unclosedTags[0].size() - 1; i > -1; i--) {
			fixed.append("<" + unclosedTags[0].get(i) + ">");
		}
		fixed.append(str);
		for(int i = unclosedTags[1].size() - 1; i > -1; i--) {
			String s = unclosedTags[1].get(i);
			if(s != null) {
				fixed.append("</" + s + ">");
			}
		}
		return fixed.toString();
	}
	private static TagsList[] getUnclosedTags(String str) {
		// 存放标签
		StringBuffer temp = new StringBuffer(); 	
		TagsList[] unclosedTags = new TagsList[2];
		// 前不闭合，如有</div>而前面没有<div> 
		unclosedTags[0] = new TagsList(); 
		// 后不闭合，如有<div>而后面没有</div> 
		unclosedTags[1] = new TagsList(); 	
		// 记录双引号"或单引号' 
		boolean flag = false;	
		@SuppressWarnings("unused")
		// 记录需要跳过''还是"" 
		char currentJump = ' '; 	
		// 当前 & 上一个  
		char current = ' ', last = ' '; 	
		// 开始判断 
		for(int i = 0; i < str.length();) {
			// 读取一个字符 
			current = str.charAt(i++); 	
			if(current == '"' || current == '\'') {
				// 若为引号，flag翻转 
				flag = flag ? false : true; 	
				currentJump = current;
			}
			if(!flag) {
				// 开始提取标签 
				if(current == '<') { 	
					current = str.charAt(i++);
					// 标签的闭合部分，如</div> 
					if(current == '/') { 	
						current = str.charAt(i++);
						// 读取标签 
						while(i < str.length() && current != '>') {
							temp.append(current);
							current = str.charAt(i++);
						}
						// 从tags_bottom移除一个闭合的标签 
						// 若移除失败，说明前面没有需要闭合的标签 
						if(!unclosedTags[1].remove(temp.toString())) { 
							// 此标签需要前闭合 
							unclosedTags[0].add(temp.toString()); 
						}
						// 清空temp 
						temp.delete(0, temp.length()); 
					}else { 
						// 标签的前部分，如<div> 
						last = current;
						while(i < str.length() && current != ' ' && current != ' ' && current != '>') {
							temp.append(current);
							last = current;
							current = str.charAt(i++);
						}
						// 已经读取到标签，跳过其他内容，如<div id=test>跳过id=test 
						while(i < str.length() && current != '>') {
							last = current;
							current = str.charAt(i++);
							// 判断引号 
							if(current == '"' || current == '\'') { 
								flag = flag ? false : true;
								currentJump = current;
								// 若引号不闭合，跳过到下一个引号之间的内容 
								if(flag) { 
									/* while(i < str.length() && str.charAt(i++) != currentJump)	; */
									current = str.charAt(i++);
									flag = false;
								}
							}
						}
						// 判断这种类型：<TagName /> 
						if(last != '/' && current == '>'){ 
							unclosedTags[1].add(temp.toString());
						}
						temp.delete(0, temp.length());
					}
				}
			}else {
				/*while(i < str.length() && str.charAt(i++) != currentJump)	; // 跳过引号之间的部分  */ 
				flag = false;
			}
		}
		return unclosedTags;
	}
	
	
}

