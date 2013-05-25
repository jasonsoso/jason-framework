package com.jason.framework.domain;

public interface DomainObject<T> {
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	boolean sameIdentityAs(T other);
}
