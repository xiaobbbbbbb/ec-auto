package com.ecarinfo.auto.ralcontroller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.auto.base.BaseController;
import com.ecarinfo.auto.vo.AjaxJson;
import com.ecarinfo.ralasafe.service.LoginService;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.ralasafe.utils.MD5Util;

@Controller
@RequestMapping("/eclogin")
public class LoginController extends BaseController {

	@Resource
	private LoginService loginService;

	@RequestMapping(value = "/login")
	public String login() {
		return "/login";
	}

	@RequestMapping(value = "/dologin")
	@ResponseBody
	public AjaxJson doLogin(String loginName, String passwd, HttpServletRequest request) {
		AjaxJson jsonObj = new AjaxJson();
		passwd = MD5Util.md5AndSha(passwd);
		boolean result = this.loginService.loginIn(loginName, passwd);
		System.err.println(passwd);
		System.err.println(result);
		com.ecarinfo.ralasafe.po.RalUser user=EcUtil.getCurrentUser();
		request.getSession().setAttribute("user", user);
		if (result) {
			jsonObj.setSuccess(true);
			jsonObj.setMsg("登陆成功");
			return jsonObj;
		} else {
			jsonObj.setSuccess(false);
			jsonObj.setMsg("用户名密码错误！");
			return jsonObj;
		}
	}

	@RequestMapping(value = "/exit")
	public String saveExit(HttpServletRequest request) {
		if (loginService.loginOut())
			return REDIRECT + "/eclogin/login/";
		else
			return null;
	}

	@RequestMapping(value = "/back_index")
	public String back(HttpServletRequest request) {
		return "/backend/index";
	}
}
