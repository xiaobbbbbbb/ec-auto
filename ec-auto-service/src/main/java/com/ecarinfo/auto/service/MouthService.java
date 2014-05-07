/**
 * 
 */
package com.ecarinfo.auto.service;

import java.util.Date;
import java.util.List;

import com.ecarinfo.auto.po.Article;
import com.ecarinfo.auto.vo.ArticleMouthVO;
import com.ecarinfo.auto.vo.ArticleQuestionVO;
import com.ecarinfo.auto.vo.ChartCoreDataVO;
import com.ecarinfo.auto.vo.MouthData;
import com.ecarinfo.auto.vo.MouthSerialName;
import com.ecarinfo.persist.paging.ECPage;

/**
 * @author zengb
 * @description 口碑分析
 */
public interface MouthService {
	
	/**
	 * 观点大类统计情况
	 * @param serialId
	 * @param affection
	 * @return
	 */
	List<ChartCoreDataVO> getViewpointGroup(Integer serialId,Integer affection);
	
	/**
	 * 观点大类统计情况
	 * @param serialId
	 * @param affection
	 * @return
	 */
	long getViewpointNums(Integer serialId,String viewpoint,Integer affection);
	
	/**
	 * 获取某车系的观点总数
	 * @param serialId
	 * @return
	 */
	long getViewpointNums(Integer serialId);
	
	/**
	 * 获取好中差观点数量
	 * @param serialId
	 * @return
	 */
	List<ChartCoreDataVO> getViewpointCounts(Integer serialId);
	
	/**
	 * 口碑列表
	 * @param serialId
	 * @param viewpiontTypeId
	 * @param affection
	 * @param startDate
	 * @param endDate
	 * @param questionId
	 * @param cityId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public ECPage<ArticleMouthVO> queryViewpointArticleList(Integer[] serialId, int viewpiontTypeId, int affection, int provinceId, int cityId,
			String startDate, String endDate, int pageNo, int pageSize);

	/**
	 * 口碑列表new
	 * @param serialId
	 * @param viewpiontTypeId
	 * @param affection
	 * @param provinceId
	 * @param cityId
	 * @param startDate
	 * @param endDate
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public ECPage<ArticleMouthVO> queryViewpointArticleList(Integer[] serialId, String viewpiontTypeId, int affection, int provinceId, int cityId,
			String startDate, String endDate, int pageNo, int pageSize);

	/**
	 * 获取车系的关键词   
	 * 按照数量从多到少排行
	 * @param serialId
	 * @return String（动力强劲,配置齐全,油耗高,内饰精美,操控性好,外观时尚,异响严重）
	 */
	public String getMouthKeywords(Integer serialId);
	
	//用户口碑数据
	public MouthData mouthDate(Integer serialId,Integer affection);

	/**
	 * 获取用户口碑下的车辆
	 */
	public List<MouthSerialName> getMouthKeywords();
}
