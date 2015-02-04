package com.jason.framework.orm.redis;


import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Tuple;

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
		
		Double s = zsetRepository.get(key, "blue");
		Assert.assertEquals(s.doubleValue(), 5800,5800);
		
		
	}
	
	@Test
	public void testGetList(){
		String key = "zz";
		Set<String> set = zsetRepository.getListDesc(key);
		printSet(set);
		
		Set<String> set2 =zsetRepository.getListAsc(key);
		printSet(set2);
		
		
		Set<Tuple> tuples = zsetRepository.getListDescWithScores(key);
		printSetWithScores(tuples);
		Set<Tuple> tuples2 = zsetRepository.getListAscWithScores(key);
		printSetWithScores(tuples2);
	}

	private void printSetWithScores(Set<Tuple> tuples) {
		System.out.println("----------------------");
		Iterator<Tuple> it = tuples.iterator();
		while (it.hasNext()) {
			Tuple tuple = it.next();
			System.out.println(tuple.getElement()+" "+tuple.getScore());
		}
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
