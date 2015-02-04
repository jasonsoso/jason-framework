package com.jason.framework.orm.redis.util;

public interface SerializerHandler<T> {

	byte[] serialize(T t) throws SerializerException;

	T deserialize(byte[] bytes) throws SerializerException;
}
