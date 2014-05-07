package com.ecarinfo.auto.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecarinfo.auto.vo.QueryParameter;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;

public class QueryUtil {

	public static Logger logger = LoggerFactory.getLogger(QueryUtil.class);

	// 获取查询的参数
	public static QueryParameter getQueryParameter(HttpServletRequest request) {
		QueryParameter query = new QueryParameter();
		String brandId = request.getParameter("brandId");
		String areaId = request.getParameter("areaId");
		String time = request.getParameter("time");
		String serialIds = request.getParameter("serialIds");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String brandIds = request.getParameter("brandIds");
		String provinceId = request.getParameter("provinceId");
		String grade = request.getParameter("grade");
		String serialId = request.getParameter("serialId");
		String cityId = request.getParameter("cityId");

		if (StringUtils.isNotEmpty(brandId)) {
			Integer brandId_ = Integer.valueOf(brandId);
			if (brandId_ != null && brandId_ > 0)
				query.setBrandId(brandId_);
		}
		if (StringUtils.isNotEmpty(serialId)) {
			Integer serialId_ = Integer.valueOf(serialId);
			if (serialId_ != null && serialId_ > 0)
				query.setSerialId(serialId_);
		}
		if (StringUtils.isNotEmpty(provinceId)) {
			Integer provinceId_ = Integer.valueOf(provinceId);
			if (provinceId_ != null && provinceId_ > 0)
				query.setProvinceId(provinceId_);
		}

		if (StringUtils.isNotEmpty(grade)) {
			Integer grade_ = Integer.valueOf(grade);
			if (grade_ != null && grade_ > 0)
				query.setGrade(grade_);
		}

		if (StringUtils.isNotEmpty(areaId)) {
			Integer areaId_ = Integer.valueOf(areaId);
			if (areaId_ != null && areaId_ > 0)
				query.setAreadId(areaId_);
		}
		if (StringUtils.isNotEmpty(cityId)) {
			Integer cityId_ = Integer.valueOf(cityId);
			if (cityId_ != null && cityId_ > 0)
				query.setCityId(cityId_);
		}

		if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
			startTime += " 00:00:00";
			endTime += " 23:59:59";
			query.setStartTime(DateUtils.stringToDate(startTime, TimeFormatter.YYYY_MM_DD_HH_MM_SS));
			query.setEndTime(DateUtils.stringToDate(endTime, TimeFormatter.YYYY_MM_DD_HH_MM_SS));
		} else {
			if (StringUtils.isNotEmpty(time)) {
				Integer time_ = Integer.valueOf(time);
				Date startDate = null;
				Date endDate = null;
				if (time_ < 0) { // 倒退的时间差
				// if(time_==-1){
				// endDate= DateUtils.getDateByDay(time_);
				// }
					startDate = DateUtils.getDateByDay(time_);

				} else {
					String newDate = DateUtils.dateToString(new Date(), TimeFormatter.YYYY_MM_DD);
					startTime = newDate + " 00:00:00";
					startDate = DateUtils.stringToDate(startTime, TimeFormatter.YYYY_MM_DD_HH_MM_SS);
				}
				query.setStartTime(startDate);
				if (time_ == -1 || time_ == 0) {
					query.setEndTime(endDate);
				} else {
					query.setEndTime(new Date());
				}
			}
		}
		if (StringUtils.isNotEmpty(serialIds)) {
			String[] sids = serialIds.split(",");
			Integer[] _ids = new Integer[sids.length];
			for (int i = 0; i < sids.length; i++) {
				_ids[i] = Integer.valueOf(sids[i]);
			}
			query.setSerialIds(_ids);
		}
		if (StringUtils.isNotEmpty(brandIds)) {
			String[] sids = brandIds.split(",");
			Integer[] _ids = new Integer[sids.length];
			for (int i = 0; i < sids.length; i++) {
				_ids[i] = Integer.valueOf(sids[i]);
			}
			query.setBrandIds(_ids);
		}
		query.setTitle(getChartTitle(time != null ? Integer.valueOf(time) : null, startTime, endTime));
		logger.debug("查询参数:" + query);
		return query;
	}

	public static String getChartTitle(Integer time, String startTime, String endTime) {
		String title = "（最近7天）";
		if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
			title = "（" + startTime.substring(0, 10) + "至" + endTime.substring(0, 10) + "）";
		} else {
			if (time != null)
				if (time == 0) {
					title = "（今天）";
				} else if (time == -1) {
					title = "（昨天）";
				} else if (time == -6) {
					title = "（最近7天）";
				} else if (time == -14) {
					title = "（最近15天）";
				} else if (time == -29) {
					title = "（最近30天）";
				}
		}
		return title;
	}
}
