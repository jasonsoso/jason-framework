package com.jason.framework.util;

import org.junit.Test;

public class MD5UtilsTest {
	@Test
	public void testEncode() throws Exception {
		String encode = MD5Utils.encode("xdxx62");
		System.out.println(encode);
	}
}
