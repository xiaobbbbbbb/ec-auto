package com.ecarinfo.auto.backend.web.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import com.ecarinfo.auto.backend.web.helper.CommonDataCache;
import com.ecarinfo.auto.backend.web.helper.ConfigHelper;
import com.ecarinfo.auto.backend.web.helper.ExportHelper;
import com.ecarinfo.auto.backend.web.helper.VOHelper;
import com.ecarinfo.persist.service.GenericService;

@Component
public abstract class BaseAction {
	@Resource
	protected GenericService baseService;

	@Resource
	protected CommonDataCache cache;
	
	@Resource
	protected VOHelper voHelper;
	
	@Resource
	protected ExportHelper exportHelper;
	
	@Resource
	protected ConfigHelper configHelper;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), true));
	}

}
