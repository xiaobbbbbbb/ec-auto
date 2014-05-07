package com.ecarinfo.auto.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.auto.base.BaseController;
import com.ecarinfo.auto.po.DictArea;
import com.ecarinfo.auto.po.DictCarSerial;
import com.ecarinfo.auto.po.DictProvince;
import com.ecarinfo.auto.po.ViewpointType;
import com.ecarinfo.auto.service.PositiveService;
import com.ecarinfo.auto.util.DtoUtil;
import com.ecarinfo.auto.util.QueryUtil;
import com.ecarinfo.auto.vo.ArticleSimpleListVO;
import com.ecarinfo.auto.vo.ChartCoreDataVO;
import com.ecarinfo.auto.vo.PositiveVO;
import com.ecarinfo.auto.vo.PositiveViewVO;
import com.ecarinfo.auto.vo.QueryParameter;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.persist.paging.ECPage;

/**
 *负面信息
 */

@Controller
@RequestMapping("/positive")
public class PositiveController extends BaseController {
	final private static Logger logger = LoggerFactory.getLogger(PositiveController.class);
	@Resource
	PositiveService positiveService;
	
	@RequestMapping(value = "/index")
	public String Index(ModelMap map, HttpServletRequest request) {
		int defaultBrandId = commonCache.getDefaultCarBrand().getId();
		DictCarSerial serial= commonCache.getBrandId2SerialsMap().get(defaultBrandId).get(0);
		List<DictCarSerial> serials = commonCache.getBrandId2SerialsMap().get(defaultBrandId);
		map.put("serial", serial);
		map.put("serials", serials);
		map.put("brand", commonCache.getBrandMap().get(defaultBrandId));
		addQuery(map);
		return "positive/index";
	}

	/*
	 * top数据
	 */
	@RequestMapping(value = "/getData")
	@ResponseBody
	public PositiveVO getPositiveNums(HttpServletRequest request){
		QueryParameter param = QueryUtil.getQueryParameter(request);
		int defaultBrandId = commonCache.getDefaultCarBrand().getId();
		int serialId = 0;
		if(null!=param.getSerialId()){
			serialId = param.getSerialId();
		}
		PositiveVO dto = new PositiveVO();
		long allNums = this.positiveService.getPositiveCounts(defaultBrandId,serialId,0);//所有负面报道数量
		long lowNums = this.positiveService.getPositiveCounts(defaultBrandId,serialId,1);//一般负面报道数量
		long highNums = this.positiveService.getPositiveCounts(defaultBrandId,serialId,2);//严重负面报道数量
		//按类别统计的数量
		List<ChartCoreDataVO> dataList = this.positiveService.getPositiveCountsByType(defaultBrandId,serialId);
		if(dataList!=null&&dataList.size()>0){
			for(ChartCoreDataVO vo :dataList){
				if((Integer)vo.name==1){
					dto.setChanping(vo.getValue());
				}if((Integer)vo.name==2){
					dto.setFuwu(vo.getValue());
				}if((Integer)vo.name==3){
					dto.setQita(vo.getValue());
				}
			}
		}
		dto.setAllNums(allNums);
		dto.setHighNums(highNums);
		dto.setLowNums(lowNums);
		dto.setList(dataList);
		return dto;
	}
	
	/*
	 * 最近15天趋势图
	 */
	@RequestMapping(value = "/chart")
	@ResponseBody 
	public List<ChartCoreDataVO> chart(HttpServletRequest request){
		QueryParameter param = QueryUtil.getQueryParameter(request);
		int defaultBrandId = commonCache.getDefaultCarBrand().getId();
		int serialId = 0;
		if(null!=param.getSerialId()){
			serialId = param.getSerialId();
		}
		Date startDate = DateUtils.getDateByDay(-14);
		Date endDate = new Date();
		List<ChartCoreDataVO>  list = this.positiveService.getChartVO(defaultBrandId,serialId, DateUtils.dateToString(startDate, TimeFormatter.FORMATTER2),
				DateUtils.dateToString(endDate, TimeFormatter.FORMATTER2));
		return list;
	}
	
	private void addQuery(ModelMap map) {
		List<DictArea> areas = commonCache.getAreas();
		map.put("areas", areas);
		List<DictProvince> provinces = commonCache.getProvinces();
		List<ViewpointType> viewpointType = commonCache.getViewpointTypeList();
		map.put("viewpointType", viewpointType);
		map.put("provinces", provinces);
	}
	
	/*
	 * 询问列表
	 */
	@RequestMapping(value = "/queryList")
	@ResponseBody
	public ECPage<PositiveViewVO> queryList(HttpServletRequest request, 
			Integer serialId, Integer grade, Integer provinceId, Integer viewpointTypeId,Integer cpage, Integer pageSize) {
		QueryParameter param = QueryUtil.getQueryParameter(request);
		int defaultBrandId = commonCache.getDefaultCarBrand().getId();
		if (param.getProvinceId() == null) {
			param.setProvinceId(0);
		}
		if (param.getCityId() == null) {
			param.setCityId(0);
		}
		ECPage<PositiveViewVO> page = positiveService.getPositives(DateUtils.dateToString(param.getStartTime(), TimeFormatter.FORMATTER2), DateUtils.dateToString(param.getEndTime(), TimeFormatter.FORMATTER2), 
				defaultBrandId,serialId, grade, provinceId,param.getCityId(), viewpointTypeId,cpage, 25);
		return page;
	}
	
	/**
	 * 二级页面
	 */
	@RequestMapping(value = "/second")
	public String second(ModelMap map,HttpServletRequest request){
		QueryParameter param = QueryUtil.getQueryParameter(request);
		if (param.getSerialId() == null) {
			param.setSerialId(0);
		}
		map.put("grade", param.getGrade());
		map.put("serialId",param.getSerialId());
		return "positive/grade_list";

	}
	
	/**
	 * 二级页面
	 */
	@RequestMapping(value = "/secondList")
	@ResponseBody
	public ECPage<ArticleSimpleListVO> secondList(HttpServletRequest request,String title, Integer cpage, Integer pageSize) {
		QueryParameter param = QueryUtil.getQueryParameter(request);
		try{
			int defaultBrandId = commonCache.getDefaultCarBrand().getId();
			if(param.getBrandId()==null){
				param.setBrandId(defaultBrandId);
			}
			if (param.getSerialId() == null) {
				param.setSerialId(0);
			}
			if(title!=null){
				title=DtoUtil.incode(title);
			}
			ECPage<ArticleSimpleListVO> page = positiveService.queryAegativeArticles(param.getBrandId(),param.getSerialId(), param.getGrade(),title, cpage == null ? 1 : cpage, pageSize == null ? 25 : pageSize);
			return page;
		}catch(Exception e ){
			logger.error("查询列表出错",e);
		}
		return null;
	}
}
