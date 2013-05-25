package com.jason.framework.application;

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
	
	@Test
	public void testGet(){
		Authority authority = authorityService.get("40289f813e60ae93013e60bcd7880003");
		logger.info(authority.getName());
	}
	/**
	 * 
	 * SQLQueryFactory sqlQueryFactory 根据sql进行查询
	 * @author JasonTan
	 * @since 2013-5-25
	 */
	@Test
	public void testGetfromSQL(){
		Authority object = authorityService.getfromSQL("40289f813e60ae93013e60bcd7880003");
		logger.info(object.toString());
	}
	
	
}
