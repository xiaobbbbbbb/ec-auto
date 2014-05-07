package com.ecarinfo.auto.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ecarinfo.auto.dao.ArticleDao;
import com.ecarinfo.auto.po.Article;
import com.ecarinfo.auto.po.ArticleComment;
import com.ecarinfo.auto.po.ArticleKeyword;
import com.ecarinfo.auto.po.DictCarBrand;
import com.ecarinfo.auto.rm.ArticleCommentRM;
import com.ecarinfo.auto.rm.ArticleKeywordRM;
import com.ecarinfo.auto.rm.ArticleRM;
import com.ecarinfo.auto.rm.ViewpointRM;
import com.ecarinfo.auto.service.ArticleService;
import com.ecarinfo.auto.util.PageHelper;
import com.ecarinfo.auto.vo.ArticleAegativeVO;
import com.ecarinfo.auto.vo.ArticleSimpleListVO;
import com.ecarinfo.auto.vo.ChartCoreDataVO;
import com.ecarinfo.auto.vo.HighChartVO;
import com.ecarinfo.auto.vo.LineChartDataVO;
import com.ecarinfo.auto.vo.StatisticsVO;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.Paginable;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.persist.util.RowMapperUtils;

@Service
public class ArticleServiceImpl implements ArticleService {

	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

	@Resource
	private GenericDao genericDao;

	@Resource
	private ArticleDao articleDao;
	
	@Override
	public Article getArticleByPk(long articleId) {
		return this.genericDao.findByPK(Article.class, articleId);
	}
	
	/**
	 * 获取关联文章
	 * @param int pageNo 页码
	 * @param int pageSize 每页多少条
	 * @param long articleId文章ID
	 */
	@Override
	public ECPage<ArticleSimpleListVO> getRelatedArticles(long articleId,int pageNo,int pageSize) {
		try {
			logger.info("======getRelatedArticles==articleId:" + articleId);
			Article article = this.getArticleByPk(articleId);
			Criteria whereBy = new Criteria();
			whereBy.eq(" a." + ArticleRM.status, 1).eq(" a." + ArticleRM.affection, article.getAffection());

			// 根据文章ID找出文章关键词
			List<ArticleKeyword> keyList = this.genericDao.findList(ArticleKeyword.class,new Criteria().eq(ArticleKeywordRM.articleId, articleId));
			if (keyList != null && keyList.size() > 0) {
				Long[] lo = new Long[keyList.size()];
				for (int i = 0; i < keyList.size(); i++) {
					lo[i] = keyList.get(i).getKeywordId();
				}
				List<ArticleKeyword> keyList2 = this.genericDao.findList(
						ArticleKeyword.class,
						new Criteria().in(ArticleKeywordRM.articleId, lo)
								.notEquals(ArticleKeywordRM.articleId,
										articleId, CondtionSeparator.AND));
				if (keyList2 != null && keyList2.size() > 0) {
					Long[] lo2 = new Long[keyList2.size()];
					for (int i = 0; i < keyList2.size(); i++) {
						lo2[i] = keyList2.get(i).getArticleId();
					}
					whereBy.in(" a." + ArticleRM.id, lo2, CondtionSeparator.AND);

					long counts = articleDao.getArticleStatisticsCountsBybrandIds(whereBy);
					if (pageSize > 0) {
						whereBy.setPage(pageNo, pageSize);
					}
					List<Map<String, Object>> map = articleDao.getArticleStatisticsBybrandIds(whereBy);
					List<ArticleSimpleListVO> list = RowMapperUtils.map2List(map, ArticleSimpleListVO.class);
					ECPage<ArticleSimpleListVO> page = PageHelper.list(counts,list, whereBy);
					return page;
				}
			}
		} catch (Exception e) {
			logger.error("查询出错", e);
		}
		return null;
	}
	
	/**
	 * @laochen index
	 * 获取某一厂牌在一间段内的文章汇总统计结果
	 * @param carBrandId
	 * @param startDate
	 * @param endDate
	 * @return List<LineStatisticsVO>
	 */
	@Override
	public List<LineChartDataVO> getArticleStatisticsByCarBrandId(
			Integer carBrandId, Date startDate, Date endDate) {
		
		List<LineChartDataVO> rlist = new ArrayList<LineChartDataVO>();
		
		StringBuffer sql = new StringBuffer();
		sql.append(String.format("select s.name,a.serial_id id,count(a.serial_id) as num,a.article_date date from article a left join dict_car_serial s on (a.serial_id=s.id and s.is_valid=1) where a.status=1 and a.brand_id='%d'",carBrandId));
		
		if(null!=startDate && null!=endDate) {
			sql.append(String.format(" and a.article_date>='%s'", DateUtils.dateToString(startDate, DateUtils.TimeFormatter.FORMATTER2)));
			sql.append(String.format(" and a.article_date<='%s'", DateUtils.dateToString(endDate, DateUtils.TimeFormatter.FORMATTER2)));
		} else if(startDate!=null) {
			sql.append(String.format(" and a.article_date='%s'", DateUtils.dateToString(startDate, DateUtils.TimeFormatter.FORMATTER2)));
		} else if(endDate!=null) {
			sql.append(String.format(" and a.article_date='%s'", DateUtils.dateToString(endDate, DateUtils.TimeFormatter.FORMATTER2)));
		}
		sql.append(" group by a.brand_id,a.serial_id,a.article_date order by a.article_date asc");
		
		logger.debug("[getArticleStatisticsByCarBrandId]sql:"+sql.toString());
		
		List<Map<String,Object>> tmp_list = genericDao.findListBySql(sql.toString());
		List<StatisticsVO> list = RowMapperUtils.map2List(tmp_list,StatisticsVO.class);
		
		Map<String,List<ChartCoreDataVO>> map = new HashMap<String, List<ChartCoreDataVO>>();
		
		if(list!=null && list.size()>0){
			for (StatisticsVO statisticsVO : list) {
				String carSerialName = statisticsVO.getName();
				
				ChartCoreDataVO vo = new ChartCoreDataVO();
				vo.setName(statisticsVO.getDate());
				vo.setValue(statisticsVO.getNum());
				
				if (map.containsKey(carSerialName)) {
					map.get(carSerialName).add(vo);
				} else {
					List<ChartCoreDataVO> tlist = new ArrayList<ChartCoreDataVO>();
					tlist.add(vo);
					map.put(carSerialName, tlist);
				}
			}
			for (Entry<String, List<ChartCoreDataVO>> e: map.entrySet()) {
				LineChartDataVO rvo = new LineChartDataVO();
				rvo.setName(e.getKey());
				rvo.setData(e.getValue());
				rlist.add(rvo);
			}
		}
		return rlist;
	}
	
	@Override
	public List<LineChartDataVO> getArticleStatisticsByCarBrandId(Integer[] carBrandIds,Integer grade,Integer areaId,Integer proveinceId, Date startDate,Date endDate) {
		
		List<LineChartDataVO> rlist = new ArrayList<LineChartDataVO>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select cb.name,a.serial_id id,count(a.serial_id) as num,a.article_date date from article a left join dict_car_brand cb on (a.brand_id=cb.id and cb.is_valid=1) where a.status=1 and a.affection=1");
		if(null!=carBrandIds && carBrandIds.length>0){
			sql.append(String.format(" and a.brand_id in (%s)", StringUtils.join(carBrandIds, ",")));
		}
		if(grade>0){
			sql.append(String.format(" and a.grade='%d'", grade));
		}
		if(areaId>0){
			sql.append(String.format(" and a.area_id='%d'", areaId));
		}
		if(proveinceId>0){
			sql.append(String.format(" and a.province_id='%d'", proveinceId));
		}
		if(null!=startDate && null!=endDate) {
			sql.append(String.format(" and a.article_date>='%s'", DateUtils.dateToString(startDate, DateUtils.TimeFormatter.FORMATTER2)));
			sql.append(String.format(" and a.article_date<='%s'", DateUtils.dateToString(endDate, DateUtils.TimeFormatter.FORMATTER2)));
		} else if(startDate!=null) {
			sql.append(String.format(" and a.article_date='%s'", DateUtils.dateToString(startDate, DateUtils.TimeFormatter.FORMATTER2)));
		} else if(endDate!=null) {
			sql.append(String.format(" and a.article_date='%s'", DateUtils.dateToString(endDate, DateUtils.TimeFormatter.FORMATTER2)));
		}
		sql.append(" group by a.brand_id,a.article_date order by a.article_date asc");
		
		logger.debug("[getArticleStatisticsByCarBrandId]sql:"+sql.toString());
		
		List<Map<String,Object>> tmp_list = genericDao.findListBySql(sql.toString());
		List<StatisticsVO> list = RowMapperUtils.map2List(tmp_list,StatisticsVO.class);
		
		Map<String,List<ChartCoreDataVO>> map = new HashMap<String, List<ChartCoreDataVO>>();
		
		if(list!=null && list.size()>0){
			for (StatisticsVO statisticsVO : list) {
				String carSerialName = statisticsVO.getName();
				
				ChartCoreDataVO vo = new ChartCoreDataVO();
				vo.setName(statisticsVO.getDate());
				vo.setValue(statisticsVO.getNum());
				
				if (map.containsKey(carSerialName)) {
					map.get(carSerialName).add(vo);
				} else {
					List<ChartCoreDataVO> tlist = new ArrayList<ChartCoreDataVO>();
					tlist.add(vo);
					map.put(carSerialName, tlist);
				}
			}
			for (Entry<String, List<ChartCoreDataVO>> e: map.entrySet()) {
				LineChartDataVO rvo = new LineChartDataVO();
				rvo.setName(e.getKey());
				rvo.setData(e.getValue());
				rlist.add(rvo);
			}
		}
		return rlist;
	}

	/**
	 * @laochen discuss
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
	@Override
	public List<LineChartDataVO> getArticleStatisticsByCarSerialId(
			Integer[] carSerialId, Date startDate, Date endDate,
			int viewpointAffection, int areaId, int provinceId, int cityId) {
		
		List<LineChartDataVO> rlist = new ArrayList<LineChartDataVO>();

		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT s.name ,a.serial_id id, a.article_date date,count(a.id) num from article a left join dict_car_serial s on (a.serial_id=s.id and s.is_valid=1) where a.status=1");
		StringBuffer ids = new StringBuffer();
		int i=0;
		for (Integer serialId : carSerialId) {
			ids.append(serialId);
			i++;
			if(i<carSerialId.length){
				ids.append(",");
			}
		}
		if(carSerialId!=null && carSerialId.length>0){			
			sql.append(String.format("  and a.serial_id in (%s)",ids.toString()));
		}
		if(viewpointAffection>0){
			sql.append(String.format(" and a.affection='%d'", viewpointAffection));
		}		
		if(areaId>0){
			sql.append(String.format(" and a.area_id='%d'", areaId));
		}		
		if(provinceId>0){
			sql.append(String.format(" and a.province_id='%d'", provinceId));
		}
		if(cityId>0){
			sql.append(String.format(" and a.city_id='%d'", cityId));
		}
		
		if(startDate!=null && endDate!=null){
			sql.append(String.format(" and a.article_date>='%s'", DateUtils.dateToString(startDate, DateUtils.TimeFormatter.FORMATTER2)));
			sql.append(String.format(" and a.article_date<='%s'", DateUtils.dateToString(endDate, DateUtils.TimeFormatter.FORMATTER2)));
		} else if(startDate!=null){
			sql.append(String.format(" and a.article_date='%s'", DateUtils.dateToString(startDate, DateUtils.TimeFormatter.FORMATTER2)));
		} else if(endDate!=null){
			sql.append(String.format(" and a.article_date='%s'", DateUtils.dateToString(endDate, DateUtils.TimeFormatter.FORMATTER2)));
		}
		
		sql.append(" GROUP BY a.serial_id,a.article_date ORDER BY a.article_date asc");
		
		logger.debug("getArticleStatisticsByCarSerialId sql:"+sql.toString());
		
		List<Map<String,Object>> list = genericDao.findListBySql(sql.toString());		
		List<StatisticsVO> vlist = RowMapperUtils.map2List(list,StatisticsVO.class);
		
		Map<String,List<ChartCoreDataVO>> map = new HashMap<String, List<ChartCoreDataVO>>();		
		if(list!=null && list.size()>0){
			for (StatisticsVO statisticsVO : vlist) {
				String carSerialName = statisticsVO.getName();
				
				ChartCoreDataVO vo = new ChartCoreDataVO();
				vo.setName(statisticsVO.getDate());
				vo.setValue(statisticsVO.getNum());
				
				if (map.containsKey(carSerialName)) {
					map.get(carSerialName).add(vo);
				} else {
					List<ChartCoreDataVO> tlist = new ArrayList<ChartCoreDataVO>();
					tlist.add(vo);
					map.put(carSerialName, tlist);
				}
			}
			for (Entry<String, List<ChartCoreDataVO>> e: map.entrySet()) {
				LineChartDataVO rvo = new LineChartDataVO();
				rvo.setName(e.getKey());
				rvo.setData(e.getValue());
				rlist.add(rvo);
			}
		}
		return rlist;
	}
	
	/**
	 * @laochen downside
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
	@Override
	public List<Map<String, Object>> getAegativeArticleStatisticsByCarbrandId(
			Integer[] carBrandId,int grade,int areaId,int proviceId,int cityId, String startDate, String endDate,int pageNo,int pageSize) {

		StringBuffer sb =new StringBuffer();
		sb.append(" SELECT article_date '0' ");
		for (Integer id : carBrandId) {	
			if(id>0){
				DictCarBrand car = this.genericDao.findByPK(DictCarBrand.class, id);
				sb.append(",COUNT( CASE WHEN a.brand_id="+id+"   THEN 0 ELSE NULL END ) as '"+id+"_"+car.getName()+"' ");
			}
		}
		sb.append(" FROM article a where a. STATUS = 1 AND a.affection = 1 ");
		if(grade>0){
			sb.append(" and grade="+grade);
		}
		
		if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
			sb.append(" and article_date>='"+startDate+"'");
			sb.append(" and article_date<='"+endDate+"'");
		} else if(StringUtils.isNotBlank(startDate)){
			sb.append(" and article_date='"+startDate+"'");
		} else if(StringUtils.isNotBlank(endDate)){
			sb.append(" and article_date='"+endDate+"'");
		}
		if(areaId>0){
			sb.append(" and area_id="+areaId);
		}
		if(proviceId>0){
			sb.append(" and province_id="+proviceId);
		}
		if(cityId>0){
			sb.append(" and city_id="+cityId);
		}
		sb.append(" GROUP BY article_date order by article_date DESC ");
		if(pageSize>0){
			sb.append(" limit "+pageNo+","+pageSize);
		}
		List<Map<String, Object>>  list=genericDao.findListBySql(sb.toString());
		return list;
	}	

	/**
	 * @laochen media
	 * 模块：传播分析->传播占比 (按媒体、日期进行分组)
	 * 根据某几个媒体类型获取一定时间内的资讯统计数据
	 * @param startDate
	 * @param endDate
	 * @param mediaTypeId
	 * @return List<LineChartDataVO>
	 */
	@Override
	public List<LineChartDataVO> getArticleStatisticsByMediaType(
			Integer[] brandIds, Date startDate, Date endDate) {
		List<LineChartDataVO> rlist = new ArrayList<LineChartDataVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select a.media_type id,mt.name,a.article_date date,count(a.media_type) num");
		sql.append(" from article a left join dict_media_type mt on (mt.id=a.media_type and mt.is_valid=1) where a.status=1");		
		if(null!=brandIds && brandIds.length>0){
			sql.append(String.format(" and a.brand_id in (%s)", StringUtils.join(brandIds, ",")));
		}
		
		if(null!=startDate && null!=endDate){
			String sdate = DateUtils.dateToString(startDate,DateUtils.TimeFormatter.FORMATTER2);
			String edate = DateUtils.dateToString(endDate,DateUtils.TimeFormatter.FORMATTER2);
			sql.append(String.format(" and a.article_date>='%s'",sdate));
			sql.append(String.format(" and a.article_date<='%s'", edate));
		} else if(null!=startDate) {
			String date = DateUtils.dateToString(startDate,DateUtils.TimeFormatter.FORMATTER2);
			sql.append(String.format(" and a.article_date='%s'", date));
		} else if(null!=endDate) {
			String date = DateUtils.dateToString(endDate,DateUtils.TimeFormatter.FORMATTER2);
			sql.append(String.format(" and a.article_date='%s'", date));
		}
		sql.append(" group by a.media_type,a.article_date order by a.article_date asc");
		
		logger.debug("[getAegativeArticleStatisticsByCarbrandId]"+sql.toString());
		
		List<Map<String,Object>> tmp_list = genericDao.findListBySql(sql.toString());
		List<StatisticsVO> list = RowMapperUtils.map2List(tmp_list,StatisticsVO.class);		
		
		if(null!=list && list.size()>0){
			Map<String,List<ChartCoreDataVO>> map = new HashMap<String, List<ChartCoreDataVO>>();
			for (StatisticsVO statisticsVO : list) {				
				ChartCoreDataVO vo = new ChartCoreDataVO();
				vo.setName(statisticsVO.getDate());
				vo.setValue(statisticsVO.getNum());
				String mediaTypeName =statisticsVO.getName();
				
				if (map.containsKey(mediaTypeName)) {
					map.get(mediaTypeName).add(vo);
				} else {
					List<ChartCoreDataVO> tlist = new ArrayList<ChartCoreDataVO>();
					tlist.add(vo);					
					map.put(mediaTypeName, tlist);
				}
			}
			for (Entry<String,List<ChartCoreDataVO>> tmap : map.entrySet()) {
				LineChartDataVO lvo = new LineChartDataVO();
				lvo.setName(tmap.getKey());
				lvo.setData(tmap.getValue());
				rlist.add(lvo);
			}
		}
		return rlist;
	}
	/**
	 * @laochen index
	 * 模块：传播分析->传播占比  （按媒体分组）
	 * 根据某几个媒体类型获取一定时间内的资讯统计数据
	 * @param startDate
	 * @param endDate
	 * @param mediaTypeId
	 * @return List<ChartCoreDataVO>
	 */
	public List<ChartCoreDataVO> getArticleStatistics(Integer[] brandIds,Date startDate,Date endDate)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select mt.name,count(a.media_type) value");
		sql.append(" from article a left join dict_media_type mt on (mt.id=a.media_type and mt.is_valid=1) where a.status=1");		
		if(null!=brandIds && brandIds.length>0){
			sql.append(String.format(" and a.brand_id in (%s)", StringUtils.join(brandIds, ",")));
		}
		if(null!=startDate && null!=endDate){
			String sdate = DateUtils.dateToString(startDate,DateUtils.TimeFormatter.FORMATTER2);
			String edate = DateUtils.dateToString(endDate,DateUtils.TimeFormatter.FORMATTER2);
			sql.append(String.format(" and a.article_date>='%s'",sdate));
			sql.append(String.format(" and a.article_date<='%s'", edate));
		} else if(null!=startDate) {
			String date = DateUtils.dateToString(startDate,DateUtils.TimeFormatter.FORMATTER2);
			sql.append(String.format(" and a.article_date='%s'", date));
		} else if(null!=endDate) {
			String date = DateUtils.dateToString(endDate,DateUtils.TimeFormatter.FORMATTER2);
			sql.append(String.format(" and a.article_date='%s'", date));
		}
		sql.append(" group by a.media_type");
		logger.debug("[getArticleStatistics]"+sql.toString());		
		List<Map<String,Object>> tmp_list = genericDao.findListBySql(sql.toString());
		
		List<ChartCoreDataVO> list = RowMapperUtils.map2List(tmp_list,ChartCoreDataVO.class);
		return list;
	}

	/**
	 * @laochen index,media
	 * 模块：传播分析->传播排行
	 * 将所有资讯内容按媒体进行分组统计
	 * 注意：输出内容按统计数量的顺序排列
	 * @param brandIds
	 * @param startDate
	 * @param endDate
	 * @return List<ChartCoreDataVO>
	 */
	@Override
	public List<ChartCoreDataVO> getArticleStatisticsRankingByMediaType(Integer[] brandIds, Date startDate, Date endDate){		
		StringBuffer sql = new StringBuffer();
		sql.append("select mi.name,count(a.media_id) as value");
		sql.append(" from article a left join media_info mi on (mi.id=a.media_id and mi.is_valid=1) where a.status=1");		
		if(null!=brandIds && brandIds.length>0){
			sql.append(String.format(" and a.brand_id in (%s)", StringUtils.join(brandIds, ",")));
		}
		if(null!=startDate && null!=endDate){
			String sdate = DateUtils.dateToString(startDate,DateUtils.TimeFormatter.FORMATTER2);
			String edate = DateUtils.dateToString(endDate,DateUtils.TimeFormatter.FORMATTER2);
			sql.append(String.format(" and a.article_date>='%s'",sdate));
			sql.append(String.format(" and a.article_date<='%s'", edate));
		} else if(null!=startDate) {
			String date = DateUtils.dateToString(startDate,DateUtils.TimeFormatter.FORMATTER2);
			sql.append(String.format(" and a.article_date='%s'", date));
		} else if(null!=endDate) {
			String date = DateUtils.dateToString(endDate,DateUtils.TimeFormatter.FORMATTER2);
			sql.append(String.format(" and a.article_date='%s'", date));
		}
		sql.append(" group by a.media_id order by value asc");
		
		logger.debug("[getArticleStatisticsRankingByMediaType]"+sql.toString());
		
		List<Map<String,Object>> tmp_list = genericDao.findListBySql(sql.toString());		
		List<ChartCoreDataVO> rlist = RowMapperUtils.map2List(tmp_list,ChartCoreDataVO.class);
		return rlist;
	}

	/**
	 * TODO 改造为使用SQL查询
	 * @laochen discuss
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
	@Override
	public ECPage<ArticleSimpleListVO> getArticlesByViewpointAndCarSerialId(
			int viewPointId,int carSerialId,int viewPointAffection,Date startDate,Date endDate, int pageNo,int pageSize) {
		Criteria whereBy = new Criteria();
		whereBy.eq("a."+ArticleRM.status, 1);
		logger.info("-------getArticlesByViewpointAndCarSerialId()---viewPointId:"+viewPointId);
		logger.info("-------getArticlesByViewpointAndCarSerialId()---carSerialId:"+carSerialId);
		if(viewPointId>0){
			whereBy.eq(" v."+ViewpointRM.id, viewPointId, CondtionSeparator.AND);
		}
		if(viewPointAffection>0){
			whereBy.eq(" v."+ViewpointRM.affection, viewPointAffection);
		}
		if(carSerialId>0){
			whereBy.eq(" a."+ArticleRM.serialId, carSerialId);
		}
		if(startDate!=null && endDate!=null){
			whereBy.greateThenOrEquals(" a." + ArticleRM.articleDate,DateUtils.dateToString(startDate, TimeFormatter.FORMATTER2));
			whereBy.lessThenOrEquals(" a." + ArticleRM.articleDate,DateUtils.dateToString(endDate, TimeFormatter.FORMATTER2));
		}  else	if (startDate != null) {
			whereBy.eq(" a." + ArticleRM.articleDate,DateUtils.dateToString(startDate, TimeFormatter.FORMATTER2));
		} else if (endDate != null) {
			whereBy.eq(" a." + ArticleRM.articleDate,DateUtils.dateToString(endDate, TimeFormatter.FORMATTER2));
		}
		
		long counts = this.articleDao.getViewPointArticleCountsByCriteria(whereBy);
		
		whereBy.setPage(pageNo, pageSize).orderBy(" a." + ArticleRM.articleCtime, OrderType.DESC);
		List<Map<String, Object>> map = articleDao.getViewPointArticlesByCriteria(whereBy);
		List<ArticleSimpleListVO> list = RowMapperUtils.map2List(map,ArticleSimpleListVO.class);
		ECPage<ArticleSimpleListVO> page = PageHelper.list(counts,list, whereBy);
		
		return page;
	}

	/**
	 * @laochen index
	 * 首页>>负面情报》
	 * 获取一段时间内某厂牌不同等级的负面资讯统计
	 *  @param carBrandId
	 * @param grade
	 * @param startDate
	 * @return List<LineChartDataVO>
	 */
	@Override
	public List<LineChartDataVO> getAegativeArticleStatisticsByCarbrandId(
			Integer carBrandId, Date startDate, Date endDate) {
		List<LineChartDataVO> rlist = new ArrayList<LineChartDataVO>();
		StringBuffer sql = new StringBuffer();
		sql.append("select a.grade id,a.article_date date,count(a.grade) num,case grade when 3 then '严重' when 2 then '中等' when 1 then '一般' end name");
		sql.append(String.format(" from article a where a.status=1 and a.affection=1 and a.brand_id='%d'",carBrandId));
		
		if(null!=startDate && null!=endDate){
			String sdate = DateUtils.dateToString(startDate,DateUtils.TimeFormatter.FORMATTER2);
			String edate = DateUtils.dateToString(endDate,DateUtils.TimeFormatter.FORMATTER2);
			sql.append(String.format(" and a.article_date>='%s'",sdate));
			sql.append(String.format(" and a.article_date<='%s'", edate));
		} else if(null!=startDate) {
			String date = DateUtils.dateToString(startDate,DateUtils.TimeFormatter.FORMATTER2);
			sql.append(String.format(" and a.article_date='%s'", date));
		} else if(null!=endDate) {
			String date = DateUtils.dateToString(endDate,DateUtils.TimeFormatter.FORMATTER2);
			sql.append(String.format(" and a.article_date='%s'", date));
		}
		sql.append(" group by a.brand_id,a.grade,a.article_date order by a.article_date asc");
		logger.debug("[getAegativeArticleStatisticsByCarbrandId]"+sql.toString());
		List<Map<String,Object>> tmp_list = genericDao.findListBySql(sql.toString());
		List<StatisticsVO> list = RowMapperUtils.map2List(tmp_list,StatisticsVO.class);
		
		if(null!=list && list.size()>0){
			Map<String,List<ChartCoreDataVO>> map = new HashMap<String, List<ChartCoreDataVO>>();
			for (StatisticsVO statisticsVO : list) {				
				ChartCoreDataVO vo = new ChartCoreDataVO();
				vo.setName(statisticsVO.getDate());
				vo.setValue(statisticsVO.getNum());
				
				String gradeName =statisticsVO.getName();
				if (map.containsKey(gradeName)) {
					map.get(gradeName).add(vo);
				} else {
					List<ChartCoreDataVO> tlist = new ArrayList<ChartCoreDataVO>();
					tlist.add(vo);					
					map.put(gradeName, tlist);
				}
			}
			for (Entry<String,List<ChartCoreDataVO>> tmap : map.entrySet()) {
				LineChartDataVO lvo = new LineChartDataVO();
				lvo.setName(tmap.getKey());
				lvo.setData(tmap.getValue());
				rlist.add(lvo);
			}
		}
		return rlist;
	}

	/**
	 * @laochen index downside
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
	@Override
	public ECPage<ArticleSimpleListVO> queryAegativeArticles(
			Integer[] carBrandId,String startDate,String endDate,int grade,int des_content,int areaId,int proviceId,int cityId,int pageNo,int pageSize) {
		try {

			final Criteria whereBy = new Criteria();
			whereBy.eq(" a."+ArticleRM.status, 1).eq(" a."+ArticleRM.affection, 1);
			if (grade > 0) {
				whereBy.eq(" a." + ArticleRM.grade, grade);
			}
			if(des_content>0){
				whereBy.eq(" a." + ArticleRM.desContent, des_content);
			}			
			if(areaId>0){
				whereBy.eq(" a."+ArticleRM.areaId,areaId);
			}
			if(proviceId>0){
				whereBy.eq(" a."+ArticleRM.provinceId,proviceId);
			}
			if(cityId>0){
				whereBy.eq(" a."+ArticleRM.cityId,cityId);
			}
			if (carBrandId != null && carBrandId.length > 0) {
				whereBy.in(" a."+ArticleRM.brandId, carBrandId);
			}
			
			if (startDate != null && endDate!=null) {
				whereBy.greateThenOrEquals(" a." + ArticleRM.articleDate,startDate);
				whereBy.lessThenOrEquals(" a." + ArticleRM.articleDate,endDate);
			} else if (startDate != null) {
				whereBy.eq(" a." + ArticleRM.articleDate,startDate);
			} else	if (endDate != null) {
				whereBy.eq(" a." + ArticleRM.articleDate,endDate);
			}
			
			whereBy.orderBy(" a."+ArticleRM.articleDate, OrderType.DESC);
			ECPage<ArticleSimpleListVO> page = PagingManager.list(new Paginable<ArticleSimpleListVO>(pageNo, pageSize) {
				@Override
				public List<ArticleSimpleListVO> findList() {
					whereBy.setPage(this.getPageNo(), this.getRowsPerPage());
					List<Map<String, Object>> map = articleDao.getArticleStatisticsBybrandIds(whereBy);
					List<ArticleSimpleListVO> list = RowMapperUtils.map2List(map, ArticleSimpleListVO.class);
					return list;
				}
				@Override
				public Long count() {
					whereBy.setEnableLimit(false);
//					Long c = articleDao.getArticleStatisticsCountsBybrandIds(whereBy);
					Long c = (long)this.getRowsPerPage();
					whereBy.setEnableLimit(true);
					if(c == null) {
						return 0l;
					}
					return c;
				}
			});	
			
			return page;
		} catch (Exception e) {
			logger.error("查询列表出错", e);
		}
		return null;
	}

	@Override
	public ECPage<ArticleComment> getRelatedArticlesComments(int pageNo,int pageSize, long articleId) {
		Criteria whereBy = new Criteria();
		whereBy.eq(ArticleCommentRM.articleId, articleId);		
		long counts = this.genericDao.count(ArticleComment.class,whereBy);		
		whereBy.setPage(pageNo, pageSize);
		List<ArticleComment> list = this.genericDao.findList(ArticleComment.class, whereBy);
		ECPage<ArticleComment> page = PageHelper.list(counts,list, whereBy);		
		return page;
	}

	/**
	 * @laochen discuss
	 * 模块：负面情报-> 车友评价明细
	 * TODO 需要查询viewpoint 获取对应的观点组合内容
	 * @param areaId
	 * @param provinceId
	 * @param startDate (yyyy-mm-dd)
	 * @param endDate (yyyy-mm-dd)
	 * @param affection
	 * @param desContent
	 * @param carSerialId[]
	 * @return ECPage<ArticleSimpleListVO> 
	 */
	@Override
	public ECPage<ArticleSimpleListVO> queryAegativeArticles(
			Integer[] carSerialIds, int areaId, int provinceId, String startDate,
			String endDate, int affection, int desContent, int pageNo, int pageSize) {		
		try {			
			final StringBuffer sql = new StringBuffer();
			//affection 文章类型 1：口碑 2：情报 3：其它
			//TODO 好、中、差的值从静态变量或数据库中进行获取,不要在SQL中写死
			sql.append("select a.id,a.title,case a.affection when 1 then '差' when 3 then '好' else '中' end affection,case a.des_target when 1 then '主机厂' when 2 then '4S店' else '其它' end desTarget,a.article_ctime pubDate,a.ctime createTime,a.url,a.simple_content content, '' as viewPoints,m.name mediaName from article a left join media_info m on a.media_id=m.id where a.has_viewpoint=1 and a.has_viewpoint=1 and a.status=1 ");
			
			final StringBuffer sqlCount = new StringBuffer();
			sqlCount.append("select count(id) as nums from article a where  a.has_viewpoint=1 and a.status=1 ");
			
			final StringBuffer conStr = new StringBuffer();
			if (affection > 0) {
				conStr.append(String.format(" and a.affection='%d'", affection));
			}
			if (desContent > 0) {
				conStr.append(String.format(" and a.des_content='%d'",desContent));				
			}
			if (provinceId > 0) {
				conStr.append(String.format(" and a.province_id='%d'",provinceId));
			} 
			if (areaId > 0) {
				conStr.append(String.format(" and a.area_id='%d'", areaId));
			}			
			if(null!=carSerialIds && carSerialIds.length>0){
				conStr.append(String.format(" and a.serial_id in (%s)",StringUtils.join(carSerialIds,",")));
			}			
			if (startDate != null && startDate.length()==10 && endDate != null && startDate.length()==10) {
				conStr.append(String.format(" and a.article_date>='%s'",startDate));
				conStr.append(String.format(" and a.article_date<='%s'",endDate));
			} else if (startDate != null && startDate.length()==10) {
				conStr.append(String.format(" and a.article_date='%s'",startDate));
			} else if (endDate != null && startDate.length()==10) {
				conStr.append(String.format(" and a.article_date='%s'",endDate));
			}						
			conStr.append(" order By a.article_date desc ");
			ECPage<ArticleSimpleListVO> page = PagingManager
					.list(new Paginable<ArticleSimpleListVO>(pageNo,
							pageSize) {
						@Override
						public List<ArticleSimpleListVO> findList() {
							sql.append(conStr.toString());							
							sql.append(String.format(" limit %d,%d", this.getOffset(),this.getRowsPerPage()));
							
							System.err.println("sql:"+sql.toString());
							
							List<Map<String, Object>> list = genericDao.findListBySql(sql.toString());
							List<ArticleSimpleListVO> vos = RowMapperUtils.map2List(list, ArticleSimpleListVO.class);
							
							List<ArticleSimpleListVO> rvos = new ArrayList<ArticleSimpleListVO>();
							for (ArticleSimpleListVO vo : vos) {
								String sub_sql = String.format("select GROUP_CONCAT(v.name SEPARATOR ';') as viewPoints from article_viewpoint av left join viewpoint v on av.viewpoint_id=v.id where av.article_id='%d' group by av.article_id", vo.getId());
								Map<String,Object> td = genericDao.findOneBySql(sub_sql);
								if(td!=null){
									vo.setViewPoints((String)td.get("viewPoints"));
									rvos.add(vo);
								}
							}
							return rvos;
						}
						@Override
						public Long count() {
							sqlCount.append(conStr.toString());
							System.err.println("sqlCount:"+sqlCount.toString());
							Map<String,Object> data = genericDao.findOneBySql(sqlCount.toString());
							return (Long) data.get("nums");
						}
					});
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("queryAegativeArticles error "+e.getMessage());
		}
		return null;
	}
	
	/**
	 * @laochen downside
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
	@Override
	public List<HighChartVO> queryAegativeArticleStatistics(
			Integer[] carBrandId, int grade, int areaId, int proviceId,
			int cityId, Date startDate, Date endDate) {
		List<HighChartVO> list =new ArrayList<HighChartVO>();
		
		logger.info("Map<Integer, Map<Date, Integer>> getAegativeArticleStatisticsByCarBrandId().....carBrandId="+ carBrandId[0]);
		
		for (Integer id : carBrandId) {
			HighChartVO vo = new HighChartVO();
			
			Criteria whereBy = new Criteria();
			whereBy.eq(ArticleRM.affection, 1).eq(ArticleRM.status, 1);
			if (grade > 0) {
				whereBy.eq(ArticleRM.grade, grade, CondtionSeparator.AND);
			}
			if(areaId>0){
				whereBy.eq(ArticleRM.areaId,areaId,CondtionSeparator.AND);
			}
			if(proviceId>0){
				whereBy.eq(ArticleRM.provinceId,proviceId,CondtionSeparator.AND);
			}
			if(cityId>0){
				whereBy.eq(ArticleRM.cityId,cityId,CondtionSeparator.AND);
			}
			
			if (id > 0) {
				whereBy.eq(ArticleRM.brandId, id, CondtionSeparator.AND);
			}
			if (startDate != null && endDate != null) {
				whereBy.greateThenOrEquals(	ArticleRM.articleDate,DateUtils.dateToString(startDate, TimeFormatter.FORMATTER2));
				whereBy.lessThenOrEquals(ArticleRM.articleDate,DateUtils.dateToString(endDate, TimeFormatter.FORMATTER2));
			} else if (startDate != null) {
				whereBy.eq(	ArticleRM.articleDate,DateUtils.dateToString(startDate, TimeFormatter.FORMATTER2));
			} else if (endDate != null) {
				whereBy.eq(ArticleRM.articleDate,DateUtils.dateToString(endDate, TimeFormatter.FORMATTER2));
			} else {
				Calendar ca = Calendar.getInstance();
				ca.add(Calendar.DAY_OF_YEAR, -7);
				whereBy.greateThenOrEquals(ArticleRM.articleDate, DateUtils.dateToString(ca.getTime(), TimeFormatter.FORMATTER2));
				whereBy.lessThenOrEquals(ArticleRM.articleDate, DateUtils.dateToString(new Date(), TimeFormatter.FORMATTER2));
			}
						
			
			whereBy.groupBy(ArticleRM.articleDate).orderBy(ArticleRM.articleDate,OrderType.ASC);

			List<Map<String, Object>> mapList = articleDao.findAegativeArticleStatisticsByGrade(whereBy);
			List<ArticleAegativeVO> listvo = RowMapperUtils.map2List(mapList,ArticleAegativeVO.class);
			vo.setId(id);
			vo.setTitle(this.genericDao.findByPK(DictCarBrand.class, id).getName());
			vo.setList(listvo);
			list.add(vo);
		}
		return list;
	}

	/**
	 * @laochen downside
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
	@Override
	public long getAegativeArticleStatisticsCountsByCarbrandId(
			Integer[] carBrandId, int grade,int areaId, int proviceId, int cityId,
			String startDate, String endDate) {
		StringBuffer sb =new StringBuffer();
		sb.append("select count(date) as aa from( SELECT article_date as  date ");
		for (Integer id : carBrandId) {			
			if(id>0){
				DictCarBrand car = this.genericDao.findByPK(DictCarBrand.class, id);
				sb.append(",COUNT( CASE WHEN a.brand_id="+id+"   THEN 0 ELSE NULL END ) as '"+id+"_"+car.getName()+"' ");
			}
		}
		sb.append(" FROM article a where  a. STATUS = 1 AND a.affection = 1 ");
		if(grade>0){
			sb.append(" and grade="+grade);
		}
		if(areaId>0){
			sb.append(" and area_id="+areaId);
		}
		if(proviceId>0){
			sb.append(" and province_id="+proviceId);
		}
		if(cityId>0){
			sb.append(" and city_id="+cityId);
		}
		
		if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
			sb.append(" and article_date>='"+startDate+"'");
			sb.append(" and article_date<='"+endDate+"'");
		} else if(StringUtils.isNotBlank(startDate)){
			sb.append(" and article_date='"+startDate+"'");
		} else if(StringUtils.isNotBlank(endDate)){
			sb.append(" and article_date='"+endDate+"'");
		}
		
		sb.append(" GROUP BY article_date ");
		sb.append(" )aa");
		List<Map<String, Object>>  list=genericDao.findListBySql(sb.toString());
		return (Long) list.get(0).get("aa");
	}
	
	/**
	 * @laochen index
	 * 获取指定品牌的负面情报列表
	 * @param carBrandId
	 * @param limit
	 * @return List<ArticleSimpleListVO>
	 */
	@Override
	public List<ArticleSimpleListVO> getAegativeArticlesByCarBrandId(
			int carBrandId, int pageSize) {
		Criteria criteria = new Criteria();		
		criteria.eq(ArticleRM.affection, 1)//指定为负面文章 affection {1:负面，2：中性，3：正面}
		.eq(ArticleRM.status, 1)
		.eq(ArticleRM.brandId, carBrandId)
		.setPage(1, pageSize)
		.orderBy(ArticleRM.articleCtime, OrderType.DESC);
		
		List<Article> tlist = genericDao.findList(Article.class, criteria);				
		if(tlist!=null && tlist.size()>0){
			
			//TODO 文章描述对像 需要 从缓存或从静态变量中获取
			Map<Integer,String> destTargetDict = new HashMap<Integer, String>();
			destTargetDict.put(1, "主机厂");
			destTargetDict.put(2, "4S店");
			destTargetDict.put(3, "其它");
			
			List<ArticleSimpleListVO> rlist = new ArrayList<ArticleSimpleListVO>();			
			for (Article article : tlist) {
				ArticleSimpleListVO vo = new ArticleSimpleListVO();
				vo.setId(article.getId());
				vo.setTitle(article.getTitle());
//				vo.setCreateTime(article.getArticleCtime());
				vo.setPubDate(article.getArticleCtime());
				vo.setUrl(article.getUrl());
				vo.setContent(article.getSimpleContent());
				vo.setDesTarget(destTargetDict.get(article.getDesTarget()));		
				rlist.add(vo);				
			}
			return rlist;
		}
		return null;
	}	
}
