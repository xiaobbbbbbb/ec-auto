package com.ecarinfo.auto.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ecarinfo.auto.po.Article;
import com.ecarinfo.auto.po.ArticleComment;
import com.ecarinfo.auto.vo.ArticleSimpleListVO;
import com.ecarinfo.auto.vo.ChartCoreDataVO;
import com.ecarinfo.auto.vo.HighChartVO;
import com.ecarinfo.auto.vo.LineChartDataVO;
import com.ecarinfo.persist.paging.ECPage;

/**
 * 获取文章服务
 * @author laochen 
 * 
 */
public interface ArticleService {
	
	/**
	 * 根据文章ID获取文章的详细信息
	 * @param articleId
	 * @return Article
	 */
	public Article getArticleByPk(long articleId);
	
	
	/**
	 * 根据文章ID获取具有相似性的文章列表
	 * @param offset
	 * @param limit
	 * @param articleId
	 * @return ECPage<ArticleSimpleListVO>
	 */
	public ECPage<ArticleSimpleListVO> getRelatedArticles(long articleId,int pageNo,int pageSize);
	
	/**
	 * 根据文章ID获取相关评论列表
	 * @param offset
	 * @param limit
	 * @param articleId
	 * @return ECPage<ArticleComment>
	 */
	public ECPage<ArticleComment> getRelatedArticlesComments(int pageNo,int pageSize,long articleId);
	
		
	/**
	 * 获取某一厂牌在一间段内的文章汇总统计结果
	 * @param carBrandId
	 * @param startDate
	 * @param endDate
	 * @return List<LineStatisticsVO>
	 */
	public List<LineChartDataVO> getArticleStatisticsByCarBrandId(Integer carBrandId,Date startDate,Date endDate);
	
	/**
	 * 获取厂牌在一间段内的负面文章汇总统计结果
	 * @param carBrandId
	 * @param grade
	 * @param areaId
	 * @param proveinceId
	 * @param startDate
	 * @param endDate
	 * @return List<LineStatisticsVO>
	 */
	public List<LineChartDataVO> getArticleStatisticsByCarBrandId(Integer[] carBrandIds,Integer grade,Integer areaId,Integer proveinceId, Date startDate,Date endDate);

			
	/**
	 * 模块：负面情报->负面情报列表
	 * 根据条件检索负面资讯列表
	 * @param carBrandId
	 * @param startDate
	 * @param mediaTypeId
	 * @param grade
	 * @param tagart
	 * @param content
	 * @param keyword
	 * @param offset
	 * @param limit
	 * @return ECPage<ArticleSimpleListVO>
	 */
	public ECPage<ArticleSimpleListVO> queryAegativeArticles(Integer[] carBrandId,String startDate,String endDate,int grade,int des_content,int areaId,int proviceId,int cityId,int pageNo,int pageSize);
		
	
	/**
	 * 获取指定品牌的负面情报列表
	 * @param carBrandId
	 * @param limit
	 * @return List<ArticleSimpleListVO>
	 */
	public List<ArticleSimpleListVO> getAegativeArticlesByCarBrandId(int carBrandId,int pageSize);
	
	
	/**
	 * 获取指定车系在一定时间内的文章数统计结果
	 * @param carSerialId
	 * @param startDate
	 * @param endDate
	 * @param viewpointAffection
	 * @param areaId
	 * @param provinceId
	 * @param cityId
	 * @return List<LineChartDataVO>
	 */
	public List<LineChartDataVO> getArticleStatisticsByCarSerialId(Integer[] carSerialId,Date startDate,Date endDate,int viewpointAffection,int areaId,int provinceId,int cityId);
	
			
	
	/**
	 * 首页>>负面情报》
	 * 获取一段时间内某厂牌不同等级的负面资讯统计
	 *  @param carBrandId
	 * @param grade
	 * @param startDate
	 */
	public List<LineChartDataVO> getAegativeArticleStatisticsByCarbrandId(Integer carBrandId,Date startDate,Date endDate);
	
	/**
	 * 
	 * 按条件检索负面情报信息
	 */
	
	/**
	 * 负面情报》条件检索负面情报信息
	 * @param carBrandId
	 * @param grade
	 * @param areaId
	 * @param proviceId
	 * @param cityId
	 * @param startDate
	 * @param endDate
	 * @return List<HighChartVO>
	 */
	public List<HighChartVO> queryAegativeArticleStatistics(Integer[] carBrandId,int grade ,int areaId,int proviceId,int cityId,Date startDate,Date endDate);
		
	
	/**
	 * 模块：负面情报》负面情报分析table
	 * 按时间获取厂牌咨询数量
	 * @param carBrandId
	 * @param grade
	 * @param areaId
	 * @param proviceId
	 * @param cityId
	 * @param startDate
	 * @param endDate
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>>  getAegativeArticleStatisticsByCarbrandId(Integer[] carBrandId,int grade,int areaId,int proviceId,int cityId,String startDate,String endDate,int pageNo,int pageSize);
	
		
	/**
	 * 模块：负面情报》负面情报分析table
	 * 统计总条数用于分页
	 * @param carBrandId
	 * @param grade
	 * @param areaId
	 * @param proviceId
	 * @param cityId
	 * @param startDate
	 * @param endDate
	 * @return long 
	 */
	public long  getAegativeArticleStatisticsCountsByCarbrandId(Integer[] carBrandId,int grade,int areaId,int proviceId,int cityId,String startDate,String endDate);
	
		

	/**
	 * 模块：传播分析->传播占比 (按媒体、日期进行分组)
	 * 根据某几个媒体类型获取一定时间内的资讯统计数据
	 * @param startDate
	 * @param endDate
	 * @param mediaTypeId
	 * @return List<LineChartDataVO>
	 */
	public List<LineChartDataVO> getArticleStatisticsByMediaType(Integer[] brandIds,Date startDate,Date endDate);
	
	/**
	 * 模块：传播分析->传播占比  （按媒体）
	 * 根据某几个媒体类型获取一定时间内的资讯统计数据
	 * @param startDate
	 * @param endDate
	 * @param mediaTypeId
	 * @return List<ChartCoreDataVO>
	 */
	public List<ChartCoreDataVO> getArticleStatistics(Integer[] brandIds,Date startDate,Date endDate);
	

	
	/**
	 * 模块：传播分析->传播排行
	 * 将所有资讯内容按媒体进行分组统计
	 * 注意：输出内容按统计数量的顺序排列
	 * @param brandIds
	 * @param startDate
	 * @param endDate
	 * @return List<ChartCoreDataVO>
	 */
	List<ChartCoreDataVO> getArticleStatisticsRankingByMediaType(Integer[] brandIds, Date startDate, Date endDate);

		
	/**
	 * 模块：口碑分析->产品口碑->评论列表
	 * 根据评论观点及对应的产品（车系）获取资讯列表
	 * @param viewPointId
	 * @param carSerialId
	 * @param viewPointAffection
	 * @param startDate
	 * @param endDate
	 * @param pageNo
	 * @param pageSize
	 * @return ECPage<ArticleSimpleListVO>
	 */
	public ECPage<ArticleSimpleListVO> getArticlesByViewpointAndCarSerialId(int viewPointId,int carSerialId,int viewPointAffection,Date startDate,Date endDate, int pageNo,int pageSize);
	
	
	/**
	 * 模块：负面情报->车友评价明细
	 * TODO 需要查询viewpoint 获取对应的观点组合内容
	 * @param areaId
	 * @param provinceId
	 * @param startDate
	 * @param endDate
	 * @param affection
	 * @param desContent
	 * @param carSerialIds[]
	 * @return ECPage<ArticleSimpleListVO> 
	 */
	public ECPage<ArticleSimpleListVO> queryAegativeArticles(Integer[] carSerialIds,int areaId,
			int provinceId, String startDate, String endDate, int affection,
			int desContent, int pageNo,int pageSize);
}