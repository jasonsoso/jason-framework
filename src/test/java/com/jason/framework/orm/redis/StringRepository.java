package com.jason.framework.orm.redis;

import java.util.List;
import java.util.Map;


public interface StringRepository {
	
	void set(String key, String value);
	
	void set(Map<String,String> batchData);

	String get(String key);
	
	List<String> get(String[] keys);
	
	void del(String key);

	boolean exists(String key);
}
