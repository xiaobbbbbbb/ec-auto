package com.ecarinfo.auto.ralcontroller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.auto.util.EntityUtil;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.ralasafe.base.RalBaseController;
import com.ecarinfo.ralasafe.po.RalGroup;
import com.ecarinfo.ralasafe.po.RalUser;
import com.ecarinfo.ralasafe.rm.RalGroupRM;
import com.ecarinfo.ralasafe.rm.RalUserRM;
import com.ecarinfo.ralasafe.utils.EcUtil;
import com.ecarinfo.ralasafe.utils.Json;

/**
 * @Description: 机构管理
 * @Date: 2013-1-14
 * @Version: V1.0
 */

@Controller
@RequestMapping("rgroup")
public class RGroupController extends RalBaseController {

	private static final Logger logger = Logger.getLogger(RGroupController.class);

	@RequestMapping(value = "/list")
	public String back(ModelMap map, HttpServletRequest request) {
		List<RalGroup> groups = genericService.findAll(RalGroup.class);
		Set<Integer> ids = new HashSet<Integer>();
		for (RalGroup group : groups) {
			if (group.getUpdateUserId() != null && group.getUpdateUserId() > 0)
				ids.add(group.getUpdateUserId());
		}

		List<RalUser> users = this.genericService.findList(RalUser.class, new Criteria().in(RalUserRM.userId, ids.toArray()));
		Map<Object, RalUser> maps = EntityUtil.getField2EntityMap(users, "userId");
		List<RalGroup> dtos = new ArrayList<RalGroup>();
		for (RalGroup group : groups) {
			RalUser user = maps.get(group.getUpdateUserId());
			group.setUpdateUserName(user.getName());
			dtos.add(group);
		}
		map.put("groups", dtos);
		return "/backend/ralasafe/group/list";
	}

	/**
	 * 寻找要修改的
	 */
	@RequestMapping(value = "/{id}/edit")
	public String edit(@PathVariable int id, Model model) {
		RalGroup dto = this.genericService.findByPK(RalGroup.class, id);
		model.addAttribute("dto", dto);
		return "backend/ralasafe/group/add_update";
	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Json update(@RequestBody RalGroup dto) {
		Json json = new Json();
		try {
			this.genericService.updateWithCriteria(RalGroup.class, new Criteria().update(RalGroupRM.name, dto.getName()).eq(RalGroupRM.pk, dto.getGroupId())
					.update(RalGroupRM.updateTime, DateUtils.dateToString(new Date(), TimeFormatter.FORMATTER1)).update(RalGroupRM.updateUserId, EcUtil.getCurrentUser().getUserId())
						);
			json.setMsg("公司信息修改成功!");
		} catch (Exception e) {
			logger.error("公司信息修改失败!", e);
		}
		return json;
	}
}
