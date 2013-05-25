package com.jason.framework.util;

import org.junit.Test;

public class PropertiesUtilsTest {
	@Test
	public void testcreateThumbnail() throws Exception {
		String photoPath = PropertiesUtils.getEntryValue("photoServer.domain");
		System.out.println(" photoPath:"+photoPath);
	}
}
