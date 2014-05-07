package com.ecarinfo.auto.weixin.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ecarinfo.auto.po.Article;
import com.ecarinfo.auto.po.ArticleKeyword;
import com.ecarinfo.auto.po.DictCarBrand;
import com.ecarinfo.auto.po.UserInfo;
import com.ecarinfo.auto.po.Viewpoint;
import com.ecarinfo.auto.rm.ArticleKeywordRM;
import com.ecarinfo.auto.rm.ArticleRM;
import com.ecarinfo.auto.weixin.helper.Constants;
import com.ecarinfo.auto.weixin.util.EntityUtils;
import com.ecarinfo.auto.weixin.vo.ArticleVo;
import com.ecarinfo.auto.weixin.vo.MobilePage;
import com.ecarinfo.auto.weixin.vo.ViewpointVo;
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
import com.ecarinfo.persist.simple.PageHolder;

@Component
@MessageModule("viewpoint")
public class ViewpointAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(ViewpointAction.class);
	
	@RequestURI(value = "/index", method = RequestMethod.GET)
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
			
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT p.viewpoint_id as id, vp.name as name, count(p.article_id) as articleCount, vp.affection as affection FROM `article_viewpoint` as p ")
			.append(" left join article as a on a.id = p.article_id ");
			sql.append(" left join viewpoint as vp on vp.id = p.viewpoint_id ");
			sql.append("where a.status=1 and a.brand_id=").append(brandId);
			if (searchLevel==null || searchLevel<=0) {				
				searchLevel = 3; //默认为好评
			}			
			sql.append(" and vp.affection=").append(searchLevel);
			
			model.put("searchLevel", searchLevel);
			if (searchDateType!=null && searchDateType>0) {
				model.put("searchDateType", searchDateType);
				if (searchDateType==1) { 
					searchBdate = DateUtils.dateToString(new Date(new Date().getTime()-Constants.SIX_DAY_TIME_INTERVAL), TimeFormatter.FORMATTER2);
					searchEdate = DateUtils.dateToString(new Date(), TimeFormatter.FORMATTER2);
					sql.append(" and a.article_date>='").append(searchBdate).append("'");
					sql.append(" and a.article_date<='").append(searchEdate).append("'");
					model.put("searchBdate", searchBdate);
					model.put("searchEdate", searchEdate);
				} else if (searchDateType==2) {
					searchBdate = DateUtils.dateToString(new Date(new Date().getTime()-Constants.TWENTY_NINE_DAY_TIME_INTERVAL), TimeFormatter.FORMATTER2);
					searchEdate = DateUtils.dateToString(new Date(), TimeFormatter.FORMATTER2);
					sql.append(" and a.article_date>='").append(searchBdate).append("'");
					sql.append(" and a.article_date<='").append(searchEdate).append("'");
					model.put("searchBdate", searchBdate);
					model.put("searchEdate", searchEdate);
				} else if (searchDateType==3) {
					if (StringUtils.isNotEmpty(searchBdate)) {
						sql.append(" and a.article_date>='").append(searchBdate).append("'");
						model.put("searchBdate", searchBdate);
					}
					if (StringUtils.isNotEmpty(searchEdate)) {
						sql.append(" and a.article_date<='").append(searchEdate).append("'");
						model.put("searchEdate", searchEdate);
					}
				}
			}
			sql.append(" group by p.viewpoint_id order by articleCount desc");
			logger.info("[VIEWPOINT_SQL]:" + sql.toString());
			List<ViewpointVo> viewpoints = DaoTool.queryForList(ViewpointVo.class, sql.toString());
			processViewpointVos(viewpoints);
			
			model.put("viewpoints", viewpoints);
			html = ReportMaker.exeute4Content(model, "viewpoint/index.ftl");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return html;
	}
	
	private void processViewpointVos(List<ViewpointVo> vos) {
		if (!CollectionUtils.isEmpty(vos)) {
			Integer total = 0;		
			for (ViewpointVo vo : vos) {
				total += vo.getArticleCount();
			}
			if (total>0) {
				for (ViewpointVo vo : vos) {
					if (vo.getArticleCount()>0) {
						vo.setArticlePercent(vo.getArticleCount().floatValue()/total);
					}
				}
			}
		}
	}
	@RequestURI(value = "/detail", method = RequestMethod.GET)
	public String detail(Integer userId, Integer brandId, Long viewpointId, Float articlePercent, Integer page, Integer searchDateType, String searchBdate, String searchEdate) {
		String html = null;
		Map<String, Object> model = new HashMap<String, Object>();		
		try {
			model.put("brandId", brandId);
			model.put("userId", userId);
			model.put("articlePercent", articlePercent);
			model.put("searchDateType", -1);
			model.put("searchBdate", "");
			model.put("searchEdate", "");
			
			model.put("viewpoint", baseService.findByPK(Viewpoint.class, viewpointId));
			
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT a.* FROM `article` as a ")
			.append(" left join article_viewpoint as p on a.id = p.article_id ");
			sql.append("where a.status=1 and a.brand_id=").append(brandId);
			sql.append(" and p.viewpoint_id=").append(viewpointId);
			
			if (searchDateType!=null && searchDateType>0) {
				model.put("searchDateType", searchDateType);
				if (searchDateType==1) { 
					searchBdate = DateUtils.dateToString(new Date(new Date().getTime()-Constants.SIX_DAY_TIME_INTERVAL), TimeFormatter.FORMATTER2);
					searchEdate = DateUtils.dateToString(new Date(), TimeFormatter.FORMATTER2);
					sql.append(" and a.article_date>='").append(searchBdate).append("'");
					sql.append(" and a.article_date<='").append(searchEdate).append("'");
					model.put("searchBdate", searchBdate);
					model.put("searchEdate", searchEdate);
				} else if (searchDateType==2) {
					searchBdate = DateUtils.dateToString(new Date(new Date().getTime()-Constants.TWENTY_NINE_DAY_TIME_INTERVAL), TimeFormatter.FORMATTER2);
					searchEdate = DateUtils.dateToString(new Date(), TimeFormatter.FORMATTER2);
					sql.append(" and a.article_date>='").append(searchBdate).append("'");
					sql.append(" and a.article_date<='").append(searchEdate).append("'");
					model.put("searchBdate", searchBdate);
					model.put("searchEdate", searchEdate);
				} else if (searchDateType==3) {
					if (StringUtils.isNotEmpty(searchBdate)) {
						sql.append(" and a.article_date>='").append(searchBdate).append("'");
						model.put("searchBdate", searchBdate);
					}
					if (StringUtils.isNotEmpty(searchEdate)) {
						sql.append(" and a.article_date<='").append(searchEdate).append("'");
						model.put("searchEdate", searchEdate);
					}
				}
			}
			logger.info(String.format("[VIEWPONIT DETAIL SQL]: %s", sql.toString()));
			PageHolder<ArticleVo> pager = DaoTool.queryForPage(ArticleVo.class, sql.toString(), page==null?1:page, 10);

			model.put("page", MobilePage.from(pager));
			
			html = ReportMaker.exeute4Content(model, "viewpoint/detail.ftl");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return html;
	}
}
