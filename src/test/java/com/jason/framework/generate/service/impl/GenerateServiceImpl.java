package com.jason.framework.generate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jason.framework.generate.domain.Column;
import com.jason.framework.generate.repository.GenerateRepository;
import com.jason.framework.generate.service.GenerateService;

@Transactional
@Service
public class GenerateServiceImpl implements GenerateService {
	
	@Autowired
	private GenerateRepository generateRepository;

	/* (non-Javadoc)
	 * @see com.jason.framework.generate.service.GenerateService#queryTable()
	 */
	@Override
	public List<Column> queryTable() {
		return generateRepository.queryTable();
	}
	

}
