package com.ecarinfo.auto.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetDateUtil {
	/**
	 * 获取本周头一天日期
	 * @return
	 */
	public static Date getFristday(){
		Calendar cal =Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");                  
        //获取本周一日期
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); 
        return cal.getTime();
	}
}
