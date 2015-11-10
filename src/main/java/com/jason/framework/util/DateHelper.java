package com.jason.framework.util;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * 日期帮助类
 * @author Jason
 * @data 2014-6-30  
 */
public class DateHelper {
	
	private DateHelper(){
	}

    public static final String YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDD_HHMMSS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String YYYYMMDD_HHMMSS_S = "yyyy-MM-dd HH:mm:ss.S";
    public static final String YYYYMMDD = "yyyy-MM-dd";
    public static final String MMDD = "MMdd";
    public static final String YYYYMM = "yyyyMM";
    public static final String YYYY = "yyyy";
    /**
     * 日期格式化
     * @param date
     * @param targetformat
     * @return String
    */ 
    public static String formatDate(Date date, String targetformat) {
        return DateFormatUtils.format(date, targetformat);
    }
    
    /**
     * 针对毫秒数，进行格式化时间
     * @param date
     * @param targetformat
     * @return
     */
    public static String formatDate(long date, String targetformat) {
        return DateFormatUtils.format(date, targetformat);
    }
    
    /**
     * 针对毫秒数，进行格式化时间
     * @param date
     * @return
     */
    public static String formatDate(Long date) {
    	if(null == date){
    		return "";
    	}else{
    		return DateFormatUtils.format(date, DateHelper.YYYYMMDD_HHMMSS);
    	}
    }
    /**针对毫秒数，进行格式化时间 ,格式化为YYYYMM
     * @param date
     * @return
     */
    public static String formatDateYYYYMM(Long date) {
    	if(null == date){
    		return "";
    	}else{
    		return DateFormatUtils.format(date, DateHelper.YYYYMM);
    	}
    }
    /**针对毫秒数，进行格式化时间 ,格式化为YYYY
     * @param date
     * @return
     */
    public static String formatDateYYYY(Long date) {
    	if(null == date){
    		return "";
    	}else{
    		return DateFormatUtils.format(date, DateHelper.YYYY);
    	}
    }
    
    /**
     * 时间字符串转换为Date
     * @param dateStr
     * @param sourceformat
     * @return
     * @throws ParseException
     */
    public static Date toDate(String dateStr,String sourceformat){
    	java.util.Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sourceformat);
        try {
        	date = simpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			throw ExceptionUtils.toUnchecked(e, "时间转换失败！");
		}
		return date;
    }
    
    /**
     * 获取当前年份
     * @return
     */
    public static int getSysYear(){
    	return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取当前时间
     * @return
     */
    public static java.util.Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }
    
    /**
     * 获取当前时间
     * @return
     */
    public static Timestamp getCurrentTimestamp() {
    	long date = getCurrentDate().getTime();
        return  new Timestamp(date);
    }
    
    /**
     * 获取当前日期（只是日期）
     * @return
     * @throws ParseException
     */
    public static java.util.Date getCurrentOnlyDate() {
    	java.util.Date now = getCurrentDate();
        String onlyDateStr = formatDate(now, DateHelper.YYYYMMDD);
        return  toDate(onlyDateStr, DateHelper.YYYYMMDD);
    }
    
    /**
     * 获取一个日期在一个星期中第几天
     * @param date
     * @return
     * @throws Exception
     */
    public static int getDayOfWeek(java.util.Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
    
    /**
     * 日期相減
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    public static int dateSub(java.util.Date startDate,java.util.Date endDate){
        long start = startDate.getTime();
        long end = endDate.getTime();
        return (int) ((end - start)/(24*60*60*1000));
    }
    
    /**
     * 日期时间转换  Date => Calendar
     * @param date
     * @return
     */
    public static Calendar toCalendar(java.util.Date date){
		return DateUtils.toCalendar(date);
    }
    
    /**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return formatDate(new Date(), YYYYMMDD);
	}
}
