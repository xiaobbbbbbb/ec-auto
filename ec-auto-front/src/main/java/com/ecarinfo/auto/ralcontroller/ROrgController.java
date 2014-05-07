package com.ecarinfo.auto.ralcontroller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.ralasafe.base.RalBaseController;
import com.ecarinfo.ralasafe.dto.ZtreeDto;
import com.ecarinfo.ralasafe.po.RalOrg;
import com.ecarinfo.ralasafe.po.RalUser;
import com.ecarinfo.ralasafe.po.RalUserRole;
import com.ecarinfo.ralasafe.rm.RalGroupRM;
import com.ecarinfo.ralasafe.rm.RalOrgRM;
import com.ecarinfo.ralasafe.rm.RalUserRM;
import com.ecarinfo.ralasafe.rm.RalUserRoleRM;
import com.ecarinfo.ralasafe.service.RalOrgService;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.ralasafe.utils.EntityUtil;
import com.ecarinfo.ralasafe.utils.Json;

/**
 * @Description: 机构管理
 * @Date: 2013-1-14
 * @Version: V1.0
 */

@Controller
@RequestMapping("rorg")
public class ROrgController extends RalBaseController {

	private static final Logger logger = Logger.getLogger(ROrgController.class);

	@Resource
	private RalOrgService ralOrgService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listPost(Integer groupId, String name, String code, Integer disabled, Model model) {
		try {
			name = DtoUtil.incode(name);
//			ECPage<RalOrg> ECPage=PagingManager.list(genericService, RalOrg, criteria)
			
			ECPage<RalOrg> ECPage = this.ralOrgService.queryOrgPages(groupId, name, code, disabled);

			List<RalOrg> orgs = ECPage.getList();
			Set<Integer> ids = new HashSet<Integer>();

			Set<Integer> pids = new HashSet<Integer>();

			for (RalOrg org : orgs) {
				if (org.getUpdateUserId() != null && org.getUpdateUserId() > 0)
					ids.add(org.getUpdateUserId());

				if (org.getParentId() != null && org.getParentId() > 0)
					pids.add(org.getParentId());
			}
			List<RalUser> users = null;
			if (ids.size() > 0) {
				users = this.genericService.findList(RalUser.class, new Criteria().in(RalUserRM.userId, ids.toArray()));
			}

			List<RalOrg> porgs = null;
			if (pids.size() > 0) {
				porgs = this.genericService.findList(RalOrg.class, new Criteria().in(RalOrgRM.orgId, pids.toArray()));
			}
			Map<Object, RalUser> maps = EntityUtil.getField2EntityMap(users, "userId");

			Map<Object, RalOrg> pmaps = null;
			if (porgs != null) {
				pmaps = EntityUtil.getField2EntityMap(porgs, "orgId");
			}

			List<RalOrg> dtos = new ArrayList<RalOrg>();
			for (RalOrg org : orgs) {
				RalUser user = maps.get(org.getUpdateUserId());
				if (user != null)
					org.setUpdateUserName(user.getName());

				if (porgs != null) {
					RalOrg porg = pmaps.get(org.getParentId());
					if (porg != null)
						org.setParentName(porg.getName());
				}
				dtos.add(org);
			}

			ECPage.setList(dtos);

			model.addAttribute("ECPage", ECPage);
			return "/backend/ralasafe/org/list";
		} catch (Exception e) {
			logger.error("部门列表加载失败", e);
		}
		return null;
	}

	/**
	 * 获取部门树(showUrl:表示是否显示链接)
	 */
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	@ResponseBody
	public List<ZtreeDto> getZtree(Boolean showUrl, Integer id, HttpServletRequest request) {
		List<ZtreeDto> zTree = new ArrayList<ZtreeDto>();
		try {
			Integer pid = (id != null && id > 0) ? id : 0;
			List<RalOrg> dtos = this.genericService.findList(RalOrg.class,
					new Criteria().eq(RalGroupRM.disabled, 1).eq(RalOrgRM.parentId, pid, CondtionSeparator.AND));

			String webpath = request.getContextPath();
			String target = "org_rightFrame";
			String url = webpath + "/org/updateUI?id=";
			for (RalOrg dto : dtos) {
				ZtreeDto tree = new ZtreeDto();
				tree.setId(dto.getGroupId());
				tree.setName(dto.getName());
				if (dto.getIsLeaf() > 0) {
					tree.setIsParent(true);
					tree.setOpen(true);
				}
				if (showUrl) {
					tree.setUrl(url + dto.getGroupId());
					tree.setTarget(target);
				}
				tree.setpId(dto.getParentId());
				zTree.add(tree);
			}
		} catch (Exception e) {
			logger.error("部门信息获取失败!", e);
		}
		return zTree;
	}

	/**
	 * 寻找要修改的
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable int id, Model model) {
		RalOrg dto = this.genericService.findByPK(RalOrg.class, id);
		int pid = dto.getParentId();
		if (pid > 0) {
			RalOrg pDto = this.genericService.findByPK(RalOrg.class, pid);
			dto.setParentName(pDto.getName());
		}
		model.addAttribute("dto", dto);
		List<RalOrg> orgs = genericService.findList(RalOrg.class, new Criteria().eq(RalOrgRM.disabled, 1));
		model.addAttribute("orgs", orgs);
		return "/backend/ralasafe/org/add_update";
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Json update(@RequestBody RalOrg dto) {
		Json json = new Json();
		try {
			dto.setUpdateUserId(EcUtil.getCurrentUser().getUserId());
			this.ralOrgService.update(dto);
			json.setMsg("部门信息修改成功!");
		} catch (Exception e) {
			logger.error("部门信息修改失败!", e);
		}
		return json;
	}

	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI(Model model) {
		List<RalOrg> orgs = genericService.findList(RalOrg.class, new Criteria().eq(RalOrgRM.disabled, 1));
		model.addAttribute("orgs", orgs);
		return "/backend/ralasafe/org/add_update";
	}

	/**
	 * 选择部门界面
	 */
	@RequestMapping(value = "/selectOrg", method = RequestMethod.GET)
	public String selectDep() {
		return "/backend/ralasafe/org/select_org";
	}

	/**
	 * 部门名称检查
	 */
	@RequestMapping(value = "/checkName", method = RequestMethod.GET)
	@ResponseBody
	public Json checkName(String name) {
		Json json = new Json();
		long count = 0;
		try {
			name = DtoUtil.incode(name);
			count = this.genericService.count(RalOrg.class, new Criteria().eq(RalOrgRM.name, name));
		} catch (Exception e) {
			logger.error("部门检查失败!", e);
		}
		json.setObj(count);
		return json;
	}

	// 添加部门
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Json add(@RequestBody RalOrg dto) {
		Json json = new Json();
		try {
			dto.setUpdateUserId(EcUtil.getCurrentUser().getUserId());
			dto.setIsLeaf(0);
			this.ralOrgService.save(dto);
			json.setMsg("部门添加成功!");
		} catch (Exception e) {
			logger.error("部门添加失败!", e);
		}
		return json;
	}

	// 启用停用（删除）
	@RequestMapping(value = "/{id}/delete")
	@ResponseBody
	public Json delete(@PathVariable Integer id, Integer disabled) {
		Json json = new Json();
		try {
			List<RalUser> list= this.genericService.findList(RalUser.class, new Criteria().eq(RalUserRM.orgId, id));
			if(list!=null&&list.size()>0){
				json.setSuccess(false);
				json.setMsg("部门已存在操作员，无法停用!");
				return json;
			}
			RalOrg ralOrg = genericService.findByPK(RalOrg.class, id);
			ralOrg.setDisabled(disabled);
			genericService.update(ralOrg);

			json.setMsg("部门删除成功!");
		} catch (Exception e) {
			logger.error("部门删除失败!", e);
		}
		return json;
	}

	/**
	 * 删除角色之前的数据检验---根据多个角色id，获取角色集合
	 */
	@RequestMapping(value = "/delCheckData", method = RequestMethod.GET)
	@ResponseBody
	public Json delCheckData(Integer ids) {
		Json json = new Json();
		try {
			if (ids != null) {
				List<RalUser> dtos = this.genericService.findByAttr(RalUser.class, RalUserRM.orgId, ids);
				json.setObj(dtos.size());
			}
		} catch (Exception e) {
			logger.error("删除部门前前检测部门下是否有用户数据失败!", e);
		}
		return json;
	}

	@RequestMapping(value = "/searchUI", method = RequestMethod.GET)
	public String searchUI(Model model) {
		return "/backend/ralasafe/org/search";
	}

}
