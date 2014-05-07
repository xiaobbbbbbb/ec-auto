package com.ecarinfo.auto.backcontroller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.auto.base.BaseController;
import com.ecarinfo.auto.po.DictCarBrand;
import com.ecarinfo.auto.po.DictCarSerial;
import com.ecarinfo.auto.rm.DictCarBrandRM;
import com.ecarinfo.auto.rm.DictCarSerialRM;
import com.ecarinfo.auto.service.BackendService;
import com.ecarinfo.auto.util.DtoUtil;
import com.ecarinfo.auto.vo.AjaxJson;
import com.ecarinfo.auto.vo.CarbrandVO;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.ralasafe.utils.EcUtil;

@Controller
@RequestMapping("/carbrand")
public class CarbrandController extends BaseController {
	private static final Logger logger = Logger.getLogger(CarbrandController.class);
	@Resource
	private GenericService genericService;
	@Resource
	private BackendService backendService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listPost(Integer groupId,String name,Integer type,Model model) {
		try {
			if(name!=null){
				name = DtoUtil.incode(name);
			}
			ECPage<CarbrandVO> ECPage = backendService.getCarbrandList(name, type);
			model.addAttribute("ECPage", ECPage);
			return "/backend/carbrand/carbrand_list";
		} catch (Exception e) {
			logger.error("厂牌列表加载失败", e);
		}
		return null;
	}
 
	
	@RequestMapping(value = "/goAdd")
	public String goAdd(ModelMap map, HttpServletRequest request) {
		DictCarBrand myBrand = commonCache.getDefaultCarBrand();
		Integer brandId = null;
		if(null!=myBrand&&myBrand.getId()!=null&&myBrand.getId()>0){
			brandId = myBrand.getId();
		}
		map.put("myBrand", brandId);
		map.put("status", "doAdd");
		return "/backend/carbrand/add_update";
	}
	
	@RequestMapping(value = "/doAdd")
	@ResponseBody
	public AjaxJson doAdd(String name,Integer type, HttpServletResponse response, HttpServletRequest request) {
		DictCarBrand carBrand=new DictCarBrand();
		AjaxJson jsonObj = new AjaxJson();
		if(type==1 && existsDictCarBrandType()){
			jsonObj.setSuccess(false);
			jsonObj.setMsg("我的厂牌已设置,请重新选择！");
			return jsonObj;
		}
		
		if (existsDictCarBrandName(name)) {
			jsonObj.setSuccess(false);
			jsonObj.setMsg("厂牌名已经存在！");
			return jsonObj;
		}
		carBrand.setIsValid(1);
		carBrand.setName(name);
		carBrand.setEditDatetime(new Date());
		carBrand.setType(type);
		carBrand.setOrderNo(0);
		carBrand.setEditUid(EcUtil.getCurrentUser().getUserId());
		genericService.save(carBrand);
		//清空缓存数据
		commonCache.reloadCarBrandOrSerialsCaches();
		jsonObj.setSuccess(true);
		return jsonObj;
	}
	
	private boolean existsDictCarBrandName(String name) {
		Criteria c = new Criteria();
		c.eq(DictCarBrandRM.name, name);	
		c.eq(DictCarBrandRM.isValid, 1);
		DictCarBrand carBrand = genericService.findOne(DictCarBrand.class, c);
		if (carBrand==null) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 验证是都是我的
	 * @return
	 */
	private boolean existsDictCarBrandType() {
		Criteria c = new Criteria();
		c.eq(DictCarBrandRM.type, 1);		
		DictCarBrand carBrand = genericService.findOne(DictCarBrand.class, c);
		if (carBrand==null) {
			return false;
		} else {
			return true;
		}
	}
	
	@RequestMapping(value = "/goEdit")
	public String goEdit(ModelMap map,Integer carBrandId, HttpServletRequest request) {
		DictCarBrand dictCarBrand = genericService.findByPK(DictCarBrand.class, carBrandId);
		map.put("dto", dictCarBrand);
		map.put("status", "doUpdate");
		return "/backend/carbrand/add_update";
		 
	}
	
	synchronized
	@RequestMapping(value = "/doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(String name,Integer type,Integer id, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson jsonObj = new AjaxJson();
		DictCarBrand newBrand = genericService.findByPK(DictCarBrand.class, id);
		if(type==1 && existsDictCarBrandType()){
			jsonObj.setSuccess(false);
			jsonObj.setMsg("我的厂牌已设置,请重新选择！");
			return jsonObj;
		}
		if (!newBrand.getName().equals(name) && existsDictCarBrandName(name)) {
			jsonObj.setSuccess(false);
			jsonObj.setMsg("厂牌名已经存在！");
			return jsonObj;
		}
		newBrand.setName(name);
		newBrand.setEditDatetime(new Date());
		newBrand.setType(type);
		newBrand.setOrderNo(0);
		newBrand.setEditUid(EcUtil.getCurrentUser().getUserId());
		genericService.update(newBrand);
		jsonObj.setSuccess(true);
		//清空缓存数据
		commonCache.reloadCarBrandOrSerialsCaches();
		return jsonObj;
	}
	
	@RequestMapping(value = "/discard")
	@ResponseBody
	public AjaxJson discard(Integer carBrandId, Boolean discard, HttpServletResponse response) {
		AjaxJson jsonObj = new AjaxJson();
		List<DictCarSerial> listDcs= genericService.findList(DictCarSerial.class, new Criteria().eq(DictCarSerialRM.brandId, carBrandId));
		if(listDcs.size()>0){
			jsonObj.setSuccess(false);
			jsonObj.setMsg("已经添加车系的厂牌不能删除");
			return jsonObj;
		}
		genericService.updateWithCriteria(DictCarBrand.class, new Criteria().eq(DictCarBrandRM.pk, carBrandId).update(DictCarBrandRM.isValid, discard?0:1));
		//清空缓存数据
		commonCache.reloadCarBrandOrSerialsCaches();
		jsonObj.setSuccess(true);
		return jsonObj;
	}
	
	@RequestMapping(value = "/download")
	public void download() {
			
	}
	
	@RequestMapping(value = "/searchUI", method = RequestMethod.GET)
	public String searchUI(Model model) {
		return "/backend/carbrand/search";
	}
}
