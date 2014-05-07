package com.ecarinfo.auto.backend.web.helper.search;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ecarinfo.auto.backend.web.util.WebUtils;
import com.ecarinfo.auto.rm.DictCarBrandRM;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;

public class CarBrandSearchHelper {
private static Logger logger = Logger.getLogger(CarBrandSearchHelper.class);
	
	public static Criteria getSearchCriteria(HttpServletRequest request) {
		Criteria criteria = WebUtils.getPageCriteria(request);
		criteria.eq("1", 1);
		int isValid = WebUtils.getParamAsInt(request, "isValid", -1);
		String name = WebUtils.getParam(request, "name", null);

		if (isValid>=0) {
			criteria.eq(DictCarBrandRM.isValid, isValid, CondtionSeparator.AND);
		}
		if (StringUtils.isNotEmpty(name)) {
			criteria.like(DictCarBrandRM.name, "%" + name + "%", CondtionSeparator.AND);
		}
		return criteria;
	}
	
}
