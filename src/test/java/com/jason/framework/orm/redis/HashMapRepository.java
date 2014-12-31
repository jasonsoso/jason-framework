package com.jason.framework.orm.redis;

import java.util.Map;

public interface HashMapRepository {
	void set(String key, String mapKey, String mapVal);

	void set(String key, Map<String, String> map);

	Map<String, String> get(String key);

	String get(String key, String mapKey);

	void del(String key, String mapKey);

	void del(String key, String[] mapKey);

	boolean exists(String key, String mapKey);
}
