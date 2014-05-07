package com.ecarinfo.auto.controller;

import java.util.ArrayList;
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
import com.ecarinfo.auto.po.DictCity;
import com.ecarinfo.auto.po.DictProvince;
import com.ecarinfo.auto.po.Question;
import com.ecarinfo.auto.util.CommonCache;
import com.ecarinfo.auto.util.QueryUtil;
import com.ecarinfo.auto.vo.AreaCountVO;
import com.ecarinfo.auto.vo.ArticleQuestionVO;
import com.ecarinfo.auto.vo.ChartCoreDataVO;
import com.ecarinfo.auto.vo.QueryParameter;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.persist.paging.ECPage;

/**
 *用车询问
 */

@Controller
@RequestMapping("/question")
public class QuestionController extends BaseController {
	@Resource
	private CommonCache commonCache;

	@RequestMapping(value = "/index")
	public String Index(ModelMap map, HttpServletRequest request) {
		int defaultBrandId = commonCache.getDefaultCarBrand().getId();
		DictCarSerial serial= commonCache.getBrandId2SerialsMap().get(defaultBrandId).get(0);
		map.put("serial", serial);
		map.put("brand", commonCache.getBrandMap().get(defaultBrandId));
		addQuery(map);
		return "question/index";
	}

	/*
	 * 询问列表
	 */
	@RequestMapping(value = "/queryList")
	@ResponseBody
	public ECPage<ArticleQuestionVO> queryList(HttpServletRequest request,Integer questionId,
			Integer cpage, Integer rowsPerPage) {
		QueryParameter param = QueryUtil.getQueryParameter(request);

		Integer brandId = commonCache.getDefaultCarBrand().getId();

		if (param.getSerialId() == null||param.getSerialId()<1) {
			param.setSerialId(commonCache.getBrandId2SerialsMap().get(brandId).get(0).getId());
		}
		if (param.getProvinceId() == null) {
			param.setProvinceId(0);
		}
		if (param.getCityId() == null) {
			param.setCityId(0);
		}
		if (param.getAreadId() == null) {
			param.setAreadId(0);
		}
		if(questionId==null){
			questionId=0;
		}
		ECPage<ArticleQuestionVO> page = this.questionService.queryQuestionList(
				param.getSerialId(), DateUtils
				.dateToString(param.getStartTime(), TimeFormatter.FORMATTER2), DateUtils.dateToString(param.getEndTime(), TimeFormatter.FORMATTER2)
				, questionId, param.getProvinceId(), param.getCityId(), cpage == null ? 1 : cpage,
						rowsPerPage == null ? 25 : rowsPerPage);

		return page;

	}

	private void addQuery(ModelMap map) {
		List<DictArea> areas = commonCache.getAreas();
		map.put("areas", areas);
		List<DictProvince> provinces = commonCache.getProvinces();
		List<Question> questions = commonCache.getQuestions();
		map.put("questions", questions);
		map.put("provinces", provinces);
	}

	/**
	 *  询问类型统计
	 * @param request
	 * @param type 1用车 2买车
	 * @return
	 */
	
	@RequestMapping(value = "/chart")
	@ResponseBody
	public List<ChartCoreDataVO> chart(HttpServletRequest request,Integer type) {
		QueryParameter param = QueryUtil.getQueryParameter(request);
		int defaultBrandId = commonCache.getDefaultCarBrand().getId();
		if (param.getSerialId()== null) {
			param.setSerialId(0);
		}
		if (param.getBrandId()== null) {
			param.setBrandId(defaultBrandId);
		}
		//排名前10个
		List<ChartCoreDataVO> list = questionService.getQuestionChartList(param.getBrandId(),param.getSerialId(), 10,type);
		return list;
	}
	
	/**
	 * 按城市统计询问数量
	 * @param request
	 * @param type 1用车 2买车
	 * @return
	 */
	@RequestMapping(value = "/census")
	@ResponseBody
	public List<AreaCountVO> census(HttpServletRequest request,Integer type) {
		QueryParameter param = QueryUtil.getQueryParameter(request);
		
		if (param.getSerialId()== null) {
			param.setSerialId(0);
		}
		List<AreaCountVO> list = questionService.getAreaQuestionCountsList(param.getSerialId(), 6,type);
		return list;
	}
	
	/**
	 * 所有地区数量统计
	 * @param request
	 * @param type 1用车 2买车
	 * @return
	 */
	@RequestMapping(value = "/allcount")
	@ResponseBody
	public Long allcount(HttpServletRequest request,Integer type) {
		QueryParameter param = QueryUtil.getQueryParameter(request);
		
		if (param.getSerialId()== null) {
			param.setSerialId(0);
		}
		long counts = questionService.allQuestionCounts(param.getSerialId(), type);
		return counts;
	}
	
	/**
	 * 通过省份查询城市列表
	 * @param request
	 * @param provinceId
	 * @return
	 */
	@RequestMapping(value = "/city")
	@ResponseBody
	public List<DictCity> city(HttpServletRequest request) {
		QueryParameter param = QueryUtil.getQueryParameter(request);
		return this.commonCache.getProvinceId2CitysMap().get(param.getProvinceId());
	}
	
	@RequestMapping(value = "/serials")
	@ResponseBody
	public List<DictCarSerial> serials(HttpServletRequest request) {
		QueryParameter param = QueryUtil.getQueryParameter(request);
		return this.commonCache.getBrandId2SerialsMap().get(param.getBrandId());
	}
	
	@RequestMapping(value = "/select")
	public String select(ModelMap map, HttpServletRequest request,Integer type,Integer otherSerialId) {
		QueryParameter param = QueryUtil.getQueryParameter(request);
		List<DictCarBrand> brandList =null;
		if(type!=null&&type==1){
			brandList = new ArrayList<DictCarBrand>();
			brandList.add(commonCache.getDefaultCarBrand());
		}else{
			brandList = commonCache.getBrands();
		}
		List<DictCarSerial> serailList = commonCache.getBrandId2SerialsMap().get(param.getBrandId());
		map.put("serailList", serailList);
		map.put("brandList", brandList);
		map.put("brandId", param.getBrandId());
		map.put("serialId", param.getSerialId());
		if(otherSerialId!=null&&otherSerialId>0){
			map.put("otherSerialId", otherSerialId);
		}
		
		return "question/select";
	}
}
