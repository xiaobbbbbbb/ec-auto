package com.ecarinfo.auto.weixin.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import com.ecarinfo.auto.po.Article;
import com.ecarinfo.auto.po.ArticleKeyword;
import com.ecarinfo.auto.po.DictCarBrand;
import com.ecarinfo.auto.po.DictCarSerial;
import com.ecarinfo.auto.po.UserInfo;
import com.ecarinfo.auto.rm.ArticleRM;
import com.ecarinfo.auto.rm.ArticleKeywordRM;
import com.ecarinfo.auto.rm.DictCarSerialRM;
import com.ecarinfo.auto.weixin.helper.Constants;
import com.ecarinfo.auto.weixin.util.EntityUtils;
import com.ecarinfo.auto.weixin.vo.DictCarSerialVo;
import com.ecarinfo.auto.weixin.vo.MobilePage;
import com.ecarinfo.auto.weixin.vo.OneSerialOneDayArticleCount;
import com.ecarinfo.auto.weixin.vo.ProductAttentionTrendVo;
import com.ecarinfo.auto.weixin.vo.SerialSearchOptionVo;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.common.utils.ReportMaker;
import com.ecarinfo.frame.httpserver.core.annotation.MessageModule;
import com.ecarinfo.frame.httpserver.core.annotation.RequestURI;
import com.ecarinfo.frame.httpserver.core.type.RequestMethod;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.persist.simple.DaoTool;

@Component
@MessageModule("article")
public class ArticleAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(ArticleAction.class);
	
	@RequestURI(value = "/bad_article_index", method = RequestMethod.GET)
	public String index(Integer userId, Integer brandId, Integer page, Integer searchLevel, Integer searchDateType, String searchBdate, String searchEdate) {
		String html = null;
		Map<String, Object> model = new HashMap<String, Object>();		
		try {	
			UserInfo user = baseService.findByPK(UserInfo.class, userId);
			if (brandId==null) {
				brandId = user.getBrandId();
			}
			model.put("brandId", brandId);
			model.put("userId", userId);
			model.put("brands", baseService.findAll(DictCarBrand.class));				
			model.put("searchLevel", -1);
			model.put("searchDateType", -1);
			model.put("searchBdate", "");
			model.put("searchEdate", "");
			
			Criteria criteria = new Criteria().eq(ArticleRM.brandId, brandId).eq(ArticleRM.status, 1, CondtionSeparator.AND).eq(ArticleRM.affection, 1, CondtionSeparator.AND).setPage(page==null?1:page, 10).orderBy(ArticleRM.ctime, OrderType.DESC);
			if (searchLevel!=null && searchLevel>0) {
				criteria.eq(ArticleRM.grade, searchLevel, CondtionSeparator.AND);
				model.put("searchLevel", searchLevel);
			}
			if (searchDateType!=null && searchDateType!=-1) {	
				model.put("searchDateType", searchDateType);
				if (searchDateType==1) { 
					searchBdate = DateUtils.dateToString(new Date(new Date().getTime()-Constants.SIX_DAY_TIME_INTERVAL), TimeFormatter.FORMATTER2);
					searchEdate = DateUtils.dateToString(new Date(), TimeFormatter.FORMATTER2);
					criteria.greateThenOrEquals(ArticleRM.articleDate, searchBdate, CondtionSeparator.AND);
					criteria.lessThenOrEquals(ArticleRM.articleDate, searchEdate, CondtionSeparator.AND);
					model.put("searchBdate", searchBdate);
					model.put("searchEdate", searchEdate);
				} else if (searchDateType==2) {
					searchBdate = DateUtils.dateToString(new Date(new Date().getTime()-Constants.TWENTY_NINE_DAY_TIME_INTERVAL), TimeFormatter.FORMATTER2);
					searchEdate = DateUtils.dateToString(new Date(), TimeFormatter.FORMATTER2);
					criteria.greateThenOrEquals(ArticleRM.articleDate, searchBdate, CondtionSeparator.AND);
					criteria.lessThenOrEquals(ArticleRM.articleDate, searchEdate, CondtionSeparator.AND);
					model.put("searchBdate", searchBdate);
					model.put("searchEdate", searchEdate);
				} else if (searchDateType==3) {
					if (StringUtils.isNotEmpty(searchBdate)) {
						criteria.greateThenOrEquals(ArticleRM.articleDate, searchBdate, CondtionSeparator.AND);
						model.put("searchBdate", searchBdate);
					}
					if (StringUtils.isNotEmpty(searchEdate)) {
						criteria.lessThenOrEquals(ArticleRM.articleDate, searchEdate, CondtionSeparator.AND);
						model.put("searchEdate", searchEdate);
					}
				}
			}		
			
			ECPage<Article> pager = PagingManager.list(baseService, Article.class, criteria);
			
			model.put("page", MobilePage.from(pager));
			html = ReportMaker.exeute4Content(model, "article/bad_article_index.ftl");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return html;
	}
	
	@RequestURI(value = "/detail", method = RequestMethod.GET)
	public String detail(Integer userId, Long articleId, Integer page) {
		String html = null;
		Map<String, Object> model = new HashMap<String, Object>();		
		try {
			model.put("userId", userId);
			
			Article article = baseService.findByPK(Article.class, articleId);
			model.put("article", article);
			
			ECPage<Article> pager = new ECPage<Article>();
			
			List<ArticleKeyword> keywords = baseService.findByAttr(ArticleKeyword.class, ArticleKeywordRM.articleId, articleId);
			List<Long> keywordIds = EntityUtils.getOneFieldValues(keywords, "keywordId", Long.class);	
			
			if (!CollectionUtils.isEmpty(keywordIds)) {
				ECPage<ArticleKeyword> keypager = PagingManager.list(baseService, ArticleKeyword.class, new Criteria().notEquals(ArticleKeywordRM.articleId, articleId).in(ArticleKeywordRM.keywordId, keywordIds.toArray(), CondtionSeparator.AND).setPage(page==null?1:page, 5).orderBy(ArticleKeywordRM.id, OrderType.DESC));
				List<Article> articles = null;
				if (!CollectionUtils.isEmpty(keypager.getList())) {
					List<Long> ids = EntityUtils.getOneFieldValues(keypager.getList(), "articleId", Long.class);
					articles = baseService.findList(Article.class, new Criteria().in(ArticleRM.pk, ids.toArray()));
				}
				BeanUtils.copyProperties(keypager, pager, new String[] {"list"});
				pager.setList(articles);
			}
			model.put("page", MobilePage.from(pager));
						
			html = ReportMaker.exeute4Content(model, "article/detail.ftl");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return html;
	}
	
	static final Integer[] mockCounts = {0,0,0,0,0,0,0};
	@RequestURI(value = "/seven_day_attention_trend", method = RequestMethod.GET)
	public String sevenDayAttentionTrend(Integer userId, Integer brandId, String serialsStr, Integer page) {
		String html = null;
		Map<String, Object> model = new HashMap<String, Object>();		
		try {
			model.put("userId", userId);
			UserInfo user = baseService.findByPK(UserInfo.class, userId);
			if (brandId==null) {
				brandId = user.getBrandId();
			}
			model.put("brandId", brandId);
			model.put("userId", userId);
			DictCarBrand brand = baseService.findByPK(DictCarBrand.class, brandId);
			model.put("brandName", brand.getName());
			model.put("brands", baseService.findAll(DictCarBrand.class));
			
			List<DictCarSerial> serials = null;
			if (StringUtils.isEmpty(serialsStr)) {
				serials = baseService.findList(DictCarSerial.class, new Criteria().eq(DictCarSerialRM.brandId, brandId).setPage(1, 3));
				for (DictCarSerial dictCarSerial : serials) {
					serialsStr = "";
					if (!StringUtils.isEmpty(serialsStr)) {
						serialsStr += ",";
					}
					serialsStr += dictCarSerial.getId();
				}
			} else {
				serials = baseService.findList(DictCarSerial.class, new Criteria().in(DictCarSerialRM.id, serialsStr.split(",")));
			}			
			Map<Integer, DictCarSerial> id2serialMap = EntityUtils.getField2EntityMap(serials, "id", Integer.class);
			
			
			String searchBdate = DateUtils.dateToString(new Date(new Date().getTime()-Constants.SIX_DAY_TIME_INTERVAL), TimeFormatter.FORMATTER2);
			String searchEdate = DateUtils.dateToString(new Date(), TimeFormatter.FORMATTER2);
			
			StringBuffer sql = new StringBuffer();
			sql.append("select a.serial_id as serialId, a.article_date as articleDate, count(a.id) as articleCount from article as a where a.status=1 and serial_id in(").append(serialsStr).append(")")
			.append(" and article_date>='").append(searchBdate).append("'")
			.append(" and article_date<='").append(searchEdate).append("'")
			.append(" group by serial_id,article_date");
			logger.info("[SEVEN_TREND_SQL]:" + sql.toString());
			
			List<OneSerialOneDayArticleCount> list = DaoTool.queryForList(OneSerialOneDayArticleCount.class, sql.toString());
			List<ProductAttentionTrendVo> trends = new ArrayList<ProductAttentionTrendVo>();
			
			Map<Integer, ProductAttentionTrendVo> serialId2trendMap = new HashMap<Integer, ProductAttentionTrendVo>();
			for (OneSerialOneDayArticleCount item : list) {
				ProductAttentionTrendVo trend = serialId2trendMap.get(item.getSerialId());
				if (trend==null) {
					trend = new ProductAttentionTrendVo();
					trend.setSerialId(item.getSerialId());
					DictCarSerial serial = id2serialMap.get(item.getSerialId());
					trend.setSerialName(serial==null?null:serial.getName());					
					serialId2trendMap.put(serial.getId(), trend);
					
					trends.add(trend);
				}
				List<Integer> counts = trend.getArticleCounts();
				if (counts==null) {
					counts = new ArrayList<Integer>();
					trend.setArticleCounts(counts);
				}
				counts.add(item.getArticleCount());
			}		

			for (DictCarSerial serial : serials) {
				if (!serialId2trendMap.containsKey(serial.getId())) {
					ProductAttentionTrendVo trend = new ProductAttentionTrendVo();
					trend.setSerialId(serial.getId());
					trend.setSerialName(serial.getName());
					List<Integer> counts = new ArrayList<Integer>(Arrays.asList(mockCounts));
					trend.setArticleCounts(counts);
					
					trends.add(trend);
				}
			}
					
			model.put("dateNames", getLastSevenDayDateNames());
			model.put("yearMonth", DateUtils.dateToString(new Date(), "yyyy年MM月"));
			model.put("trends", trends);
			
			model.put("searchOptions", genSerialSearchOptionVoList(brandId, EntityUtils.getOneFieldValues(serials, "id", Integer.class)));
			html = ReportMaker.exeute4Content(model, "article/seven_day_attention_trend.ftl");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return html;
	}
	private static final long ONE_DAY_TIMES = 24l*3600*1000;
	private static final long SIX_DAY_TIMES = 6 *24l*3600*1000;
	private List<String> getLastSevenDayDateNames() {
		List<String> names = new ArrayList<String>();
		Date today = new Date();
		for (int i=0; i<7; i++) {
			names.add(DateUtils.dateToString(today.getTime()-SIX_DAY_TIMES+ONE_DAY_TIMES*i, "dd"));
		}
		return names;		
	}
	
	private List<SerialSearchOptionVo> genSerialSearchOptionVoList(Integer brandId, List<Integer> selectedSerialIds) {
		List<SerialSearchOptionVo> vos = new ArrayList<SerialSearchOptionVo>();
		Set<Integer> selectedSerialIdsSet = new HashSet<Integer>(selectedSerialIds);
		Map<Integer, SerialSearchOptionVo> map = new HashMap<Integer, SerialSearchOptionVo>();
		List<DictCarSerial> serials = baseService.findAll(DictCarSerial.class);
		for (DictCarSerial serial : serials) {
			SerialSearchOptionVo vo = map.get(serial.getBrandId());
			if (vo==null) {
				vo = new SerialSearchOptionVo();
				vo.setBrandId(serial.getBrandId());
				vo.setBrandName(serial.getBrandName());
				vo.setSelect(brandId==serial.getBrandId()?true:false);
				map.put(serial.getBrandId(), vo);				
				vos.add(vo);
			} 
			
			List<DictCarSerialVo> serialVos = vo.getSerials();
			if (serialVos==null) {
				serialVos = new ArrayList<DictCarSerialVo>();
				vo.setSerials(serialVos);
			}
			DictCarSerialVo serialVo = new DictCarSerialVo();
			BeanUtils.copyProperties(serial, serialVo);
			serialVo.setSelect(selectedSerialIdsSet.contains(serial.getId())?true:false);

			serialVos.add(serialVo);
		}
		return vos;		
	}
}
