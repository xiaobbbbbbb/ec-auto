package com.ecarinfo.auto.util;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

public class ChartUtils {

	public static String getJoinStr(Collection<?> dtos) {
		String str = "";
		for (Object dto : dtos) {
			str += "'" + dto + "',";
		}
		if (StringUtils.isNotEmpty(str))
			str = str.substring(0, str.length() - 1);
		return str;
	}
}
