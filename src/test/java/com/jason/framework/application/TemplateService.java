package com.jason.framework.application;

import com.jason.framework.domain.template.Template;
import com.jason.framework.orm.Page;

/**
 * 
 * @author Jason
 * @date 2014-7-3
 */
public interface TemplateService {
	/**
	 * 添加模板
	 * @param site
	 */
	void save(Template template);
	/**
	 * 删除
	 * @param id
	 */
	void delete(Template template);
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	Page<Template> queryPage(Page<Template> page);
	/**
	 * 根据id查询Site
	 * @param id
	 * @return
	 */
	Template get(Long id);

	/**
	 * 更新
	 * @param site
	 */
	void update(Template template);
	
}
