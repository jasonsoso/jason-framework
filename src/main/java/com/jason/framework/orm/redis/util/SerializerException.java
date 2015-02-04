package com.jason.framework.orm.redis.util;

import org.springframework.core.NestedRuntimeException;

public class SerializerException extends NestedRuntimeException {
	private static final long serialVersionUID = 1L;

	public SerializerException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public SerializerException(String msg) {
		super(msg);
	}
}
