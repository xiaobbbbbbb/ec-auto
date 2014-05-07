package com.ecarinfo.auto.weixin.action;

import java.util.Map;
import javax.annotation.Resource;
import com.ecarinfo.auto.weixin.service.WXApiService;
import com.ecarinfo.auto.weixin.service.WXRequestService;
import com.ecarinfo.auto.weixin.vo.MobilePage;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.service.GenericService;

public class BaseAction {
	@Resource
	GenericService baseService;
	
	@Resource
	protected WXRequestService wxRequestService;
	
	@Resource
	protected WXApiService wxApiService;
	
	public void setPage(Map<String, Object> model, ECPage<?> holder) {
		model.put("page", MobilePage.from(holder));
	}
}
