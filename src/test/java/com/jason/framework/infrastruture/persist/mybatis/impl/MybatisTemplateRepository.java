package com.jason.framework.infrastruture.persist.mybatis.impl;

import org.springframework.stereotype.Repository;

import com.jason.framework.domain.template.Template;
import com.jason.framework.domain.template.TemplateRepository;
import com.jason.framework.orm.mybatis.MybatisRepositorySupport;


/**
 * 
 * @author Jason
 * @date 2014-7-3
 */
@Repository
public class MybatisTemplateRepository extends MybatisRepositorySupport<Long, Template> implements TemplateRepository {
	
	@Override
	protected String getNamespace() {
		return "com.jason.framework.domain.template";
	}
}
