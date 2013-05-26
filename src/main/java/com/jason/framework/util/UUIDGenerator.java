package com.jason.framework.util;

import java.util.UUID;

/**
 * uuid
 * @author Jason
 * @date 2013-5-25 下午10:29:20
 */
public final class UUIDGenerator {
	public UUIDGenerator(){}
	
	public static String getUUID() {
		String strUuid = UUID.randomUUID().toString();
		return strUuid.replaceAll("-", "");
	}

	public static String[] getUUID(int number) {
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}

}