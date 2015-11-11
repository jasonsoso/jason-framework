package com.jason.framework.generate.repository;

import java.util.List;

import com.jason.framework.generate.domain.Column;

/**
 * 
 * @author Jason
 * @date 2015-11-10
 */
public interface GenerateRepository {
	
	List<Column> queryTable();
	
	
}
