
package com.jason.framework.generate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;

import com.google.common.collect.Maps;
import com.jason.framework.MyBatisTestBase;
import com.jason.framework.generate.domain.Column;
import com.jason.framework.generate.service.GenerateService;
import com.jason.framework.util.DateHelper;
import com.jason.framework.util.FileUtils;
import com.jason.framework.util.FreeMarkers;
import com.jason.framework.util.PropertiesUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 代码生成器
 * @version 2013-06-21
 */
public class GenerateTest extends MyBatisTestBase{
	private static Logger logger = LoggerFactory.getLogger(GenerateTest.class);
	
	
	@Autowired
	private GenerateService generateService;
	

	// ========== ↓↓↓↓↓↓ 执行前请修改参数，谨慎执行。↓↓↓↓↓↓ ====================

	// 主要提供基本功能模块代码生成。
	// 目录生成结构：{packageName}/{moduleName}/{repository,domain,service,web}/{subModuleName}/{className}
	
	// packageName 包名，这里如果更改包名，请在applicationContext.xml和srping-mvc.xml中配置base-package、packagesToScan属性，来指定多个（共4处需要修改）。
	private static String packageName = PropertiesUtils.getEntryValue("generate.packageName");// "com.jason.framework";
	
	private static String moduleName = PropertiesUtils.getEntryValue("generate.moduleName");			// 模块名，例：sys
	private static String subModuleName = PropertiesUtils.getEntryValue("generate.subModuleName");			// 子模块名（可选） 
	private static String className = PropertiesUtils.getEntryValue("generate.className");			// 类名，例：user
	private static String dbTable = PropertiesUtils.getEntryValue("generate.dbTable");			// 表名，例：user
	private static String classAuthor = PropertiesUtils.getEntryValue("generate.classAuthor");		// 类作者，例：Jason
	private static String functionName = PropertiesUtils.getEntryValue("generate.functionName");			// 功能名，例：用户

	
	// ========== ↑↑↑↑↑↑ 执行前请修改参数，谨慎执行。↑↑↑↑↑↑ ====================
	
	
	@Test
	public void generate() throws IOException {
		
		if (StringUtils.isBlank(packageName)|| StringUtils.isBlank(className) || StringUtils.isBlank(functionName)){
			logger.error("参数设置错误：包名、类名、功能名不能为空。");
			return;
		}
		
		// 获取文件分隔符
		String separator = File.separator;
		
		// 获取工程路径
		File projectPath = new DefaultResourceLoader().getResource("").getFile();
		while(!new File(projectPath.getPath()+separator+"src"+separator+"main").exists()){
			projectPath = projectPath.getParentFile();
		}
		logger.info("Project Path: {}", projectPath);
		
		// 模板文件路径
		String tplPath = StringUtils.replace(projectPath+"/src/test/java/com/jason/framework/generate/template", "/", separator);
		logger.info("Template Path: {}", tplPath);
		
		// Java文件路径
		String javaPath = StringUtils.replaceEach(projectPath+"/out/src/main/java/"+StringUtils.lowerCase(packageName), 
				new String[]{"/", "."}, new String[]{separator, separator});
		logger.info("Java Path: {}", javaPath);
		
		// Mybatis 映射文件路径
		String xmlPath = StringUtils.replace(projectPath+"/out/src/main/resources/META-INF/mybatis", "/", separator);
		logger.info("Mybatis Xml Path: {}", xmlPath);
		
		// 视图文件路径
		String viewPath = StringUtils.replace(projectPath+"/out/src/main/webapp/WEB-INF/views", "/", separator);
		logger.info("View Path: {}", viewPath);
		
		//moduleName路径
		String formatPathModuleName = StringUtils.isNotBlank(moduleName)?"/"+StringUtils.lowerCase(moduleName):"";
		String formatPackageModuleName = StringUtils.isNotBlank(moduleName)?"."+StringUtils.lowerCase(moduleName):"";
		
		String formatPackageSubModuleName = StringUtils.isNotBlank(subModuleName)?"."+StringUtils.lowerCase(subModuleName):"";
		String formatPathSubModuleName = StringUtils.isNotBlank(subModuleName)?"/"+StringUtils.lowerCase(subModuleName):"";
		String formatPathSubModuleName2 = StringUtils.isNotBlank(subModuleName)?StringUtils.lowerCase(subModuleName)+"/":"";
		
		// 代码模板配置
		Configuration cfg = new Configuration();
		cfg.setDefaultEncoding("UTF-8");
		cfg.setDirectoryForTemplateLoading(new File(tplPath));

		// 定义模板变量
		Map<String, Object> model = Maps.newHashMap();
		model.put("packageName", StringUtils.lowerCase(packageName));
		model.put("moduleName", formatPackageModuleName);
		model.put("subModuleName", formatPackageSubModuleName);
		model.put("formatPathSubModuleName", formatPathSubModuleName);
		model.put("formatPathSubModuleName2", formatPathSubModuleName2);
		model.put("className", StringUtils.uncapitalize(className));
		model.put("ClassName", StringUtils.capitalize(className));
		model.put("dbTable", StringUtils.lowerCase(dbTable));
		model.put("classAuthor", StringUtils.isNotBlank(classAuthor)?classAuthor:"Generate Tools");
		model.put("classVersion", DateHelper.getDate());
		model.put("functionName", functionName);
		//model.put("tableName", model.get("moduleName")+(StringUtils.isNotBlank(subModuleName)
		//		?"_"+StringUtils.lowerCase(subModuleName):"")+"_"+model.get("className"));
		
		model.put("urlPrefix", formatPathModuleName+(StringUtils.isNotBlank(subModuleName)
				?"/"+StringUtils.lowerCase(subModuleName):"")+"/"+model.get("className"));
		//model.put("viewPrefix", //StringUtils.substringAfterLast(model.get("packageName"),".")+"/"+
		//		model.get("urlPrefix"));
		//model.put("permissionPrefix", model.get("moduleName")+(StringUtils.isNotBlank(subModuleName)
		//		?":"+StringUtils.lowerCase(subModuleName):"")+":"+model.get("className"));

		//读取数据库 并组装参数
		List<Column> columns = generateService.queryTable();
		List<Column> newColumns = new ArrayList<Column>();
		Set<String> setColumns = new TreeSet<String>();
		for (Column column:columns) {
			newColumns.add(column.build());
			if(StringUtils.indexOf(column.getJavatype(), ".")!=-1){
				setColumns.add(column.getJavatype());
			}
		}
		model.put("setColumns", setColumns);
		model.put("columns", newColumns);
		
		// 生成 domain
		Template template = cfg.getTemplate("domain.ftl");
		String content = FreeMarkers.renderTemplate(template, model);
		String filePath = javaPath+formatPathModuleName+separator+"domain"
				+separator+StringUtils.lowerCase(subModuleName)+separator+model.get("ClassName")+".java";
		writeFile(content, filePath);
		logger.info("Domain: {}", filePath);
		
		// 生成 repository接口类
		template = cfg.getTemplate("repository.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = javaPath+formatPathModuleName+separator+"repository"+separator
				+StringUtils.lowerCase(subModuleName)+separator+model.get("ClassName")+"Repository.java";
		writeFile(content, filePath);
		logger.info("Repository: {}", filePath);
		
		// 生成 repository实现类
		template = cfg.getTemplate("repositoryImpl.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = javaPath+formatPathModuleName+separator+"repository"+separator
				+StringUtils.lowerCase(subModuleName)+separator+"impl"+separator+model.get("ClassName")+"RepositoryImpl.java";
		writeFile(content, filePath);
		logger.info("Repository: {}", filePath);
		
		// 生成 Service接口
		template = cfg.getTemplate("service.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = javaPath+formatPathModuleName+separator+"service"+separator
				+StringUtils.lowerCase(subModuleName)+separator+model.get("ClassName")+"Service.java";
		writeFile(content, filePath);
		logger.info("Service: {}", filePath);
		
		// 生成 Service实现类
		template = cfg.getTemplate("serviceImpl.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = javaPath+formatPathModuleName+separator+"service"+separator
				+StringUtils.lowerCase(subModuleName)+separator+"impl"+separator+model.get("ClassName")+"ServiceImpl.java";
		writeFile(content, filePath);
		logger.info("Service: {}", filePath);
		
		
		// 生成 Controller
		template = cfg.getTemplate("controller.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = javaPath+formatPathModuleName+separator+"web"+separator
				+StringUtils.lowerCase(subModuleName)+separator+model.get("ClassName")+"Controller.java";
		writeFile(content, filePath);
		logger.info("Controller: {}", filePath);
		
		// 生成 Mybatis Xml映射文件
		template = cfg.getTemplate("mapping.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = xmlPath+separator
				+separator+formatPathModuleName+
				separator+StringUtils.lowerCase(subModuleName)+
				separator+model.get("className")+"Mapper.xml";
		writeFile(content, filePath);
		logger.info("Mybatis Xml映射文件: {}", filePath);
		
		// 生成 ViewForm
		template = cfg.getTemplate("viewForm.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = viewPath+formatPathModuleName+separator+StringUtils.lowerCase(subModuleName)
				+separator+model.get("className")+separator+"form.jsp";
		writeFile(content, filePath);
		logger.info("ViewForm: {}", filePath);
		
		// 生成 ViewList
		template = cfg.getTemplate("viewList.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = viewPath+formatPathModuleName+separator+StringUtils.lowerCase(subModuleName)
		+separator+model.get("className")+separator+"list.jsp";
		writeFile(content, filePath);
		logger.info("ViewList: {}", filePath);
		
		logger.info("Generate Success.");
	}
	
	/**
	 * 将内容写入文件
	 * @param content
	 * @param filePath
	 */
	public static void writeFile(String content, String filePath) {
		try {
			if (FileUtils.createFile(filePath)){
				FileOutputStream fos = new FileOutputStream(filePath);
				Writer writer = new OutputStreamWriter(fos,"UTF-8");
				BufferedWriter bufferedWriter = new BufferedWriter(writer);
				bufferedWriter.write(content);
				bufferedWriter.close();
				writer.close();
			}else{
				logger.info("生成失败，文件已存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
