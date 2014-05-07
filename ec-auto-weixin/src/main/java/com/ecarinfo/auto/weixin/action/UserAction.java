package com.ecarinfo.auto.weixin.action;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import com.ecarinfo.auto.po.UserInfo;
import com.ecarinfo.auto.po.WxUserInfo;
import com.ecarinfo.auto.rm.UserInfoRM;
import com.ecarinfo.auto.rm.WxUserInfoRM;
import com.ecarinfo.auto.weixin.util.AjaxUtils;
import com.ecarinfo.auto.weixin.util.MD5Utils;
import com.ecarinfo.common.utils.ReportMaker;
import com.ecarinfo.frame.httpserver.core.annotation.MessageModule;
import com.ecarinfo.frame.httpserver.core.annotation.RequestURI;
import com.ecarinfo.frame.httpserver.core.type.RequestMethod;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;

@Component
@MessageModule("user")
public class UserAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(UserAction.class);
	
	@RequestURI(value = "/login", method = RequestMethod.GET)
	public String login(String openId) {
		String html = null;
		Map<String, Object> model = new HashMap<String, Object>();		
		try {
			model.put("openId", openId);
			html = ReportMaker.exeute4Content(model, "user/login.ftl");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return html;
	}
	
	
	@RequestURI(value = "/dologin", method = RequestMethod.POST)
	public Object doLogin(String loginName, String passwd, String openId) {
		UserInfo user = baseService.findOne(UserInfo.class, new Criteria().eq(UserInfoRM.loginName, loginName).eq(UserInfoRM.passwd, MD5Utils.md5AndSha(passwd), CondtionSeparator.NONE));
		if (user!=null) {
			if (hasLogined(user.getId())) {
				return AjaxUtils.genError("error", "该账号已经被使用");
			}
			
			WxUserInfo wxUserInfo = baseService.findOneByAttr(WxUserInfo.class, WxUserInfoRM.openId, openId);
			if (wxUserInfo!=null) {
				wxUserInfo.setUserId(user.getId());
				baseService.update(wxUserInfo);
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", user.getId());
			map.put("openId", openId);
			wxApiService.sendCustomTextMsg(openId, String.format("尊敬的%s，欢迎登陆企业战略情报决策分析系统，您可以点击底部菜单进行相关操作。", user.getDisplayName()==null?"用户":user.getDisplayName()));
			
			return AjaxUtils.genSucc(map);
		}
		return AjaxUtils.genError("error", "用户名或者密码不正确");
	}
	
	private boolean hasLogined(Integer userId) {
		WxUserInfo wxUserInfo = baseService.findOneByAttr(WxUserInfo.class, WxUserInfoRM.userId, userId);
		return wxUserInfo==null?false:true;
	}
	
	@RequestURI(value = "/setting", method = RequestMethod.GET)
	public String setting(Integer userId) {
		String html = null;
		Map<String, Object> model = new HashMap<String, Object>();		
		try {
			UserInfo user = baseService.findByPK(UserInfo.class, userId);
			model.put("user", user);
			html = ReportMaker.exeute4Content(model, "user/setting.ftl");
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return html;
	}
	
	@RequestURI(value = "/dosetting", method = RequestMethod.POST)
	public Object doSetting(Integer userId, String displayName, String email, String telNo) {
		UserInfo user = baseService.findByPK(UserInfo.class, userId);
		if (user!=null) {
			user.setDisplayName(displayName);
			user.setEmail(email);
			user.setTelNo(telNo);
			baseService.update(user);
			return AjaxUtils.genSucc();
		}
		return AjaxUtils.genError("error", "用户不存在");
	}
}
