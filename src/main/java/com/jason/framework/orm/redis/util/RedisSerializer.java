package com.jason.framework.orm.redis.util;

public interface RedisSerializer<T> {

	byte[] serialize(T t) throws SerializationException;

	T deserialize(byte[] bytes) throws SerializationException;
}
