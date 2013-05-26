package com.jason.framework.util;



import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * MD5加密
 * @author Jason
 */
public final class MD5Utils {
	
	public MD5Utils(){
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MD5Utils.class);
	
	private static final String[] HEX_DIGITS  = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
		'9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
		'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v'};
	/***
	 * 
	 * @param b
	 * @return
	 */
	private static String byteArrayToHexString(byte[] b) { 
		StringBuffer resultSb = new StringBuffer(); 
		for (int i = 0; i < b.length; i++) { 
			resultSb.append(byteToHexString(b[i])); 
		} 
		return resultSb.toString(); 
	} 

	/***
	 * 
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) { 
		int n = b; 
		if (n < 0){
			n = 256 + n; 
		}
		int d1 = n / 16; 
		int d2 = n % 16; 
		return HEX_DIGITS[d1] + HEX_DIGITS[d2]; 
	} 

	/***
	 * 
	 * @param origin
	 * @return
	 */
	public static String asMD5Encode(String origin) { 
		String resultString = null; 
		try {
			resultString=origin; 
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			resultString=byteArrayToHexString(md.digest(resultString.getBytes())); 
		}catch (Exception ex) {
			LOGGER.error("asMD5Encode error!", ex);
		} 
		return resultString; 
	}
	public static String asMD5EncodeUnsigned(String origin){
		return toUnsignedString(asMD5Encode(origin));
	}
	private static String toUnsignedString(String base) {
		String dest="";
		for(int i=0;i<base.length();i=i+2){
			int ten=Integer.parseInt(base.substring(i,i+2),16);
			int index=ten%32;
			dest=dest+DIGITS[index];
		}
		return dest;
	}
}

