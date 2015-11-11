package com.jason.framework.generate.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jason.framework.generate.domain.Column;
import com.jason.framework.generate.repository.GenerateRepository;
import com.jason.framework.orm.mybatis.MybatisQueryRepositorySupport;
import com.jason.framework.util.PropertiesUtils;


/**
 * 
 * @author Jason
 * @date 2015-11-10
 */
@Repository
public class MybatisGenerateRepository extends MybatisQueryRepositorySupport implements GenerateRepository {

	/* (non-Javadoc)
	 * @see com.jason.framework.generate.repository.GenerateRepository#queryTable()
	 */
	@Override
	public List<Column> queryTable() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("dbSchema", PropertiesUtils.getEntryValue("generate.dbSchema"));
		map.put("dbTable", PropertiesUtils.getEntryValue("generate.dbTable"));
		
		return super.query("com.jason.framework.domain.generate.queryTable", map);
	}
	
}
