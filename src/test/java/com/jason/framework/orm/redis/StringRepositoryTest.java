package com.jason.framework.orm.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jason.framework.AbstractTestBase;

public class StringRepositoryTest extends AbstractTestBase{
	@Autowired
	private StringRepository stringRepository;
	@Test
	public void testSet(){
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			stringRepository.set("keyString:"+i, "valString:"+i);
		}
		long end  = System.currentTimeMillis();
		System.out.println("单个插入10000,花费毫秒："+(end-start));
	}
	
	@Test
	public void testSet2(){
		long start = System.currentTimeMillis();
		Map<String,String> batchData = new HashMap<String, String>();
		for (int i = 0; i < 10000; i++) {
			batchData.put("keyString2:"+i, "valString:"+i);
		}
		
		stringRepository.set(batchData);
		long end  = System.currentTimeMillis();
		System.out.println("批量插入10000,花费毫秒："+(end-start));
	}
	
	@Test
	public void testGet(){
		for (int i = 0; i < 100; i++) {
			String key = "keyString:"+i;
			String val = stringRepository.get(key);
			System.out.println(key+" "+val);
		}
	}
	
	@Test
	public void testGets(){
		String[] keys = new String[]{"keyString:1", "keyString:2","keyString:3"};
		List<String> list = stringRepository.get(keys);
		for (String str:list) {
			System.out.println(str);
		}
	}
	
	@Test
	public void testDel(){
		for (int i = 0; i < 100; i++) {
			String key = "keyString:"+i;
			if(stringRepository.exists(key)){
				stringRepository.del(key);
			}
		}
	}
	
}
