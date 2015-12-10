package com.jason.framework.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;

import com.jason.framework.util.ExceptionUtils;


public class HttpDownload {
	
	private HttpDownload (){
	}
	
	public static final int CACHE = 10 * 1024;
	public static final boolean ISWINDOWS;
	public static final String SPLASH;
	public static final String ROOT;
	static {
		if (System.getProperty("os.name") != null
				&& System.getProperty("os.name").toLowerCase()
						.contains("windows")) {
			ISWINDOWS = true;
			SPLASH = "\\";
			ROOT = "D:";
		} else {
			ISWINDOWS = false;
			SPLASH = "/";
			ROOT = "/search";
		}
	}

	/**
	 * 根据url下载文件，文件名从response header头中获取
	 * 
	 * @param url
	 * @return
	 */
	public static String download(String url) {
		return download(url, null);
	}

	/**
	 * 根据url下载文件，保存到filepath中
	 * 
	 * @param url
	 * @param filepath
	 * @return
	 */
	public static String download(String url, String filepath) {
		try {  
            HttpGet httpget = new HttpGet(url);  
            HttpResponse response  = HttpRequester.get(httpget, new ArrayList<NameValuePair>(), HttpConstants.DEFAULT_CHARSET);
  
            HttpEntity entity = response.getEntity();  
            InputStream is = entity.getContent();  
            if (filepath == null){
            	filepath = getFilePath(response); 
            }
            File file = new File(filepath);  
            file.getParentFile().mkdirs();  
            FileOutputStream fileout = new FileOutputStream(file);  
            /** 
             * 根据实际运行效果 设置缓冲区大小 
             */  
            byte[] buffer=new byte[CACHE];  
            int ch = 0;  
            while ((ch = is.read(buffer)) != -1) {  
                fileout.write(buffer,0,ch);  
            }  
            is.close();  
            fileout.flush();  
            fileout.close();  
  
        } catch (Exception e) {
        	throw ExceptionUtils.toUnchecked(e, "download error!");
        }  
        return null;  
	}

	/**
	 * 获取response要下载的文件的默认路径
	 * 
	 * @param response
	 * @return
	 */
	public static String getFilePath(HttpResponse response) {
		String filepath = ROOT + SPLASH;
		String filename = getFileName(response);

		if (filename != null) {
			filepath += filename;
		} else {
			filepath += getRandomFileName();
		}
		return filepath;
	}

	/**
	 * 获取response header中Content-Disposition中的filename值
	 * 
	 * @param response
	 * @return
	 */
	public static String getFileName(HttpResponse response) {
		Header contentHeader = response.getFirstHeader("Content-Disposition");
		String filename = null;
		if (contentHeader != null) {
			HeaderElement[] values = contentHeader.getElements();
			if (values.length == 1) {
				NameValuePair param = values[0].getParameterByName("filename");
				if (param != null) {
					// filename = new
					// String(param.getValue().toString().getBytes(),
					// "utf-8");
					// filename=URLDecoder.decode(param.getValue(),"utf-8");
					filename = param.getValue();
				}
			}
		}
		return filename;
	}

	/**
	 * 获取随机文件名
	 * 
	 * @return
	 */
	public static String getRandomFileName() {
		return String.valueOf(System.currentTimeMillis());
	}
}
