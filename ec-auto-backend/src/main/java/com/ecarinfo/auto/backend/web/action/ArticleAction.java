package com.ecarinfo.auto.backend.web.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.auto.backend.web.helper.search.ArticleSearchHelper;
import com.ecarinfo.auto.backend.web.util.AjaxUtils;
import com.ecarinfo.auto.backend.web.util.DateUtils;
import com.ecarinfo.auto.backend.web.util.EntityUtils;
import com.ecarinfo.auto.backend.web.vo.EasyUIPage;
import com.ecarinfo.auto.backend.web.vo.ViewpointVo;
import com.ecarinfo.auto.po.Article;
import com.ecarinfo.auto.po.ArticleViewpoint;
import com.ecarinfo.auto.po.DictCarSerial;
import com.ecarinfo.auto.po.DictCity;
import com.ecarinfo.auto.po.DictMediaType;
import com.ecarinfo.auto.po.DictProvince;
import com.ecarinfo.auto.po.MediaInfo;
import com.ecarinfo.auto.po.Viewpoint;
import com.ecarinfo.auto.rm.ArticleRM;
import com.ecarinfo.auto.rm.ArticleViewpointRM;
import com.ecarinfo.auto.rm.ViewpointRM;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;

/**
 * 车友评价
 * @author zhanglu
 *
 */
@RequestMapping("article")
@Controller
public class ArticleAction extends BaseAction{
	private static final Logger logger = LoggerFactory.getLogger(ArticleAction.class);

	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request) {
		
		List<MediaInfo> medias = baseService.findAll(MediaInfo.class);
		request.setAttribute("medias", medias);
			
		List<Viewpoint> viewpoints = baseService.findAll(Viewpoint.class);
		request.setAttribute("viewpoints", viewpoints);
		
		request.setAttribute("brands", cache.getBrands());
		
		request.setAttribute("areas", cache.getAreas());
		request.setAttribute("menu_prefix", "article");
		request.setAttribute("todayDate", DateUtils.dateToString(new Date(), DateUtils.yyyy_MM_dd));
		
		List<DictMediaType> mediaTypes = baseService.findAll(DictMediaType.class);
		request.setAttribute("mediaTypes", mediaTypes);
		
		return "/article/index";
	}
	
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public EasyUIPage list(HttpServletRequest request) {
		ECPage<Article> pageHolder = PagingManager.list(baseService, Article.class, ArticleSearchHelper.getSearchCriteria(request));			
		return EasyUIPage.from(voHelper.getArticleVoPage(pageHolder));
	}
	
	@RequestMapping(value = "/serials")
	@ResponseBody
	public List<DictCarSerial> getSerialsByBrandId(Integer brandId, HttpServletRequest request) {
		return cache.getBrandId2SerialsMap().get(brandId);
	}
	
	@RequestMapping(value = "/provinces")
	@ResponseBody
	public List<DictProvince> getProvincesByAreaId(Integer areaId, HttpServletRequest request) {
		return cache.getAreaId2ProvincesMap().get(areaId);
	}
	
	@RequestMapping(value = "/citys")
	@ResponseBody
	public List<DictCity> getCitysByProvinceId(Integer provinceId, HttpServletRequest request) {
		return cache.getProvinceId2CitysMap().get(provinceId);
	}
	
	@RequestMapping(value = "/edit")
	public Article edit(Long id, HttpServletRequest request) {
		Article art = baseService.findByPK(Article.class, id);
		request.setAttribute("article", art);
		List<DictMediaType> mediaTypes = baseService.findAll(DictMediaType.class);
		request.setAttribute("mediaTypes", mediaTypes);
		return art;
	}
	
	@RequestMapping(value = "/viewpoints")
	@ResponseBody
	public List<ViewpointVo> viewpoints(Long articleId, HttpServletRequest request) throws Exception {
		List<ViewpointVo> vos = new ArrayList<ViewpointVo>();
		List<Viewpoint> ps = baseService.findList(Viewpoint.class, new Criteria().eq(ViewpointRM.status, 1));
		List<ArticleViewpoint> aps = baseService.findByAttr(ArticleViewpoint.class, ArticleViewpointRM.articleId, articleId);
		Map<Integer, ArticleViewpoint> viewpointId2ArticleMap = EntityUtils.getField2EntityMap(aps, "viewpointId", Integer.class);
		
		for (Viewpoint p : ps) {
			ViewpointVo vo = new ViewpointVo();
			BeanUtils.copyProperties(vo, p);
			if (viewpointId2ArticleMap.containsKey(p.getId())) {
				vo.setSelect(true);
			} else {
				vo.setSelect(false);
			}
			vos.add(vo);
		}
		return vos;
	}
	
	@RequestMapping(value = "/doedit")
	public void doEdit(Article form, String articleViewpointIds, HttpServletResponse response) {
		Article newArt = baseService.findByPK(Article.class, form.getId());

		newArt.setAffection(form.getAffection());
		newArt.setDesContent(form.getDesContent());
		newArt.setDesTarget(form.getDesTarget());
		newArt.setGrade(form.getGrade());
		newArt.setStatus(1);
		newArt.setMediaId(form.getMediaId());
		newArt.setMediaType(form.getMediaType());
		newArt.setSimpleContent(form.getSimpleContent());
		newArt.setTitle(form.getTitle());
		newArt.setUrl(form.getUrl());
		newArt.setBrandId(form.getBrandId());
		newArt.setSerialId(form.getSerialId());
		newArt.setAreaId(form.getAreaId());
		newArt.setProvinceId(form.getProvinceId());
		newArt.setCityId(form.getCityId());
		if (StringUtils.isEmpty(articleViewpointIds)) {
			newArt.setHasViewpoint(false);
		} else {
			newArt.setHasViewpoint(true);
		}
		baseService.update(newArt);
		
		baseService.delete(ArticleViewpoint.class, new Criteria().eq(ArticleViewpointRM.articleId, form.getId()));
		if (StringUtils.isNotEmpty(articleViewpointIds)) {
			String[] strIds = articleViewpointIds.split(",");	
			for (String strId : strIds) {
				ArticleViewpoint avp = new ArticleViewpoint();
				avp.setArticleId(form.getId());
				avp.setViewpointId(Integer.parseInt(strId));
				baseService.save(avp);
			}
		}
		AjaxUtils.printSucc(response);
	}
	
	@RequestMapping(value = "/discard")
	public void discard(Integer articleId, Boolean discard, HttpServletResponse response) {
		baseService.updateWithCriteria(Article.class, new Criteria().eq(ArticleRM.pk, articleId).update(ArticleRM.status, discard?2:1));
		AjaxUtils.printSucc(response);
	}
	
	/**
	 *  批量作废 /启用
	 * @param articleIds
	 * @param discard
	 * @param response
	 */
	@RequestMapping(value = "/batch/discard")
	public void batchDiscard(@RequestParam("articleIds[]") Long[] articleIds, Boolean discard, HttpServletResponse response) {
		baseService.updateWithCriteria(Article.class, new Criteria().in(ArticleRM.pk, articleIds).update(ArticleRM.status, discard?2:1));
		logger.info("[BATCH DISCARD] article:" + Arrays.asList(articleIds));
		AjaxUtils.printSucc(response);
	}
}
