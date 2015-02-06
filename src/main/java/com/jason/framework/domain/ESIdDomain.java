package com.jason.framework.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 针对 搜索引擎
 * 统一定义  Domain 基类.
 * @author Jason
 * @date 2014-7-23
 */
public abstract class ESIdDomain implements DomainObject<ESIdDomain>, Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 索引Id,为索引主键
	 */
	protected String indexId;
	
	protected long id;
	protected int ssid;


	public String getIndexId() {
		return indexId;
	}
	/**
	 * 设置索引Id,暂为ssid+id组合而成，比如id：1，ssid:1,则indexId为1_1;
	 * @param indexId
	 */
	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getSsid() {
		return ssid;
	}

	public void setSsid(int ssid) {
		this.ssid = ssid;
	}

	@Override
	public boolean sameIdentityAs(ESIdDomain other) {
		if (null == other) {
			return false;
		}
		return new EqualsBuilder().append(this.getIndexId(), other.getIndexId()).isEquals();
	}
}
