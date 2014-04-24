package com.jason.framework.mapper;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import com.jason.framework.util.ExceptionUtils;


/**
 * Json 格式转换类
 * 简单封装Jackson，实现JSON String<->Java Object的Mapper.
 * 
 * @author Jason
 * @date 2013-5-25 下午10:14:35
 */
public final class JsonMapper {
	private static ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
	}

	/**
	 * json to string
	 * @param bean
	 * @return String
	 */
	public static String toJsonString(Object bean) {
		try {
			return mapper.writeValueAsString(bean);
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}
	}

	/**
	 * json to object
	 * @param <T>
	 * @param jsonString
	 * @param clazz
	 * @return <T> T
	 */
	public static <T> T fromJsonString(String jsonString, Class<T> clazz) {
		try {
			return mapper.readValue(jsonString, clazz);
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}
	}

	/**
	 * 
	 * @param bean
	 * @param clazz
	 * @return
	 */
	public static <T> T newfor(Object bean, Class<T> clazz) {
		try {
			return mapper.convertValue(bean, clazz);
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}
	}
	
	/**
	 * 读取树结构的json字符串
	 * @param jsonString 
	 * @return JsonNode
	 */
	public static JsonNode readTree(String jsonString){
		try {
			return mapper.readTree(jsonString);
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e);
		}
	}

	/**
	 * 解析JsonNode
	 * @param jsonNode
	 * @param fieldName
	 * @return
	 */
	public static JsonNode path(JsonNode jsonNode,String fieldName){
		return jsonNode.path(fieldName);
	}
	public static String asText(JsonNode jsonNode,String fieldName){
		return path(jsonNode, fieldName).asText();
	}
	public static int asInt(JsonNode jsonNode,String fieldName){
		return path(jsonNode, fieldName).asInt();
	}
	public static long asLong(JsonNode jsonNode,String fieldName){
		return path(jsonNode, fieldName).asLong();
	}
	public static boolean asBoolean(JsonNode jsonNode,String fieldName){
		return path(jsonNode, fieldName).asBoolean();
	}
	public static double asDouble(JsonNode jsonNode,String fieldName){
		return path(jsonNode, fieldName).asDouble();
	}
	
	private JsonMapper() {}
}
