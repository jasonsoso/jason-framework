package com.jason.framework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串 辅助类
 * @author Jason
 */
public final class StringsHelper {
	
	
	private StringsHelper(){}
	
	/**
	 * 文件名获取后缀名
	 * @param filename
	 * @return
	 */
	public static String suffix(String filename) {
		if (isBlank(filename)) {
			return "";
		}
		int dotIndex = filename.lastIndexOf('.');
		if (dotIndex == -1) {
			return "";
		}
		return filename.substring(dotIndex + 1);
	}
	
	/**
	 * 是否为空
	 * @param text
	 * @return
	 */
	public static boolean isBlank(String text) {
		if (isEmpty(text)) {
			return true;
		}
		int length = text.length();
		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(text.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	/**
	 * eg : null => true
	 * eg : "" => true
	 * @param text
	 * @return
	 */
	public static boolean isEmpty(String text) {
		if (null == text) {
			return true;
		}
		return text.length() <= 0;
	}
	
	
    /**
     * 驼峰式 中的 下划线
     */
    public static final char UNDERLINE='_';
    
    /**
     * 驼峰式 转换为 下划线,大写字母前添加下划线
     * eg:userType => user_type
     * @param param
     * @return
     */
    public static String camelToUnderline(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (Character.isUpperCase(c)){
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }
    /**
     * 下划线 转换为 驼峰式
     * @param param
     * @return
     */
    public static String underlineToCamel(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (c==UNDERLINE){
               if (++i<len){
                   sb.append(Character.toUpperCase(param.charAt(i)));
               }
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    /**
     * 下划线 转换为 驼峰式
     * @param param
     * @return
     */
    public static String underlineToCamel2(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        StringBuilder sb=new StringBuilder(param);
        Matcher mc= Pattern.compile("_").matcher(param);
        int i=0;
        while (mc.find()){
            int position=mc.end()-(i++);
            //String.valueOf(Character.toUpperCase(sb.charAt(position)));
            sb.replace(position-1,position+1,sb.substring(position,position+1).toUpperCase());
        }
        return sb.toString();
    }
	public static void main(String[] args) {
		String name = "user_type";
		String n = underlineToCamel(name);
		System.out.println("下划线 转换为 驼峰式:"+name+" => "+n);
		String norder = underlineToCamel2(name);
		System.out.println("下划线 转换为 驼峰式:"+name+" => "+norder);
		
		
		
		String name2 = "userType";
		String n2= camelToUnderline(name2);
		System.out.println("驼峰式 转换为 下划线:"+name2+" => "+n2);
	}

}
