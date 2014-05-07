package com.ecarinfo.auto.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.auto.base.BaseController;
import com.ecarinfo.auto.po.BaiduHotRank;
import com.ecarinfo.auto.po.DictCarBrand;
import com.ecarinfo.auto.po.DictCarSerial;
import com.ecarinfo.auto.po.IndustryArticle;
import com.ecarinfo.auto.po.UserInfo;
import com.ecarinfo.auto.rm.BaiduHotRankRM;
import com.ecarinfo.auto.rm.DictCarSerialRM;
import com.ecarinfo.auto.rm.UserInfoRM;
import com.ecarinfo.auto.util.CommonCache;
import com.ecarinfo.auto.util.MD5Utils;
import com.ecarinfo.auto.util.QueryUtil;
import com.ecarinfo.auto.vo.AjaxJson;
import com.ecarinfo.auto.vo.ArticleSimpleListVO;
import com.ecarinfo.auto.vo.ChartCoreDataVO;
import com.ecarinfo.auto.vo.ChartVO;
import com.ecarinfo.auto.vo.ColumnChartVO;
import com.ecarinfo.auto.vo.LineChartDataVO;
import com.ecarinfo.auto.vo.PieChartVO;
import com.ecarinfo.auto.vo.QueryParameter;
import com.ecarinfo.auto.vo.ViewpointVO;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.ralasafe.utils.EcUtil;

@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {

	@Resource
	private CommonCache commonCache;

	final private static Logger logger = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping(value = "/")
	public String Index(ModelMap map, HttpServletRequest request) {
		// List<DictCarBrand> brands = commonService.getAllDictCarBrand();
		List<DictCarBrand> brands = commonCache.getBrands();
		map.put("brands", brands);
		int defaultBrandId = commonCache.getDefaultCarBrand().getId();

		// 负面情报
		List<ArticleSimpleListVO> aegativeArticles = articleService.getAegativeArticlesByCarBrandId(defaultBrandId, 9);
		map.put("aegativeArticles", aegativeArticles);

		// 行业动态
		List<IndustryArticle> industryArticles = industryService.getLastArticlesByCarBrandId(7);
		map.put("industryArticles", industryArticles);

		// 默认的厂牌下的车系
		List<DictCarSerial> serials = commonCache.getBrandId2SerialsMap().get(defaultBrandId);
		map.put("serials", serials);

		return "index";
	}

	// 负面情报列表
	@RequestMapping(value = "/aegativeArticles")
	@ResponseBody
	public List<ArticleSimpleListVO> aegativeArticles(Integer brandId, HttpServletRequest request) {
		int defaultBrandId = commonCache.getDefaultCarBrand().getId();
		if (brandId != null && brandId > 0)
			defaultBrandId = brandId;
		List<ArticleSimpleListVO> aegativeArticles = articleService.getAegativeArticlesByCarBrandId(defaultBrandId, 9);
		return aegativeArticles;
	}

	// 获取车系字典信息
	@RequestMapping(value = "/getSerials")
	@ResponseBody
	public List<DictCarSerial> getSerials(Integer brandId) {
		return commonService.getAllDictCarSerialByCarBrandId(brandId);
	}

	// 最近7天评论数
	@RequestMapping(value = "/judge")
	@ResponseBody
	public List<LineChartDataVO> judge(Integer brandId, HttpServletRequest request) {
		int defaultBrandId = commonCache.getDefaultCarBrand().getId();
		if (brandId == null || brandId < 1) {
			brandId = defaultBrandId;
		}
		logger.debug("car brand id:" + defaultBrandId);
		Date startDate = DateUtils.getDateByDay(-7);
		List<LineChartDataVO> list = articleService.getArticleStatisticsByCarBrandId(brandId, startDate, new Date());
		return list;
	}

	// 最近7天统计
	@RequestMapping(value = "/judgePie")
	@ResponseBody
	public Set<PieChartVO> judgePie(Integer brandId, Integer serialId, Integer affection, HttpServletRequest request) {
		if (brandId != null && brandId > 0) {
			DictCarSerial serial = this.genericService.findOne(DictCarSerial.class, new Criteria().eq(DictCarSerialRM.brandId, brandId));
			if (serial != null)
				serialId = serial.getId();
		}
		int defaultBrandId = commonCache.getDefaultCarBrand().getId();
		Integer defaultSerialIdId = commonCache.getSerialMap().get(defaultBrandId).getId();

		if (serialId == null || serialId < 1) {
			serialId = defaultSerialIdId;
		}
		logger.debug("judgePie serailId=" + serialId);

		if (affection == null || affection < 1)
			affection = 3;

		Date startDate = DateUtils.getDateByDay(-7);
		List<ViewpointVO> dtos = viewpointService.getViewpointsBySerialId(new Integer[] { serialId }, affection, 0, 0, 0, startDate, new Date(), 10);
		Set<PieChartVO> vos = new HashSet<PieChartVO>();
		for (ViewpointVO dto : dtos) {
			PieChartVO vo = new PieChartVO();
			vo.setChartName(dto.getName());
			vo.setChartValue(dto.getArticleNums());
			vos.add(vo);
		}
		return vos;
	}

	// 最近7天负面情报趋势报表
	@RequestMapping(value = "/downside")
	@ResponseBody
	public List<LineChartDataVO> downside(Integer brandId, HttpServletRequest request) {
		int defaultBrandId = commonCache.getDefaultCarBrand().getId();
		if (brandId == null || brandId < 1) {
			brandId = defaultBrandId;
		}
		Date startDate = DateUtils.getDateByDay(-7);
		List<LineChartDataVO> list = articleService.getAegativeArticleStatisticsByCarbrandId(brandId, startDate, new Date());

		return list;
	}

	// 传播效力模块 排行
	@RequestMapping(value = "/statistics")
	@ResponseBody
	public ChartVO statistics(HttpServletRequest request) {
		QueryParameter param = QueryUtil.getQueryParameter(request);

		// Session的厂牌ID
		int defaultBrandId = commonCache.getDefaultCarBrand().getId();

		if (param.getBrandIds() == null) {
			Integer[] brandIds = new Integer[1];
			brandIds[0] = defaultBrandId;
			param.setBrandIds(brandIds);
		}
		Date startDate = DateUtils.getDateByDay(-6);
		Date endDate = new Date();
		List<ChartCoreDataVO> rlist = null;
		if (param.getStartTime() != null || param.getEndTime() != null) {
			startDate = param.getStartTime();
			endDate = param.getEndTime();
			rlist = articleService.getArticleStatistics(param.getBrandIds(), startDate, endDate);
		} else {
			rlist = articleService.getArticleStatistics(null, startDate, endDate);
		}
		ChartVO chartVO = new ChartVO();
		chartVO.setTitle(param.getTitle());
		if (null != rlist && rlist.size() > 0) {
			Set<PieChartVO> vos = new HashSet<PieChartVO>();
			for (ChartCoreDataVO chartCoreDataVO : rlist) {
				PieChartVO pieChartVO = new PieChartVO();
				pieChartVO.setChartName((String) chartCoreDataVO.getName());
				pieChartVO.setChartValue(chartCoreDataVO.getValue());
				vos.add(pieChartVO);
			}
			chartVO.setObject(vos);
		}
		return chartVO;
	}

	// 媒体传播占比
	@RequestMapping(value = "/statisticsRanking")
	@ResponseBody
	public ChartVO statisticsRanking(HttpServletRequest request) {
		int defaultBrandId = commonCache.getDefaultCarBrand().getId();
		QueryParameter param = QueryUtil.getQueryParameter(request);

		if (param.getBrandIds() == null) {
			Integer[] brandIds = new Integer[] { defaultBrandId };
			param.setBrandIds(brandIds);
		}
		Date startDate = DateUtils.getDateByDay(-6);
		Date endDate = new Date();
		Map<String, Integer> maps = new TreeMap<String, Integer>();

		List<ChartCoreDataVO> rlist = null;
		if (param.getStartTime() != null || param.getEndTime() != null) {
			startDate = param.getStartTime();
			endDate = param.getEndTime();
			rlist = articleService.getArticleStatisticsRankingByMediaType(param.getBrandIds(), startDate, endDate);
		} else {
			rlist = articleService.getArticleStatisticsRankingByMediaType(null, startDate, endDate);
		}
		ChartVO chartVO = new ChartVO();
		chartVO.setTitle(param.getTitle());

		if (null != rlist && rlist.size() > 0) {
			List<ColumnChartVO> vos = new ArrayList<ColumnChartVO>();
			for (ChartCoreDataVO chartCoreDataVO : rlist) {
				ColumnChartVO columnChartVO = new ColumnChartVO();
				columnChartVO.setCategories((String) chartCoreDataVO.getName());
				columnChartVO.setData("" + chartCoreDataVO.getValue());
				vos.add(columnChartVO);
			}
			chartVO.setObject(vos);
		}
		return chartVO;
	}

	@RequestMapping(value = "/login")
	public String login() {
		return "/login";
	}

	@RequestMapping(value = "/ask")
	public String ask() {
		return "/ask/use_list";
	}

	@RequestMapping(value = "/dologin")
	@ResponseBody
	public AjaxJson doLogin(String loginName, String passwd, HttpServletRequest request) {
		AjaxJson jsonObj = new AjaxJson();
		UserInfo userInfo = genericService.findOne(UserInfo.class,
				new Criteria().eq(UserInfoRM.loginName, loginName).eq(UserInfoRM.passwd, MD5Utils.md5AndSha(passwd), CondtionSeparator.AND));
		if (userInfo != null) {
			request.getSession().setAttribute("user", userInfo);
			jsonObj.setSuccess(true);
			jsonObj.setMsg("登陆成功");
			return jsonObj;
		} else {
			jsonObj.setSuccess(false);
			jsonObj.setMsg("用户名密码错误！");
			return jsonObj;
		}
	}

	@RequestMapping(value = "/ranking")
	@ResponseBody
	public List<BaiduHotRank> ranking(HttpServletRequest request) {
		List<BaiduHotRank> list = genericService.findList(BaiduHotRank.class, new Criteria().orderBy(BaiduHotRankRM.num, OrderType.DESC).setPage(1, 10));
		return list;
	}

	@RequestMapping(value = "/exit")
	public String saveExit(HttpServletRequest request) {
		request.getSession().setAttribute("user", null);
		return "/login";
	}

	// 情报预警
	@RequestMapping(value = "/alarm")
	@ResponseBody
	public List<ArticleSimpleListVO> alarm(HttpServletRequest request) {
		// 舆情报警
		// 4条
		int brandId = commonCache.getDefaultCarBrand().getId();
		Integer[] brandIds = new Integer[1];
		brandIds[0] = brandId;
		ECPage<ArticleSimpleListVO> page = articleService.queryAegativeArticles(brandIds, null, null, 2, 0, 0, 0, 0, 1, 4);
		return page.getList();
	}

	@RequestMapping(value = "/repasswd")
	@ResponseBody
	public Object repasswd(String oldPasswd, String newPasswd, HttpServletRequest request) {
		AjaxJson res = new AjaxJson();
		if (org.springframework.util.StringUtils.hasText(newPasswd)) {
			com.ecarinfo.ralasafe.po.RalUser user=EcUtil.getCurrentUser();
			String oldPassword = MD5Utils.md5AndSha(oldPasswd);
			System.err.println(oldPassword);
			if (org.springframework.util.StringUtils.hasText(oldPasswd) && oldPassword.equals(user.getPassword())) {
				user.setPassword(MD5Utils.md5AndSha(newPasswd));
				genericService.update(user);
				res.setMsg("修改成功!");
				res.setSuccess(true);
			} else {
				res.setMsg("旧密码不正确!");
				res.setSuccess(false);
			}
		} else {
			res.setMsg("新密码不能为空!");
			res.setSuccess(false);
		}
		return res;
	}

}
