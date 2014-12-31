package com.jason.framework.orm.redis.util;

import org.springframework.core.NestedRuntimeException;

public class SerializationException extends NestedRuntimeException {
	private static final long serialVersionUID = 1L;

	public SerializationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public SerializationException(String msg) {
		super(msg);
	}
}
