package com.jason.framework.util;

/**
 * 自定义断言
 * @author Jason
 * @date 2013-5-25 下午10:25:54
 */
public final class Precondition {
	
	public Precondition(){}
	
	public static void isTrue(boolean expression) {
		isTrue(expression, "");
	}

	public static void isTrue(boolean expression, String msg) {
		if (!expression) {
			throw new IllegalArgumentException(msg);
		}
	}
}
