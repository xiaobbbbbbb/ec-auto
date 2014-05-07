package com.ecarinfo.auto.backend.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecarinfo.auto.backend.web.helper.search.CarSerialSearchHelper;
import com.ecarinfo.auto.backend.web.util.AjaxUtils;
import com.ecarinfo.auto.backend.web.vo.EasyUIPage;
import com.ecarinfo.auto.po.DictCarBrand;
import com.ecarinfo.auto.po.DictCarSerial;
import com.ecarinfo.auto.rm.DictCarSerialRM;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;

@RequestMapping("carserial")
@Controller
public class CarSerialAction extends BaseAction{
	private static final Logger logger = Logger.getLogger(CarSerialAction.class);

	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request) {	
		request.setAttribute("menu_prefix", "dict");
		request.setAttribute("brands", cache.getBrands());
		return "/carserial/index";
	}
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public EasyUIPage list(HttpServletRequest request) {
		ECPage<DictCarSerial> pageHolder = PagingManager.list(baseService, DictCarSerial.class, CarSerialSearchHelper.getSearchCriteria(request));			
		return EasyUIPage.from(pageHolder);
	}
	
	@RequestMapping(value = "/doadd")
	public void doAdd(DictCarSerial serial, HttpServletResponse response) {
		if (serial!=null) {
			if (existsDictCarSerialName(serial.getName())) {
				AjaxUtils.printError(response, "exist name", "车系名已经存在！");
				return;
			}
			serial.setIsValid(true);
			DictCarBrand brand = baseService.findByPK(DictCarBrand.class, serial.getBrandId());
			if (brand!=null) {
				serial.setBrandName(brand.getName());
			}
			baseService.save(serial);
			cache.reloadCarBrandOrSerialsCaches();
			AjaxUtils.printSucc(response);
		}
	}
	
	private boolean existsDictCarSerialName(String name) {
		Criteria c = new Criteria();
		c.eq(DictCarSerialRM.name, name);		
		DictCarSerial serial = baseService.findOne(DictCarSerial.class, c);
		if (serial==null) {
			return false;
		} else {
			return true;
		}
	}
	
	@RequestMapping(value = "/edit")
	@ResponseBody
	public DictCarSerial edit(Integer serialId, HttpServletRequest request) {
		DictCarSerial serial = baseService.findByPK(DictCarSerial.class, serialId);
		return serial;
	}
	
	synchronized
	@RequestMapping(value = "/doedit")
	public void doEdit(DictCarSerial form, HttpServletResponse response) {
		DictCarSerial newSerial = baseService.findByPK(DictCarSerial.class, form.getId());
		
		if (!newSerial.getName().equals(form.getName()) && existsDictCarSerialName(form.getName())) {
			AjaxUtils.printError(response, "exist loginName", "该观点名已经存在！");
			return;
		}
		newSerial.setIsValid(true);
		newSerial.setName(form.getName());
		newSerial.setBrandId(form.getBrandId());
		newSerial.setOrderNo(form.getOrderNo());
		newSerial.setIsFollow(form.getIsFollow());
		DictCarBrand brand = baseService.findByPK(DictCarBrand.class, form.getBrandId());
		if (brand!=null) {
			newSerial.setBrandName(brand.getName());
		}
		baseService.update(newSerial);
		cache.reloadCarBrandOrSerialsCaches();
		AjaxUtils.printSucc(response);
	}
	
	@RequestMapping(value = "/discard")
	public void discard(Integer serialId, Boolean discard, HttpServletResponse response) {
		baseService.updateWithCriteria(DictCarSerial.class, new Criteria().eq(DictCarSerialRM.pk, serialId).update(DictCarSerialRM.isValid, discard?0:1));
		AjaxUtils.printSucc(response);
	}
	
	@RequestMapping(value = "/download")
	public void download() {
			
	}
}
