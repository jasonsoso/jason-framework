
package com.jason.framework.util;

/**
 * json model
 * @author Jason
 * @date 2013-2-11 上午09:11:22
 */
public class JsonModel {
    
    /** 返回消息的数据 */
	private boolean success;

    /** 返回消息的数据 */
	private Object root;
	
	/** 返回消息的数据 保留用 */
	private Object orderRoot;

	public JsonModel(){
		
	}
	
	public JsonModel(boolean success){
		this.success = success;
	}
	
	public JsonModel(boolean success,Object root){
		this.success = success;
		this.root = root;
	} 
	
	public boolean isSuccess() {
		return success;
	}

	public JsonModel setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public Object getRoot() {
		return root;
	}

	public JsonModel setRoot(Object root) {
		this.root = root;
		return this;
	}

	public Object getOrderRoot() {
		return orderRoot;
	}

	public JsonModel setOrderRoot(Object orderRoot) {
		this.orderRoot = orderRoot;
		return this;
	}

}
