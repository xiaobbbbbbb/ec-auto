package com.ecarinfo.auto.dao;

import java.util.List;
import java.util.Map;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.auto.po.DictCarSerial;

public interface DictCarSerialDao extends ECDao<DictCarSerial> {
	long findCarSerialCountsByCriteria(Criteria criteria);
	
	List<Map<String, Object>> findCarSerialByCriteria(Criteria criteria);
}
