package com.jason.framework.orm.elasticsearch.domain;

import com.jason.framework.domain.ESIdDomain;
import com.jason.framework.mapper.JsonMapper;

/**
 * 资讯
 * @author Jason
 * @date 2014-6-30
 */
public class Info extends ESIdDomain {
	private static final long serialVersionUID = 1L;

	private long channel;		//频道
	private String title;		//标题
	private String author;		//作者
	private String source;		//来源（发布者）
	private String content;		//内容
	private long status;		//状态
	private String tag;			//标签
	private long country;		//国家
	private long createTime;	//创建时间
	private long publishTime;	//发布时间
	private long expirationDate;//失效时间
	private String picture;		//缩略图（图片）	
	
	public Info(){
	}
	@Override
	public String toString() {
		return JsonMapper.toJsonString(this);
	}
	public long getChannel() {
		return channel;
	}
	public void setChannel(long channel) {
		this.channel = channel;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public long getCountry() {
		return country;
	}
	public void setCountry(long country) {
		this.country = country;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(long publishTime) {
		this.publishTime = publishTime;
	}
	public long getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(long expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	
}
