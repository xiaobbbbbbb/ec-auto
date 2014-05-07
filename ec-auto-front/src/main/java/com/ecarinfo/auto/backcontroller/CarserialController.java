package com.ecarinfo.auto.backcontroller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import com.ecarinfo.auto.vo.CarSerialVO;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.ralasafe.utils.EcUtil;

@Controller
@RequestMapping("/carserial")
public class CarserialController extends BaseController {
	private static final Logger logger = Logger.getLogger(CarserialController.class);
	@Resource
	private GenericService genericService;
	@Resource
	private BackendService backendService;
	@RequestMapping(value = "/goAdd")
	public String goAdd(ModelMap map, HttpServletRequest request) {
		map.put("status", "doAdd");
		List<DictCarBrand> carBrandList=genericService.findList(DictCarBrand.class,new Criteria().eq(DictCarBrandRM.isValid, 1));
		map.put("carBrandList", carBrandList);
		return "/backend/carserial/add_update";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String query(Model model,Integer brandId,String name,Integer type) throws UnsupportedEncodingException {
		try {
			if(name!=null){
				name=DtoUtil.incode(name);
			}
			ECPage<CarSerialVO> ECPage = backendService.getCarSerialList(brandId,name, type);
			
			List<CarSerialVO> temp = ECPage.getList();
			List<CarSerialVO> csvo = new ArrayList<CarSerialVO>();
			String oldbrandName="";
			int newIndex=-1;
			Integer[] arr=new Integer[temp.size()];
			
			for (int i = 0; i < temp.size(); i++) {
				CarSerialVO vo = temp.get(i);
				if(!oldbrandName.equals(vo.getBrandName())){
					newIndex++;
					arr[newIndex]=0;
				}
				arr[newIndex]++;
				oldbrandName=vo.getBrandName();
			}
			newIndex=1;
			oldbrandName="";
			for (CarSerialVO vo : temp) {
				String tr="<tr>";
				if(!oldbrandName.equals(vo.getBrandName())){
					tr += "<td rowspan='"+arr[newIndex-1]+"' >" + newIndex + "</td>";
					tr += "<td rowspan='"+arr[newIndex-1]+"' >"+ vo.getBrandName() + "</td>";
					newIndex++;
				}
				oldbrandName=vo.getBrandName();
				tr += "<td >"+ vo.getName() + "</td>";
				String typeStr="";
				if(vo.getType()==1){
					typeStr="我的";
				}else if(vo.getType()==2){
					typeStr="竞品";
				}
				tr += "<td>"+typeStr+"</td>";
				tr += "<td >" +(vo.getEditName()==null?"":vo.getEditName()) + "</td>";
				tr += "<td >" +(vo.getEtime()==null?"":vo.getEtime())  + "</td>";
				tr += "<td ><a href='javascript:void(0);' title='修改' class='update_a' onclick='update("+vo.getId()+")'></a><a href='javascript:void(0);' title='删除'  class='delete_a' onclick='discardRecord("+vo.getId()+",true)'></a></td></tr>";
				vo.setTr(tr);
				csvo.add(vo);
			}
			ECPage.setList(csvo);
			model.addAttribute("ECPage", ECPage);
			return "/backend/carserial/carserial_list";
		} catch (Exception e) {
			logger.error("车系列表加载失败", e);
		}
		return null;
	}
	
	@RequestMapping(value = "/goEdit")
	public String goEdit(ModelMap map,Integer carSerialId, HttpServletRequest request) {
		DictCarSerial carSerial = genericService.findByPK(DictCarSerial.class, carSerialId);
		map.put("dto", carSerial);
		map.put("status", "doUpdate");
		List<DictCarBrand> carBrandList=genericService.findAll(DictCarBrand.class);
		map.put("carBrandList", carBrandList);
		return "/backend/carserial/add_update";
		 
	}
	 
	@RequestMapping(value = "/doAdd")
	@ResponseBody
	public AjaxJson doAdd(String name,Integer brandId,HttpServletResponse response, HttpServletRequest request) {
		DictCarSerial carSerial=new DictCarSerial();
		AjaxJson jsonObj = new AjaxJson();
		if (existsDictCarSerialName(name)) {
			jsonObj.setSuccess(false);
			jsonObj.setMsg("车系名已经存在！");
			return jsonObj;
		}
		carSerial.setName(name);
		carSerial.setEditDatetime(new Date());
		carSerial.setOrderNo(0);
		carSerial.setEditUid(EcUtil.getCurrentUser().getUserId());
		carSerial.setIsValid(true);
		DictCarBrand brand = genericService.findByPK(DictCarBrand.class, brandId);
		if (brand!=null) {
			carSerial.setBrandName(brand.getName());
			carSerial.setBrandId(brandId);
		}
		genericService.save(carSerial);
		//清空缓存数据
		commonCache.reloadCarBrandOrSerialsCaches();
		jsonObj.setSuccess(true);
		return jsonObj;
	}
	
	private boolean existsDictCarSerialName(String name) {
		Criteria c = new Criteria();
		c.eq(DictCarSerialRM.name, name);		
		c.eq(DictCarSerialRM.isValid, 1);		
		DictCarSerial serial = genericService.findOne(DictCarSerial.class, c);
		if (serial==null) {
			return false;
		} else {
			return true;
		}
	}
	
	@RequestMapping(value = "/edit")
	@ResponseBody
	public DictCarSerial edit(Integer serialId, HttpServletRequest request) {
		DictCarSerial serial = genericService.findByPK(DictCarSerial.class, serialId);
		return serial;
	}
	
	synchronized
	@RequestMapping(value = "/doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(String name,Integer brandId,Integer id, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson jsonObj = new AjaxJson();
		DictCarSerial newSerial = genericService.findByPK(DictCarSerial.class, id);
		if (!newSerial.getName().equals(name) && existsDictCarSerialName(name)) {
			jsonObj.setSuccess(false);
			jsonObj.setMsg("车系已经存在！");
			return jsonObj;		
		}
		newSerial.setIsValid(true);
		newSerial.setName(name);
		newSerial.setEditDatetime(new Date());
		newSerial.setEditUid(EcUtil.getCurrentUser().getUserId());
		DictCarBrand brand = genericService.findByPK(DictCarBrand.class, brandId);
		if (brand!=null) {
			newSerial.setBrandName(brand.getName());
			newSerial.setBrandId(brandId);
		}
		genericService.update(newSerial);
		//清空缓存数据
		commonCache.reloadCarBrandOrSerialsCaches();
		jsonObj.setSuccess(true);
		return jsonObj;
	}
	
	@RequestMapping(value = "/discard")
	@ResponseBody
	public AjaxJson discard(Integer serialId, Boolean discard, HttpServletResponse response) {
		AjaxJson jsonObj = new AjaxJson();
		genericService.updateWithCriteria(DictCarSerial.class, new Criteria().eq(DictCarSerialRM.pk, serialId).update(DictCarSerialRM.isValid, discard?0:1));
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
		List<DictCarBrand> carBrandList=genericService.findList(DictCarBrand.class,new Criteria().eq(DictCarBrandRM.isValid, 1));
		model.addAttribute("carBrandList", carBrandList);
		return "/backend/carserial/search";
		
	}
	
	
}
