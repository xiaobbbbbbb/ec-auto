package com.ecarinfo.auto.backend.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.ecarinfo.auto.backend.web.util.AjaxUtils;
import com.ecarinfo.auto.backend.web.util.MD5Utils;
import com.ecarinfo.auto.backend.web.util.WebUtils;
import com.ecarinfo.ralasafe.po.RalUser;
import com.ecarinfo.auto.rm.RalUserRM;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;

@RequestMapping("raluser")
@Controller
public class RalUserAction extends BaseAction{
	private static final Logger logger = Logger.getLogger(RalUserAction.class);
	
	@RequestMapping(value = "/dologin",method =RequestMethod.POST)
	public void dologin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		logger.debug(String.format("[ACTION_PARAMS]:{username:%s, password:%s}", username, password));
		
		if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) { 
			RalUser user = baseService.findOne(RalUser.class, new Criteria().eq(RalUserRM.loginName, username).eq(RalUserRM.password, MD5Utils.md5AndSha(password), CondtionSeparator.AND));
			if (user!=null) {
				WebUtils.setSession(request, WebUtils.SESSION_KEY_USER, user);
				AjaxUtils.printSucc(response);
			} else {
				AjaxUtils.printError(response, "login error", "用户名不存在或者密码错误");
			}
		} else {
			AjaxUtils.printError(response, "login error", "用户名或者密码为空");
		}
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		WebUtils.setSession(request, WebUtils.SESSION_KEY_USER, null);
		return "/raluser/login";
	}
}
