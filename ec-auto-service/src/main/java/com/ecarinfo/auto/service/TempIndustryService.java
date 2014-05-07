/**
 * 
 */
package com.ecarinfo.auto.service;

import com.ecarinfo.auto.vo.TempIndustryVO;
import com.ecarinfo.persist.paging.ECPage;

/**
 * 热点新闻
 * @author yinql
 *
 */
public interface TempIndustryService {
	/**
	 * 重点新闻分页查询
	 * @param title
	 * @param type
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public ECPage<TempIndustryVO> getIndustryArticleList(String startDate,String endDate,String title,Integer type,Integer[] carbrand_id,int pageNo,int pageSize);

	
	/**
	 * 查询首页面重点新闻数据
	 * @return
	 */
	public long getTempIndustryCounts(String startDate,String endDate,Integer type,Integer[] carbrand_id);
}
