package com.ecarinfo.auto.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.auto.po.IndustryArticle;

public interface IndustryArticleDao extends ECDao<IndustryArticle> {
	
	Long findIndustryArticleCountsByCriteria(Criteria criteria);
	
	Long findIndustryArticleCountsById(long id);

	List<Map<String, Object>> findIndustryArticleByCriteria(Criteria criteria);	
	
	Long countIndustryArticleByKeywordId(@Param("keywordId") Long keywordId);
	
	List<Map<String, Object>> findIndustryArticleByKeywordId(@Param("keywordId") Long keywordId,@Param("offset") Integer offset,@Param("rowsPerPage") Integer rowsPerPage);
	
	List<Map<String, Object>> findIndustryArticleForJob();	
	
}
