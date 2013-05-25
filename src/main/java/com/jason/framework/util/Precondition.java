package com.jason.framework.util;

public final class Precondition {
	public static void isTrue(boolean expression) {
		isTrue(expression, "");
	}

	public static void isTrue(boolean expression, String msg) {
		if (!expression) {
			throw new IllegalArgumentException(msg);
		}
	}
}
