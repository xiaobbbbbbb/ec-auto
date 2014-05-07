package com.ecarinfo.auto.ralcontroller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.ralasafe.base.RalBaseController;
import com.ecarinfo.ralasafe.dto.ZtreeDto;
import com.ecarinfo.ralasafe.po.RalResource;
import com.ecarinfo.ralasafe.service.RalResourceService;
import com.ecarinfo.ralasafe.utils.DtoUtil;
import com.ecarinfo.ralasafe.utils.Json;

/**
 * @Description: 部门管理
 * @Date: 2013-1-14
 * @Version: V1.0
 */

@Controller
@RequestMapping("rresource")
public class RResourceController extends RalBaseController {

	private static final Logger logger = Logger.getLogger(RResourceController.class);

	@Resource
	private RalResourceService ralResourceService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String rightTopUI() {
		return "ralasafe/resource/list";
	}

	@RequestMapping(value = "/defaultUI", method = RequestMethod.GET)
	public String defaultUI() {
		return "ralasafe/resource/default";
	}

	@RequestMapping(value = "/addUI", method = RequestMethod.GET)
	public String addUI(String pname, Model model) {
		try {
			pname = DtoUtil.incode(pname);
			if (pname != null) {
				model.addAttribute("pname", pname);
			}
			return "ralasafe/resource/add_update";
		} catch (Exception e) {
			logger.error("导航到资源添加页面错误", e);
		}
		return null;
	}

	/**
	 * 获取资源树(showUrl:表示是否显示链接)
	 */
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	@ResponseBody
	public List<ZtreeDto> getZtree(boolean showUrl, String roleId, String id) {
		List<ZtreeDto> dto = null;
		try {
			int pid = 0;
			if (id != null && id.trim().length() > 0) {
				pid = Integer.parseInt(id);
			}
			String target = "resource_rightFrame";
			String url = "ralasafe/resource/updateUI";
			dto = this.ralResourceService.getZtree(showUrl, roleId, pid, target, url, null);
		} catch (Exception e) {
			logger.error("资源树获取失败!", e);
		}
		return dto;
	}

	// 添加
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Json add(@RequestBody RalResource dto) {
		Json json = new Json();
		try {
			this.ralResourceService.save(dto);
			json.setMsg("资源添加成功!");
		} catch (Exception e) {
			logger.error("资源添加失败!", e);
		}
		return json;
	}

	/**
	 * 寻找要修改的
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable int id, Model model) {
		RalResource dto = this.genericService.findByPK(RalResource.class, id);
		int pid = dto.getParentId();
		if (pid > 0) {
			RalResource pDto = this.genericService.findByPK(RalResource.class, pid);
			dto.setParentName(pDto.getName());
		}
		model.addAttribute("dto", dto);
		return "ralasafe/resource/add_update";
	}

	// 删除
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(int id) {
		Json json = new Json();
		try {
			this.ralResourceService.delete(id);
			json.setMsg("资源删除成功!");
		} catch (Exception e) {
			logger.error("资源删除失败!", e);
		}
		return json;
	}

	// 修改
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody RalResource dto) {
		Json json = new Json();
		try {
			this.genericService.update(dto);
			json.setMsg("资源信息修改成功!");
		} catch (Exception e) {
			logger.error("资源信息修改失败!", e);
		}
		return json;
	}

	/**
	 * 删除资源之前的数据检验---根据多个资源id，获取资源集合
	 */
	@RequestMapping(value = "/delCheckData", method = RequestMethod.GET)
	@ResponseBody
	public Json delCheckData(Integer[] ids) {
		Json json = new Json();
		try {
			if (ids != null && ids.length > 0) {
				List<RalResource> dtos = this.ralResourceService.findByIds(ids);
				json.setObj(dtos.size());
			}
		} catch (Exception e) {
			logger.error("删除系统资源前检测关联数据失败!", e);
		}
		return json;
	}
}
