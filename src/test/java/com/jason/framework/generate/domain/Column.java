package com.jason.framework.generate.domain;

import org.apache.commons.lang.StringUtils;

import com.jason.framework.generate.util.Utils;


/**
 * 表中列
 * @author Jason
 * @date 2015-11-10
 */
public class Column {
	private String name;	//列名
	private String dbtype;	//类型
	private Integer len;	//长度
	private Integer scale;
	private String pkey;	//主键
	private String extra;	//自增
	private String comment;	//注解
	
	private String javatype;	//java类型，比如:java.util.Date
	private String jt;			//java类型最后的单词,比如：Date
	private String property;	//列名驼峰式
	private String bigProperty;	//首字母大写
	
	public Column(){
		
	}
	public Column build(){
		this.property = Utils.toProperty(name);
		this.javatype = Utils.getJavaType(dbtype);
		this.jt = Utils.procJavatype(javatype);
		this.bigProperty = StringUtils.capitalize(property);
		return this;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the dbtype
	 */
	public String getDbtype() {
		return dbtype;
	}
	/**
	 * @param dbtype the dbtype to set
	 */
	public void setDbtype(String dbtype) {
		this.dbtype = dbtype;
	}
	/**
	 * @return the len
	 */
	public Integer getLen() {
		return len;
	}
	/**
	 * @param len the len to set
	 */
	public void setLen(Integer len) {
		this.len = len;
	}
	/**
	 * @return the scale
	 */
	public Integer getScale() {
		return scale;
	}
	/**
	 * @param scale the scale to set
	 */
	public void setScale(Integer scale) {
		this.scale = scale;
	}
	/**
	 * @return the pkey
	 */
	public String getPkey() {
		return pkey;
	}
	/**
	 * @param pkey the pkey to set
	 */
	public void setPkey(String pkey) {
		this.pkey = pkey;
	}
	/**
	 * @return the extra
	 */
	public String getExtra() {
		return extra;
	}
	/**
	 * @param extra the extra to set
	 */
	public void setExtra(String extra) {
		this.extra = extra;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the javatype
	 */
	public String getJavatype() {
		return javatype;
	}
	/**
	 * @param javatype the javatype to set
	 */
	public void setJavatype(String javatype) {
		this.javatype = javatype;
	}
	/**
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}
	/**
	 * @param property the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}
	/**
	 * @return the jt
	 */
	public String getJt() {
		return jt;
	}
	/**
	 * @param jt the jt to set
	 */
	public void setJt(String jt) {
		this.jt = jt;
	}
	/**
	 * @return the bigProperty
	 */
	public String getBigProperty() {
		return bigProperty;
	}
	/**
	 * @param bigProperty the bigProperty to set
	 */
	public void setBigProperty(String bigProperty) {
		this.bigProperty = bigProperty;
	}
	
	
	
}
