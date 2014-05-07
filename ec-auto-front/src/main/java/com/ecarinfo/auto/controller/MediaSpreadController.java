package com.ecarinfo.auto.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.auto.base.BaseController;
import com.ecarinfo.auto.po.DictArea;
import com.ecarinfo.auto.po.DictCarBrand;
import com.ecarinfo.auto.po.DictCarSerial;
import com.ecarinfo.auto.po.DictProvince;
import com.ecarinfo.auto.util.CommonCache;
import com.ecarinfo.auto.util.QueryUtil;
import com.ecarinfo.auto.vo.ChartVO;
import com.ecarinfo.auto.vo.LineChartDataVO;
import com.ecarinfo.auto.vo.QueryParameter;
import com.ecarinfo.auto.vo.ViewpointVO;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.persist.paging.ECPage;

/**
 * 媒体传播
 */

@Controller
@RequestMapping("/media")
public class MediaSpreadController extends BaseController {
	
	@Resource
	private CommonCache commonCache;

	@RequestMapping(value = "/index")
	public String Index(ModelMap map,HttpServletRequest request) {
		List<DictCarBrand> brands = commonCache.getBrands();
		map.put("brands", brands);
		int defaultBrandId =  commonCache.getDefaultCarBrand().getId();
		DictCarBrand brand = commonCache.getBrandMap().get(defaultBrandId);
		map.put("brand", brand);
		addQuery(map, defaultBrandId);

		return "media_spread";
	}

	@RequestMapping(value = "/getSerials")
	@ResponseBody
	public List<DictCarSerial> getSerials(Integer brandId) {
//		List<DictCarSerial> serials = commonService.getAllDictCarSerialByCarBrandId(brandId);
		List<DictCarSerial> serials = commonCache.getBrandId2SerialsMap().get(brandId);
		return serials;
	}

	private void addQuery(ModelMap map, Integer brandId) {
//		List<DictCarSerial> serials = commonService.getAllDictCarSerialByCarBrandId(brandId);
		List<DictCarSerial> serials = commonCache.getBrandId2SerialsMap().get(brandId);
		map.put("serials", serials);

		Date startDate = DateUtils.getDateByDay(-7);
//		List<DictCarSerial> serial = commonService.getAllDictCarSerialByCarBrandId(brandId);
		ECPage<ViewpointVO> page = viewpointService.getViewpoints(brandId, serials.get(0).getId(), startDate, new Date(), 0, 0, 0, 0, 1, 10);
		
//		List<DictArea> areas = commonService.getDictArea();
		List<DictArea> areas = commonCache.getAreas();
		map.put("areas", areas);

//		List<DictProvince> provinces = commonService.getDictProvince();
		List<DictProvince> provinces = commonCache.getProvinces();
		map.put("provinces", provinces);
		map.put("viewpoints", page.getList());
	}

	// 传播效力---传播趋势
	@RequestMapping(value = "/spreadTrend")
	@ResponseBody
	public ChartVO spreadTrend(HttpServletRequest request) {
		QueryParameter param = QueryUtil.getQueryParameter(request);

		// Session的厂牌ID
		int defaultBrandId =  commonCache.getDefaultCarBrand().getId();

		if (param.getBrandIds() == null) {
			param.setBrandIds(new Integer[]{defaultBrandId});
		}
		
		if (param.getStartTime() == null && param.getEndTime() == null) {
			param.setStartTime(DateUtils.getDateByDay(-6));
			param.setEndTime(new Date());
		}
		List<LineChartDataVO> list =articleService.getArticleStatisticsByMediaType(param.getBrandIds(), param.getStartTime(), param.getEndTime());
		ChartVO chartVO =new ChartVO();
		chartVO.setTitle(param.getTitle());
		chartVO.setObject(list);
		return chartVO;
	}
}
