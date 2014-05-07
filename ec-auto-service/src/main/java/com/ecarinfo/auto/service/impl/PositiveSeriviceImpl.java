package com.ecarinfo.auto.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.ecarinfo.auto.dao.ArticleDao;
import com.ecarinfo.auto.po.Article;
import com.ecarinfo.auto.rm.ArticleRM;
import com.ecarinfo.auto.service.PositiveService;
import com.ecarinfo.auto.vo.ArticleSimpleListVO;
import com.ecarinfo.auto.vo.ChartCoreDataVO;
import com.ecarinfo.auto.vo.PositiveViewVO;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.Paginable;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.persist.util.RowMapperUtils;
@Service
public class PositiveSeriviceImpl implements PositiveService {
	private static final Logger logger = LoggerFactory.getLogger(PositiveSeriviceImpl.class);

	@Resource
	GenericDao genericDao;
	
	@Resource
	ArticleDao articleDao;
	
	@Override
	public long getPositiveCounts(int brandId,int serialId,int grade) {
		Criteria whereBy = new Criteria();
		whereBy.eq(ArticleRM.status, 1).eq(ArticleRM.affection, 1, CondtionSeparator.AND);
		if(grade>0){
			whereBy.eq(ArticleRM.grade,grade);
		}
		if(brandId>0){
			whereBy.eq(ArticleRM.brandId, brandId, CondtionSeparator.AND);
		}
		if(serialId>0){
			whereBy.eq(ArticleRM.serialId, serialId, CondtionSeparator.AND);
		}
		long counts = this.genericDao.count(Article.class, whereBy);
		return counts;
	}

	@Override
	public List<ChartCoreDataVO> getPositiveCountsByType(int brandId,int serialId) {
		StringBuffer sql = new StringBuffer();
		sql .append( "select count(id) value ,des_content name  from article a  where  a.`status`=1 and a.affection =1 and a.grade>0") ;
		if(brandId>0){
			sql.append(String.format(" and a.brand_id=%d", brandId));
		}
		if(serialId>0){
			sql.append(String.format(" and a.serial_id=%d", serialId));
		}
		sql.append("  GROUP BY des_content order by des_content desc  ");
		logger.debug("[getPositiveCountsByType]sql:"+sql.toString());
		List<Map<String,Object>> tmp_list = genericDao.findListBySql(sql.toString());
		List<ChartCoreDataVO> list = RowMapperUtils.map2List(tmp_list,ChartCoreDataVO.class);
		return list;
	}

	@Override
	public List<ChartCoreDataVO> getChartVO(int brandId,int serialId,String startDate,String endDate) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(id) value,article_date as name from article a  where a.affection=1 and a.`status`=1 and a.grade>0 " );
		if(brandId>0){
			sql.append(String.format(" and brand_id=%d",brandId));
		}
		if(serialId>0){
			sql.append(String.format(" and serial_id=%d",serialId));
		}
		if(null!=startDate && null!=endDate) {
			sql.append(String.format(" and a.article_date>='%s'", startDate));
			sql.append(String.format(" and a.article_date<='%s'", endDate));
		} else if(startDate!=null) {
			sql.append(String.format(" and a.article_date='%s'", startDate));
		} else if(endDate!=null) {
			sql.append(String.format(" and a.article_date='%s'", endDate));
		}
		sql.append(" group by a.article_date ");
		
		logger.debug("[getChartVO]sql:"+sql.toString());
		
		List<Map<String,Object>> tmp_list = genericDao.findListBySql(sql.toString());
		List<ChartCoreDataVO> list = RowMapperUtils.map2List(tmp_list,ChartCoreDataVO.class);
		
		return list;
	}

	@Override
	public ECPage<PositiveViewVO> getPositives(String startDate, String endDate,Integer brandId,
			Integer serialId, Integer grade, Integer provinceId,Integer cityId,Integer viewPointType, Integer pageNo, Integer pageSize) {
		try {			
			final StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT a.id, a.title,a.grade, a.url,ds.name serialName, dp.`name` provinceName,dc.`name` cityName, a.article_date FROM article a "+
						"LEFT JOIN dict_province dp ON dp.id = a.province_id  "+
						"LEFT JOIN dict_car_serial ds ON ds.id = a.serial_id "+

						"LEFT JOIN dict_city dc ON dc.id = a.city_id "+
						"WHERE a.`status` = 1 AND a.affection = 1  and a.grade>0 ");

			final StringBuffer sqlCount = new StringBuffer();
			sqlCount.append("SELECT count(a.id) num FROM	article a "+
						"LEFT JOIN dict_province dp ON dp.id = a.province_id "+
						"LEFT JOIN dict_car_serial ds ON ds.id = a.serial_id "+
						"LEFT JOIN dict_city dc ON dc.id = a.city_id "+
						"WHERE a.`status` = 1 AND a.affection = 1 and a.grade>0 ");
			
			final StringBuffer conStr = new StringBuffer();
			if(brandId!=null && brandId>0){			
				conStr.append(String.format("  and a.brand_id='%d'",brandId));
			}
			if(serialId!=null && serialId>0){			
				conStr.append(String.format("  and a.serial_id='%d'",serialId));
			}
			if (grade!=null && grade>0) {
				conStr.append(String.format(" and a.grade='%d'",grade));				
			}
			if (provinceId!=null &&  provinceId > 0) {
				conStr.append(String.format(" and a.province_id='%d'", provinceId));
			}	
			 		
			if(cityId!=null && cityId>0){
				conStr.append(String.format(" and a.city_id='%d'", cityId));
			}
			if (startDate != null && startDate.length()==10 && endDate != null && startDate.length()==10) {
				conStr.append(String.format(" and a.article_date>='%s'",startDate));
				conStr.append(String.format(" and a.article_date<='%s'",endDate));
			} else if (startDate != null && startDate.length()==10) {
				conStr.append(String.format(" and a.article_date='%s'",startDate));
			} else if (endDate != null && startDate.length()==10) {
				conStr.append(String.format(" and a.article_date='%s'",endDate));
			}		
			
		
			ECPage<PositiveViewVO> page = PagingManager
					.list(new Paginable<PositiveViewVO>(pageNo,
							pageSize) {
						@Override
						public List<PositiveViewVO> findList() {
							sql.append(conStr.append(" order By a.article_date desc "));							
							sql.append(String.format(" limit %d,%d", this.getOffset(),this.getRowsPerPage()));
							
							System.err.println("sql:"+sql.toString());
							
							List<Map<String, Object>> list = genericDao.findListBySql(sql.toString());
							List<PositiveViewVO> vos = RowMapperUtils.map2List(list, PositiveViewVO.class);
							
							return vos;
						}
						@Override
						public Long count() {
							sqlCount.append(conStr.toString());
							System.err.println("sqlCount:"+sqlCount.toString());
							Map<String,Object> data = genericDao.findOneBySql(sqlCount.toString());
							return (Long) data.get("num");
						}
					});
			return  page;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getPositives error "+e.getMessage());
		}
		return null;
	}

	@Override
	public ECPage<ArticleSimpleListVO> queryAegativeArticles(Integer brandId,Integer serialId,
			int grade, String title, Integer pageNo, Integer pageSize) {
		try {

			final Criteria whereBy = new Criteria();
			whereBy.eq(" a."+ArticleRM.status, 1).eq(" a."+ArticleRM.affection, 1);
			whereBy.greateThenOrEquals(" a."+ArticleRM.grade, 1, CondtionSeparator.AND);
			if(brandId!=null&&brandId>0){
				whereBy.eq(" a."+ArticleRM.brandId, brandId, CondtionSeparator.AND);
			}
			if(serialId!=null&&serialId>0){
				whereBy.eq(" a."+ArticleRM.serialId, serialId, CondtionSeparator.AND);
			}
			if (grade > 0) {
				whereBy.eq(" a."+ ArticleRM.grade,  grade,CondtionSeparator.AND);
			}
			if(!StringUtils.isEmpty(title)){
				whereBy.like(" a."+ArticleRM.title, "%"+title+"%", CondtionSeparator.AND);
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
					Long c = articleDao.getArticleStatisticsCountsBybrandIds(whereBy);
//					Long c = (long)this.getRowsPerPage();
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

}
