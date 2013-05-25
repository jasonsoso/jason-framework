package com.jason.framework.web.exception;

@SuppressWarnings("serial")
public class MaliciousRequestException extends RuntimeException {

	public MaliciousRequestException(){
		super();
	}
	
	public MaliciousRequestException(String message) {
		super(message);
	}
}
