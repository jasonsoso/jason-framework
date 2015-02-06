package com.jason.framework.domain.template;

import com.jason.framework.orm.Page;

public interface TemplateRepository {
	void save(Template template);
	
	void delete(Template template);

	Page<Template> queryPage(Page<Template> page);

	Template get(Long id);
	
	void update(Template template);
	
}
