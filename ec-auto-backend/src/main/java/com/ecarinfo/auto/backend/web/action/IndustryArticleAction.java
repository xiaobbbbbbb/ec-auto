package com.ecarinfo.auto.backend.web.action;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.auto.backend.web.helper.search.IndustryArticleSearchHelper;
import com.ecarinfo.auto.backend.web.util.AjaxUtils;
import com.ecarinfo.auto.backend.web.util.DateUtils;
import com.ecarinfo.auto.backend.web.vo.EasyUIPage;
import com.ecarinfo.auto.po.IndustryArticle;
import com.ecarinfo.auto.po.MediaInfo;
import com.ecarinfo.auto.rm.IndustryArticleRM;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;

/**
 * 动态新闻
 * @author zhanglu
 *
 */
@RequestMapping("industry_article")
@Controller
public class IndustryArticleAction extends BaseAction{
	private static final Logger logger = Logger.getLogger(IndustryArticleAction.class);

	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request) {
		List<MediaInfo> medias = baseService.findAll(MediaInfo.class);
		request.setAttribute("medias", medias);
		request.setAttribute("todayDate", DateUtils.dateToString(new Date(), DateUtils.yyyy_MM_dd));
		request.setAttribute("menu_prefix", "industry_article");
		return "/industryarticle/index";
	}
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public EasyUIPage list(HttpServletRequest request) {
		ECPage<IndustryArticle> pageHolder = PagingManager.list(baseService, IndustryArticle.class, IndustryArticleSearchHelper.getSearchCriteria(request));			
		return EasyUIPage.from(voHelper.getIndustryArticleVoPage(pageHolder));
	}
	
	@RequestMapping(value = "/edit")
	public IndustryArticle edit(Long id, HttpServletRequest request) {
		IndustryArticle art = baseService.findByPK(IndustryArticle.class, id);
		request.setAttribute("article", art);
		return art;
	}
	
	@RequestMapping(value = "/doedit")
	public void doEdit(IndustryArticle form, HttpServletResponse response) {
		IndustryArticle newArt = baseService.findByPK(IndustryArticle.class, form.getId());
		
		newArt.setMediaId(form.getMediaId());
		newArt.setSimpleContent(form.getSimpleContent());
		newArt.setTitle(form.getTitle());
		newArt.setUrl(form.getUrl());
		newArt.setStatus(1);
		baseService.update(newArt);
		AjaxUtils.printSucc(response);
	}
	
	@RequestMapping(value = "/discard")
	public void discard(Integer articleId, Boolean discard, HttpServletResponse response) {
		baseService.updateWithCriteria(IndustryArticle.class, new Criteria().eq(IndustryArticleRM.pk, articleId).update(IndustryArticleRM.status, discard?2:1));
		AjaxUtils.printSucc(response);
	}
	
	/**
	 * 批量作废 /启用
	 * @param articleIds
	 * @param discard
	 * @param response
	 */
	@RequestMapping(value = "/batch_discard")
	public void batchDiscard(@RequestParam("articleIds[]") Long[] articleIds, Boolean discard, HttpServletResponse response) {
		baseService.updateWithCriteria(IndustryArticle.class, new Criteria().in(IndustryArticleRM.pk, articleIds).update(IndustryArticleRM.status, discard?2:1));
		logger.info("[BATCH DISCARD] industryarticle:" + Arrays.asList(articleIds));
		AjaxUtils.printSucc(response);
	}
}
