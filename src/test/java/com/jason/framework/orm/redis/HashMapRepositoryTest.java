package com.jason.framework.orm.redis;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jason.framework.AbstractTestBase;

public class HashMapRepositoryTest extends AbstractTestBase{
	@Autowired
	private HashRepository hashMapRepository;
	
	@Before
	public void before(){
		hashMapRepository.set("user:1", "name", "jason");
		hashMapRepository.set("user:1", "age", "25");
		
		hashMapRepository.set("user:2", "name", "lisa");
		hashMapRepository.set("user:2", "age", "24");
		
		Map<String,String> map  = new HashMap<String,String>();
		map.put("name", "yes");
		map.put("age", "25");
		hashMapRepository.set("user:3", map);
	}
	@Test
	public void testSet(){
		before();
	}
	@Test
	public void testGet(){
		
		Map<String,String> map2 = hashMapRepository.get("user:3");
		String name = map2.get("name");
		String age = map2.get("age");
		Assert.assertEquals(name, "yes");
		Assert.assertEquals(age, "25");
		
		
		String name2 = hashMapRepository.get("user:1", "name");
		Assert.assertEquals(name2, "jason");
	}
	@Test
	public void testExists(){
		if(hashMapRepository.exists("user:1", "name")){
			String name2 = hashMapRepository.get("user:1", "name");
			Assert.assertEquals(name2, "jason");
		}
	}
	@Test
	public void testDel(){
		boolean fla = hashMapRepository.exists("user:1", "name");
		Assert.assertTrue(fla);
		
		hashMapRepository.del("user:1", "name");
		
		boolean fla2 = hashMapRepository.exists("user:1", "name");
		Assert.assertFalse(fla2);
		
	}
	
}
