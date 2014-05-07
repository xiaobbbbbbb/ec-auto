/**
 * 
 */
package com.ecarinfo.auto.service;

import java.util.List;

import com.ecarinfo.auto.vo.AreaCountVO;
import com.ecarinfo.auto.vo.ArticleQuestionVO;
import com.ecarinfo.auto.vo.ArticleSimpleListVO;
import com.ecarinfo.auto.vo.ChartCoreDataVO;
import com.ecarinfo.persist.paging.ECPage;

/**
 * @author zengb
 * @description 用车询问
 */
public interface QuestionService {
	/**
	 * 用车询问 询问类型统计柱状图前10条
	 */
	List<ChartCoreDataVO> getQuestionChartList(Integer brandId,Integer serialId,Integer limit,int questionType);
	
	/**
	 * 询问数量按城市分组7条
	 * @param serialId
	 * @return
	 */
	List<AreaCountVO> getAreaQuestionCountsList(Integer serialId,Integer limit,int questionType);
	
	/**
	 * 询问列表
	 * @param serialId
	 * @param startDate
	 * @param endDate
	 * @param questionId
	 * @param cityId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public ECPage<ArticleQuestionVO> queryQuestionList(Integer serialId,String startDate,String endDate,int questionId,int provinceId ,int cityId,int pageNo,int pageSize);
	
	/**
	 * 所有区域的询问数量统计
	 */
	public long allQuestionCounts(Integer sericalId,int type);
}
