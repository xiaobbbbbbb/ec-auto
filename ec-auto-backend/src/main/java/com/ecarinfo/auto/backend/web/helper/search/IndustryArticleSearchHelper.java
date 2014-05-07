package com.ecarinfo.auto.backend.web.helper.search;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ecarinfo.auto.backend.web.util.DateUtils;
import com.ecarinfo.auto.backend.web.util.WebUtils;
import com.ecarinfo.auto.rm.ArticleRM;
import com.ecarinfo.auto.rm.IndustryArticleRM;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;

public class IndustryArticleSearchHelper {
private static Logger logger = Logger.getLogger(IndustryArticleSearchHelper.class);
	
	public static Criteria getSearchCriteria(HttpServletRequest request) {
		Criteria criteria = WebUtils.getPageCriteria(request);
		criteria.eq("1", 1);
		int mediaId = WebUtils.getParamAsInt(request, "mediaId", -1);
		String title = WebUtils.getParam(request, "title", null);
		int status = WebUtils.getParamAsInt(request, "status", -1);
		String search_pub_time_begin = WebUtils.getParam(request, "search_pub_time_begin", null);
		String search_pub_time_end = WebUtils.getParam(request, "search_pub_time_end", null);
		if (mediaId>=0) {
			criteria.eq(IndustryArticleRM.mediaId, mediaId, CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(title)) {
			criteria.like(IndustryArticleRM.title, "%" + title + "%", CondtionSeparator.AND);
		}
		if (status>=0) {
			criteria.eq(IndustryArticleRM.status, status, CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(search_pub_time_begin)) {
			criteria.greateThenOrEquals(IndustryArticleRM.pubTime, search_pub_time_begin + " " + DateUtils.HHMMSS_MIN, CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(search_pub_time_end)) {
			criteria.lessThenOrEquals(IndustryArticleRM.pubTime, search_pub_time_end + " " + DateUtils.HHMMSS_MAX, CondtionSeparator.AND);
		}
		criteria.orderBy(IndustryArticleRM.ctime, OrderType.DESC);
		return criteria;
	}
	
}
