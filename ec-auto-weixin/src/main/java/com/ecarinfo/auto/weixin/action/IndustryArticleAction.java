package com.ecarinfo.auto.weixin.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ecarinfo.auto.po.IndustryArticle;
import com.ecarinfo.auto.po.IndustryKeyword;
import com.ecarinfo.auto.rm.IndustryArticleRM;
import com.ecarinfo.auto.rm.IndustryKeywordRM;
import com.ecarinfo.auto.weixin.util.EntityUtils;
import com.ecarinfo.auto.weixin.vo.MobilePage;
import com.ecarinfo.common.utils.ReportMaker;
import com.ecarinfo.frame.httpserver.core.annotation.MessageModule;
import com.ecarinfo.frame.httpserver.core.annotation.RequestURI;
import com.ecarinfo.frame.httpserver.core.type.RequestMethod;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;

@Component
@MessageModule("industry_article")
public class IndustryArticleAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(IndustryArticleAction.class);
	
	
	@RequestURI(value = "/index", method = RequestMethod.GET)
	public String index(Integer userId, Integer page) {
		String html = null;
		Map<String, Object> model = new HashMap<String, Object>();		
		try {
			model.put("userId", userId);
			
			ECPage<IndustryArticle> pager = PagingManager.list(baseService, IndustryArticle.class, new Criteria().eq(IndustryArticleRM.status, 1).setPage(page==null?1:page, 10).orderBy(IndustryArticleRM.ctime, OrderType.DESC));
			model.put("page", MobilePage.from(pager));
			
			html = ReportMaker.exeute4Content(model, "industryarticle/index.ftl");
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
			
			IndustryArticle article = baseService.findByPK(IndustryArticle.class, articleId);
			model.put("article", article);
			
			ECPage<IndustryArticle> pager = new ECPage<IndustryArticle>();
			
			List<IndustryKeyword> keywords = baseService.findByAttr(IndustryKeyword.class, IndustryKeywordRM.industryArticleId, articleId);
			List<Long> keywordIds = EntityUtils.getOneFieldValues(keywords, "keywordId", Long.class);	
			
			if (!CollectionUtils.isEmpty(keywordIds)) {
				ECPage<IndustryKeyword> keypager = PagingManager.list(baseService, IndustryKeyword.class, new Criteria().notEquals(IndustryKeywordRM.industryArticleId, articleId).in(IndustryKeywordRM.keywordId, keywordIds.toArray(), CondtionSeparator.AND).setPage(page==null?1:page, 5).orderBy(IndustryKeywordRM.id, OrderType.DESC));
				List<IndustryArticle> articles = null;
				if (!CollectionUtils.isEmpty(keypager.getList())) {
					List<Long> ids = EntityUtils.getOneFieldValues(keypager.getList(), "industryArticleId", Long.class);
					articles = baseService.findList(IndustryArticle.class, new Criteria().in(IndustryArticleRM.pk, ids.toArray()));
				}
				BeanUtils.copyProperties(keypager, pager, new String[] {"list"});
				pager.setList(articles);
			}
			model.put("page", MobilePage.from(pager));
						
			html = ReportMaker.exeute4Content(model, "industryarticle/detail.ftl");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return html;
	}
}
