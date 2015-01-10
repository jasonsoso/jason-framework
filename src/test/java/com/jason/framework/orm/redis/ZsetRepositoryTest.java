package com.jason.framework.orm.redis;


import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jason.framework.AbstractTestBase;

public class ZsetRepositoryTest extends AbstractTestBase{
	
	@Autowired
	private ZsetRepository zsetRepository;
	
	@Test
	public void testSet(){
		String key ="salary";
		zsetRepository.set(key, "jason", 4500);
		zsetRepository.set(key, "tom", 4000);
		zsetRepository.set(key, "blue", 5800);
		
		Long size = zsetRepository.size(key);
		Assert.assertEquals(size.longValue(), 3);
		
		zsetRepository.del(key, "jason");
		Long size2 = zsetRepository.size(key);
		Assert.assertEquals(size2.longValue(), 2);
		
	}
	
	@Test
	public void testGetList(){
		String key = "zz";
		Set<String> set = zsetRepository.getListDesc(key);
		printSet(set);
		
		Set<String> set2 =zsetRepository.getListAsc(key);
		printSet(set2);
	}

	private void printSet(Set<String> set) {
		System.out.println("----------------------");
		// 获得迭代器
		Iterator<String> it = set.iterator();
		// 判断是否还有元素可以迭代
		while (it.hasNext()) {
			// 返回迭代的下一个元素。
			String st = it.next();
			// 结果输出
			System.out.println(st);
		}
	}
}
