package com.jason.framework.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.builder.EqualsBuilder;


/**
 * 统一定义 自增id的 Domain 基类.
 * @author Jason
 * @date 2013-1-27 上午11:23:54
 */
public abstract class IdDomainObject implements DomainObject<IdDomainObject>, Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}


	@Override
	public boolean sameIdentityAs(IdDomainObject other) {

		if (null == other) {
			return false;
		}
		return new EqualsBuilder().append(this.getId(), other.getId()).isEquals();
	}
}
