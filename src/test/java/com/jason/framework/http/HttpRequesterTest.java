package com.jason.framework.http;

import hk.cloudcall.tools.EncryptTool;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;



public class HttpRequesterTest {
	
	 private final static String SERVER         = "https://test.yintong.com.cn/traderapi/";

	@Test
	public void testSendGet() throws Exception {
		
		Map<String,String> map = new HashMap<String,String>();
        map.put("q","喜爱夜蒲");
        
		HttpRespons httpRespons =  HttpRequester.sendGet("http://api.douban.com/v2/movie/search", map);
        
		System.out.println("-------testSendGet----"+httpRespons.getContent());
        
        Assert.assertEquals(httpRespons.getStatusCode(), 200);

        System.out.println("---------------------------华丽的分割线-----------------------------");
	}

	@Test
    public void testQuery() throws JSONException  {
        String reqJson = "{\"no_order\":\"20140501094312\",\"oid_partner\":\"201307012000003504\",\"query_version\":\"1.1\",\"sign\":\"Qkgv1piD31WRGqXSp3FXKRKt+nGzTK7wW2KXoNaVUD2ub6MraxUrM9VvllXZUPdz5JG9chmk+9JnmxttM8+mmwrQTQd1nxz+cHY3uvjPNZ6oH7hIoD86rOt7dN8hDzr47+JfxTeFhnLpRcc1ioZm2lkLVLEjyfb9oqPnTvNwkyk=\",\"sign_type\":\"RSA\"}";
        long startTime = System.currentTimeMillis();
        
        HttpRespons httpRespons2 =  HttpRequester.sendBinary(SERVER + "orderquery.htm", reqJson);
        
        long elapsed = System.currentTimeMillis() - startTime;
        System.out.println("花费时间："+elapsed+"毫秒");
        // String resJson = HttpSender.send(reqJson);
        // BankCardPayBean res = JSON.parseObject(resJson, BankCardPayBean.class);
        System.out.println("结果报文为:" + httpRespons2.getContent());
        
    }
	@Test
    public void testQuery2() throws JSONException  {
        
        JSONObject jsonObject = new JSONObject();
		jsonObject.put("pageNo", 1);
		jsonObject.put("pageSize", 10);
        
        HttpRespons httpRespons4 =  HttpRequester.sendBinary("http://localhost:8080/Auth_WeLove" + "/account/getAllUserInfo.do", jsonObject.toString());
        System.out.println("httpRespons4 结果报文为:" + httpRespons4.getContent());
    }
	@Test
    public void testQuery3() throws JSONException  {
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("telnumber", "13750357236");
		jsonObject.put("appkey", "gu");
        
		String decString = new String(EncryptTool.encdec("test".getBytes()));
		System.out.println("decString:"+decString);
        HttpRespons httpRespons5 =  HttpRequester.sendBinary("http://localhost:8080/Auth_WeLove" + "/user/exist.do", decString);
        System.out.println("httpRespons5 结果报文为:" + httpRespons5.getContent());
    }
}
