package com.ecarinfo.auto.service;

import java.util.Date;
import java.util.List;

import com.ecarinfo.auto.po.Article;
import com.ecarinfo.auto.vo.NegativeVo;

/*
 * 负面情报
 */
public interface NegativeService {
	/**
	 * 近7天负面情报
	 */
	List<NegativeVo> getSevendaysNegative(Integer days,Integer brandId);
	
	/**
	 * 近7天负面情报列表
	 */
	List<Article> negativeArticlesList(Integer days);
	
	/**
	 * 负面情报分析
	 * @param sdate 开始时间
	 * @param edate 结束时间
	 * @param brandIds[] 厂牌id
	 */
	List<NegativeVo> negativeAnalysisList(Date sdate,Date edate,Integer[] brandIds);
	
	/**
	 * 负面情报详细
	 * @param sdate 开始时间
	 * @param edate 结束时间
	 * @param brandIds[] 厂牌id
	 * @param mediaType 媒体类型 1论坛,2新闻,3博客,4,微博，0其它
	 * @param target 责任对象 1主机厂，2:4s店，0：其它
	 * @param affection 等级 -1 一级，-2 2级，-3 3级
	 */
	List<Article> negativeArticlesList(Date sdate,Date edate,Integer[] brandIds,Integer mediaType,Integer target,Integer affection,Integer content);
}
