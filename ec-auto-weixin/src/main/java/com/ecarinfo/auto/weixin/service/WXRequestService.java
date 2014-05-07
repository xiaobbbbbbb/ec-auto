package com.ecarinfo.auto.weixin.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ecarinfo.auto.po.IndustryArticle;
import com.ecarinfo.auto.po.WxUserInfo;
import com.ecarinfo.auto.rm.IndustryArticleRM;
import com.ecarinfo.auto.rm.WxUserInfoRM;
import com.ecarinfo.auto.weixin.dto.request.FullWXRequest;
import com.ecarinfo.auto.weixin.util.ConfigUtils;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;

/**
 * 	VIEWPOINT, //产品观点
	SEVEN_DAY_ATTENTION_TREND, //7天关注趋势	
	BAD_INDUSTRY_ARTICLE, //负面情报
	INDUSTRY_ARTICLE, //行业动态
	SETTING	 //设置
 * @author zhanglu
 *
 */
@Service
public class WXRequestService {
	@Resource
	ConfigUtils configUtils;
	
	@Resource
	private GenericDao baseDao;
	
	public Map<String, String> clickViewpoint(FullWXRequest wx) {
		if (!wx.hasLogined()) {
			return suggestLogin(wx);
		}
		
		Map<String, String> model = new HashMap<String, String>();

		model.put("msgType", "news1");
		model.put("${toUserName}", wx.getFromUserName());
		model.put("${fromUserName}", wx.getToUserName());
		model.put("${createTime}", String.valueOf(new Date().getTime()));		
		model.put("${title1}", "点此进入产品观点页面");
		model.put("${url1}", String.format("%s/viewpoint/index?userId=%s", configUtils.getDomain(), wx.getUserId()));
		model.put("${picUrl1}", String.format("%s/static/image/viewpoint.png", configUtils.getDomain()));
		return model;	
	}
	
	public Map<String, String> clickSevenDayAttentionTrend(FullWXRequest wx) {
		if (!wx.hasLogined()) {
			return suggestLogin(wx);
		}
		
		Map<String, String> model = new HashMap<String, String>();

		model.put("msgType", "news1");
		model.put("${toUserName}", wx.getFromUserName());
		model.put("${fromUserName}", wx.getToUserName());
		model.put("${createTime}", String.valueOf(new Date().getTime()));		
		model.put("${title1}", "点此进入7天关注趋势页面");
		model.put("${url1}", String.format("%s/article/seven_day_attention_trend?userId=%s", configUtils.getDomain(), wx.getUserId()));
		model.put("${picUrl1}", String.format("%s/static/image/seven_trend.png", configUtils.getDomain()));
		return model;	
	}

	public Map<String, String> clickBadArticle(FullWXRequest wx) {
		if (!wx.hasLogined()) {
			return suggestLogin(wx);
		}
		
		Map<String, String> model = new HashMap<String, String>();
		model.put("msgType", "news1");
		model.put("${toUserName}", wx.getFromUserName());
		model.put("${fromUserName}", wx.getToUserName());
		model.put("${createTime}", String.valueOf(new Date().getTime()));		
		model.put("${title1}", "点此进入负面情报页面");
		model.put("${url1}", String.format("%s/article/bad_article_index?userId=%s", configUtils.getDomain(), wx.getUserId()));
		model.put("${picUrl1}", String.format("%s/static/image/bad_article.png", configUtils.getDomain()));
		return model;	
	}

	public Map<String, String> clickIndustryArticle(FullWXRequest wx) {
		if (!wx.hasLogined()) {
			return suggestLogin(wx);
		}
	
		ECPage<IndustryArticle> pager = PagingManager.list(baseDao, IndustryArticle.class, new Criteria().eq(IndustryArticleRM.status, 1).setPage(1, 3).orderBy(IndustryArticleRM.ctime, OrderType.DESC));
		
		Map<String, String> model = new HashMap<String, String>();
		if (!CollectionUtils.isEmpty(pager.getList())) {		
			for (int i=1; i<=pager.getList().size(); i++) {
				model.put("${title" + i + "}", pager.getList().get(i-1).getTitle());
				model.put("${url" + i + "}", String.format("%s/industry_article/detail?userId=%s&articleId=%s", configUtils.getDomain(), wx.getUserId(), pager.getList().get(i-1).getId()));
			}
			model.put("${picUrl1}", String.format("%s/static/image/04.png", configUtils.getDomain()));
			if (pager.getTotalPage()>1) {
				model.put("${title4}", "查看更多  >>");
				model.put("${url4}", String.format("%s/industry_article/index?userId=%s", configUtils.getDomain(), wx.getUserId()));
				model.put("msgType", "news" + 4);
			} else {
				model.put("msgType", "news" + pager.getList().size());
			}
			
		} else {
			model.put("msgType", "text");
			model.put("${content}", String.format("<a href='%s/industry_article/index?userId=%s'>点此进入产业动态页面</a>", configUtils.getDomain(), wx.getUserId()));
		}
		
		model.put("${toUserName}", wx.getFromUserName());
		model.put("${fromUserName}", wx.getToUserName());
		model.put("${createTime}", String.valueOf(new Date().getTime()));		
		
		return model;		
	}
	
	public Map<String, String> clickSetting(FullWXRequest wx) {
		if (!wx.hasLogined()) {
			return suggestLogin(wx);
		}
		
		Map<String, String> model = new HashMap<String, String>();
		model.put("msgType", "text");
		model.put("${toUserName}", wx.getFromUserName());
		model.put("${fromUserName}", wx.getToUserName());
		model.put("${createTime}", String.valueOf(new Date().getTime()));		
		model.put("${content}", String.format("<a href='%s/user/setting?userId=%s'>点此进入设置页面</a>", configUtils.getDomain(), wx.getUserId()));
		return model;		
	}

	public Map<String, String> subscribe(FullWXRequest wx) {
		WxUserInfo wxUser = baseDao.findOneByAttr(WxUserInfo.class, WxUserInfoRM.openId, wx.getOpenId());
		boolean add = false;
		if (wxUser==null) {
			add = true;
			wxUser = new WxUserInfo();
		}
		wxUser.setOpenId(wx.getOpenId());
		wxUser.setSubscribe(1);
		wxUser.setSubscribeTime(new Date());
		if (add) {
			baseDao.insert(wxUser);
		} else {
			baseDao.update(wxUser);
		}		
		
		Map<String, String> model = new HashMap<String, String>();
		model.put("msgType", "text");
		model.put("${toUserName}", wx.getFromUserName());
		model.put("${fromUserName}", wx.getToUserName());
		model.put("${createTime}", String.valueOf(new Date().getTime()));		
		model.put("${content}", String.format("您好，欢迎关注企业战略情报决策分析系统。<a href='%s/user/login?openId=%s'>点此登录</a>", configUtils.getDomain(), wx.getOpenId()));
		return model;
	}

	public void unsubscribe(FullWXRequest wx) {
		WxUserInfo wxUser = baseDao.findOneByAttr(WxUserInfo.class, WxUserInfoRM.openId, wx.getOpenId());
		if (wxUser!=null) {
			wxUser.setSubscribe(0);
			wxUser.setUnsubscribeTime(new Date());
		}
	}

	private Map<String, String> suggestLogin(FullWXRequest wx) {		
		Map<String, String> model = new HashMap<String, String>();
		model.put("msgType", "text");
		model.put("${toUserName}", wx.getFromUserName());
		model.put("${fromUserName}", wx.getToUserName());
		model.put("${createTime}", String.valueOf(new Date().getTime()));		
		model.put("${content}", String.format("您尚未登录。请<a href='%s/user/login?openId=%s'>点此登录</a>", configUtils.getDomain(), wx.getOpenId()));
		return model;
	}
	
	
}
