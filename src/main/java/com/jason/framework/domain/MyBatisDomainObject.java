package com.jason.framework.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;

public class MyBatisDomainObject implements DomainObject<MyBatisDomainObject>, Serializable {

	private static final long serialVersionUID = 1L;
	
	protected long id;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}


	@Override
	public boolean sameIdentityAs(MyBatisDomainObject other) {
		if (null == other) {
			return false;
		}
		return new EqualsBuilder().append(this.getId(), other.getId()).isEquals();
	}
}
