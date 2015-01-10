package com.jason.framework.orm.redis;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jason.framework.AbstractTestBase;

public class QueueRepositoryTest extends AbstractTestBase{
	
	@Autowired
	private QueueRepository queueRepository;
	
	@Test
	public void testFront(){
		String key = "list:1";
		queueRepository.pushFront(key, "list1");
		queueRepository.pushFront(key, "list2");
		queueRepository.pushFront(key, "list3");
		Long size = queueRepository.size(key);
		Assert.assertTrue(size >= 3);
		String[] datas = new String[]{"test1","test2","test3"};
		queueRepository.pushFront(key, datas);
		Long size2 = queueRepository.size(key);
		Assert.assertTrue(size2 >= 6);
		
		String val = queueRepository.popFront(key);
		Assert.assertEquals(val, "test3");
		Long size3 = queueRepository.size(key);
		Assert.assertEquals(size3.longValue(), (size2.longValue()-1));
	}
	@Test
	public void testBack(){
		String key = "list:2";
		queueRepository.pushBack(key, "list1");
		queueRepository.pushBack(key, "list2");
		queueRepository.pushBack(key, "list3");
		Long size = queueRepository.size(key);
		Assert.assertTrue(size >= 3);
		String[] datas = new String[]{"test1","test2","test3"};
		queueRepository.pushBack(key, datas);
		
		Long size2 = queueRepository.size(key);
		Assert.assertTrue(size2 >= 6);
		
		String val = queueRepository.popBack(key);
		Assert.assertEquals(val, "test3");
		Long size3 = queueRepository.size(key);
		Assert.assertEquals(size3.longValue(), (size2.longValue()-1));
	}
	
	@Test
	public void testGetList(){
		String key = "list:3";
		String[] datas = new String[]{"test1","test2","test3"};
		queueRepository.pushBack(key, datas);
		
		List<String> list = queueRepository.getList(key);
		for (String str:list) {
			System.out.println(str);
		}
		Long size = queueRepository.size(key);
		int count = list.size();
		
		Assert.assertEquals(size.longValue(), Long.valueOf(count).longValue());
	}
}
