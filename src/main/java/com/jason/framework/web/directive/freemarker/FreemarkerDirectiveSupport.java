package com.jason.framework.web.directive.freemarker;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;

public abstract class FreemarkerDirectiveSupport implements TemplateDirectiveModel {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected BeansWrapper beansWrapper = new BeansWrapper();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public final void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException,
			IOException {
		if (beforeExecute(env, params, loopVars)) {
			doExecute(env, params, loopVars, body);
		}
	}

	protected boolean beforeExecute(Environment env, Map<String, ?> params, TemplateModel[] loopVars) {
		return true;
	}

	protected abstract void doExecute(Environment env, Map<String, ?> params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException;
	
	
	
	
	//----------------------------- 华丽的分割线 ---------------------------------
	
	
	
	protected boolean isNotBlankScalarModel(TemplateScalarModel scalarModel) throws TemplateModelException {
		return null != scalarModel && StringUtils.isNotBlank(scalarModel.getAsString());
	}
	
	protected boolean isNotBlankNumberModel(TemplateNumberModel numberModel) throws TemplateModelException {
		return null != numberModel;
	}

	/**
	 * 获取String参数
	 * @param Map<String, ?> params 
	 * @param String key 参数键
	 * @param String defaultValue 默认值
	 * @return String value 参数值
	 * @throws TemplateModelException
	 */
	protected String getStringValueByParams(Map<String, ?> params,String key,String defaultValue) throws TemplateModelException {
		TemplateScalarModel scalarModel = (TemplateScalarModel)params.get(key);
		if(isNotBlankScalarModel(scalarModel)){
			return scalarModel.getAsString();
		}else{
			return defaultValue;
		}
	}
	
	/**
	 *  获取int参数
	 * @param Map<String, ?> params 
	 * @param String key 参数键
	 * @param String defaultValue 默认值
	 * @return int value 参数值
	 * @throws TemplateModelException
	 */
	protected int getIntValueByParams(Map<String, ?> params,String key,int defaultValue) throws TemplateModelException {
		TemplateNumberModel numberModel = (TemplateNumberModel)params.get(key);
		if(isNotBlankNumberModel(numberModel)){
			return numberModel.getAsNumber().intValue();
		}else{
			return defaultValue;
		}
	}
	
}
