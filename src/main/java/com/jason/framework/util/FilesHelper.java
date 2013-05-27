package com.jason.framework.util;

public final class FilesHelper {
	private FilesHelper(){}
	
	/**
	 * 后缀名称前插入字符串 拼装字符
	 * eg:123.jpg -> 123_${insertString}.jpg
	 * @param fileName
	 * @param insertString
	 * @return
	 */
	public static String insertString(String fileName, String insertString) {
		String fileNameWithoutExtension = getFileNameWithoutExtension(fileName);
		String extension = getFileExtensionWithDot(fileName);
		return String.format("%s%s%s%s", fileNameWithoutExtension,"_",insertString, extension);
	}
	
	/**
	 * eg: resources/upload/1.jpg -> resources/upload/1_{insertString}.jpg
	 * @param path
	 * @param insertString
	 * @return
	 */
	public static String insertStringForPath(String path, String insertString){
		String fileName = getFileNameFormUrl(path);
		String filePath = getFilePathFormUrl(path);
		String truefileName = insertString(fileName, insertString);
		return String.format("%s%s", filePath,truefileName);
	}

	/**
	 * abc_180_180.jpg
	 * @param fileName  abc
	 * @param insertString 180_180
	 * @param extension .jpg
	 * @return
	 */
	public static String insertFileNameAndString(String fileName, String insertString,String extension){
		return String.format("%s_%s%s", fileName, insertString, extension);
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileNameWithoutExtension(String fileName) {
		if (fileName.indexOf('.') != -1) {
			return fileName.substring(0, fileName.lastIndexOf('.'));
		}
		return fileName;
	}
	
	/**
	 * .后缀名
	 * @param fileName
	 * @return
	 */
	public static String getFileExtensionWithDot(String fileName) {
		if (fileName.indexOf('.') != -1) {
			return fileName.substring(fileName.lastIndexOf('.'));
		}
		return fileName;
	}
	
	/**
	 * eg:xxx/1.jpg -> 1.jpg
	 * @param path
	 * @return
	 */
	public static String getFileNameFormUrl(String path){
		return path.substring(path.lastIndexOf('/')+1);
	}
	
	/**
	 * eg:xxx/1.jpg -> xxx/
	 * @param path
	 * @return
	 */
	public static String getFilePathFormUrl(String path){
		return path.substring(0,path.lastIndexOf('/')+1);
	}
}
