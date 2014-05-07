package com.ecarinfo.auto.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.ecarinfo.auto.base.BaseController;
import com.ecarinfo.auto.po.Advertising;
import com.ecarinfo.auto.po.AdvertisingDetail;
import com.ecarinfo.auto.po.DictCarBrand;
import com.ecarinfo.auto.po.MediaInfo;
import com.ecarinfo.auto.rm.AdvertisingDetailRM;
import com.ecarinfo.auto.service.AdvertService;
import com.ecarinfo.auto.vo.AdvertVO;
import com.ecarinfo.auto.vo.AdvertingVO;
import com.ecarinfo.auto.vo.AjaxJson;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.ralasafe.utils.EcUtil;

/**
 *广宣分析
 */

@Controller
@RequestMapping("/advert")
public class AdvertController extends BaseController {

	@Resource
	AdvertService advertService;
	@RequestMapping(value = "/index")
	public String index(ModelMap map, HttpServletRequest request) {
		
		return "advert/index";
	}
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public ECPage<AdvertingVO> list(Integer cpage,Integer pageSize){
		
		ECPage<AdvertingVO> page = advertService.getAdvertList(cpage == null ? 1 : cpage, pageSize == null ? 2 : pageSize);
		List<AdvertingVO> list = page.getList();
		if(list!=null&&list.size()>0){
			for(AdvertingVO vo :list){
				vo.setDetail(advertService.getAdvertingDetailByPid(vo.getId()));
			}
		}
		page.setList(list);
		return page;
	}
	
	

	@RequestMapping(value = "/goAdd")
	public String goadd(ModelMap map,HttpServletRequest request){
		List<MediaInfo> mediaInfo = commonCache.getMediaInfos();
		map.put("mediaInfo", mediaInfo);
		return "advert/add";
	}
	
	@RequestMapping(value = "/add")
	@ResponseBody
	public AjaxJson doAdd(@RequestBody AdvertVO dto) {
		AjaxJson jsonObj = new AjaxJson();
		Advertising advertising=new Advertising();
		if(StringUtils.isEmpty(dto.getDescontent())){
			jsonObj.setMsg("活动说明不能为空!");
			jsonObj.setSuccess(false);
		}else{
			advertising.setContent(dto.getDescontent());
		}
		advertising.setStatus(1);
		advertising.setCtime(new Date());
		genericService.save(advertising);//保存活动
		
		AdvertisingDetail detail =new AdvertisingDetail();
		detail.setContent(dto.getContent());
		detail.setCtime(new Date());
		detail.setMediaId(dto.getMediaId());
		detail.setPageId(dto.getPageId());
		detail.setPid(advertising.getId());
		detail.setPlanStime(DateUtils.stringToDate(dto.getStartTime(),TimeFormatter.FORMATTER2));
		detail.setPlanEtime(DateUtils.stringToDate(dto.getEndTime(),TimeFormatter.FORMATTER2));
		detail.setStatus(1);//未投放
		detail.setTitle(dto.getTitle());
		detail.setUrl(dto.getUrl());
		genericService.save(detail);
		
		jsonObj.setSuccess(true);
		jsonObj.setObj(advertising.getId());
		jsonObj.setMsg("配置成功");
		return jsonObj;
	}
	
	@RequestMapping(value = "/goUpdate")
	public String goUpdate(ModelMap map,Integer id){
		AdvertisingDetail detail = this.genericService.findByPK(AdvertisingDetail.class, id);
		Advertising ad = genericService.findByPK(Advertising.class, detail.getPid());
		List<MediaInfo> mediaInfo = commonCache.getMediaInfos();
		map.put("ad", ad);
		map.put("mediaInfo", mediaInfo);
		map.put("detail", detail);
		return "advert/update";
	}
	
	@RequestMapping(value = "/update")
	@ResponseBody
	public AjaxJson update(@RequestBody AdvertisingDetail dto) {
		AjaxJson jsonObj = new AjaxJson();
		Criteria updateFileld = new Criteria();
		updateFileld.update(AdvertisingDetailRM.planStime, 
				DateUtils.dateToString(dto.getPlanStime(),TimeFormatter.FORMATTER2))
			.update(AdvertisingDetailRM.planEtime,DateUtils.dateToString( dto.getPlanEtime(),TimeFormatter.FORMATTER2))
			.update(AdvertisingDetailRM.title, dto.getTitle()).update(AdvertisingDetailRM.content, dto.getContent())
			.update(AdvertisingDetailRM.mediaId, dto.getMediaId())
			.update(AdvertisingDetailRM.url, dto.getUrl())
			.update(AdvertisingDetailRM.pageId, dto.getPageId())
			.eq(AdvertisingDetailRM.pk, dto.getId());
		genericService.updateWithCriteria(AdvertisingDetail.class, updateFileld);
		jsonObj.setSuccess(true);
		jsonObj.setMsg("修改成功！");
		return jsonObj;
	}
	
}
