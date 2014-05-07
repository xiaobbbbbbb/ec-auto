package com.ecarinfo.auto.ralcontroller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.auto.util.DtoUtil;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.ralasafe.base.RalBaseController;
import com.ecarinfo.ralasafe.po.RalRole;
import com.ecarinfo.ralasafe.po.RalUser;
import com.ecarinfo.ralasafe.po.RalUserRole;
import com.ecarinfo.ralasafe.rm.RalRoleRM;
import com.ecarinfo.ralasafe.rm.RalUserRM;
import com.ecarinfo.ralasafe.rm.RalUserRoleRM;
import com.ecarinfo.ralasafe.service.RalRoleService;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.ralasafe.utils.EntityUtil;
import com.ecarinfo.ralasafe.utils.Json;

/**
 * 角色
 * 
 * @author
 * 
 */
@Controller
@RequestMapping("/rrole")
public class RRoleController extends RalBaseController {

	private static final Logger logger = Logger
			.getLogger(RRoleController.class);

	@Resource
	private RalRoleService ralRoleService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listPost(Model model, String name) throws UnsupportedEncodingException {
		if(name!=null){
			name=DtoUtil.incode(name);
		}
		ECPage<RalRole> ECPage = this.ralRoleService.queryPage(name, null);
		List<RalRole> rrs = ECPage.getList();
		Set<Integer> ids = new HashSet<Integer>();
		for (RalRole rr : rrs) {
			if (rr.getUpdateUserId() != null && rr.getUpdateUserId() > 0)
				ids.add(rr.getUpdateUserId());
		}

		System.err.println(ids.toString() + "d");
		List<RalUser> users = null;
		if (ids.size() > 0) {
			users = this.genericService.findList(RalUser.class,
					new Criteria().in(RalUserRM.userId, ids.toArray()));
		}

		Map<Object, RalUser> maps = null;
		if (users != null) {
			maps = EntityUtil.getField2EntityMap(users, "userId");
		}
		List<RalRole> dtos = new ArrayList<RalRole>();
		for (RalRole rr : rrs) {
			if (users != null) {
				RalUser user = maps.get(rr.getUpdateUserId());
				if (user != null) {
					rr.setUpdateUserName(user.getName() == null ? "" : user
							.getName());
				}
			}
			dtos.add(rr);
		}
		ECPage.setList(dtos);
		model.addAttribute("ECPage", ECPage);

		return "/backend/ralasafe/role/list";
	}

	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI() {
		return "/backend/ralasafe/role/add_update";
	}

	// 添加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Json add(@RequestBody RalRole dto) {
		Json json = new Json();
		try {
			if(existsRalRoleName(dto.getName())){
				json.setMsg("该角色已经存在!");
				json.setSuccess(false);
				return json;
			}
			dto.setUpdateUserId(EcUtil.getCurrentUser().getUserId());
			this.ralRoleService.save(dto);
			json.setMsg("角色添加成功!");
		} catch (Exception e) {
			logger.error("角色添加失败!", e);
		}
		return json;
	}

	/**
	 * 寻找要修改的
	 */
	@RequestMapping(value = "/{id}/updateUI", method = RequestMethod.GET)
	public String edit(@PathVariable Integer id, Model model) {
		RalRole dto = genericService.findByPK(RalRole.class, id);
		model.addAttribute("dto", dto);
		return "/backend/ralasafe/role/add_update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody RalRole dto) {
		Json json = new Json();
		try {
			RalRole role = genericService.findByPK(RalRole.class, dto.getRoleId());
			if (!role.getName().equals(dto.getName()) && existsRalRoleName(dto.getName()) ) {
				json.setMsg("该角色已经存在!");
				json.setSuccess(false);
				return json;
			}
			dto.setUpdateUserId(EcUtil.getCurrentUser().getUserId());
			this.ralRoleService.update(dto);
			json.setMsg("角色信息修改成功!");
		} catch (Exception e) {
			logger.error("角色信息修改失败!", e);
		}
		return json;
	}

	// 删除
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(Integer[] ids) {
		Json json = new Json();
		try {
			if (ids != null && ids.length > 0) {
				
				List<RalUserRole> list= this.genericService.findList(RalUserRole.class, new Criteria().in(RalUserRoleRM.roleId, ids));
				if(list!=null&&list.size()>0){
					json.setSuccess(false);
					json.setMsg("已有用户绑定此角色,不能删除!");
					return json;
				}
				this.ralRoleService.delete(ids);
				json.setMsg("角色删除成功!");
			} else {
				json.setSuccess(false);
				json.setMsg("角色删除失败!");
			}
		} catch (Exception e) {
			logger.error("角色删除失败!", e);
		}
		return json;
	}

	/**
	 * 选择角色界面
	 */
	@RequestMapping(value = "/selectRole", method = RequestMethod.GET)
	public String selectDep(Model model) {
		List<RalRole> dtos = this.genericService.findList(
				RalRole.class,
				new Criteria().eq("1", "1").orderBy(RalRoleRM.roleId,
						OrderType.ASC));
		model.addAttribute("dtos", dtos);
		return "/backend/ralasafe/role/select_role";
	}

	/**
	 * 删除角色之前的数据检验---根据多个角色id，获取角色集合
	 */
	@RequestMapping(value = "/delCheckData", method = RequestMethod.GET)
	@ResponseBody
	public Json delCheckData(Integer[] ids) {
		Json json = new Json();
		try {
			if (ids != null && ids.length > 0) {
				List<RalRole> dtos = this.ralRoleService.findByIds(ids);
				json.setObj(dtos.size());
			}
		} catch (Exception e) {
			logger.error("删除系统角色前检测关联数据失败!", e);
		}
		return json;
	}

	// 角色是否重复
	@RequestMapping(value = "/checkRoleName", method = RequestMethod.GET)
	@ResponseBody
	public Json checkModelName(String name) {
		Json json = new Json();
		long flag = 0;
		try {
			name = DtoUtil.incode(name);
			flag = this.genericService.count(RalRole.class,
					new Criteria().eq(RalRoleRM.name, name));
		} catch (Exception e) {
			logger.error("检查角色是否重复失败!", e);
		}
		json.setObj(flag);
		return json;
	}

	@RequestMapping(value = "/searchUI", method = RequestMethod.GET)
	public String searchUI(Model model) {
		return "/backend/ralasafe/role/search";
	}
	
	
	
	private boolean existsRalRoleName(String name) {
		Criteria c = new Criteria();
		c.eq(RalRoleRM.name, name);	
		RalRole role = genericService.findOne(RalRole.class, c);
		if (role==null) {
			return false;
		} else {
			return true;
		}
	}
}
