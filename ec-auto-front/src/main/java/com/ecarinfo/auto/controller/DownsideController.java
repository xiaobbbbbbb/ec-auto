package com.ecarinfo.auto.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.auto.base.BaseController;
import com.ecarinfo.auto.po.DictArea;
import com.ecarinfo.auto.po.DictCarBrand;
import com.ecarinfo.auto.po.DictProvince;
import com.ecarinfo.auto.util.CommonCache;
import com.ecarinfo.auto.util.QueryUtil;
import com.ecarinfo.auto.vo.ArticleSimpleListVO;
import com.ecarinfo.auto.vo.ChartVO;
import com.ecarinfo.auto.vo.LineChartDataVO;
import com.ecarinfo.auto.vo.LineChartDataVO;
import com.ecarinfo.auto.vo.NegativeVO;
import com.ecarinfo.auto.vo.QueryParameter;
import com.ecarinfo.auto.vo.SeriesVO;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.Paginable;
import com.ecarinfo.persist.paging.PagingManager;

/**
 * 负面情报
 */

@Controller
@RequestMapping("/downside")
public class DownsideController extends BaseController {
	@Resource
	private CommonCache commonCache;

	@RequestMapping(value = "/index")
	public String Index(ModelMap map, HttpServletRequest request) {
		List<DictCarBrand> brands = commonCache.getBrands();
		map.put("brands", brands);
		int defaultBrandId = commonCache.getDefaultCarBrand().getId();
		map.put("brand", commonCache.getBrandMap().get(defaultBrandId));
		addQuery(map);
		return "downside";
	}

	/*
	 * 负面情报列表
	 */
	@RequestMapping(value = "/list")
	public String list(ModelMap map, HttpServletRequest request, Integer brandId, String queryDate, Integer grade) {

		List<DictCarBrand> brands = commonCache.getBrands();
		map.put("brands", brands);
		map.put("queryDate", queryDate);
		map.put("grade", grade);
		if (brandId == null) {
			brandId = commonCache.getDefaultCarBrand().getId();
		}

		// 默认的品牌名称 改为从SESSION中获取 ;缓存
		DictCarBrand brand = commonCache.getBrandMap().get(brandId);
		map.put("brand", brand);
		addQuery(map);
		return "downside_list";
	}

	/*
	 * 负面情报列表
	 */
	@RequestMapping(value = "/queryList")
	@ResponseBody
	public ECPage<ArticleSimpleListVO> queryList(HttpServletRequest request, Integer des_content, Integer cpage, Integer rowsPerPage) {
		QueryParameter param = QueryUtil.getQueryParameter(request);

		Integer brandId = commonCache.getDefaultCarBrand().getId();

		if (param.getBrandIds() == null) {
			Integer[] brandIds = new Integer[1];
			brandIds[0] = brandId;
			param.setBrandIds(brandIds);
		}
		if (param.getGrade() == null) {
			param.setGrade(0);
		}
		if (param.getProvinceId() == null) {
			param.setProvinceId(0);
		}
		if (param.getAreadId() == null) {
			param.setAreadId(0);
		}

		Date startDate = DateUtils.getDateByDay(-7);
		Date endDate = DateUtils.getDateByDay(1);

		if (param.getStartTime() != null || param.getEndTime() != null) {
			startDate = param.getStartTime();
			endDate = param.getEndTime();
		}
		ECPage<ArticleSimpleListVO> page = this.articleService.queryAegativeArticles(param.getBrandIds(),
				DateUtils.dateToString(startDate, TimeFormatter.FORMATTER2),

				DateUtils.dateToString(endDate, TimeFormatter.FORMATTER2), param.getGrade(), des_content, param.getAreadId(), param.getProvinceId(), 0,
				cpage == null ? 1 : cpage, rowsPerPage == null ? 25 : rowsPerPage);

		return page;

	}

	private void addQuery(ModelMap map) {
		List<DictArea> areas = commonCache.getAreas();
		map.put("areas", areas);
		List<DictProvince> provinces = commonCache.getProvinces();
		map.put("provinces", provinces);
	}

	// 负面情报分析
	@RequestMapping(value = "/anChart")
	@ResponseBody
	public ChartVO downside(HttpServletRequest request) {
		QueryParameter param = QueryUtil.getQueryParameter(request);

		int defaultBrandId =  commonCache.getDefaultCarBrand().getId();
		if (param.getBrandIds() == null) {
			Integer[] brandIds = new Integer[1];
			brandIds[0] = defaultBrandId;
			param.setBrandIds(brandIds);
		}
		if (param.getGrade() == null) {
			param.setGrade(0);
		}
		if (param.getProvinceId() == null) {
			param.setProvinceId(0);
		}
		if (param.getAreadId() == null) {
			param.setAreadId(0);
		}
		if (param.getStartTime() == null && param.getEndTime() == null) {
			param.setStartTime(DateUtils.getDateByDay(-6));
			param.setEndTime(new Date());
		}
		List<LineChartDataVO> list = articleService.getArticleStatisticsByCarBrandId(param.getBrandIds(), param.getGrade(), param.getAreadId(),
				param.getProvinceId(), param.getStartTime(), param.getEndTime());

		ChartVO chartVO = new ChartVO();
		chartVO.setTitle(param.getTitle());
		chartVO.setObject(list);
		return chartVO;
	}

	@RequestMapping(value = "/query")
	@ResponseBody
	public NegativeVO query(HttpServletRequest request, Integer cpage, Integer rowsPerPage) {
		final QueryParameter param = QueryUtil.getQueryParameter(request);

		int defaultBrandId = commonCache.getDefaultCarBrand().getId();

		if (param.getBrandIds() == null) {
			Integer[] brandIds = new Integer[1];
			brandIds[0] = defaultBrandId;
			param.setBrandIds(brandIds);
		}

		if (param.getAreadId() == null) {
			param.setAreadId(0);
		}
		if (param.getProvinceId() == null) {
			param.setProvinceId(0);
		}
		if (param.getGrade() == null) {
			param.setGrade(0);
		}

		Date startDate = DateUtils.getDateByDay(-7);
		Date endDate = DateUtils.getDateByDay(1);
		if (param.getStartTime() != null || param.getEndTime() != null) {
			startDate = param.getStartTime();
			endDate = param.getEndTime();
		}
		final NegativeVO negativeVO = new NegativeVO();
		ECPage<SeriesVO> page = PagingManager.list(new Paginable<SeriesVO>(cpage == null ? 1 : cpage, rowsPerPage == null ? 25 : rowsPerPage) {

			@Override
			public List<SeriesVO> findList() {
				List<Map<String, Object>> volist = articleService.getAegativeArticleStatisticsByCarbrandId(param.getBrandIds(), param.getGrade(),
						param.getAreadId(), param.getProvinceId(), 0, DateUtils.dateToString(param.getStartTime(), TimeFormatter.FORMATTER2),
						DateUtils.dateToString(param.getEndTime(), TimeFormatter.FORMATTER2), this.getOffset(), this.getRowsPerPage());
				List<SeriesVO> series = new ArrayList<SeriesVO>();
				for (Map<String, Object> entry : volist) {
					SeriesVO serie = new SeriesVO();
					Map<String, Object> maplist = sort(entry);
					Set<String> keySet = maplist.keySet();
					Iterator<String> iter = keySet.iterator();
					while (iter.hasNext()) {
						String key = iter.next();
					}
					serie.setData(StringUtils.join(maplist.values(), ","));
					negativeVO.setData(StringUtils.join(maplist.keySet(), ","));
					series.add(serie);
				}
				return series;
			}

			@Override
			public Long count() {
				Long c = articleService.getAegativeArticleStatisticsCountsByCarbrandId(param.getBrandIds(), param.getGrade(), param.getAreadId(),
						param.getProvinceId(), 0, DateUtils.dateToString(param.getStartTime(), TimeFormatter.FORMATTER2),
						DateUtils.dateToString(param.getEndTime(), TimeFormatter.FORMATTER2));
				return c == null ? 0 : c;
			}
		});
		negativeVO.setPage(page);
		return negativeVO;
	}

	public static Map sort(Map map) {
		Map<Object, Object> mapVK = new TreeMap<Object, Object>(new Comparator<Object>() {
			public int compare(Object obj1, Object obj2) {
				String v1 = (String) obj1;
				String v2 = (String) obj2;
				int s = v1.compareTo(v2);
				return s;
			}
		});
		Set col = map.keySet();
		Iterator<Object> iter = col.iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			mapVK.put(key, map.get(key));
		}
		return mapVK;
	}
}
