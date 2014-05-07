package com.ecarinfo.auto.controller;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.auto.base.BaseController;
import com.ecarinfo.auto.po.Article;
import com.ecarinfo.auto.po.ArticleComment;
import com.ecarinfo.auto.po.DictArea;
import com.ecarinfo.auto.po.DictCarBrand;
import com.ecarinfo.auto.po.DictCarSerial;
import com.ecarinfo.auto.po.DictProvince;
import com.ecarinfo.auto.po.Viewpoint;
import com.ecarinfo.auto.rm.DictCarSerialRM;
import com.ecarinfo.auto.util.CommonCache;
import com.ecarinfo.auto.util.QueryUtil;
import com.ecarinfo.auto.vo.ArticleCommentVo;
import com.ecarinfo.auto.vo.ArticleSimpleListVO;
import com.ecarinfo.auto.vo.ArticleVo;
import com.ecarinfo.auto.vo.ChartVO;
import com.ecarinfo.auto.vo.LineChartDataVO;
import com.ecarinfo.auto.vo.QueryParameter;
import com.ecarinfo.auto.vo.ViewpointContrastVO;
import com.ecarinfo.auto.vo.ViewpointVO;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.common.utils.NumberUtils;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.paging.ECPage;

/**
 * 产品评论
 */

@Controller
@RequestMapping("/discuss")
public class ProductDiscussController extends BaseController {
	@Resource
	private CommonCache commonCache;
	
	@RequestMapping(value = "/index")
	public String Index(ModelMap map,HttpServletRequest request) {
//		List<DictCarBrand> brands = commonService.getAllDictCarBrand();
		List<DictCarBrand> brands = commonCache.getBrands();
		List<DictCarBrand> brands_ = new ArrayList<DictCarBrand>();
		for (DictCarBrand brand : brands) {
			if (brands_.size() <= 4) {
				brands_.add(brand);
			}
		}
		map.put("brands", brands_);

		// Session的厂牌ID
		int defaultBrandId = getUser(request).getBrandId();

		addQuery(map, defaultBrandId);

		return "product_discuss";
	}

	@RequestMapping(value = "/list")
	public String list(ModelMap map,HttpServletRequest request) {
		List<DictCarBrand> brands = commonCache.getBrands();
		List<DictCarBrand> brands_ = new ArrayList<DictCarBrand>();
		for (DictCarBrand brand : brands) {
			if (brands_.size() <= 4) {
				brands_.add(brand);
			}
		}
		map.put("brands", brands_);

		// Session的厂牌ID
		int defaultBrandId = getUser(request).getBrandId();

		addQuery(map, defaultBrandId);

		return "product_discuss_list";
	}

	private void addQuery(ModelMap map, Integer brandId) {
		List<DictCarSerial> serials = commonCache.getBrandId2SerialsMap().get(brandId);
		map.put("serials", serials);

		Date startDate = DateUtils.getDateByDay(-7);
		int defaultAffection = 3;

		Integer[] carSerialId = null;
		if (serials.size() <= 3) {
			carSerialId = new Integer[serials.size()];
			for (int i = 0; i < serials.size(); i++) {
				carSerialId[i] = serials.get(i).getId();

			}
		} else {
			carSerialId = new Integer[3];
			for (int i = 0; i < 3; i++) {
				carSerialId[i] = serials.get(i).getId();
			}
		}

		List<ViewpointContrastVO> vos = new ArrayList<ViewpointContrastVO>();
		for (int i = 0; i < 3; i++) {
			ViewpointContrastVO vo = new ViewpointContrastVO();
			DictCarSerial serial=serials.get(i);
			List<ViewpointVO> dtos = viewpointService.getViewpointsBySerialId(new Integer[] { serial.getId() }, defaultAffection, 0, 0, 0, startDate,
					new Date(), 20);
			vo.setViewpoint(getViewpointVO(dtos));
			vo.setSerialName(serial.getName());
			vo.setCarSerialId(serial.getId());
			vos.add(vo);
		}
		map.put("vos", vos);
		List<DictArea> areas = commonCache.getAreas();
		
		map.put("areas", areas);

		List<DictProvince> provinces = commonCache.getProvinces();
		map.put("provinces", provinces);
	}

	// 最近7天评论数
	@RequestMapping(value = "/judge")
	@ResponseBody
	public ChartVO judge(HttpServletRequest request) {
		QueryParameter param = QueryUtil.getQueryParameter(request);

		// 获取Session中默认的用户厂牌
		int defaultBrandId = getUser(request).getBrandId();
		Integer brandId = (param.getBrandId() != null && param.getBrandId() > 0) ? param.getBrandId() : defaultBrandId;
		Integer[] serialIds = param.getSerialIds();

		List<DictCarSerial> serials = null;
		if (serialIds != null && serialIds.length > 0) {
			System.err.println("serialIds" + serialIds.toString());
			serials = genericService.findList(DictCarSerial.class, new Criteria().in(DictCarSerialRM.pk, serialIds));
		} else {
			serials = commonCache.getBrandId2SerialsMap().get(brandId);
			serialIds = new Integer[serials.size()];
			for (int i = 0; i < serials.size(); i++) {
				serialIds[i] = serials.get(i).getId();
			}
		}

		Date startDate = DateUtils.getDateByDay(-6);
		Date endDate = new Date();
		if (param.getStartTime() != null || param.getEndTime() != null) {
			startDate = param.getStartTime();
			endDate = param.getEndTime();
		}

		List<LineChartDataVO> list = articleService.getArticleStatisticsByCarSerialId(serialIds, startDate, endDate, 0, 0, 0, 0);
		
		ChartVO chartVO =new ChartVO();
		chartVO.setTitle(param.getTitle());
		chartVO.setObject(list);
		return chartVO;
	}

	// 传播效力---传播趋势
	@RequestMapping(value = "/spreadTrend")
	@ResponseBody
	public ChartVO spreadTrend(Integer brandId, HttpServletRequest request) {
		Integer[] bid = null;
		if (brandId != null && brandId > 0) {
			bid = new Integer[] { brandId };
		} else {
			List<DictCarBrand> brands = commonCache.getBrands();
			List<DictCarBrand> brands_ = new ArrayList<DictCarBrand>();
			for (DictCarBrand brand : brands) {
				if (brands_.size() <= 5) {
					brands_.add(brand);
				}
			}
			if (brands != null && brands.size() > 0) {
				bid = new Integer[brands.size()];
				for (int i = 0; i < brands.size(); i++) {
					bid[i] = brands.get(i).getId();
				}
			}
		}
		return null;
	}

	@RequestMapping(value = "/viewpointsMore")
	@ResponseBody
	public List<ViewpointContrastVO> viewpointsMore(Integer affection, HttpServletRequest request) {
		QueryParameter param = QueryUtil.getQueryParameter(request);
		Date startDate = DateUtils.getDateByDay(-7);
		Date endDate = new Date();
		if (param.getStartTime() != null || param.getEndTime() != null) {
			startDate = param.getStartTime();
			endDate = param.getEndTime();
		}
		// TODO 获取前三个车系进行比较
		Integer defaultSerialIdId = 1;
		Integer[] carSerialId = null;
		if (param.getSerialIds() != null && param.getSerialIds().length > 3) {
			carSerialId = new Integer[param.getSerialIds().length-3];
			for (int i = 3; i < param.getSerialIds().length; i++) {
				carSerialId[i-3] = param.getSerialIds()[i];
			}
		} else {
			carSerialId = new Integer[] { defaultSerialIdId };
		}

		List<DictCarSerial> serials = genericService.findList(DictCarSerial.class, new Criteria().in(DictCarSerialRM.pk, carSerialId));
		List<ViewpointContrastVO> vos = new ArrayList<ViewpointContrastVO>();
		for (DictCarSerial serial : serials) {
			ViewpointContrastVO vo = new ViewpointContrastVO();
			List<ViewpointVO> dtos = viewpointService.getViewpointsBySerialId(new Integer[] { serial.getId() }, affection, 0, 0, 0, startDate, endDate, 20);
			vo.setViewpoint(getViewpointVO(dtos));
			vo.setSerialName(serial.getName());
			vo.setCarSerialId(serial.getId());
			vos.add(vo);
		}
		return vos;
	}
	
	@RequestMapping(value = "/viewpoints")
	@ResponseBody
	public List<ViewpointContrastVO> viewpoints(Integer affection, HttpServletRequest request) {
		QueryParameter param = QueryUtil.getQueryParameter(request);
		Date startDate = DateUtils.getDateByDay(-7);
		Date endDate = new Date();
		if (param.getStartTime() != null || param.getEndTime() != null) {
			startDate = param.getStartTime();
			endDate = param.getEndTime();
		}
		// TODO 获取前三个车系进行比较
		Integer defaultSerialIdId = 1;
		Integer[] carSerialId = null;
		if (param.getSerialIds() != null && param.getSerialIds().length > 0) {
			if (param.getSerialIds().length <= 3) {
				carSerialId = new Integer[param.getSerialIds().length];
				for (int i = 0; i < param.getSerialIds().length; i++) {
					carSerialId[i] = param.getSerialIds()[i];
				}
			} else {
				carSerialId = new Integer[3];
				for (int i = 0; i < 3; i++) {
					carSerialId[i] = param.getSerialIds()[i];
				}
			}
		} else {
			carSerialId = new Integer[] { defaultSerialIdId };
		}

		List<DictCarSerial> serials = genericService.findList(DictCarSerial.class, new Criteria().in(DictCarSerialRM.pk, carSerialId));
		List<ViewpointContrastVO> vos = new ArrayList<ViewpointContrastVO>();
		for (DictCarSerial serial : serials) {
			ViewpointContrastVO vo = new ViewpointContrastVO();
			List<ViewpointVO> dtos = viewpointService.getViewpointsBySerialId(new Integer[] { serial.getId() }, affection, 0, 0, 0, startDate, endDate, 20);
			vo.setViewpoint(getViewpointVO(dtos));
			vo.setSerialName(serial.getName());
			vo.setCarSerialId(serial.getId());
			vos.add(vo);
		}
		return vos;
	}
	

	// 求占比
	private List<ViewpointVO> getViewpointVO(List<ViewpointVO> dtos) {
		int count = 0;
		for (ViewpointVO dto : dtos) {
			count += (dto.getArticleNums() != null && dto.getArticleNums() > 0) ? dto.getArticleNums() : 0;
		}
		System.err.println("count" + count);
		List<ViewpointVO> newDto = new ArrayList<ViewpointVO>();
		for (ViewpointVO dto : dtos) {
			Double rate = 0D;
			System.err.println("dd" + dto.getArticleNums());
			if (dto.getArticleNums() != null && dto.getArticleNums() > 0 && count > 0) {
				System.err.println(dto.getArticleNums() / count);
				rate = (Double.valueOf(dto.getArticleNums()) / count);
			}
			DecimalFormat df = new DecimalFormat("##%"); // ##.00% 百分比格式，后面不足2位的用0补齐
			dto.setRate(df.format(rate));
			newDto.add(dto);
		}
		return newDto;
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
		Article article = genericService.findByPK(Article.class, aid);
		ArticleVo vo = new ArticleVo();
		BeanUtils.copyProperties(vo, article);
		if (article.getMediaId() != null) {
			vo.setMediaName(commonDataCache.getMediaMap().get(article.getMediaId()));
		}
		if (article.getArticleCtime() != null) {
			vo.setShowArticleCtime(DateUtils.dateToString(article.getArticleCtime(), TimeFormatter.FORMATTER1));
		}
		return vo;
	}

	/**
	 * 
	 * @param map
	 * @param aid
	 * @param type 用来标识是首页的负面情报跳转过来的还是   产品评价跳转过来的
	 * @return
	 */
	@RequestMapping(value = "/detail/{aid}")
	public String detail(ModelMap map, @PathVariable Long aid,Integer type) {
		map.put("aid", aid);
		map.put("type", type);
		return "/discuss/detail";
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

	/**
	 * 查询
	 * @param request
	 * @param map
	 * @param serialIds
	 * @param startTime
	 * @param endTime
	 * @param affection 好、中、差
	 * @param desContent
	 * @param offset
	 * @param limit
	 * @return
	 */
	@RequestMapping(value = "/query")
	@ResponseBody
	public Object query(Integer[] serialIds, String startTime, String endTime, Integer affection, Integer desContent,
			Integer offset, Integer limit,Integer areaId,Integer provinceId) {
		Date sDate = null;
		Date eDate = null;
		if (org.springframework.util.StringUtils.hasText(startTime) && startTime.length() < 3) {
			int type = Integer.parseInt(startTime.trim());
			Date today = DateUtils.getToday(0, 0, 0).toDate();
			switch (type) {
			case -1:
				sDate = DateUtils.plusDays(today, -1);
				eDate = sDate;//DateUtils.plusDays(today, 0);
				break;
			case 1:
				sDate = DateUtils.plusDays(today, 0);
				eDate = sDate;//DateUtils.plusDays(today, 1);
				break;
			case 7:
				sDate = DateUtils.plusDays(today, -6);
				eDate = DateUtils.plusDays(today, 1);
				break;
			case 15:
				sDate = DateUtils.plusDays(today, -14);
				eDate = DateUtils.plusDays(today, 1);
				break;
			case 30:
				sDate = DateUtils.plusDays(today, -29);
				eDate = DateUtils.plusDays(today, 1);
				break;
			}
			startTime = DateUtils.dateToString(sDate, TimeFormatter.FORMATTER2);
			endTime = DateUtils.dateToString(eDate, TimeFormatter.FORMATTER2);
		}
	
		ECPage<ArticleSimpleListVO> page = articleService.queryAegativeArticles(serialIds, areaId == null ? 0 : areaId, provinceId == null ? 0 :provinceId, startTime, endTime, affection, 0, offset == null ? 1 : offset,
				limit == null ? 25 : limit);
		return page;
	}
	
	
	 /**
	  * 产品评价-由评论数点击进入-观点列表界面
	  * @param map
	  * @param request
	  * @param serialName 产品名称 
	  * @param viewPointName 观点名称
	  * @param viewPointId  观点id
	  * @param carSerialId   产品id
	  * @param viewPointAffection
	  * @return
	  */
	@RequestMapping(value = "/discuss_list")
	public String discuss_list(ModelMap map,Integer viewPointId,
			Integer carSerialId,Integer viewPointAffection,String startTime,String endTime,Integer time) {
		map.put("viewPointId", viewPointId);
		map.put("carSerialId", carSerialId);
		map.put("viewPointAffection", viewPointAffection);
//		Viewpoint vp=genericService.findByPK(Viewpoint.class, viewPointId);
		Viewpoint vp=commonCache.getViewpointsMap().get(viewPointId);
		map.put("viewPointName",  vp!=null?vp.getName():"");
		DictCarSerial carSerial=genericService.findByPK(DictCarSerial.class, carSerialId);
		map.put("serialName",carSerial!=null?carSerial.getName():"");
		Date sDate = DateUtils.getDateByDay(-7);
		Date eDate = DateUtils.getDateByDay(1);
		if (startTime != null && endTime != null) {
			sDate = DateUtils.stringToDate(startTime, TimeFormatter.FORMATTER2);
			eDate = DateUtils.stringToDate(endTime, TimeFormatter.FORMATTER2);
			eDate = DateUtils.plusDays(eDate, 1);
		}else{
			Date today = DateUtils.getToday(0, 0, 0).toDate();
			switch (time) {
			case -1:  //昨天
				sDate = DateUtils.plusDays(today, -1);
				eDate = sDate;
				break;
			case 0:  //今天
				sDate = DateUtils.plusDays(today, 0);
				eDate = sDate;
				break;
			case -7:
				sDate = DateUtils.plusDays(today, -6);
				eDate = DateUtils.plusDays(today, 1);
				break;
			case -15:
				sDate = DateUtils.plusDays(today, -14);
				eDate = DateUtils.plusDays(today, 1);
				break;
			case -30:
				sDate = DateUtils.plusDays(today, -29);
				eDate = DateUtils.plusDays(today, 1);
				break;
			}
		}
		map.put("startDate", DateUtils.dateToString(sDate, TimeFormatter.FORMATTER2));
		map.put("endDate", DateUtils.dateToString(eDate, TimeFormatter.FORMATTER2));
		return "discuss_list";
	}
	
	/**
	 * 产品评价-由评论数点击进入-观点列表数据
	 * @param viewPointId
	 * @param carSerialId
	 * @param viewPointAffection
	 * @param startTime
	 * @param endTime
	 * @param offset
	 * @return
	 */
	@RequestMapping(value = "/queryDiscussList")
	@ResponseBody
	public Object queryDiscussList(Integer viewPointId,
			Integer carSerialId,Integer viewPointAffection,String startTime,String endTime,Integer offset) {
		Date sDate = DateUtils.stringToDate(startTime,TimeFormatter.FORMATTER2);
		Date eDate = DateUtils.stringToDate(endTime,TimeFormatter.FORMATTER2);
		ECPage<ArticleSimpleListVO> page = articleService.getArticlesByViewpointAndCarSerialId(viewPointId, carSerialId, viewPointAffection, sDate, eDate,offset == null ? 1 : offset,
				25);
		return page;
	}
}
