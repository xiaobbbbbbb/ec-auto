package com.ecarinfo.auto.backend.web.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.ecarinfo.auto.backend.web.util.DateUtils;
import com.ecarinfo.auto.backend.web.util.EntityUtils;
import com.ecarinfo.auto.backend.web.vo.ArticleVo;
import com.ecarinfo.auto.backend.web.vo.IndustryArticleVo;
import com.ecarinfo.auto.backend.web.vo.UserInfoVo;
import com.ecarinfo.auto.backend.web.vo.ViewpointVo;
import com.ecarinfo.auto.po.Article;
import com.ecarinfo.auto.po.ArticleViewpoint;
import com.ecarinfo.auto.po.DictArea;
import com.ecarinfo.auto.po.DictCarBrand;
import com.ecarinfo.auto.po.DictCarSerial;
import com.ecarinfo.auto.po.DictCity;
import com.ecarinfo.auto.po.DictProvince;
import com.ecarinfo.auto.po.IndustryArticle;
import com.ecarinfo.auto.po.UserInfo;
import com.ecarinfo.auto.po.Viewpoint;
import com.ecarinfo.auto.rm.ArticleViewpointRM;
import com.ecarinfo.auto.rm.ViewpointRM;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.service.GenericService;

@Component("voHelper")
public class VOHelper {
	@Resource
	private GenericService baseService;
	@Resource 
	private CommonDataCache cache;
	private static Map<Integer, String> articleGradeMap = new HashMap<Integer, String>();
	private static Map<Integer, String> articleAffectionMap = new HashMap<Integer, String>();
	private static Map<Integer, String> articleDesTargetMap = new HashMap<Integer, String>();
	private static Map<Integer, String> articleDesContentMap = new HashMap<Integer, String>();
	private static Map<Integer, String> articleMediaTypeMap = new HashMap<Integer, String>();
	private static Map<Integer, String> articleArticleTypeMap = new HashMap<Integer, String>();
	
	private static Map<Integer, String> statusMap = new HashMap<Integer, String>();
	static {
		articleGradeMap.put(1, "1级");
		articleGradeMap.put(2, "2级");
		articleGradeMap.put(3, "3级");
		
		articleAffectionMap.put(3, "正面");
		articleAffectionMap.put(2, "中性");
		articleAffectionMap.put(1, "负面");
		
		articleDesTargetMap.put(1, "主机厂");
		articleDesTargetMap.put(2, "4s店");
		articleDesTargetMap.put(3, "其它");
		
		articleDesContentMap.put(1, "质量");
		articleDesContentMap.put(2, "价格");
		articleDesContentMap.put(3, "服务");
		articleDesContentMap.put(4, "其他");
		
		
		articleMediaTypeMap.put(1, "新闻");
		articleMediaTypeMap.put(2, "论坛");
		articleMediaTypeMap.put(3, "微博");
		
		articleArticleTypeMap.put(1, "口碑");
		articleArticleTypeMap.put(2, "负面情报");
		articleArticleTypeMap.put(3, "其他");
		
		statusMap.put(0, "初始");
		statusMap.put(1, "有效");
		statusMap.put(2, "无效");
	}
	
	public ECPage<ArticleVo> getArticleVoPage(ECPage<Article> page) {
		ECPage<ArticleVo> pageVo = new ECPage<ArticleVo>();
		BeanUtils.copyProperties(page, pageVo, new String[] {"list"});
		List<Article> pos = page.getList();
		if (!CollectionUtils.isEmpty(pos)) {
			List<ArticleVo> vos = new ArrayList<ArticleVo>();
			long i=(page.getCurrentPage()-1)*page.getRowsPerPage();
			List<Long> articleIds = EntityUtils.getOneFieldValues(pos, "id", Long.class);
			List<ArticleViewpoint> art2viewpoints = baseService.findList(ArticleViewpoint.class, new Criteria().in(ArticleViewpointRM.articleId, articleIds.toArray()));
			Set<Integer> viewpointIds = new HashSet(EntityUtils.getOneFieldValues(art2viewpoints, "viewpointId"));
			Map<Integer, Viewpoint> id2viewpointMap=null;
			Map<Long, List<Integer>> articleId2viewpointIdMap=null;
			if (!CollectionUtils.isEmpty(viewpointIds)) {
				List<Viewpoint> viewpoints = baseService.findList(Viewpoint.class, new Criteria().in(ViewpointRM.id, viewpointIds.toArray()));
				id2viewpointMap = EntityUtils.getField2EntityMap(viewpoints, "id", Integer.class);
				articleId2viewpointIdMap = getArticleId2viewpointIdMap(art2viewpoints);
			}
			for (Article po : pos) {
				i++;
				ArticleVo vo = getArticleVo(po, id2viewpointMap, articleId2viewpointIdMap);
				vo.setSqId(i);
				vos.add(vo);				
			}			
			pageVo.setList(vos);
		}
		return pageVo;
	}
	
	private Map<Long, List<Integer>> getArticleId2viewpointIdMap(List<ArticleViewpoint> avps) {
		Map<Long, List<Integer>> map = new HashMap<Long, List<Integer>>();
		if (!CollectionUtils.isEmpty(avps)) {
			for (ArticleViewpoint avp : avps) {
				List<Integer> viewpointIds = map.get(avp.getArticleId());
				if (viewpointIds==null) {
					viewpointIds = new ArrayList<Integer>();				
					map.put(avp.getArticleId(), viewpointIds);
				}
				viewpointIds.add(avp.getViewpointId());
			}
		}
		return map;
	}
	
	public ArticleVo getArticleVo(Article po, Map<Integer, Viewpoint> id2viewpointMap, Map<Long, List<Integer>> articleId2viewpointIdMap) {
		
		ArticleVo vo = new ArticleVo();
		BeanUtils.copyProperties(po, vo);
		
		if (po.getGrade()!=null) {
			vo.setShowGrade(articleGradeMap.get(po.getGrade()));
		}
		if (po.getAffection()!=null) {
			vo.setShowAffection(articleAffectionMap.get(po.getAffection()));
		}
		if (po.getDesTarget()!=null) {
			vo.setShowDesTarget(articleDesTargetMap.get(po.getDesTarget()));
		}
		if (po.getDesContent()!=null) {
			vo.setShowDesContent(articleDesContentMap.get(po.getDesContent()));
		}
		if (po.getMediaType()!=null) {
			vo.setShowMediaType(articleMediaTypeMap.get(po.getMediaType()));
		}
		if (po.getArticleCtime()!=null) {
			vo.setShowArticleCtime(DateUtils.dateToString(po.getArticleCtime(), DateUtils.yyyy_MM_dd_HH_mm_ss));
		}
		if (po.getCtime()!=null) {
			vo.setShowCtime(DateUtils.dateToString(po.getCtime(), DateUtils.yyyy_MM_dd_HH_mm_ss));
		}
		
		if (po.getMediaId()!=null) {
			vo.setShowMediaId(cache.getMediaMap().get(po.getMediaId()));
		}
		
		if (po.getStatus()!=null) {
			vo.setShowStatus(statusMap.get(po.getStatus()));
		}
		if (po.getUrl()!=null) {
			vo.setShowUrl(String.format("<a href='%s' target='_blank'>%s</a>", po.getUrl(), po.getUrl()));
		}
		if (po.getBrandId()!=null) {
			DictCarBrand brand = cache.getBrandMap().get(po.getBrandId());
			vo.setShowBrandId(brand==null?null:brand.getName());
		}
		if (po.getSerialId()!=null) {
			DictCarSerial serial = cache.getSerialMap().get(po.getSerialId());
			vo.setShowSerialId(serial==null?null:serial.getName());
		}
		
		if (po.getAreaId()!=null) {
			DictArea area = cache.getAreaMap().get(po.getAreaId());
			vo.setShowAreaId(area==null?null:area.getName());
		}
		if (po.getProvinceId()!=null) {
			DictProvince province = cache.getProvinceMap().get(po.getProvinceId());
			vo.setShowProvinceId(province==null?null:province.getName());
		}
		if (po.getCityId()!=null) {
			DictCity city = cache.getCityMap().get(po.getCityId());
			vo.setShowCityId(city==null?null:city.getName());
		}
		if(id2viewpointMap!=null && articleId2viewpointIdMap!=null){
			vo.setShowViewpoints(getShowViewpionts(po.getId(), id2viewpointMap, articleId2viewpointIdMap));
		}
		return vo;	
	}
	
	private String getShowViewpionts(Long activiyId, Map<Integer, Viewpoint> id2viewpointMap, Map<Long, List<Integer>> articleId2viewpointIdMap) {
		StringBuffer show = new StringBuffer();
		List<Integer> ids = articleId2viewpointIdMap.get(activiyId);
		if (!CollectionUtils.isEmpty(ids)) {
			for (Integer id : ids) {
				if (show.length()>0) {
					show.append(",");
				}
				show.append(id2viewpointMap.get(id).getName());
			}
		}		
		return show.toString();
		
	}
	public ECPage<IndustryArticleVo> getIndustryArticleVoPage(ECPage<IndustryArticle> page) {
		ECPage<IndustryArticleVo> pageVo = new ECPage<IndustryArticleVo>();
		BeanUtils.copyProperties(page, pageVo, new String[] {"list"});
		List<IndustryArticle> pos = page.getList();
		if (!CollectionUtils.isEmpty(pos)) {
			List<IndustryArticleVo> vos = new ArrayList<IndustryArticleVo>();
			long i=(page.getCurrentPage()-1)*page.getRowsPerPage();
			for (IndustryArticle po : pos) {
				i++;
				IndustryArticleVo vo = getIndustryArticleVo(po);
				vo.setSqId(i);
				vos.add(vo);		
				
			}			
			pageVo.setList(vos);
		}
		return pageVo;
	}
	
	public IndustryArticleVo getIndustryArticleVo(IndustryArticle po) {
		
		IndustryArticleVo vo = new IndustryArticleVo();
		BeanUtils.copyProperties(po, vo);
		if (po.getCtime()!=null) {
			vo.setShowCtime(DateUtils.dateToString(po.getCtime(), DateUtils.yyyy_MM_dd_HH_mm_ss));
		}
		if (po.getPubTime()!=null) {
			vo.setShowPubTime(DateUtils.dateToString(po.getPubTime(), DateUtils.yyyy_MM_dd_HH_mm_ss));
		}
		if (po.getMediaId()!=null) {
			vo.setShowMediaId(cache.getMediaMap().get(po.getMediaId()));
		}
		if (po.getStatus()!=null) {
			vo.setShowStatus(statusMap.get(po.getStatus()));
		}
		if (po.getUrl()!=null) {
			vo.setShowUrl(String.format("<a href='%s' target='_blank'>%s</a>", po.getUrl(), po.getUrl()));
		}
		return vo;	
	}
	
	public ECPage<ViewpointVo> getViewpointVoPage(ECPage<Viewpoint> page) {
		ECPage<ViewpointVo> pageVo = new ECPage<ViewpointVo>();
		BeanUtils.copyProperties(page, pageVo, new String[] {"list"});
		List<Viewpoint> pos = page.getList();
		if (!CollectionUtils.isEmpty(pos)) {
			List<ViewpointVo> vos = new ArrayList<ViewpointVo>();
			long i=(page.getCurrentPage()-1)*page.getRowsPerPage();
			for (Viewpoint po : pos) {
				i++;
				ViewpointVo vo = getViewpointVo(po);
				vo.setSqId(i);
				vos.add(vo);		
				
			}			
			pageVo.setList(vos);
		}
		return pageVo;
	}
	
	public ViewpointVo getViewpointVo(Viewpoint po) {
		
		ViewpointVo vo = new ViewpointVo();
		BeanUtils.copyProperties(po, vo);
		if (po.getCtime()!=null) {
			vo.setShowCtime(DateUtils.dateToString(po.getCtime(), DateUtils.yyyy_MM_dd_HH_mm_ss));
		}
		
		if (po.getStatus()!=null) {
			vo.setShowStatus(statusMap.get(po.getStatus()));
		}
		if (po.getStatus()!=null) {
			vo.setShowStatus(statusMap.get(po.getStatus()));
		}
		if (po.getAffection()!=null) {
			vo.setShowAffection(articleAffectionMap.get(po.getAffection()));
		}
		return vo;	
	}
	
	public ECPage<UserInfoVo> getUserVoPage(ECPage<UserInfo> page) {
		ECPage<UserInfoVo> pageVo = new ECPage<UserInfoVo>();
		BeanUtils.copyProperties(page, pageVo, new String[] {"list"});
		List<UserInfo> pos = page.getList();
		if (!CollectionUtils.isEmpty(pos)) {
			List<UserInfoVo> vos = new ArrayList<UserInfoVo>();
			for (UserInfo po : pos) {
				UserInfoVo vo = getUserVo(po);
				vos.add(vo);				
			}			
			pageVo.setList(vos);
		}
		return pageVo;
	}
	
	public UserInfoVo getUserVo(UserInfo po){
		UserInfoVo vo = new UserInfoVo();
		BeanUtils.copyProperties(po, vo);
		if (po.getCtime()!=null) {
			vo.setShowCtime(DateUtils.dateToString(po.getCtime(), DateUtils.yyyy_MM_dd_HH_mm_ss));
		}
		if (po.getBrandId()!=null) {
			DictCarBrand brand = cache.getBrandMap().get(po.getBrandId());
			vo.setShowBrandId(brand==null?null:brand.getName());
		}
		if (po.getSerialId()!=null) {
			DictCarSerial serial = cache.getSerialMap().get(po.getSerialId());
			vo.setShowSerialId(serial==null?null:serial.getName());
		}
		vo.setShowEmail(po.getEmail());
		vo.setShowDisplayName(po.getDisplayName());
		vo.setShowLoginName(po.getLoginName());
		return vo;	
	}
}
