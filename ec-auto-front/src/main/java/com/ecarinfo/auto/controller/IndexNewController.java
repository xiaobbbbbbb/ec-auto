package com.ecarinfo.auto.controller;

import java.util.ArrayList;
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
import com.ecarinfo.auto.front.util.JsonUtils;
import com.ecarinfo.auto.po.DictCarSerial;
import com.ecarinfo.auto.po.EventBrief;
import com.ecarinfo.auto.po.MouthKeyword;
import com.ecarinfo.auto.rm.EventBriefRM;
import com.ecarinfo.auto.rm.MouthKeywordRM;
import com.ecarinfo.auto.service.MouthService;
import com.ecarinfo.auto.service.PositiveService;
import com.ecarinfo.auto.service.TempIndustryService;
import com.ecarinfo.auto.util.CommonCache;
import com.ecarinfo.auto.util.GetDateUtil;
import com.ecarinfo.auto.util.QueryUtil;
import com.ecarinfo.auto.vo.AjaxJson;
import com.ecarinfo.auto.vo.ChartCoreDataVO;
import com.ecarinfo.auto.vo.PositiveViewVO;
import com.ecarinfo.auto.vo.QueryParameter;
import com.ecarinfo.auto.vo.TempIndustryVO;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.paging.ECPage;

@Controller
@RequestMapping("/index_new")
public class IndexNewController extends BaseController {

	@Resource
	private CommonCache commonCache;
	
	@Resource
	private PositiveService positiveService;
	
	@Resource
	private TempIndustryService tempIndustryService;
	
	@Resource
	private MouthService mouthService;
	final private static Logger logger = LoggerFactory.getLogger(IndexNewController.class);

	@RequestMapping(value = "/")
	public String Index(ModelMap map, HttpServletRequest request) {
		getPositiveDatas(map);// 负面信息

		int defaultBrandId = commonCache.getDefaultCarBrand().getId();

		// 默认的厂牌下的车系
		List<DictCarSerial> serials = commonCache.getBrandId2SerialsMap().get(defaultBrandId);
		map.put("serials", serials);
		
		//重点新闻的数量
		Integer carBrandId=commonCache.getDefaultCarBrand().getId();
		Integer[] ids={carBrandId};
		map.put("importantDayCount", getIndustoryImportantDayCount(1, 1, ids)); 
		map.put("importantWeekCount", getIndustoryImportantDayCount(1, 2, ids)); 
		List<TempIndustryVO> list=tempIndustryService.getIndustryArticleList(null, null, null, 1, ids, 1, 5).getList();
		List<TempIndustryVO> temp=new ArrayList<TempIndustryVO>();
		for (TempIndustryVO vo : list) {
			Integer count=vo.getHotCount();
			if(count>=1 && count<=2){
				vo.setStartCount(1);
			}else if(count>=3 && count<=4){
				vo.setStartCount(2);
			}else if(count>=5 && count<=7){
				vo.setStartCount(3);
			}else if(count>=8 && count<=10){
				vo.setStartCount(4);
			}else if(count>=11){
				vo.setStartCount(5);
			}
			temp.add(vo);
		}
		map.put("importantList",temp );  //新闻列表数据
		
		
		Criteria c = new Criteria();
		c.greateThenOrEquals(EventBriefRM.eventType, 1);
        c.setPage(1, 8);
		c.orderBy(EventBriefRM.createTime, OrderType.DESC);
		List<EventBrief> ebList = genericService.findList(EventBrief.class, c);
		map.put("eventList", ebList); // 新闻列表数据

		map.put("mouthNames", JsonUtils.Object2JsonString(mouthService.getMouthKeywords())); // 用户口碑

		return "index_new";
	}
	
	/**
	 * 口碑分析
	 */
	@RequestMapping(value = "/mouthKey")
	@ResponseBody
	public AjaxJson mouthKey(Integer id) {
		AjaxJson ajaxJson = new AjaxJson();
		if (id == null) {
			int defaultBrandId = commonCache.getDefaultCarBrand().getId();
			// 默认的厂牌下的车系
			List<DictCarSerial> serials = commonCache.getBrandId2SerialsMap().get(defaultBrandId);
			id = serials.get(0).getId();
		}
		List<MouthKeyword> list=genericService.findList(MouthKeyword.class,new  Criteria().eq(MouthKeywordRM.serialId, id));
		//String keywords = mouthService.getMouthKeywords(id);
		 ajaxJson.setObj(list);
		return ajaxJson;
	}
	
	/**
	 * 负面信息
	 */
	private void getPositiveDatas(ModelMap map){
		int defaultBrandId = commonCache.getDefaultCarBrand().getId();
		long lowNums = this.positiveService.getPositiveCounts(defaultBrandId,0,1);//一般负面报道数量
		long highNums = this.positiveService.getPositiveCounts(defaultBrandId,0,2);//严重负面报道数量
		map.put("lowNums", lowNums);
		map.put("highNums", highNums);
		ECPage<PositiveViewVO> page = positiveService.getPositives(null, null,defaultBrandId, 0, 0, 0,0, 0,1, 5);
		List<PositiveViewVO> positiveList= page.getList();
		map.put("positiveList", positiveList);
	}
	
	/**
	 * 需求研究
	 */
	@RequestMapping(value = "/question")
	@ResponseBody
	public List<ChartCoreDataVO> chart(HttpServletRequest request, Integer type) {
		//我的厂牌下的数据
		int defaultBrandId = commonCache.getDefaultCarBrand().getId();
		QueryParameter param = QueryUtil.getQueryParameter(request);
		if (param.getSerialId() == null) {
			param.setSerialId(0);
		}
		if (param.getBrandId()== null) {
			param.setBrandId(defaultBrandId);
		}
		List<ChartCoreDataVO> list = questionService.getQuestionChartList(param.getBrandId(),param.getSerialId(),0,type);
		
		return list;
	}

	/**
	 * 需求研究
	 */
	@RequestMapping(value = "/userMouth")
	@ResponseBody
	public List<ChartCoreDataVO> userMouth(HttpServletRequest request, Integer type) {
		QueryParameter param = QueryUtil.getQueryParameter(request);
		int defaultBrandId = commonCache.getDefaultCarBrand().getId();
		if (param.getSerialId() == null) {
			param.setSerialId(0);
		}
		if (param.getBrandId()== null) {
			param.setBrandId(defaultBrandId);
		}
		List<ChartCoreDataVO> list = questionService.getQuestionChartList(param.getBrandId(),param.getSerialId(), 0, type);
		
		return list;
	}
	
	/**
	 * 获取日或周关于重点新闻的数量
	 * @param type
	 * @param date
	 * @param ids
	 * @return
	 */
	public Long getIndustoryImportantDayCount(Integer type,Integer date,Integer[] ids){
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
		return tempIndustryService.getTempIndustryCounts(startTime, endTime, type, ids);
	}
}
