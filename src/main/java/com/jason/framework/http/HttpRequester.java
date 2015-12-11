package com.jason.framework.http;


import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jason.framework.util.ExceptionUtils;


/**
 * HTTP 请求客户端
 * @author Jason
 */
public class HttpRequester {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequester.class);
	
	private HttpRequester(){
	}
    //private static final int  TIME_OUT = 1000 * 30;
    
    /**
     * httpClient 4.2 客户端
     */
    private static HttpClient httpClient; 
    private static HttpClient httpsClient;
   
    public static HttpClient getClient(HttpRequestBase httpRequestBase){
	   String scheme = httpRequestBase.getURI().getScheme();
	   if(StringUtils.equalsIgnoreCase(scheme, "https")){
		   return getHttpsClient();
	   }else{
		   return getHttpClient();
	   }
    }
    public static synchronized HttpClient getHttpClient(){
    	 if (httpClient == null)
         {
             return httpClientInstance();
         }
         return httpClient;
    }
    
    public static synchronized HttpClient getHttpsClient(){
   	 if (httpsClient == null)
        {
            return httpsClientInstance();
        }
        return httpsClient;
    }
    private static HttpClient httpClientInstance(){
        ClientConnectionManager connManager = new PoolingClientConnectionManager();
        HttpClient httpClient = new DefaultHttpClient(connManager);
        
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,5 * 1000);//设置连接超时
    	httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,20 * 1000);//设置读取超时
    	httpClient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
    	
        return httpClient;
    }
    @SuppressWarnings("deprecation")
	private static HttpClient httpsClientInstance(){
        KeyStore trustStore;
        SSLSocketFactory sf = null;
        try
        {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (KeyManagementException e)
        {
        	LOGGER.error(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e)
        {
        	LOGGER.error(e.getMessage(), e);
        } catch (UnrecoverableKeyException e)
        {
        	LOGGER.error(e.getMessage(), e);
        } catch (IOException e)
        {
        	LOGGER.error(e.getMessage(), e);
        } catch (CertificateException e)
        {
        	LOGGER.error(e.getMessage(), e);
        } catch (KeyStoreException e)
        {
        	LOGGER.error(e.getMessage(), e);
        }
        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));
        schReg.register(new Scheme("https", sf, 443));

        ClientConnectionManager connManager = new PoolingClientConnectionManager(schReg);
        HttpClient httpClient = new DefaultHttpClient(connManager);
        
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,5 * 1000);//设置连接超时
    	httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,20 * 1000);//设置读取超时
    	httpClient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
    	
        return httpClient;
    }
    /**
     * GET请求
     * @param url  路径
     * @return HttpRespons
     */
    public static  HttpRespons sendGet(String url){
        return sendGet(url, new HashMap<String, String>());
     }
    /**
     * GET请求
     * @param url 路径
     * @param params 参数
     * @return HttpRespons
     */
    public static  HttpRespons sendGet(String url, Map<String, String> params){
       return sendGet(url, params, HttpConstants.DEFAULT_CHARSET);
    }
    
    /**
     * GET请求
     * @param url 路径
     * @param params 参数
     * @param charset 编码
     * @return HttpRespons
     */
    public static  HttpRespons sendGet(String url, Map<String, String> params,String charset){
        return send(url, "GET", params,charset);
    }
    /**
     * POST请求
     * @param url 路径
     * @param params 参数
     * @return HttpRespons
     */
    public static  HttpRespons sendPost(String url, Map<String, String> params) {
        return sendPost(url, params, HttpConstants.DEFAULT_CHARSET);
    }
    /**
     * POST请求
     * @param url 路径
     * @param params 参数
     * @param charset 编码
     * @return HttpRespons
     */
    public static  HttpRespons sendPost(String url, Map<String, String> params,String charset) {
        return send(url, "POST", params,charset);
    }
    
	/**
	 * 具体发请求
	 * @param urlString 路径
	 * @param method 方法
	 * @param parameters 参数
	 * @param charset 编码
	 * @return HttpRespons
	 */
	public static HttpRespons send(String urlString, String method,
			Map<String, String> parameters, String charset) {

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (parameters != null) {
			for (String key : parameters.keySet()) {
				params.add(new BasicNameValuePair(key, parameters.get(key)));
			}
		}
		HttpRespons httpRespons = null;	//jason http Respons
		long elapsed = 0;
		if ("GET".equals(method)) {
			// 发送请求
			HttpGet httpGet = null;
			try {
				httpGet = new HttpGet(urlString);
				long startTime = System.currentTimeMillis();
				HttpResponse httpresponse = get(httpGet, params, charset);
				elapsed = System.currentTimeMillis() - startTime;

				httpRespons = new HttpRespons();
				httpRespons.setUrl(urlString);
				httpRespons.setMethod(method);
				httpRespons.setParams(parameters);
				httpRespons.setElapsedTime(elapsed);
				httpRespons.parseHttpresponse(httpresponse);//組裝httpRespons信息 提供返回
				LOGGER.debug(String.format("---发送get请求,花费%s毫秒,发送get请求：%s,请求结果%s",elapsed, urlString,httpRespons.toString()));
				return httpRespons;
			} finally{
				if(httpGet!=null){
					httpGet.releaseConnection();
				}
			}
				
		} else {
			HttpPost httpPost = null ; 
			try {
				// 发送请求
				httpPost = new HttpPost(urlString);
				long startTime = System.currentTimeMillis();
				HttpResponse httpresponse = post(httpPost, params, charset);
				elapsed = System.currentTimeMillis() - startTime;

				httpRespons = new HttpRespons();
				httpRespons.setUrl(urlString);
				httpRespons.setMethod(method);
				httpRespons.setParams(parameters);
				httpRespons.setElapsedTime(elapsed);
				httpRespons.parseHttpresponse(httpresponse);//組裝httpRespons信息 提供返回
				LOGGER.debug(String.format("---发送post请求,花费%s毫秒,发送post请求：%s,请求结果%s",elapsed, urlString,httpRespons.toString()));
				return httpRespons;
			} finally{
				if(httpPost!=null){
					httpPost.releaseConnection();
				}
			}
		}
		
	}
	
	/**
	 * Get请求
	 * @param urlString url路径
	 * @param params 参数
	 * @param charset 编码
	 * @return  org.apache.http.HttpResponse
	 */
	public static HttpResponse get(HttpGet httpGet,List<NameValuePair> params, String charset) {
		HttpResponse httpresponse = null;
		try {
			if(params.size()>0){ //有参数才拼装URL
				String str = EntityUtils.toString(new UrlEncodedFormEntity(params, charset));
				String url = httpGet.getURI().toString();
				if(StringUtils.indexOf(url, "?")>0){//如果原来url有?,则不用加"?"，否则需要添加"?"进行拼装url
					httpGet.setURI(new URI( url + str));
				}else{
					httpGet.setURI(new URI( url + "?" + str));
				}
			}
			httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
			httpGet.setHeader("Connection", "keep-alive");
			httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36");
			httpGet.setHeader("Accept-Charset", charset);
			httpGet.setHeader("ContentType", charset);
			httpresponse = getClient(httpGet).execute(httpGet);
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e, String.format("GET请求%s报错！", httpGet.getURI().toString()));
		}
		return httpresponse;
	}
	/**
	 * Post请求
	 * @param urlString url路径
	 * @param params 参数
	 * @param charset 编码
	 * @return org.apache.http.HttpResponse
	 */
	public static HttpResponse post(HttpPost httpPost,List<NameValuePair> params, String charset) {
		HttpResponse httpresponse = null;
		try {
			httpPost.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
			httpPost.setHeader("Connection", "keep-alive");
			httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36");
			httpPost.setHeader("Accept-Charset", charset);
			httpPost.setHeader("ContentType", charset);
			// 设置参数
			httpPost.setEntity(new UrlEncodedFormEntity(params,charset));
			// 发送请求
			httpresponse = getClient(httpPost).execute(httpPost);
		} catch (Exception e) {
			LOGGER.error("post error!", e);
			throw ExceptionUtils.toUnchecked(e, String.format("Post请求%s报错！", httpPost.getURI().toString()));
		}
		return httpresponse;
	}
	
	/**
	 * raw形式的post请求
	 * @param urlString 路径
	 * @param parameters 参数
	 * @return
	 */
	public static HttpRespons sendBinary(String urlString,
			String  parameters) {
		return sendBinary(urlString, parameters, HttpConstants.DEFAULT_CHARSET);
	}
	
	/**
	 * raw形式的post请求
	 * @param urlString 路径
	 * @param parameters 参数
	 * @param charset 编码
	 * @return
	 */
	public static HttpRespons sendBinary(String urlString,
			String  parameters,String charset) {

		HttpRespons httpRespons = null;	
		long elapsed = 0;
			HttpPost httpPost = null ; 
			try {
				// 发送请求
				httpPost = new HttpPost(urlString);
				long startTime = System.currentTimeMillis();
				HttpResponse httpresponse = postBinary(httpPost, parameters.getBytes(), charset);
				elapsed = System.currentTimeMillis() - startTime;

				httpRespons = new HttpRespons();
				httpRespons.setUrl(urlString);
				httpRespons.setMethod("POST");
				httpRespons.setElapsedTime(elapsed);
				httpRespons.parseHttpresponse(httpresponse);//組裝httpRespons信息 提供返回
				LOGGER.debug(String.format("---发送post请求,花费%s毫秒,发送post请求：%s,请求结果%s",elapsed, urlString,httpRespons.toString()));
				return httpRespons;
			} finally{
				if(httpPost!=null){
					httpPost.releaseConnection();
				}
			}
		
	}
	
	public static HttpResponse postBinary(HttpPost httpPost,byte[] binary, String charset) {
		HttpResponse httpresponse = null;
		try {
			httpPost.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
			httpPost.setHeader("Connection", "keep-alive");
			httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36");
			httpPost.setHeader("Accept-Charset", charset);
			httpPost.setHeader("ContentType", charset);
			
			ByteArrayEntity entity = new ByteArrayEntity(binary) ;
			// 设置参数
			httpPost.setEntity(entity);
			// 发送请求
			httpresponse = getClient(httpPost).execute(httpPost);
		} catch (Exception e) {
			throw ExceptionUtils.toUnchecked(e, String.format("Post请求%s报错！", httpPost.getURI().toString()));
		}
		return httpresponse;
	}

}
