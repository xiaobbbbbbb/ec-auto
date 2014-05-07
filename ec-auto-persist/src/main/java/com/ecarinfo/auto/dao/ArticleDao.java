package com.ecarinfo.auto.dao;

import java.util.List;
import java.util.Map;

import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.exdao.ECDao;
import com.ecarinfo.auto.po.Article;

public interface ArticleDao extends ECDao<Article> {

	List<Map<String, Object>> findAegativeArticleStatisticsByGrade(Criteria criteria);
	
	List<Map<String, Object>> getArticleStatisticsByCriteria(Criteria criteria);
	
	List<Map<String, Object>> getArticleStatisticsBybrandIds(Criteria criteria);
	long  getArticleStatisticsCountsBybrandIds(Criteria criteria);
	
	List<Map<String, Object>> getArticleStatisticsRankingByMediaType(Criteria criteria);
	
	List<Map<String, Object>> getArticleStatisticsByMediaType(Criteria criteria);
	
	List<Map<String, Object>> getViewPointArticlesByCriteria(Criteria criteria);
	
	long getViewPointArticleCountsByCriteria(Criteria criteria);
	
	long findAegativeArticleStatisticsCountByGrade(Criteria criteria);

}
