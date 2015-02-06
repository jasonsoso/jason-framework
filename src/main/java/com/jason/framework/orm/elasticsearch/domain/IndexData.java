package com.jason.framework.orm.elasticsearch.domain;

/**
 * 索引数据
 * @author Jason
 * @date 2014-11-20
 */
public class IndexData {
	
	private String index;
	private String mappings;
	
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getMappings() {
		return mappings;
	}
	public void setMappings(String mappings) {
		this.mappings = mappings;
	}

}
