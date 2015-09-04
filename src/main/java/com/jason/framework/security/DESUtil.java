package com.jason.framework.security;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.jason.framework.util.EncodeUtils;

/**
 * DES加密与解密
 * @author Jason
 * @date 2015-9-4 上午11:45:21
 */
public class DESUtil {
	
	/** key 密钥 */
	private static final byte[] DES_KEY = "12345678".getBytes();
	/** iv 初始向量 */
	private static final byte[] IV = "12345678".getBytes();
	/** 编码  */
	private static final String CHARSET_NAME = "GBK";
	
	
	/**
	 * 使用cbc,PKCS5Padding填充模式进行加密,密钥大于等于8位,只取8位,初始向量使用密钥倒置后8个byte,此方法能与php兼容
	 * @param data
	 * @return
	 */
	public static String encrypt(String data) {
		return encryptDes(data,"DES/CBC/PKCS5Padding");
	}
	
	/**
	 * 使用cbc,PKCS5Padding填充模式进行解密,初始向量使用密钥倒置后8个byte,此方法能与php兼容
	 * 
	 * @param cryptData   加密数据
	 * @return 解密后的数据
	 */
	public static String decode(String cryptData) {
		return decodeDES(cryptData,"DES/CBC/PKCS5Padding");
	}
	
	
	
	/**
	 * 数据加密，算法（DES）,通过transformer 指定具体的算法目前有
	 * DES/CBC/NoPadding (56)
	 * DES/CBC/PKCS5Padding (56)
	 * DES/ECB/NoPadding (56)
	 * DES/ECB/PKCS5Padding (56)
	 * CBC模式需要传向量
	 * @param data    数据
	 * @param transformer 具体的算法
	 * @return 加密后的数据
	 */
	public static String encryptDes(String data,String transformer) {
		String encryptedData = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(DES_KEY);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 加密对象
			Cipher cipher = Cipher.getInstance(transformer);
			if(null==(IV)){
				cipher.init(Cipher.ENCRYPT_MODE, key, sr);
			}
			else{
				IvParameterSpec iv2 = new IvParameterSpec(IV);
				cipher.init(Cipher.ENCRYPT_MODE, key, iv2);
			}
			// 加密，并把字节数组编码成字符串
			encryptedData = EncodeUtils.encodeBase64(cipher.doFinal(data.getBytes(CHARSET_NAME)));
		} catch (Exception e) {
			// log.error("加密错误，错误信息：", e);
			throw new RuntimeException("加密错误，错误信息："+e.getMessage(), e);
		}
		return encryptedData;
	}
	/**
	 * 数据解密，算法（DES）,通过transformer 指定具体的算法目前有
	 * DES/CBC/NoPadding (56)
	 * DES/CBC/PKCS5Padding (56)
	 * DES/ECB/NoPadding (56)
	 * DES/ECB/PKCS5Padding (56)
	 * CBC模式需要传向量
	 * @param cryptData    加密数据
	 * @param transformer 具体的算法
	 * @return 解密后的数据
	 */
	public static String decodeDES(String cryptData,String transformer)  {
		String decryptedData = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(DES_KEY);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 解密对象
			Cipher cipher = Cipher.getInstance(transformer);
			if(null == (IV)){
				cipher.init(Cipher.DECRYPT_MODE, key, sr);
			}
			else{
				IvParameterSpec iv2 = new IvParameterSpec(IV);
				cipher.init(Cipher.DECRYPT_MODE, key, iv2);
			}
			// 把字符串解码为字节数组，并解密
			decryptedData = new String(cipher.doFinal(EncodeUtils.decodeBase64(cryptData)),CHARSET_NAME);
		} catch (Exception e) {
			// log.error("解密错误，错误信息：", e);
			throw new RuntimeException("解密错误，错误信息："+e.getMessage(), e);
		}
		return decryptedData;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String encryptDESwithCBC = DESUtil.encrypt("ABC");
		System.out.println(encryptDESwithCBC);
		System.out.println(DESUtil.decode(encryptDESwithCBC));
	}
	
}
