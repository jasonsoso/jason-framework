package com.jason.framework.util;

import java.security.MessageDigest;

import org.springframework.util.Assert;

/**
 * 加密
 * @author Jason
 * @date 2014-3-30 下午08:37:55
 */
public class EncryptUtils {


    // md5加密
    public static String md5(String inputText) {
        return encrypt(inputText, "md5");
    }

    // sha加密
    public static String sha(String inputText) {
        return encrypt(inputText, "sha-1");
    }

    /**
     * md5或者sha-1加密
     * @param inputText 要加密的内容
     * @param algorithmName 加密算法名称：md5或者sha-1，不区分大小写
     * @return
     */
    private static String encrypt(String inputText, String algorithmName) {
        Assert.hasText(inputText, "请输入要加密的内容");
        try {
            MessageDigest m = MessageDigest.getInstance(algorithmName);
            m.update(inputText.getBytes("UTF8"));
            byte s[] = m.digest();
            return hex(s);
        } catch (Exception e) {
        	throw ExceptionUtils.toUnchecked(e, "加密失败！");
        }
    }

    // 返回十六进制字符串
    private static String hex(byte[] arr) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; ++i) {
            sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1,3));
        }
        return sb.toString();
    }
}
