package com.ecarinfo.auto.controller;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.ecarinfo.auto.base.BaseController;
import com.ecarinfo.auto.po.Article;
import com.ecarinfo.auto.po.ArticleComment;
import com.ecarinfo.auto.po.DictArea;
import com.ecarinfo.auto.po.DictCarBrand;
import com.ecarinfo.auto.po.DictCarSerial;
import com.ecarinfo.auto.po.DictProvince;
import com.ecarinfo.auto.po.MouthAnalyzing;
import com.ecarinfo.auto.po.ViewpointType;
import com.ecarinfo.auto.rm.DictCarBrandRM;
import com.ecarinfo.auto.rm.MouthAnalyzingRM;
import com.ecarinfo.auto.util.CommonCache;
import com.ecarinfo.auto.util.EcExcelUtil;
import com.ecarinfo.auto.util.JFreeChartUtil;
import com.ecarinfo.auto.util.JFreeChartUtil.CarChartInfo;
import com.ecarinfo.auto.util.JFreeChartUtil.KeyVal;
import com.ecarinfo.auto.util.QueryUtil;
import com.ecarinfo.auto.vo.ArticleCommentVo;
import com.ecarinfo.auto.vo.ArticleMouthVO;
import com.ecarinfo.auto.vo.ArticleSimpleListVO;
import com.ecarinfo.auto.vo.ArticleVo;
import com.ecarinfo.auto.vo.ChartCoreDataVO;
import com.ecarinfo.auto.vo.MouthAnalyzingVO;
import com.ecarinfo.auto.vo.MouthData;
import com.ecarinfo.auto.vo.QueryParameter;
import com.ecarinfo.auto.vo.ViewpointCountVO;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.common.utils.PropUtil;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.ralasafe.utils.DtoUtil;

/**
 *用车口碑
 */

@Controller
@RequestMapping("/mouth")
public class MouthController extends BaseController {
	final private static Logger logger = LoggerFactory.getLogger(MouthController.class);
	@Resource
	private CommonCache commonCache;

	@RequestMapping(value = "/index")
	public String Index(ModelMap map, HttpServletRequest request) {
		int defaultBrandId = commonCache.getDefaultCarBrand().getId();
		DictCarSerial mySerial= commonCache.getBrandId2SerialsMap().get(defaultBrandId).get(0);
		//我的
		map.put("mySerial", mySerial);
		map.put("myBrand", commonCache.getBrandMap().get(defaultBrandId));
		//竞品
		List<DictCarBrand> bands= genericService.findList(DictCarBrand.class, new Criteria().eq(DictCarBrandRM.type, 2).orderBy(DictCarBrandRM.id, OrderType.ASC));
		DictCarSerial otherSerial =commonCache.getBrandId2SerialsMap().get( bands.get(0).getId()).get(0);
		map.put("otherSerial", otherSerial);
		map.put("otherBrand", bands.get(0));
		addQuery(map);
		return "mouth/index";
	}

	@RequestMapping(value = "/pie")
	@ResponseBody
	public ViewpointCountVO pie(HttpServletRequest request){
		QueryParameter param = QueryUtil.getQueryParameter(request);
		Integer serialId = param.getSerialId();
		if(serialId==null){
			serialId=0;
		}
		ViewpointCountVO vo  =getCounts(serialId);
		return vo;
	}

	private ViewpointCountVO getCounts(Integer serialId){
		ViewpointCountVO vo  =new ViewpointCountVO();
		MouthData data1 = mouthService.mouthDate(serialId, 1);
		MouthData data2 = mouthService.mouthDate(serialId, 2);
		MouthData data3 = mouthService.mouthDate(serialId, 3);
		long goodCounts =0;
		long middleCounts =0;
		long badCounts = 0;
		if(data1!=null){
			goodCounts = getCountsByAffection(data1);//好评数
		}if(data2!=null){
			middleCounts = getCountsByAffection(data2);//中评数
		}if(data3!=null){
			badCounts = getCountsByAffection(data3);//差评数
		}
		vo.setMiddleCounts(middleCounts);
		vo.setGoodCounts(goodCounts);
		vo.setBadCounts(badCounts);
		return vo;
	}
	/*
	 * 询问列表
	 */
	@RequestMapping(value = "/queryList")
	@ResponseBody
	public ECPage<ArticleMouthVO> queryList(HttpServletRequest request, String veiwpointTypeId,Integer affection,
			Integer cpage, Integer rowsPerPage) {
		QueryParameter param = QueryUtil.getQueryParameter(request);

		Integer brandId = commonCache.getDefaultCarBrand().getId();

		if (param.getSerialIds() == null||param.getSerialIds().length<1) {
			int myserialId = commonCache.getBrandId2SerialsMap().get(brandId).get(0).getId();
			//竞品
			List<DictCarBrand> bands= genericService.findList(DictCarBrand.class, new Criteria().eq(DictCarBrandRM.type, 2).orderBy(DictCarBrandRM.id, OrderType.ASC));
			int  otherserial =commonCache.getBrandId2SerialsMap().get( bands.get(0).getId()).get(0).getId();
			param.setSerialIds(new Integer[]{myserialId});
		}else{
			param.setSerialIds(new Integer[]{param.getSerialIds()[0]});
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
		if(affection==null){
			affection=0;
		}
		if(StringUtils.isEmpty(veiwpointTypeId)){
			veiwpointTypeId="affection";//默认综合数据
		}
		ECPage<ArticleMouthVO> page = this.mouthService.queryViewpointArticleList(param.getSerialIds(), veiwpointTypeId, affection, 
				 param.getProvinceId(),param.getCityId(),DateUtils
				 .dateToString(param.getStartTime(), TimeFormatter.FORMATTER2), DateUtils.dateToString(param.getEndTime(), TimeFormatter.FORMATTER2)
				, cpage == null ? 1 : cpage,
						rowsPerPage == null ? 25 : rowsPerPage);

		return page;
	}

	private void addQuery(ModelMap map) {
		List<DictArea> areas = commonCache.getAreas();
		map.put("areas", areas);
		List<DictProvince> provinces = commonCache.getProvinces();
		List<ViewpointType> viewpointType = commonCache.getViewpointTypeList();
		map.put("viewpointType", viewpointType);
		map.put("provinces", provinces);
	}

	// 观点分类统计
	@RequestMapping(value = "/chart")
	@ResponseBody
	public List<ChartCoreDataVO> chart(HttpServletRequest request,Integer affection) {
		QueryParameter param = QueryUtil.getQueryParameter(request);
		
		if (param.getSerialId()== null) {
			param.setSerialId(0);
		}
		if(affection==null){
			affection=1;
		}
		MouthData data = mouthService.mouthDate(param.getSerialId(), affection);
		List<ChartCoreDataVO> list =null;
		if(data!=null){
			 list = getViewpointGroup(data);
		}
		return list;
	}
	
	//口碑数据的汇总
	@RequestMapping(value = "/mouthdata")
	@ResponseBody
	public MouthData moudata(HttpServletRequest request,Integer serialId){
		QueryParameter param = QueryUtil.getQueryParameter(request);
		if (param.getSerialId()== null) {
			param.setSerialId(0);
		}
		MouthData data = mouthService.mouthDate(param.getSerialId(), 0);
		return data;
	}
	/**
	 * 情感统计
	 */
	
	private long getCountsByAffection(MouthData data){
		long appearance = data.getAppearanceGcounts();//外观
		long attitude = data.getAttitudeGcounts();//服务态度
		long interior = data.getInteriorGcounts();//内饰
		long space = data.getSpaceGcounts();//空间
		long operate = data.getOperateGcounts();//操控
		long power = data.getPowerGcounts();//动力
		long oil = data.getOilGcounts();//油耗
		long comfort = data.getComfortGcounts();//舒适性
		long configure = data.getConfigureGcounts();//配置
		long price = data.getPriceGcounts();//价格
		long quality = data.getQualityGcounts();//质量
		long cost = data.getCostGcounts();//性价比
		long maintenance = data.getMaintenanceGcounts();//维修保养
		long facility = data.getFacilityGcounts();//配套设施
		long stafflevel = data.getStafflevelGcounts();//人员水平
		return (appearance+attitude+interior+space+operate+power+oil+comfort+configure+price+quality+cost+maintenance+facility+stafflevel);
	}
	
	private List<ChartCoreDataVO> getViewpointGroup(MouthData data){
		List<ChartCoreDataVO> list = new ArrayList<ChartCoreDataVO>();
		Map<String ,Long> map = new TreeMap<String ,Long>();
		
		long appearance = data.getAppearanceGcounts();//外观
		long attitude = data.getAttitudeGcounts();//服务态度
		long interior = data.getInteriorGcounts();//内饰
		long space = data.getSpaceGcounts();//空间
		long operate = data.getOperateGcounts();//操控
		long power = data.getPowerGcounts();//动力
		long oil = data.getOilGcounts();//油耗
		long comfort = data.getComfortGcounts();//舒适性
		long configure = data.getConfigureGcounts();//配置
		long price = data.getPriceGcounts();//价格
		long quality = data.getQualityGcounts();//质量
		long cost = data.getCostGcounts();//性价比
		long maintenance = data.getMaintenanceGcounts();//维修保养
		long facility = data.getFacilityGcounts();//配套设施
		long stafflevel = data.getStafflevelGcounts();//人员水平
		
		map.put("外观",appearance);
		map.put("服务态度",attitude);
		map.put("内饰",interior);
		map.put("空间",space);
		map.put("操控",operate);
		map.put("动力",power);
		map.put("油耗",oil);
		map.put("舒适性",comfort);
		map.put("配置",configure);
		map.put("价格",price);
		map.put("质量",quality);
		map.put("性价比",cost);
		map.put("维修保养",maintenance);
		map.put("配套设施",facility);
		map.put("人员水平",stafflevel);
		Map.Entry[] entry= getSortedHashmapByValue(map);//排序
		for(int i =0 ;i<entry.length;i++){//取前10个
			if(i==10){
				break;
			}
			if(((Long) entry[i].getValue()).intValue()>0){
				ChartCoreDataVO vo= new ChartCoreDataVO();
				vo.setName(entry[i].getKey());
				vo.setValue(((Long) entry[i].getValue()).intValue());
				list.add(vo);
			}
			
		}
		return list;
	}
	

	@SuppressWarnings(value = { "unchecked", "rawtypes" })
	public static Map.Entry[] getSortedHashmapByValue(Map h) {  
	       Set set = h.entrySet();  
		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set  
	               .size()]);  
	       Arrays.sort(entries, new Comparator() {  
	           public int compare(Object arg0, Object arg1) {  
	               Long key1 = Long.valueOf(((Map.Entry) arg0).getValue().toString());  
	               Long key2 = Long.valueOf(((Map.Entry) arg1).getValue().toString());  
	               return key2.compareTo(key1);  
	           }  
	       });  
	       return entries;  
	   }  
	//导出
	@RequestMapping(value = "/export")
	public void export(HttpServletRequest request,HttpServletResponse response,Integer myserialId,Integer otherserialId,Integer myAffection,Integer otherAffection,String mySerial,String otherSerial){
		try{
			mySerial = DtoUtil.incode(mySerial);
			otherSerial = DtoUtil.incode(otherSerial);
//			System.out.println("mySerial:"+mySerial+otherSerial);
			MouthData data1 = mouthService.mouthDate(myserialId, myAffection);
			MouthData data2 = mouthService.mouthDate(otherserialId, otherAffection);
			List<ChartCoreDataVO> list1 = getViewpointGroup(data1);
			List<ChartCoreDataVO> list2 = getViewpointGroup(data2);
			List<KeyVal> kvlist1 =new ArrayList<KeyVal>();
			List<KeyVal> kvlist2 =new ArrayList<KeyVal>();
			if(null!=list1&&list1.size()>0){
				for(ChartCoreDataVO vo:list1){
					KeyVal kv = new KeyVal((String)vo.getName(),(Number)vo.getValue());
					kvlist1.add(kv);
				}
			}
			if(null!=list2&&list2.size()>0){
				for(ChartCoreDataVO vo:list2){
					KeyVal kv = new KeyVal((String)vo.getName(),(Number)vo.getValue());
					kvlist2.add(kv);
				}
			}
			ViewpointCountVO vo1  =getCounts(myserialId);
			ViewpointCountVO vo2  =getCounts(otherserialId);
			CarChartInfo carChartInfo1 = new CarChartInfo(mySerial,kvlist1,vo1.getGoodCounts(),vo1.getMiddleCounts(), vo1.getBadCounts(),vo1.getAllCounts());
			CarChartInfo carChartInfo2 = new CarChartInfo(otherSerial,kvlist2,vo2.getGoodCounts(),vo2.getMiddleCounts(), vo2.getBadCounts(),vo2.getAllCounts());
			String fileName = DateUtils.dateToString(new Date(), TimeFormatter.FORMATTER11);
			String baseUrl = PropUtil.getProp("common.properties", "export.url");
			String fullName = baseUrl+fileName+".jpg";
			logger.error("filepath:========="+fullName);
			JFreeChartUtil.export(carChartInfo1, carChartInfo2, myAffection,otherAffection, fullName);
			File file = new File(fullName);
			EcExcelUtil.downloadLocal(response, file);
			}catch(Exception e){
				logger.error("导出出错！",e);
			}
			
		}
	
	/**
	 * 根据id查找文章
	 * @param map
	 * @param aid
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/article")
	@ResponseBody
	public Object article(ModelMap map, Long aid) throws Exception {
		MouthAnalyzing article = genericService.findByPK(MouthAnalyzing.class, aid);
		if(article!=null){
			MouthAnalyzingVO vo = new MouthAnalyzingVO();
			BeanUtils.copyProperties(vo, article);
			if (article.getMediaId() != null) {
				vo.setMediaName(commonDataCache.getMediaMap().get(article.getMediaId()));
			}
			return vo;
		}
		return null;
	}
	/**
	 * 
	 * @param map
	 * @param aid
	 * @param type 用来标识是首页的负面情报跳转过来的还是   产品评价跳转过来的
	 * @return
	 */
	@RequestMapping(value = "/detail/{aid}")
	public String detail(ModelMap map, @PathVariable Long aid) {
		map.put("aid", aid);
		return "/mouth/detail";
	}
	
	
	/**
	 * 根据文章id查找所有相似文章
	 * @param map
	 * @param aid
	 * @param cpage
	 * @param rowsPerPage
	 * @return
	 */
	@RequestMapping(value = "/articles")
	@ResponseBody
	public Object articles(ModelMap map, Long aid, Integer cpage, Integer rowsPerPage) {
		ECPage<ArticleSimpleListVO> page = articleService.getRelatedArticles(cpage == null ? 1 : cpage,
				rowsPerPage == null ? 25 : rowsPerPage, aid.intValue());
		return page;
	}
	
	/**
	 * 根据文章id查找所有评论
	 * @param map
	 * @param aid
	 * @param cpage
	 * @param rowsPerPage
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping(value = "/comments")
	@ResponseBody
	public Object comments(ModelMap map, Long aid, Integer cpage, Integer rowsPerPage) throws Exception {
		ECPage<ArticleComment> page = articleCommentService.getArticleCommentByArticleId(aid, cpage == null ? 1 : cpage,
				rowsPerPage == null ? 25 : rowsPerPage);
		if (page == null) {
			return new ECPage<ArticleComment>();
		}
		ECPage<ArticleCommentVo> pageVo = new ECPage<ArticleCommentVo>();
		org.springframework.beans.BeanUtils.copyProperties(page, pageVo, new String[] { "list" });
		List<ArticleComment> pos = page.getList();
		List<ArticleCommentVo> vos = new ArrayList<ArticleCommentVo>();
		if (!CollectionUtils.isEmpty(pos)) {

			for (ArticleComment po : pos) {
				ArticleCommentVo vo = new ArticleCommentVo();
				BeanUtils.copyProperties(vo, po);
				if (po.getCtime() != null) {
					vo.setShowCtime(DateUtils.dateToString(po.getCtime(), TimeFormatter.FORMATTER1));
				}
				vos.add(vo);
			}
		}
		pageVo.setList(vos);
		return pageVo;
	}
}
