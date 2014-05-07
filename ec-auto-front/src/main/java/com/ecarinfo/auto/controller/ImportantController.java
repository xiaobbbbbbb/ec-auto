package com.ecarinfo.auto.controller;


import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.auto.base.BaseController;
import com.ecarinfo.auto.service.TempIndustryService;
import com.ecarinfo.auto.util.DtoUtil;
import com.ecarinfo.auto.util.GetDateUtil;
import com.ecarinfo.auto.vo.TempIndustryVO;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.persist.paging.ECPage;


/**
 *重点新闻
 */

@Controller
@RequestMapping("/important")
public class ImportantController extends BaseController {

	@Resource
	private TempIndustryService tempIndustryService;
	@RequestMapping(value = "/index")
	public String Index(ModelMap map, HttpServletRequest request) {
		
		return "important/index";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(ModelMap map,Integer date,String title,Integer type,Integer cpage,Integer pageNo) throws UnsupportedEncodingException {
		if(title!=null){
			title = DtoUtil.incode(title);
		}
		String startTime = "";
		String endTime = "";
		Date sDate = null;
		Date eDate = null;
		if (date!=null && date >0) {
			Date today = DateUtils.getToday(0, 0, 0).toDate();
			switch (date) {
			case 1://今天
				sDate = DateUtils.plusDays(today, 0);
				eDate = sDate; 
				endTime = DateUtils.dateToString(eDate, TimeFormatter.FORMATTER2)+" 23:59:59";
				break;
			case 2:// 本周
				sDate = GetDateUtil.getFristday();
				eDate = new Date();
				endTime = DateUtils.dateToString(eDate, TimeFormatter.FORMATTER1);
				break;
			}
			startTime = DateUtils.dateToString(sDate, TimeFormatter.FORMATTER2)+" 00:00:00";
		}
		Integer carBrandId=commonCache.getDefaultCarBrand().getId();
		Integer[] ids={carBrandId};
		map.put("carBrandId", carBrandId);
		ECPage<TempIndustryVO> page = tempIndustryService.getIndustryArticleList(startTime,endTime,title, type,ids, pageNo, 25);
		return page;
	}
	
	@RequestMapping(value = "/about")
	public String aboutIndex(ModelMap map, HttpServletRequest request,Integer type,Integer date) {
		map.addAttribute("type", type);
		map.addAttribute("date", date);
		return "important/about";
	}
	
}
