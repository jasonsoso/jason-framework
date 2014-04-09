package com.jason.framework.mapper;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * CDATA 适配器
 * @author Jason
 * @date 2014-4-9 下午02:35:29
 */
public class CDataAdapter  extends XmlAdapter<String, String> {
	 @Override
	    public String marshal(String value) throws Exception {
	        return "<![CDATA[" + value + "]]>";
	    }
	    @Override
	    public String unmarshal(String value) throws Exception {
	        return value;
	    }
}
