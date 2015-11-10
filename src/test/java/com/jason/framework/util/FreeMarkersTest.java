/**
 * 
 */
package com.jason.framework.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * @author Jason
 * @date 2015-11-10
 */
public class FreeMarkersTest {
	
	
	@Test
	public void test() throws Exception {
//		// renderString
		Map<String, String> model = new HashMap<String, String>();
		model.put("userName", "calvin");
		String result = FreeMarkers.renderString("hello ${userName}", model);
		System.out.println(result);
//		// renderTemplate
		
		// 获取文件分隔符
		String separator = File.separator;
		// 获取工程路径
		File projectPath = new DefaultResourceLoader().getResource("").getFile();
		while(!new File(projectPath.getPath()+separator+"src"+separator+"main").exists()){
			projectPath = projectPath.getParentFile();
		}
		// 模板文件路径
		String tplPath = StringUtils.replace(projectPath+"/src/test/resources/META-INF/template", "/", separator);
		// 代码模板配置
		Configuration cfg = new Configuration();
		cfg.setDefaultEncoding("UTF-8");
		cfg.setDirectoryForTemplateLoading(new File(tplPath));
		
		Template template = cfg.getTemplate("test.ftl");
		String result2 = FreeMarkers.renderTemplate(template, model);
		System.out.println(result2);
		
		Map<String, String> model3 = new HashMap<String, String>();
		model3.put("userName", "calvin");
		String result3 = FreeMarkers.renderString("hello ${userName} ${r'${userName}'}", model3);
		System.out.println(result3);
	}
}
