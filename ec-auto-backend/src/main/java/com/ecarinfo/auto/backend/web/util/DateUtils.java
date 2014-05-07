package com.ecarinfo.auto.backend.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;

public class DateUtils {
	private static Logger logger = Logger.getLogger(DateUtils.class);
	private static SimpleDateFormat dateFormat = new SimpleDateFormat();

	public static long TIME_SIX_DAY = 6l*24*3600*1000;
	public static long TIME_TWENTY_NINE_DAY = 29l*24*3600*1000;
	public static long TIME_SEVEN_DAY = 7l*24*3600*1000;
	public static long TIME_FIFTEEN_SECOND = 15*60*1000;	
	public static long TIME_ONE_DAY = 24*3600*1000;
	public static final String HHMMSS_MAX = "23:59:59";
	public static final String HHMMSS_MIN = "00:00:00";
	public static final String DEFAULT_SHORT_DATE_FORMAT="yyyy-MM-dd";
	public static final SimpleDateFormat DEFAULT_SHORT_DATE_FORMATER = new SimpleDateFormat(DEFAULT_SHORT_DATE_FORMAT);
	
	public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	public static final String MM_dd_HH_mm = "MM-dd HH:mm";
	public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
	public static final String HH_mm = "HH:mm";
	public static final String yyyy_MM = "yyyy-MM";
	
	public final static long DAY_TIMES = 24*60*60*1000;//一天的毫秒数
	public final static int DAY_HOUR_TIMES = 60*60*1000;//一小时的毫秒数
	public final static int DAY_MIN_TIMES = 60*1000;//一分钟的毫秒数
	public final static int DAY_SEC_TIMES = 1000;//一秒的毫秒数
	
	public static final String dateToString(Date date,String formatter) {
		DateTime dateTime = new DateTime(date);
		return dateTime.toString(formatter);
	}	
	
	public static final String dateToString(Long date,String formatter) {
		return dateToString(new Date(date),formatter);
	}
	
	public static final Date stringToDate(String str, String formatter)  {
		Date date = null;
		dateFormat.applyPattern(formatter);
		try {
			date = dateFormat.parse(str);
		} catch (ParseException e) {
			date = null;
			logger.error(e.getMessage(), e);
		}
		return date;
	}
	public static Date addDay(Date date, int n) {   
        long value = n*TIME_ONE_DAY;
        return new Date(date.getTime()+value);
    }   
	
	public static Integer getDaysBetween(Date from, Date to) {
		if (from != null && to != null) {
			Days daysBetween = Days.daysBetween(new DateTime(from), new DateTime(to));
			return daysBetween.getDays();
		}
		return 0;
	}
}
