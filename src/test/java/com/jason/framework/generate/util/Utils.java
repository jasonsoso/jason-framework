package com.jason.framework.generate.util;

import java.util.HashMap;

import com.sun.xml.internal.ws.util.StringUtils;

public class Utils {
	
	static HashMap<String, String> typeMap = new HashMap<String, String>();
	static HashMap<String, String> idbcTypeMap = new HashMap<String, String>();

	public static String toProperty(String name){
		String[] strings = name.split("_");
		StringBuilder strBuff = new StringBuilder();
		if (strings.length > 0){
			strBuff.append(StringUtils.decapitalize(strings[0]));
		}
		for (int i = 1; i < strings.length; i++) {
			strBuff.append(StringUtils.capitalize(strings[i]));
		}
		return strBuff.toString();
	}
	
	static {
		typeMap.put("int", "Integer");
		typeMap.put("smallint", "Integer");
		typeMap.put("mediumint", "Integer");
		typeMap.put("bigint", "Long");
		typeMap.put("decimal", "java.math.BigDecimal");

		typeMap.put("bit", "Boolean");

		typeMap.put("varchar", "String");
		typeMap.put("text", "String");
		typeMap.put("char", "String");
		
		typeMap.put("date", "java.util.Date");
		typeMap.put("time", "java.util.Date");
		typeMap.put("datetime", "java.util.Date");
		typeMap.put("timestamp", "java.util.Date");
		
		idbcTypeMap.put("int", "INTEGER");
		idbcTypeMap.put("mediumint", "INTEGER");
		idbcTypeMap.put("datetime", "TIMESTAMP");
		idbcTypeMap.put("text", "VARCHAR");
	}
	
	public static String getJavaType(String name){
		String type = typeMap.get(name);
		if (type == null){
			type = "String";
		}
		return type;
	}
	public static String getJdbcType(String name){
		String type = idbcTypeMap.get(name);
		if (type == null){
			type = name;
		}
		return type;
	}
	
	public static String procJavatype(String type){
		int lastIndexOf = type.lastIndexOf(".");
		if (lastIndexOf >= 0){
			return type.substring(lastIndexOf+1);
		}
		return type;
	}
}
