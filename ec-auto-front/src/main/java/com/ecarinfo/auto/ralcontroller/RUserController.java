package com.ecarinfo.auto.ralcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.ralasafe.base.RalBaseController;
import com.ecarinfo.ralasafe.po.RalOrg;
import com.ecarinfo.ralasafe.po.RalRole;
import com.ecarinfo.ralasafe.po.RalUser;
import com.ecarinfo.ralasafe.rm.RalOrgRM;
import com.ecarinfo.ralasafe.rm.RalRoleRM;
import com.ecarinfo.ralasafe.rm.RalUserRM;
import com.ecarinfo.ralasafe.service.RalUserService;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.ralasafe.utils.Json;
import com.ecarinfo.ralasafe.utils.MD5Util;

/**
 * 用户
 * @author test
 * 
 */
@Controller
@RequestMapping("/ruser")
public class RUserController extends RalBaseController {

	private static final Logger logger = Logger.getLogger(RUserController.class);

	@Resource
	private RalUserService ralUserService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listPost(String startTime, String endTime, String name, String loginName,String job, Integer depId, Integer isAway, Model model) {
		try {
			name = DtoUtil.incode(name);
			loginName = DtoUtil.incode(loginName);
			job = DtoUtil.incode(job);
			ECPage<com.ecarinfo.ralasafe.po.RalUser> ECPage = ralUserService.queryPage(startTime, endTime, name, loginName,job,depId, isAway);
			model.addAttribute("ECPage", ECPage);
		} catch (Exception e) {
			logger.error("用户列表加载失败", e);
		}
		return "/backend/ralasafe/user/list";
	}

	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI(ModelMap model) {
		List<RalOrg> orgs = genericService.findList(RalOrg.class, new Criteria().eq(RalOrgRM.disabled, 1));
		model.put("orgs", orgs);
		List<RalRole> roles = genericService.findAll(RalRole.class);
		model.put("roles", roles);
		return "/backend/ralasafe/user/add_update";
	}

	// 添加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Json add(@RequestBody RalUser dto) {
		Json json = new Json();
		try {
			dto.setPassword(MD5Util.md5AndSha(dto.getPassword()));
			if(existsLoginName(dto.getLoginName())){
				json.setSuccess(false);
				json.setMsg("已存在改登录名");
				return json;
			}
			dto.setDisabled(1);
			this.ralUserService.save(dto);
			json.setMsg("用户添加成功!");
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("用户添加失败!");
			logger.error("用户添加失败!", e);
		}
		return json;
	}

	// 寻找要修改的
	@RequestMapping(value = "/{id}/updateUI")
	public String edit(@PathVariable Integer id, ModelMap model) {
		RalUser dto = this.ralUserService.findById(id);
		model.put("dto", dto);
		List<RalRole> dtos = this.genericService.findList(RalRole.class, new Criteria().eq("1", "1").orderBy(RalRoleRM.roleId, OrderType.ASC));
		model.put("dtos", dtos);

		Set<RalRole> rrs = dto.getRoleSet();

		if (rrs != null && rrs.size() > 0) {
			List<RalRole> rrl = new ArrayList<RalRole>();
			rrl.addAll(rrs);
			model.put("roleId", rrl.get(0).getRoleId());
		}
		List<RalOrg> orgs = genericService.findList(RalOrg.class, new Criteria().eq(RalOrgRM.disabled, 1));
		model.put("orgs", orgs);
		List<RalRole> roles = genericService.findAll(RalRole.class);
		model.put("roles", roles);

		return "/backend/ralasafe/user/add_update";
	}

	// 修改
	@RequestMapping(value = "/update")
	@ResponseBody
	public Json update(@RequestBody RalUser dto) {
		Json json = new Json();
		try {
			Integer[] ids = {dto.getUserId()};
			if(existsLoginName(dto.getLoginName(),ids)){
				json.setSuccess(false);
				json.setMsg("已存在改登录名");
				return json;
			}
			if (!dto.getPassword().equals(dto.getOldPassword())) {
				dto.setPassword(MD5Util.md5AndSha(dto.getPassword()));
			}
			this.ralUserService.update(dto);
			json.setMsg("用户信息修改成功!");
		} catch (Exception e) {
			logger.error("用户信息修改失败!", e);
		}
		return json;
	}

	// 删除
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(Integer id,Integer disabled) {
		Json json = new Json();
		try {
			if (id != null ) {
				//this.ralUserService.delete(ids);
				RalUser ralUser = genericService.findByPK(RalUser.class, id);
				ralUser.setDisabled(disabled);
				genericService.update(ralUser);
				json.setMsg("用户删除成功!");
			} else {
				json.setSuccess(false);
				json.setMsg("用户删除失败!");
			}
		} catch (Exception e) {
			logger.error("用户删除失败!", e);
		}
		return json;
	}

	// 登录名是否重复
	@RequestMapping(value = "/checkName", method = RequestMethod.GET)
	@ResponseBody
	public Json checkName(String loginName) {
		Json json = new Json();
		int flag = 0;
		try {
			loginName = DtoUtil.incode(loginName);
			List<RalUser> dtos = this.genericService.findList(RalUser.class, new Criteria().eq(RalUserRM.loginName, loginName));
			flag = dtos.size();
		} catch (Exception e) {
			logger.error("检查登录名是否存在失败!", e);
		}
		json.setObj(flag);
		return json;
	}

	@RequestMapping(value = "/searchUI", method = RequestMethod.GET)
	public String searchUI(Model model) {
		List<RalOrg> orgs = genericService.findList(RalOrg.class, new Criteria().eq(RalOrgRM.disabled, 1));
		model.addAttribute("orgs", orgs);
		return "/backend/ralasafe/user/search";
	}

	// 修改密码界面
	@RequestMapping(value = "/updatePwdUI", method = RequestMethod.GET)
	public String updatePawUI(Model model) {
		RalUser user = EcUtil.getCurrentUser();
		model.addAttribute("user", user);
		return "/backend/ralasafe/user/update";
	}

	// 修改密码
	@RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
	@ResponseBody
	public Json updatePwd(@RequestBody RalUser dto) {
		Json json = new Json();
		try {
			RalUser user = EcUtil.getCurrentUser();
			// 是否修改用户密码
			if (!dto.getPassword().equals(dto.getOldPassword())) {
				this.genericService.updateWithCriteria(RalUser.class,
						new Criteria().update(RalUserRM.password, MD5Util.md5AndSha(dto.getPassword())).eq(RalUserRM.userId, user.getUserId()));
			}
			json.setMsg("管理员密码修改成功!");
		} catch (Exception e) {
			json.setSuccess(false);
			logger.error("管理员密码修改失败!", e);
			json.setMsg("管理员密码修改失败!");
		}
		return json;
	}

	// 选择用户
	@RequestMapping(value = "/selectUser", method = RequestMethod.GET)
	public String selectOrg(Model model) {
		Criteria whereBy = new Criteria();
		whereBy.eq(RalUserRM.disabled, 1);
		whereBy.orderBy(RalUserRM.level, OrderType.ASC);
		List<RalUser> dtos = super.genericService.findList(RalUser.class, whereBy);
		model.addAttribute("dtos", dtos);
		return "/backend/ralasafe/user/select_user";
	}
	
	private boolean existsLoginName(String loginName) {
		Criteria c = new Criteria();
		c.eq(RalUserRM.loginName, loginName);	
		c.eq(RalUserRM.disabled, 1);
		RalUser user = genericService.findOne(RalUser.class, c);
		if (user==null) {
			return false;
		} else {
			return true;
		}
	}
	
	
	private boolean existsLoginName(String loginName,Integer[] id) {
		Criteria c = new Criteria();
		c.eq(RalUserRM.loginName, loginName);	
		c.eq(RalUserRM.disabled, 1);
		c.notIn(RalUserRM.userId, id);
		RalUser user = genericService.findOne(RalUser.class, c);
		if (user!=null) {
			return true;
		} else {
			return false;
		}
	}
}
