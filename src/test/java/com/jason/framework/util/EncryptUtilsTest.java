package com.jason.framework.util;

import org.junit.Test;

public class EncryptUtilsTest {
	@Test
	public void testEncrypt() throws Exception {
		
		String encode = EncryptUtils.sha("xdxx62");
		System.out.println(encode);
		
		String encode2 = EncryptUtils.md5("xdxx62");
		System.out.println(encode2);
	}
}
