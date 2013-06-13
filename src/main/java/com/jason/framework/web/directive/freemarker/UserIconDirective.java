package com.jason.framework.web.directive.freemarker;

import java.io.IOException;
import java.util.Map;

import com.jason.framework.constants.PhotoConf.ThumbType;
import com.jason.framework.util.FilesHelper;
import com.jason.framework.util.PropertiesUtils;



import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 用户头像标签
 * 必填值path
 * eg:<img src="<@jason_user_icon path="${user.photo}" type="130_130"/>">
 * @author Jason
 * @date 2013-2-13 下午01:03:06
 */
public class UserIconDirective extends FreemarkerDirectiveSupport{
	
	public final static String TYPE_PARAM = "type";
	public final static String PATH_PARAM = "path";
	
	@Override
	protected void doExecute(Environment env, Map<String, ?> params,
			TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		//接受参数
		String type = super.getStringValueByParams(params, TYPE_PARAM, ThumbType.THUMB_SOURCE.asType());//默认源文件
		String path = super.getStringValueByParams(params, PATH_PARAM, "resources/images/default.jpg");//默认源文件

		//拼装文件
		String trueFileName = FilesHelper.insertStringForPath(path, type);
		String photoServerDomain = PropertiesUtils.getEntryValue("photoServer.domain");
		
		StringBuilder sb = new StringBuilder();
		sb.append(photoServerDomain).append(trueFileName);
		
		env.getOut().write(sb.toString());
		/*if(null!=body){
			body.render(env.getOut());
		}*/
	}
	
	@Override
	protected boolean beforeExecute(Environment env, Map<String, ?> params,
			TemplateModel[] loopVars) {
		if(params.containsKey(PATH_PARAM)){
			return true;
		}else{
			super.logger.debug(String.format("Must have this %s", PATH_PARAM));
		}
		return false;
	}




}
