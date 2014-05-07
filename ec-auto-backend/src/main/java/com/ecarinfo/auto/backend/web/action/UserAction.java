package com.ecarinfo.auto.backend.web.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.auto.backend.web.util.AjaxUtils;
import com.ecarinfo.auto.backend.web.util.MD5Utils;
import com.ecarinfo.auto.backend.web.util.WebUtils;
import com.ecarinfo.auto.backend.web.vo.EasyUIPage;
import com.ecarinfo.auto.po.DictCarBrand;
import com.ecarinfo.auto.po.DictCarSerial;
import com.ecarinfo.auto.po.UserInfo;
import com.ecarinfo.auto.rm.UserInfoRM;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;

@RequestMapping("user")
@Controller
public class UserAction extends BaseAction{
	private static final Logger logger = Logger.getLogger(UserAction.class);

	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request) {	
		request.setAttribute("menu_prefix", "user");
		List<DictCarBrand> brandLists = baseService.findAll(DictCarBrand.class);
		request.setAttribute("brandLists", brandLists);//厂牌列表
		return "/user/index";
	}
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public EasyUIPage list(HttpServletRequest request) {
		ECPage<UserInfo> pageHolder = PagingManager.list(baseService, UserInfo.class, WebUtils.getPageCriteria(request));	
		return EasyUIPage.from(voHelper.getUserVoPage(pageHolder));
	}
	
	@RequestMapping(value = "/doadd")
	public void doAdd(UserInfo user, HttpServletResponse response) {
		if (user!=null) {
			if (existsUserLoginName(user.getLoginName())) {
				AjaxUtils.printError(response, "exist loginName", "登录名已经存在！");
				return;
			}
			user.setPasswd(MD5Utils.md5AndSha(user.getPasswd()));
			user.setCtime(new Date());
			baseService.save(user);
			AjaxUtils.printSucc(response);
		}
	}
	
	private boolean existsUserLoginName(String loginName) {
		Criteria c = new Criteria();
		c.eq(UserInfoRM.loginName, loginName);		
		UserInfo user = baseService.findOne(UserInfo.class, c);
		if (user==null) {
			return false;
		} else {
			return true;
		}
	}
	
	@RequestMapping(value = "/edit")
	@ResponseBody
	public UserInfo edit(Integer userId, HttpServletRequest request) {
		UserInfo user = baseService.findByPK(UserInfo.class, userId);
		List<DictCarBrand> brandLists = baseService.findAll(DictCarBrand.class);
		request.setAttribute("brandLists", brandLists);//厂牌列表
		return user;
	}
	
	synchronized
	@RequestMapping(value = "/doedit")
	public void doEdit(UserInfo form, HttpServletResponse response) {
		UserInfo newUser = baseService.findByPK(UserInfo.class, form.getId());
		
		if (!newUser.getLoginName().equals(form.getLoginName()) && existsUserLoginName(form.getLoginName())) {
			AjaxUtils.printError(response, "exist loginName", "登录名已经存在！");
			return;
		}
		newUser.setDisplayName(form.getDisplayName());
		newUser.setEmail(form.getEmail());
		newUser.setLoginName(form.getLoginName());
		newUser.setBrandId(form.getBrandId());
		newUser.setSerialId(form.getSerialId());
		newUser.setPasswd(MD5Utils.md5AndSha(form.getPasswd()));
		baseService.update(newUser);
		AjaxUtils.printSucc(response);
	}
	
	@RequestMapping(value = "/dodelete")
	public void doDelete(Integer userId, HttpServletResponse response) {
		baseService.deleteByPK(UserInfo.class, userId);
		AjaxUtils.printSucc(response);
	}
	
	@RequestMapping(value = "/download")
	public void download() {
		ECPage<UserInfo> pageHolder = PagingManager.list(baseService, UserInfo.class, null);			
	}
	
	
	/**
	 * 厂牌id查询车系信息
	 * @param brandId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/serialIds")
	@ResponseBody
	public List<DictCarSerial> getCarSerialByAreaId(Integer brandId, HttpServletRequest request) {
		return cache.getBrandId2SerialsMap().get(brandId);
	}
}
