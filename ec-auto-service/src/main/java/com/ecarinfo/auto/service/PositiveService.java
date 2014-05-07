/**
 * 
 */
package com.ecarinfo.auto.service;

import java.util.List;

import com.ecarinfo.auto.vo.ArticleSimpleListVO;
import com.ecarinfo.auto.vo.ChartCoreDataVO;
import com.ecarinfo.auto.vo.PositiveViewVO;
import com.ecarinfo.persist.paging.ECPage;


/**
 * @author zengb
 * @description 负面情报
 */
public interface PositiveService {
	/**
	 * 统计负面新闻数量
	 * @param brandId
	 * @param serialId
	 * @param grade
	 * @return
	 */
	public long getPositiveCounts(int brandId,int serialId ,int grade);
	
	/**
	 * 按问题分类统计负面新闻数量
	 * @param brandId
	 * @param serialId
	 * @return
	 */
	public List<ChartCoreDataVO> getPositiveCountsByType(int brandId,int serialId);
	
	/**
	 * 最近15天的负面报道数量折线图
	 * @param serialId
	 * @return
	 */
	public List<ChartCoreDataVO> getChartVO(int brandId, int serialId,String startDate,String endDate);
	
	/**
	 * 负面检索列表
	 * @param startDate
	 * @param endDate
	 * @param serialId
	 * @param grade
	 * @param provinceId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public ECPage<PositiveViewVO> getPositives(String startDate, String endDate,Integer brandId, Integer serialId, Integer grade,Integer provinceId,Integer cityId,Integer viewPointType, Integer pageNo, Integer pageSize);
	
	/**
	 * 根据负面等级检索负面文章
	 * @param serialId
	 * @param grade
	 * @param title
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public ECPage<ArticleSimpleListVO> queryAegativeArticles(Integer brandId,Integer serialId,int grade,String title,Integer pageNo, Integer pageSize);
	
	
}
