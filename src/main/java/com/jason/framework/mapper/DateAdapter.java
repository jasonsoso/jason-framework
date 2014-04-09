package com.jason.framework.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * 时间 适配器
 * @author Jason
 * @date 2014-4-9 下午02:36:08
 */
public class DateAdapter extends XmlAdapter<String, Date>{

	private String pattern = "yyyy-MM-dd HH:mm:ss";
	SimpleDateFormat fmt = new SimpleDateFormat(pattern);
	
	@Override
    public Date unmarshal(String dateStr) throws Exception {
        return fmt.parse(dateStr);
    }
 
    @Override
    public String marshal(Date date) throws Exception {
        return fmt.format(date);
    }
}
