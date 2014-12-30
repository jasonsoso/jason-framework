package com.jason.framework.util;

import org.junit.Assert;
import org.junit.Test;

public class CommandLineHelperTest {
	
	@Test
	public void testOS() throws Exception {
		String os = CommandLineHelper.getOS();
		
		Assert.assertNotNull(os);
	}
	@Test
	public void testExec() throws Exception {
		boolean result = CommandLineHelper.exec("dir");
		Assert.assertTrue(result);
	}
	
}
