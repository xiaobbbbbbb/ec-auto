package com.ecarinfo.auto.backend.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ecarinfo.auto.backend.web.helper.search.CarBrandSearchHelper;
import com.ecarinfo.auto.backend.web.util.AjaxUtils;
import com.ecarinfo.auto.backend.web.vo.EasyUIPage;
import com.ecarinfo.auto.po.DictCarBrand;
import com.ecarinfo.auto.po.DictCarSerial;
import com.ecarinfo.auto.rm.DictCarBrandRM;
import com.ecarinfo.auto.rm.DictCarSerialRM;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.PagingManager;

@RequestMapping("carbrand")
@Controller
public class CarBrandAction extends BaseAction{
	private static final Logger logger = Logger.getLogger(CarBrandAction.class);

	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request) {	
		request.setAttribute("menu_prefix", "dict");
		return "/carbrand/index";
	}
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public EasyUIPage list(HttpServletRequest request) {
		ECPage<DictCarBrand> pageHolder = PagingManager.list(baseService, DictCarBrand.class, CarBrandSearchHelper.getSearchCriteria(request));			
		return EasyUIPage.from(pageHolder);
	}
	
	@RequestMapping(value = "/doadd")
	public void doAdd(DictCarBrand brand, HttpServletResponse response) {
		if (brand!=null) {
			if (existsCarBrandName(brand.getName())) {
				AjaxUtils.printError(response, "exist name", "厂牌名已经存在！");
				return;
			}
			brand.setIsValid(1);
			baseService.save(brand);
			cache.reloadCarBrandOrSerialsCaches();
			AjaxUtils.printSucc(response);
		}
	}
	
	private boolean existsCarBrandName(String name) {
		Criteria c = new Criteria();
		c.eq(DictCarBrandRM.name, name);		
		DictCarBrand brand = baseService.findOne(DictCarBrand.class, c);
		if (brand==null) {
			return false;
		} else {
			return true;
		}
	}
	
	@RequestMapping(value = "/edit")
	@ResponseBody
	public DictCarBrand edit(Integer brandId, HttpServletRequest request) {
		DictCarBrand brand = baseService.findByPK(DictCarBrand.class, brandId);
		return brand;
	}
	
	synchronized
	@RequestMapping(value = "/doedit")
	public void doEdit(DictCarBrand form, HttpServletResponse response) {
		DictCarBrand newBrand = baseService.findByPK(DictCarBrand.class, form.getId());
		
		if (!newBrand.getName().equals(form.getName()) && existsCarBrandName(form.getName())) {
			AjaxUtils.printError(response, "exist loginName", "该厂牌名已经存在！");
			return;
		}
		newBrand.setIsValid(1);
		newBrand.setLetter(form.getLetter());
		newBrand.setName(form.getName());
		newBrand.setOrderNo(form.getOrderNo());
		baseService.update(newBrand);
		
		baseService.updateWithCriteria(DictCarSerial.class, new Criteria().eq(DictCarSerialRM.brandId, form.getId()).update(DictCarSerialRM.brandName, form.getName()));
		
		cache.reloadCarBrandOrSerialsCaches();
		AjaxUtils.printSucc(response);
	}
	
	@RequestMapping(value = "/discard")
	public void discard(Integer brandId, Boolean discard, HttpServletResponse response) {
		baseService.updateWithCriteria(DictCarBrand.class, new Criteria().eq(DictCarBrandRM.pk, brandId).update(DictCarBrandRM.isValid, discard?0:1));
		AjaxUtils.printSucc(response);
	}
	
	@RequestMapping(value = "/download")
	public void download() {
			
	}
}
