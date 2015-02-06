package com.jason.framework.application;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jason.framework.MyBatisTestBase;
import com.jason.framework.domain.template.Template;
import com.jason.framework.orm.Page;

public class TemplateServiceTest extends MyBatisTestBase {

	private final static Logger logger = LoggerFactory.getLogger(TemplateServiceTest.class);
	
	@Autowired
	private TemplateService templateService;
	
	private Template entity ;
	
	@Before
	public void testSave(){
		entity = new Template();
		entity.setContent("内容");
		entity.setFileName("文件名称");
		entity.setName("名称");
		entity.setSort(1);
		entity.setUpdateTime(new Date());
		templateService.save(entity);
	}
	@Test
	public void testGet(){
		long id = entity.getId();
		Template template = templateService.get(id);
		logger.info(template.getName());
	}
	
	@Test
	public void testQueryPage(){
		Page<Template> page = new Page<Template>();
		page = templateService.queryPage(page);
		System.out.println("总数："+page.getTotalCount());
		List<Template> list = page.getResult();
		for (Template t:list) {
			System.out.println(t.getId()+" "+t.getName()+" "+t.getContent());
		}
	}
	@Test
	public void testUpdate(){
		entity.setName("名称update");
		templateService.update(entity);
		Template t = templateService.get(entity.getId());
		Assert.assertEquals("名称update", t.getName());
	}
	
	
}
