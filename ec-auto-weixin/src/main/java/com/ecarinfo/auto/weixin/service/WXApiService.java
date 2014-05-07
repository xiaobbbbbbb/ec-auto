package com.ecarinfo.auto.weixin.service;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ecarinfo.auto.po.SysConfig;
import com.ecarinfo.auto.rm.SysConfigRM;
import com.ecarinfo.auto.weixin.util.HttpClientUtils;
import com.ecarinfo.auto.weixin.util.WXApiUtils;
import com.ecarinfo.frame.httpserver.core.http.util.JsonUtils;
import com.ecarinfo.persist.exdao.GenericDao;

@Service
public class WXApiService {
	private static Logger logger = Logger.getLogger(WXApiService.class);
	@Resource
	private GenericDao baseDao;
	public boolean sendCustomTextMsg(String openId, String text) {
		SysConfig configAppId = baseDao.findOneByAttr(SysConfig.class, SysConfigRM.key, "weixinPublicAccount.appId");
		SysConfig configAppSecret = baseDao.findOneByAttr(SysConfig.class, SysConfigRM.key, "weixinPublicAccount.appSecret");
		String token = WXApiUtils.getAccessToken(configAppId.getValue(), configAppSecret.getValue());
		return WXApiUtils.sendCustomTextMsg(openId, token, text);		
	}
	
	public boolean deleteWxMenus() {
		SysConfig configAppId = baseDao.findOneByAttr(SysConfig.class, SysConfigRM.key, "weixinPublicAccount.appId");
		SysConfig configAppSecret = baseDao.findOneByAttr(SysConfig.class, SysConfigRM.key, "weixinPublicAccount.appSecret");
		return deleteWxMenus(configAppId.getValue(), configAppSecret.getValue());
	}
	
	public boolean deleteWxMenus(String appId, String appSecret) {
		String token = WXApiUtils.getAccessToken(appId, appSecret);
		boolean success = false;
		String respStr = null;
		try {
			respStr = HttpClientUtils.httpPostForHtml("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + token);
			int errcode = JsonUtils.getObjectMapper().readTree(respStr.trim()).get("errcode").asInt();
			if (errcode==0) {
				success = true;
			}
		} catch (IOException e) {
			success = false;
			logger.error("==============delete menu failed", e);
		}
		return success;
	}
	public boolean createWxMenus() {
		SysConfig configAppId = baseDao.findOneByAttr(SysConfig.class, SysConfigRM.key, "weixinPublicAccount.appId");
		SysConfig configAppSecret = baseDao.findOneByAttr(SysConfig.class, SysConfigRM.key, "weixinPublicAccount.appSecret");
		return createWxMenus(configAppId.getValue(), configAppSecret.getValue());
	}
	
	public boolean createWxMenus(String appId, String appSecret) {		
		String token = WXApiUtils.getAccessToken(appId, appSecret);
		
		boolean success = false;
		StringBuffer buf = new StringBuffer();
		buf.append("{\"button\":[");
		
			buf.append("{\"name\": \"产品评价\", \"sub_button\": [");
			buf.append(String.format("{\"type\": \"click\",\"name\": \"%s\",\"key\": \"%s\"},", "产品观点", "VIEWPOINT"));
			buf.append(String.format("{\"type\": \"click\",\"name\": \"%s\",\"key\": \"%s\"}", "7天关注趋势", "SEVEN_DAY_ATTENTION_TREND"));
			buf.append("]},");
			buf.append(String.format("{\"type\": \"click\",\"name\": \"%s\",\"key\": \"%s\"},", "负面情报", "BAD_INDUSTRY_ARTICLE"));
			buf.append("{\"name\": \"更多\", \"sub_button\": [");
			buf.append(String.format("{\"type\": \"click\",\"name\": \"%s\",\"key\": \"%s\"},", "行业动态", "INDUSTRY_ARTICLE"));
			buf.append(String.format("{\"type\": \"click\",\"name\": \"%s\",\"key\": \"%s\"}", "个人设置", "SETTING"));
			buf.append("]}");
			
		buf.append("]}");
		logger.info("[WX_MENUS]:" + buf.toString());
		String respStr = null;
		try {
			respStr = HttpClientUtils.httpPostTextForHtml("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + token, null, buf.toString());
			int errcode = JsonUtils.getObjectMapper().readTree(respStr.trim()).get("errcode").asInt();
			if (errcode==0) {
				success = true;
			} else {
				logger.error("create_menu error:" + respStr);
			}
		} catch (IOException e) {
			logger.error("create_menu error..", e);
		}
		return success;
	}
	
	public static void main(String[] args) {
		new WXApiService().deleteWxMenus("wxb2bd45977c585979", "ef45a0bea2de0ca88a165cfa8f3fa5cb");
		new WXApiService().createWxMenus("wxb2bd45977c585979", "ef45a0bea2de0ca88a165cfa8f3fa5cb");
	}
}
