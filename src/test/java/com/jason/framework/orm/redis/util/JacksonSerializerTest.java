package com.jason.framework.orm.redis.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JacksonSerializerTest {
	@Test
	public void testSerializeStr(){
		SerializerHandler<String> serializerTest = new JacksonSerializer<String>(String.class);
		byte[] b = serializerTest.serialize("I love you!");
		System.out.println(b);
		String v = serializerTest.deserialize(b);
		assertEquals("I love you!",v);
	}
	
	@Test
	public void testSerializeObj(){
		
		SerializerHandler<Person> serializer = new JacksonSerializer<Person>(Person.class);
		Person p1 = new Person();
		p1.setName("Jason");
		p1.setAge(25);
		byte[] val = serializer.serialize(p1);
		System.out.println(val);
		Person p2 = serializer.deserialize(val);
		
		assertEquals(p1.getName(), p2.getName());
		assertEquals(p1.getAge(), p2.getAge());
	}
	
}
