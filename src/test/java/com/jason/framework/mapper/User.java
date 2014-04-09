package com.jason.framework.mapper;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.elasticsearch.common.collect.Lists;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "name", "interests" }) // 指定子节点的顺序
@XmlRootElement
public class User {
	
	@XmlAttribute // 设置转换为xml节点中的属性
	private Long id;
	
	@XmlJavaTypeAdapter(value=CDataAdapter.class)
	private String name;
	
	@XmlTransient	// 设置不转换为xml
	private String password;

	// 设置对List<String>的映射, xml为<interests><interest>movie</interest></interests>
	@XmlElementWrapper(name = "interests")
	@XmlElement(name = "interest")
	private List<String> interests = Lists.newArrayList();

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getInterests() {
		return interests;
	}

	public void setInterests(List<String> interests) {
		this.interests = interests;
	}

	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}