package com.ecarinfo.auto.backend.web.helper.search;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ecarinfo.auto.backend.web.util.WebUtils;
import com.ecarinfo.auto.rm.IndustryArticleRM;
import com.ecarinfo.auto.rm.ViewpointRM;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;

public class ViewpointSearchHelper {
private static Logger logger = Logger.getLogger(ViewpointSearchHelper.class);
	
	public static Criteria getSearchCriteria(HttpServletRequest request) {
		Criteria criteria = WebUtils.getPageCriteria(request);
		criteria.eq("1", 1);
		int affection = WebUtils.getParamAsInt(request, "affection", -1);
		int isManual = WebUtils.getParamAsInt(request, "isManual", -1);
		String name = WebUtils.getParam(request, "name", null);
		int status = WebUtils.getParamAsInt(request, "status", -1);
		if (affection>=0) {
			criteria.eq(ViewpointRM.affection, affection, CondtionSeparator.AND);
		}
		if (isManual>=0) {
			criteria.eq(ViewpointRM.isManual, isManual, CondtionSeparator.AND);
		}
		if (status>=0) {
			criteria.eq(ViewpointRM.status, status, CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(name)) {
			criteria.like(ViewpointRM.name, "%" + name + "%", CondtionSeparator.AND);
		}
		criteria.orderBy(ViewpointRM.affection, OrderType.DESC);	
		criteria.orderBy(ViewpointRM.ctime, OrderType.DESC);
		return criteria;
	}
	
}
