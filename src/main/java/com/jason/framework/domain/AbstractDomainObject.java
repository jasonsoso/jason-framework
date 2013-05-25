package com.jason.framework.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

/**
 * 统一定义 uuid 的 Domain 基类.
 * @author Jason
 * @date 2013-1-27 上午11:24:08
 */
@MappedSuperclass
public abstract class AbstractDomainObject implements DomainObject<AbstractDomainObject>, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean sameIdentityAs(AbstractDomainObject other) {

		if (null == other) {
			return false;
		}

		return new EqualsBuilder().append(this.getId(), other.getId()).isEquals();
	}

}
