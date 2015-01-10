package com.jason.framework.orm.redis.util;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.jason.framework.mapper.JsonMapper;

public class JacksonJsonRedisSerializerTest {
	@Test
	public void testSerializeStr(){
		JacksonJsonRedisSerializer<String> serializerTest = new JacksonJsonRedisSerializer<String>(String.class);
		byte[] b = serializerTest.serialize("I love you!");
		System.out.println(b);
		String v = serializerTest.deserialize(b);
		assertEquals("I love you!",v);
	}
	
	@Test
	public void testSerializeObj(){
		Date now  = new Date();
		
		JacksonJsonRedisSerializer<Person> serializer = new JacksonJsonRedisSerializer<Person>(Person.class);
		Person p1 = new Person();
		p1.setName("Jason");
		p1.setAge(25);
		p1.setBirth(now);
		byte[] val = serializer.serialize(p1);
		System.out.println(val);
		Person p2 = serializer.deserialize(val);
		
		assertEquals(p1.getName(), p2.getName());
		assertEquals(p1.getAge(), p2.getAge());
		assertEquals(p1.getBirth(), p2.getBirth());
	}
	@Test
	public void testJason(){
		Date now  = new Date();
		Person p1 = new Person();
		p1.setName("Jason");
		p1.setAge(25);
		p1.setBirth(now);
		
		String jsonString = JsonMapper.toJsonString(p1);
		System.out.println(jsonString);
		
		Person p2 = JsonMapper.fromJsonString(jsonString, Person.class);
		assertEquals(p1.getName(), p2.getName());
		assertEquals(p1.getAge(), p2.getAge());
		assertEquals(p1.getBirth(), p2.getBirth());
		
	}
}
