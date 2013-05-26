package com.jason.framework.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



/**
 * XPath 工具类
 */
public final class XPathUtils {
	
	public XPathUtils(){}

	private static final XPathFactory XPATH_FACTORY = XPathFactory.newInstance();

	public static String evalAsString(Object doc, String expression) throws XPathExpressionException {
		if (StringUtils.isBlank(expression)) {
			return null;
		}
		
		List<String> values = evalAsStringList(doc, expression);
		if (values.isEmpty()) {
			return null;
		}

		return values.get(0);
	}

	public static List<String> evalAsStringList(Object doc, String expression) throws XPathExpressionException {

		if (StringUtils.isBlank(expression)) {
			return Collections.emptyList();
		}

		// always eval as NodeList.
		NodeList nodeItems = evalAsNodeList(doc, expression);
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < nodeItems.getLength(); i++) {

			Node item = nodeItems.item(i);
			if (item.getNodeType() == Node.TEXT_NODE || item.getNodeType() == Node.ATTRIBUTE_NODE) {
				result.add(item.getNodeValue());
			}
			else if (item.getNodeType() == Node.CDATA_SECTION_NODE) {
				CDATASection cdata = (CDATASection) item;
				result.add(cdata.getData());
			}
			else {
				result.add(item.getTextContent());
			}
		}

		return result;
	}

	public static NodeList evalAsNodeList(Object doc, String expression) throws XPathExpressionException {

		Assert.notNull(doc, "doc must not null!");
		Assert.notNull(expression, "xpath expresstion must not null!");
		if (StringUtils.isBlank(expression)) {
			return null;
		}
		
		XPath xpath = XPATH_FACTORY.newXPath();
		return (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
	}
}
