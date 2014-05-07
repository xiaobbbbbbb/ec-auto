package com.ecarinfo.auto.backend.web.helper.search;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ecarinfo.auto.backend.web.util.WebUtils;
import com.ecarinfo.auto.rm.DictCarBrandRM;
import com.ecarinfo.auto.rm.DictCarSerialRM;
import com.ecarinfo.auto.rm.IndustryArticleRM;
import com.ecarinfo.auto.rm.ViewpointRM;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;

public class CarSerialSearchHelper {
private static Logger logger = Logger.getLogger(CarSerialSearchHelper.class);
	
	public static Criteria getSearchCriteria(HttpServletRequest request) {
		Criteria criteria = WebUtils.getPageCriteria(request);
		criteria.eq("1", 1);
		int isValid = WebUtils.getParamAsInt(request, "isValid", -1);
		String name = WebUtils.getParam(request, "name", null);
		String brandName = WebUtils.getParam(request, "brandName", null);
		if (isValid>=0) {
			criteria.eq(DictCarSerialRM.isValid, isValid, CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(name)) {
			criteria.like(DictCarSerialRM.name, "%" + name + "%", CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(brandName)) {
			criteria.like(DictCarSerialRM.brandName, "%" + brandName + "%", CondtionSeparator.AND);
		}
		return criteria;
	}
	
}
