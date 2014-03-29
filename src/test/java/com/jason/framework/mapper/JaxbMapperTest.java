package com.jason.framework.mapper;


import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.junit.Assert;
import org.junit.Test;


/**
 * 演示基于JAXB2.0的Java对象-XML转换及Dom4j的使用.
 * 
 * 演示用xml如下:
 * 
 * <pre>
 * <?xml version="1.0" encoding="UTF-8"?>
 * <user id="1">
 * 	<name>jason</name>
 * 	<interests>
 * 		<interest>movie</interest>
 * 		<interest>sports</interest>
 * 	</interests>
 * </user>
 * </pre>
 */
public class JaxbMapperTest {

	@Test
	public void objectToXml() {
		User user = new User();
		user.setId(1L);
		user.setName("jason");

		user.getInterests().add("movie");
		user.getInterests().add("sports");

		String xml = JaxbMapper.toXml(user, "UTF-8");
		System.out.println("Jaxb Object to Xml result:\n" + xml);
		assertXmlByDom4j(xml);
	}

	@Test
	public void xmlToObject() {
		String xml = generateXmlByDom4j();
		User user = JaxbMapper.fromXml(xml, User.class);

		System.out.println("Jaxb Xml to Object result:\n" + user);
		Assert.assertEquals(user.getId(), new Long(1));
		
		List<String> list = user.getInterests();
		
		Assert.assertTrue("集合應該包含movie字符", list.contains("movie"));
		Assert.assertTrue("集合應該包含sports字符", list.contains("sports"));
	}

	/**
	 * 测试以List对象作为根节点时的XML输出
	 */
	@Test
	public void toXmlWithListAsRoot() {
		User user1 = new User();
		user1.setId(1L);
		user1.setName("jason");

		User user2 = new User();
		user2.setId(2L);
		user2.setName("kate");

		List<User> userList = new ArrayList<User>();
		userList.add(user1);
		userList.add(user2);

		String xml = JaxbMapper.toXml(userList, "userList", User.class, "UTF-8");
		System.out.println("Jaxb Object List to Xml result:\n" + xml);
	}

	/**
	 * 使用Dom4j生成测试用的XML文档字符串.
	 */
	private static String generateXmlByDom4j() {
		Document document = DocumentHelper.createDocument();

		Element root = document.addElement("user").addAttribute("id", "1");

		root.addElement("name").setText("jason");

		// List<String>
		Element interests = root.addElement("interests");
		interests.addElement("interest").addText("movie");
		interests.addElement("interest").addText("sports");

		return document.asXML();
	}

	/**
	 * 使用Dom4j验证Jaxb所生成XML的正确性.
	 */
	private static void assertXmlByDom4j(String xml) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			Assert.fail(e.getMessage());
		}
		Element user = doc.getRootElement();
		Assert.assertEquals(user.attribute("id").getValue(), "1");
		//XPath 解析XML结构
		Element interests = (Element) doc.selectSingleNode("//interests");
		Assert.assertEquals(interests.elements().size(),2);
		String movie = ((Element) interests.elements().get(0)).getText();
		Assert.assertEquals(movie,"movie");
	}

	
}
