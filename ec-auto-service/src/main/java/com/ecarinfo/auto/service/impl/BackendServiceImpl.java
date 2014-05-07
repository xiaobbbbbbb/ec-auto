package com.ecarinfo.auto.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecarinfo.auto.dao.DictCarBrandDao;
import com.ecarinfo.auto.dao.DictCarSerialDao;
import com.ecarinfo.auto.rm.DictCarBrandRM;
import com.ecarinfo.auto.rm.DictCarSerialRM;
import com.ecarinfo.auto.service.BackendService;
import com.ecarinfo.auto.util.PageHelper;
import com.ecarinfo.auto.vo.CarSerialVO;
import com.ecarinfo.auto.vo.CarbrandVO;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.util.RowMapperUtils;
import com.ecarinfo.ralasafe.dto.Constant;
import com.ecarinfo.ralasafe.dto.SystemContext;
@Service
public class BackendServiceImpl implements BackendService {
	private static final Logger logger = Logger.getLogger(BackendServiceImpl.class);
	@Autowired
	private DictCarBrandDao dictCarBrandDao;
	@Autowired
	private DictCarSerialDao dictCarSerialDao;
	@Override
	public ECPage<CarbrandVO> getCarbrandList(String name, Integer type) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		whereBy.eq(DictCarBrandRM.isValid, 1 );
		if(type!=null && type>0){
			whereBy.eq(" d."+DictCarBrandRM.type, type , CondtionSeparator.AND);
		}
		if(name!=null && name.length()>0){
			whereBy.like(" d."+DictCarBrandRM.name, "%"+name+"%", CondtionSeparator.AND);
		} 
		long counts = dictCarBrandDao.findCarbrandCountsByCriteria(whereBy);
		
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE);
		List<Map<String, Object>> mlist = dictCarBrandDao.findCarbrandByCriteria(whereBy);
		List<CarbrandVO> list = RowMapperUtils.map2List(mlist,CarbrandVO.class);
		ECPage<CarbrandVO> page = PageHelper.list(counts,list, whereBy);
		return page;
	}
	@Override
	public ECPage<CarSerialVO> getCarSerialList(Integer brandId,String name, Integer type) {
		int pagerOffset = SystemContext.getPageOffset();
		Criteria whereBy = new Criteria();
		whereBy.eq(" dcs." +DictCarSerialRM.isValid, 1 );
		if(type!=null && type>0){
			whereBy.eq(" dcb."+DictCarBrandRM.type, type, CondtionSeparator.AND);
		}
		if(brandId!=null && brandId>0){
			whereBy.eq(" dcs." +DictCarSerialRM.brandId, brandId, CondtionSeparator.AND);
		}
		if(name!=null && name.length()>0){
			whereBy.like(" dcs." +DictCarSerialRM.name, "%"+name+"%", CondtionSeparator.AND);
		} 
		long counts = dictCarSerialDao.findCarSerialCountsByCriteria(whereBy);
	
		whereBy.setPage(pagerOffset, Constant.DEFAULT_SIZE);
		whereBy.orderBy(" dcs."+DictCarSerialRM.brandId, OrderType.DESC);
		
		List<Map<String, Object>> mlist = dictCarSerialDao.findCarSerialByCriteria(whereBy);
		List<CarSerialVO> list = RowMapperUtils.map2List(mlist,CarSerialVO.class);
		ECPage<CarSerialVO> page = PageHelper.list(counts,list, whereBy);
		return page;
	}
	 
}
