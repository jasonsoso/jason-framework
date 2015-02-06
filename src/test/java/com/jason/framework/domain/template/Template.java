package com.jason.framework.domain.template;

import java.util.Date;

import com.jason.framework.domain.MyBatisDomainObject;
import com.jason.framework.util.FilesHelper;

public class Template extends MyBatisDomainObject{

	private static final long serialVersionUID = 1L;
	private String name;
	private String fileName;
	private String content;
	private int sort;
	private Date updateTime;
	
	
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public Template setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
		return this;
	}
	public  String  getMajorName(){
		return FilesHelper.getMajorName(getFileName());
	}
}
