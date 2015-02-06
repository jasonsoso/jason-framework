package com.jason.framework.application.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jason.framework.application.TemplateService;
import com.jason.framework.domain.template.Template;
import com.jason.framework.domain.template.TemplateRepository;
import com.jason.framework.orm.Page;

/**
 * 
 * @author Jason
 * @date 2014-7-3
 */
@Transactional
@Service
public class TemplateServiceImpl implements TemplateService {
	
	@Autowired
	private TemplateRepository templateRepository;
	
	@Override
	public Page<Template> queryPage(Page<Template> page) {
		return templateRepository.queryPage(page);
	}

	@Override
	public Template get(Long id) {
		return templateRepository.get(id);
	}

	@Override
	public void update(Template template) {
		template.setUpdateTime(new Date());
		templateRepository.update(template);
	}

	@Override
	public void save(Template template) {
		template.setUpdateTime(new Date());
		templateRepository.save(template);
	}

	@Override
	public void delete(Template template) {
		templateRepository.delete(template);
		
	}

}
