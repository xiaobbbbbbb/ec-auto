package com.ecarinfo.auto.dao;

import java.util.List;
import java.util.Map;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.auto.po.TmpIndustryArticle;

public interface TmpIndustryArticleDao extends ECDao<TmpIndustryArticle> {
	Long findTmpIACountsByCriteria(Criteria criteria);

	List<Map<String, Object>> findTmpIAByCriteria(Criteria criteria);	
}
