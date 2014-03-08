package com.jason.framework.application;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jason.framework.AbstractTestBase;
import com.jason.framework.domain.authority.Authority;

public class AuthorityServiceTest extends AbstractTestBase {

	private final static Logger logger = LoggerFactory.getLogger(AuthorityServiceTest.class);
	@Autowired
	private AuthorityService authorityService;
	
	private Authority entity ;
	
	@Before
	public void testSave(){
		entity = new Authority();
		entity.setName("NAME");
		entity.setPermission("P");
		authorityService.store(entity);
	}
	@Test
	public void testGet(){
		String id = entity.getId();
		Authority authority = authorityService.get(id);
		logger.info(authority.getName());
	}
	
	
	
}
