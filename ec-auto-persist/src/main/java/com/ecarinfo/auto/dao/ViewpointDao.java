package com.ecarinfo.auto.dao;

import java.util.List;
import java.util.Map;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.auto.po.Viewpoint;

public interface ViewpointDao extends ECDao<Viewpoint> {

	long findActiclViewpointsCountsByCriteria(Criteria whereBy);
	
	List<Map<String, Object>> findViewpointsByCriteria(Criteria whereBy);
	
	List<Map<String, Object>> findActiclViewpointsByCriteria(Criteria whereBy);
	
	List<Map<String, Object>> getViewpointsByProvince(Criteria whereBy);
}
