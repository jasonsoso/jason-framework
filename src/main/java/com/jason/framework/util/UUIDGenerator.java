package com.jason.framework.util;

import java.util.UUID;

public final class UUIDGenerator {
	
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