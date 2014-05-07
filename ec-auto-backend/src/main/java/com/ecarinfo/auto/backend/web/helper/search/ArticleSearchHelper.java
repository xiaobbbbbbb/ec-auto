package com.ecarinfo.auto.backend.web.helper.search;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ecarinfo.auto.backend.web.util.DateUtils;
import com.ecarinfo.auto.backend.web.util.WebUtils;
import com.ecarinfo.auto.rm.ArticleRM;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;

public class ArticleSearchHelper {
private static Logger logger = Logger.getLogger(ArticleSearchHelper.class);
	
	public static Criteria getSearchCriteria(HttpServletRequest request) {
		Criteria criteria = WebUtils.getPageCriteria(request);
		criteria.eq("1", 1);
		int affection = WebUtils.getParamAsInt(request, "affection", -1);
		int mediaType = WebUtils.getParamAsInt(request, "mediaType", -1);
		int grade = WebUtils.getParamAsInt(request, "grade", -1);
		int desTarget = WebUtils.getParamAsInt(request, "desTarget", -1);
		String title = WebUtils.getParam(request, "title", null);
		int status = WebUtils.getParamAsInt(request, "status", -1);
		int brandId = WebUtils.getParamAsInt(request, "brandId", -1);
		int serialId = WebUtils.getParamAsInt(request, "serialId", -1);
		int areaId = WebUtils.getParamAsInt(request, "areaId", -1);
		int provinceId = WebUtils.getParamAsInt(request, "provinceId", -1);
		int cityId = WebUtils.getParamAsInt(request, "cityId", -1);
		int mediaId = WebUtils.getParamAsInt(request, "mediaId", -1);
		String search_pub_time_begin = WebUtils.getParam(request, "search_pub_time_begin", null);
		String search_pub_time_end = WebUtils.getParam(request, "search_pub_time_end", null);
		
		if (affection>=0) {
			criteria.eq(ArticleRM.affection, affection, CondtionSeparator.AND);
		}
		if (mediaType>=0) {
			criteria.eq(ArticleRM.mediaType, mediaType, CondtionSeparator.AND);
		}
		if (grade>=0) {
			criteria.eq(ArticleRM.grade, grade, CondtionSeparator.AND);
		}
		if (desTarget>=0) {
			criteria.eq(ArticleRM.desTarget, desTarget, CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(title)) {
			criteria.like(ArticleRM.title, "%" + title + "%", CondtionSeparator.AND);
		}
		if (status>=0) {
			criteria.eq(ArticleRM.status, status, CondtionSeparator.AND);
		}
		if (brandId>=0) {
			criteria.eq(ArticleRM.brandId, brandId, CondtionSeparator.AND);
		}
		if (serialId>=0) {
			criteria.eq(ArticleRM.serialId, serialId, CondtionSeparator.AND);
		}
		if (areaId>=0) {
			criteria.eq(ArticleRM.areaId, areaId, CondtionSeparator.AND);
		}
		if (provinceId>=0) {
			criteria.eq(ArticleRM.provinceId, provinceId, CondtionSeparator.AND);
		}
		if (cityId>=0) {
			criteria.eq(ArticleRM.cityId, cityId, CondtionSeparator.AND);
		}
		if (mediaId>=0) {
			criteria.eq(ArticleRM.mediaId, mediaId, CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(search_pub_time_begin)) {
			criteria.greateThenOrEquals(ArticleRM.articleCtime, search_pub_time_begin + " " + DateUtils.HHMMSS_MIN, CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(search_pub_time_end)) {
			criteria.lessThenOrEquals(ArticleRM.articleCtime, search_pub_time_end + " " + DateUtils.HHMMSS_MAX, CondtionSeparator.AND);
		}
		criteria.orderBy(ArticleRM.ctime, OrderType.DESC);
		return criteria;
	}
	
}
