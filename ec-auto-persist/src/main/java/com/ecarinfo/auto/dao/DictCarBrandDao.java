package com.ecarinfo.auto.dao;

import java.util.List;
import java.util.Map;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.auto.po.DictCarBrand;

public interface DictCarBrandDao extends ECDao<DictCarBrand> {
	long findCarbrandCountsByCriteria(Criteria criteria);
	
	List<Map<String, Object>> findCarbrandByCriteria(Criteria criteria);
}
