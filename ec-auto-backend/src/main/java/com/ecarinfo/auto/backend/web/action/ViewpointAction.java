package com.ecarinfo.auto.backend.web.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.auto.backend.web.helper.search.ViewpointSearchHelper;
import com.ecarinfo.auto.backend.web.util.AjaxUtils;
import com.ecarinfo.auto.backend.web.vo.EasyUIPage;
import com.ecarinfo.auto.po.Viewpoint;
import com.ecarinfo.auto.rm.ViewpointRM;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;

@RequestMapping("viewpoint")
@Controller
public class ViewpointAction extends BaseAction{
	private static final Logger logger = Logger.getLogger(ViewpointAction.class);

	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request) {	
		request.setAttribute("menu_prefix", "dict");
		return "/viewpoint/index";
	}
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public EasyUIPage list(HttpServletRequest request) {
		ECPage<Viewpoint> pageHolder = PagingManager.list(baseService, Viewpoint.class, ViewpointSearchHelper.getSearchCriteria(request));			
		return EasyUIPage.from(voHelper.getViewpointVoPage(pageHolder));
	}
	
	@RequestMapping(value = "/doadd")
	public void doAdd(Viewpoint viewpoint, HttpServletResponse response) {
		if (viewpoint!=null) {
			if (existsViewpointName(viewpoint.getName())) {
				AjaxUtils.printError(response, "exist name", "观点名已经存在！");
				return;
			}
			viewpoint.setCtime(new Date());
			viewpoint.setStatus(1);
			baseService.save(viewpoint);
			AjaxUtils.printSucc(response);
		}
	}
	
	private boolean existsViewpointName(String name) {
		Criteria c = new Criteria();
		c.eq(ViewpointRM.name, name);		
		Viewpoint viewpoint = baseService.findOne(Viewpoint.class, c);
		if (viewpoint==null) {
			return false;
		} else {
			return true;
		}
	}
	
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Viewpoint edit(Integer viewpointId, HttpServletRequest request) {
		Viewpoint viewpoint = baseService.findByPK(Viewpoint.class, viewpointId);
		return viewpoint;
	}
	
	synchronized
	@RequestMapping(value = "/doedit")
	public void doEdit(Viewpoint form, HttpServletResponse response) {
		Viewpoint newViewpoint = baseService.findByPK(Viewpoint.class, form.getId());
		
		if (!newViewpoint.getName().equals(form.getName()) && existsViewpointName(form.getName())) {
			AjaxUtils.printError(response, "exist loginName", "该观点名已经存在！");
			return;
		}
		newViewpoint.setAffection(form.getAffection());
		newViewpoint.setIsManual(form.getIsManual());
		newViewpoint.setStatus(1);
		newViewpoint.setName(form.getName());
		baseService.update(newViewpoint);
		AjaxUtils.printSucc(response);
	}
	
	@RequestMapping(value = "/discard")
	public void discard(Integer viewpointId, Boolean discard, HttpServletResponse response) {
		baseService.updateWithCriteria(Viewpoint.class, new Criteria().eq(ViewpointRM.pk, viewpointId).update(ViewpointRM.status, discard?2:1));
		AjaxUtils.printSucc(response);
	}
	
	@RequestMapping(value = "/download")
	public void download() {
			
	}
}
