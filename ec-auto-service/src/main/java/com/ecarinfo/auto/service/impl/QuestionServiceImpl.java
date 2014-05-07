package com.ecarinfo.auto.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ecarinfo.auto.service.QuestionService;
import com.ecarinfo.auto.vo.AreaCountVO;
import com.ecarinfo.auto.vo.ArticleQuestionVO;
import com.ecarinfo.auto.vo.ChartCoreDataVO;
import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.Paginable;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.persist.util.RowMapperUtils;

@Service
public class QuestionServiceImpl implements QuestionService {
	private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);
	@Resource
	GenericDao genericDao;
	
	@Override
	public List<ChartCoreDataVO> getQuestionChartList(Integer brandId,Integer serialId,
			Integer limit,int questionType) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select q.name,count(a.question_id) as value  from article a LEFT join question q on a.question_id=q.id where a.`status`=1 and a.question_id >0 "); 
		if(serialId>0){
			sql.append(String.format("and a.serial_id=%s" ,serialId));
		}
		
		sql.append(String.format(" and q.type=%d",questionType));
		sql.append(	String.format(" GROUP BY a.question_id ORDER BY value DESC  ",limit));
		if(limit>0){
			sql.append(	String.format("  LIMIT %s ",limit));
		}
		logger.debug("[getQuestionChartList]sql:"+sql.toString());
		
		List<Map<String,Object>> tmp_list = genericDao.findListBySql(sql.toString());
		List<ChartCoreDataVO> list = RowMapperUtils.map2List(tmp_list,ChartCoreDataVO.class);
		
		return list;
	}

	@Override
	public List<AreaCountVO> getAreaQuestionCountsList(Integer serialId,
			Integer limit,int questionType) {
		StringBuffer sql = new StringBuffer();
		sql.append(String.format(" select * from (SELECT count(a.id) as counts, "+
				" p.name as province_name,"+
				" c.name as city_name"+" FROM article a left join dict_province p on a.province_id=p.id"+
				" left join question q on a.question_id=q.id"+
				" LEFT JOIN dict_city c ON a.city_id= c.id "+" WHERE a.`status` =1 AND a.question_id > 0 and a.city_id>0 AND a.serial_id = %s" ,serialId));
		sql.append(String.format(" and q.type=%d",questionType));
		sql.append(	String.format(" GROUP BY a.city_id ORDER BY counts DESC ) aa  limit %s  ",limit));
		
		logger.debug("[getAreaQuestionCountsList]sql:"+sql.toString());
		
		List<Map<String,Object>> tmp_list = genericDao.findListBySql(sql.toString());
		List<AreaCountVO> list = RowMapperUtils.map2List(tmp_list,AreaCountVO.class);
		return list;
	}

	@Override
	public ECPage<ArticleQuestionVO> queryQuestionList(Integer serialId,
			String startDate, String endDate, int questionId, int provinceId,
			int cityId, int pageNo, int pageSize) {
		try {			
			final StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT a.id,a.title,a.url,q.`name` as question_name,q.type ,a.article_date,p.`name` as province_name,c.`name` as city_name"+
					" FROM article a LEFT JOIN question q ON a.question_id = q.id LEFT JOIN dict_province p ON p.id=a.province_id " +
					" LEFT JOIN dict_city c ON c.id = a.city_id WHERE a.`status` = 1 AND a.question_id > 0 and q.type >0 ");
			
			final StringBuffer sqlCount = new StringBuffer();
			sqlCount.append("select count(a.id ) as num from article a LEFT JOIN question q ON a.question_id = q.id where a.`status`=1 and a.question_id>0 and q.type >0 ");
			
			final StringBuffer conStr = new StringBuffer();
			if (serialId > 0) {
				conStr.append(String.format(" and a.serial_id='%d'", serialId));
			}
			if (questionId > 0) {
				conStr.append(String.format(" and a.question_id='%d'",questionId));				
			}
			if (provinceId > 0) {
				conStr.append(String.format(" and a.province_id='%d'",provinceId));
			} 
			if (cityId > 0) {
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
			conStr.append(" order By a.article_date desc ");
			ECPage<ArticleQuestionVO> page = PagingManager
					.list(new Paginable<ArticleQuestionVO>(pageNo,
							pageSize) {
						@Override
						public List<ArticleQuestionVO> findList() {
							sql.append(conStr.toString());							
							sql.append(String.format(" limit %d,%d", this.getOffset(),this.getRowsPerPage()));
							
							System.err.println("sql:"+sql.toString());
							
							List<Map<String, Object>> list = genericDao.findListBySql(sql.toString());
							List<ArticleQuestionVO> vos = RowMapperUtils.map2List(list, ArticleQuestionVO.class);
							
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
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("query error "+e.getMessage());
		}
		return null;
	}

	@Override
	public long allQuestionCounts(Integer sericalId, int type) {
		try {			
			final StringBuffer sql = new StringBuffer();
			sql.append(String.format("SELECT count(a.id) num FROM article a LEFT JOIN question q ON a.question_id = q.id"+
					" WHERE a.`status` = 1 AND a.question_id > 0 AND a.serial_id =%d ",sericalId));
			sql.append( String.format("AND q.type = %d",type));
			
			Map<String,Object> data = genericDao.findOneBySql(sql.toString());
				return (Long) data.get("num");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("query error "+e.getMessage());
		}
		return 0l;
	}
}
