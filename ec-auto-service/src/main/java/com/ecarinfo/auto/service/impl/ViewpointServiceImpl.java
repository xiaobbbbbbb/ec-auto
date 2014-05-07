package com.ecarinfo.auto.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecarinfo.auto.dao.ViewpointDao;
import com.ecarinfo.auto.rm.ArticleRM;
import com.ecarinfo.auto.rm.ViewpointRM;
import com.ecarinfo.auto.service.ViewpointService;
import com.ecarinfo.auto.util.PageHelper;
import com.ecarinfo.auto.vo.ViewpointCountVO;
import com.ecarinfo.auto.vo.ViewpointVO;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.util.RowMapperUtils;

@Service
public class ViewpointServiceImpl implements ViewpointService {
	
	private static final Logger logger = LoggerFactory.getLogger(ViewpointServiceImpl.class);
	
	@Autowired
	private GenericDao genericDao;
	
	@Autowired
	private ViewpointDao viewpointDao;
	
	/**
	 * 模块：品牌分析->品牌口碑
	 * 根据厂牌ID查询一段时间内容某个区域的观点数据清单
	 * @param carBrandId
	 * @param areaId
	 * @param provinceId
	 * @param cityId
	 * @param startDate
	 * @param endDate
	 * @param limit
	 * @return
	 */
	@Override
	public List<ViewpointVO> getViewpointsByCarBrandId(int carBrandId,int affection,
			int areaId, int provinceId, int cityId, Date startDate,
			Date endDate, int limit) {
		logger.debug("getViewpointsByCarBrandId carBrandId="+carBrandId);
		return	getViewpointsByCar(carBrandId,0,affection,areaId,provinceId,cityId,startDate,endDate,limit);
		
	}

	/**
	 * @laochen index
	 * 模块：品牌分析->产品口碑
	 * 根据车系ID查询一段时间内容某个区域的观点数据清单
	 * @param carSerialId
	 * @param areaId
	 * @param provinceId
	 * @param cityId
	 * @param startDate
	 * @param endDate
	 * @param limit
	 * @return
	 */
	@Override
	public List<ViewpointVO> getViewpointsBySerialId(Integer[] carSerialId,int affection,
			int areaId, int provinceId, int cityId, Date startDate,
			Date endDate, int pageSize) {
		
		Criteria whereBy = new Criteria();
		
		whereBy.eq(" a."+ArticleRM.status, 1);
		
		if(carSerialId!=null&&carSerialId.length>0){
			whereBy.in(" a."+ArticleRM.serialId, carSerialId, CondtionSeparator.AND);
		}
		if(areaId>0){
			whereBy.eq(" a."+ArticleRM.areaId, areaId, CondtionSeparator.AND);
		}
		if(provinceId>0){
			whereBy.eq(" a."+ArticleRM.provinceId, provinceId, CondtionSeparator.AND);
		}
		if(cityId>0){
			whereBy.eq(" a."+ArticleRM.cityId, cityId, CondtionSeparator.AND);
		}
		if(startDate!=null&&endDate!=null){
			whereBy.greateThenOrEquals(" a."+ArticleRM.articleDate, DateUtils.dateToString(startDate, TimeFormatter.FORMATTER2), CondtionSeparator.AND);
			whereBy.lessThenOrEquals(" a."+ArticleRM.articleDate, DateUtils.dateToString(endDate, TimeFormatter.FORMATTER2), CondtionSeparator.AND);
		}
		else if(startDate!=null){
			whereBy.eq(" a."+ArticleRM.articleDate, DateUtils.dateToString(startDate, TimeFormatter.FORMATTER2), CondtionSeparator.AND);
		}
		else if(endDate!=null){
			whereBy.lessThenOrEquals(" a."+ArticleRM.articleDate, DateUtils.dateToString(endDate, TimeFormatter.FORMATTER2), CondtionSeparator.AND);
		}
		//默认近7天的数据
		if(startDate==null&&endDate==null){
			Calendar ca = Calendar.getInstance();
			ca.setTime(new Date());
			ca.add(Calendar.DAY_OF_YEAR, -7);
			whereBy.greateThenOrEquals(" a."+ArticleRM.articleDate, DateUtils.dateToString(ca.getTime(), TimeFormatter.FORMATTER2), CondtionSeparator.AND);
			whereBy.lessThenOrEquals(" a."+ArticleRM.articleDate, DateUtils.dateToString(new Date(), TimeFormatter.FORMATTER2), CondtionSeparator.AND);
		}
		if(affection>0){
			whereBy.eq(" v."+ViewpointRM.affection, affection, CondtionSeparator.AND);
		}
		whereBy.groupBy(" v."+ViewpointRM.name);
		if(pageSize>0){
			whereBy.setPage(1, pageSize);
		}
		List<Map<String, Object>> map = viewpointDao.findViewpointsByCriteria(whereBy);
		List<ViewpointVO> list = RowMapperUtils.map2List(map, ViewpointVO.class);
		return list;
	}

	/**
	 * @laochen media
	 * 模块： 网友评论列表
	 * 获取所有观点内容
	 * @param startDate
	 * @param endDate
	 * @param affection 情感
	 * @param areaId
	 * @param provinceId
	 * @param cityId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Override
	public ECPage<ViewpointVO> getViewpoints(int carBrandId,int carSerialId, Date startDate, Date endDate,
			int affection, int areaId, int provinceId, int cityId, int pageNo,
			int pageSize) {
		Criteria whereBy = new Criteria();
		
		whereBy.eq(" a."+ArticleRM.status, 1);
		if(carBrandId>0){
			whereBy.eq(" a."+ArticleRM.brandId, carBrandId, CondtionSeparator.AND);
		}
		if(carSerialId>0){
			whereBy.eq(" a."+ArticleRM.serialId, carSerialId, CondtionSeparator.AND);
		}else{
			logger.error("车系ID为空!");
		}
		if(startDate!=null){
			whereBy.greateThenOrEquals(" a."+ArticleRM.articleDate, DateUtils.dateToString(startDate, TimeFormatter.FORMATTER2), CondtionSeparator.AND);
		}
		if(endDate!=null){
			whereBy.lessThenOrEquals(" a."+ArticleRM.articleDate, DateUtils.dateToString(endDate, TimeFormatter.FORMATTER2), CondtionSeparator.AND);
		}
		if(startDate==null&&endDate==null){
			Calendar ca = Calendar.getInstance();
			ca.setTime(new Date());
			ca.add(Calendar.DAY_OF_YEAR, -7);
			whereBy.greateThenOrEquals(" a."+ArticleRM.articleDate, DateUtils.dateToString(ca.getTime(), TimeFormatter.FORMATTER2), CondtionSeparator.AND);
			whereBy.lessThen(" a."+ArticleRM.articleDate, DateUtils.dateToString(new Date(), TimeFormatter.FORMATTER2), CondtionSeparator.AND);
		}
		if(affection>0){
			whereBy.eq(" v."+ArticleRM.affection, affection, CondtionSeparator.AND);
		}
		if(areaId>0){
			whereBy.eq(" a."+ArticleRM.areaId, areaId, CondtionSeparator.AND);
		}
		if(provinceId>0){
			whereBy.eq(" a."+ArticleRM.provinceId, provinceId, CondtionSeparator.AND);
		}
		if(cityId>0){
			whereBy.eq(" a."+ArticleRM.cityId, cityId, CondtionSeparator.AND);
		}
		long  counts = this.viewpointDao.findActiclViewpointsCountsByCriteria(whereBy);
		if(pageSize>0&&pageNo>0){
			whereBy.setPage(pageNo, pageSize).orderBy(" a."+ArticleRM.articleDate, OrderType.DESC);
		}
		

		List<Map<String, Object>> map = viewpointDao.findActiclViewpointsByCriteria(whereBy);
		List<ViewpointVO> list = RowMapperUtils
				.map2List(map, ViewpointVO.class);
		if(list!=null&&list.size()>0){
			for(ViewpointVO vo:list){
				List<ViewpointVO> list2 =this.getViewpointsByProvince(carSerialId, vo.getId(), startDate, endDate);
				if(list2!=null&&list2.size()>0){
					vo.setArticleNums(list2.size());
				}
			}
		}
		ECPage<ViewpointVO> page = PageHelper.list(counts, list, whereBy);
		return page;
	}
	
	
	private List<ViewpointVO> getViewpointsByCar(int carBrandId,int carSerialId,int affection,
			int areaId, int provinceId, int cityId, Date startDate,
			Date endDate, int pageSize){
		
		Criteria whereBy = new Criteria();		
		whereBy.eq(" a."+ArticleRM.status, 1);
		if(affection>0){
			whereBy.eq(" v."+ViewpointRM.affection, affection, CondtionSeparator.AND);
		}
		if(areaId>0){
			whereBy.eq(" a."+ArticleRM.areaId, areaId, CondtionSeparator.AND);
		}
		if(provinceId>0){
			whereBy.eq(" a."+ArticleRM.provinceId, provinceId, CondtionSeparator.AND);
		}
		if(cityId>0){
			whereBy.eq(" a."+ArticleRM.cityId, cityId, CondtionSeparator.AND);
		}
		if(carBrandId>0){
			whereBy.eq(" a."+ArticleRM.brandId, carBrandId, CondtionSeparator.AND);
		}
		if(carSerialId>0){
			whereBy.eq(" a."+ArticleRM.serialId, carSerialId, CondtionSeparator.AND);
		}
		
		if(startDate!=null && endDate!=null){
			whereBy.greateThenOrEquals(" a."+ArticleRM.articleDate, DateUtils.dateToString(startDate, TimeFormatter.FORMATTER2));
			whereBy.lessThenOrEquals(" a."+ArticleRM.articleDate, DateUtils.dateToString(endDate, TimeFormatter.FORMATTER2));
		} else if(startDate!=null){
			whereBy.eq(" a."+ArticleRM.articleDate, DateUtils.dateToString(startDate, TimeFormatter.FORMATTER2));
		} else if(endDate!=null){
			whereBy.eq(" a."+ArticleRM.articleDate, DateUtils.dateToString(endDate, TimeFormatter.FORMATTER2));
		} else {//默认近7天的数据	
			Calendar ca = Calendar.getInstance();
			ca.setTime(new Date());
			ca.add(Calendar.DAY_OF_YEAR, -7);
			whereBy.greateThenOrEquals(" a."+ArticleRM.articleDate, DateUtils.dateToString(ca.getTime(), TimeFormatter.FORMATTER2), CondtionSeparator.AND);
			whereBy.lessThenOrEquals(" a."+ArticleRM.articleDate, DateUtils.dateToString(new Date(), TimeFormatter.FORMATTER2), CondtionSeparator.AND);
		}		
		whereBy.groupBy(" v."+ViewpointRM.name);
		if(pageSize>0){
			whereBy.setPage(1, pageSize);
		}
		List<Map<String, Object>> map = viewpointDao.findViewpointsByCriteria(whereBy);
		List<ViewpointVO> list = RowMapperUtils.map2List(map, ViewpointVO.class);
		return list;
	}	
	

	@Override
	public List<ViewpointVO> getViewpointsByProvince(int carSerialId,
			int viewpointId, Date startDate, Date endDate) {
		logger.info("====getViewpointsByProvince()  carSerialId======"+carSerialId);
		logger.info("====getViewpointsByProvince()  viewpointId======"+viewpointId);
		Criteria whereBy = new Criteria();
		
		whereBy.eq(" a."+ArticleRM.status, 1);
		if(carSerialId>0){
			whereBy.eq(" a."+ArticleRM.serialId, carSerialId, CondtionSeparator.AND);
		}
		if(viewpointId>0){
			whereBy.eq(" v."+ViewpointRM.id, viewpointId, CondtionSeparator.AND);
		}
		if(startDate!=null && endDate!=null){
			whereBy.greateThenOrEquals(" a."+ArticleRM.articleDate, DateUtils.dateToString(startDate, TimeFormatter.FORMATTER2));
			whereBy.lessThenOrEquals(" a."+ArticleRM.articleDate, DateUtils.dateToString(endDate, TimeFormatter.FORMATTER2));
		} else if(startDate!=null){
			whereBy.eq(" a."+ArticleRM.articleDate, DateUtils.dateToString(startDate, TimeFormatter.FORMATTER2));
		} else if(endDate!=null){
			whereBy.eq(" a."+ArticleRM.articleDate, DateUtils.dateToString(endDate, TimeFormatter.FORMATTER2));
		} else {	//默认近7天的数据		
			Calendar ca = Calendar.getInstance();
			ca.setTime(new Date());
			ca.add(Calendar.DAY_OF_YEAR, -7);
			whereBy.greateThenOrEquals(" a."+ArticleRM.articleDate, DateUtils.dateToString(ca.getTime(), TimeFormatter.FORMATTER2), CondtionSeparator.AND);
			whereBy.lessThenOrEquals(" a."+ArticleRM.articleDate, DateUtils.dateToString(new Date(), TimeFormatter.FORMATTER2), CondtionSeparator.AND);
		}	
		whereBy.groupBy(" v."+ViewpointRM.name);		
		List<Map<String, Object>> map = viewpointDao.findViewpointsByCriteria(whereBy);
		List<ViewpointVO> list = RowMapperUtils.map2List(map, ViewpointVO.class);
		return list;
	}
	
	
	/**
	 * 根据车系ID获取对应情感观点的统计数
	 * @param carSerialId
	 * @param affection
	 * @param areaId
	 * @param provinceId
	 * @param cityId
	 * @param startDate
	 * @param endDate
	 * @param limit
	 * @return  List<Map<String,Integer>>
	 */
	@Override
	public  List<ViewpointCountVO> getViewpointsStatisticsByCarSerial(
			int carSerialId, int affection, int areaId, int provinceId,
			int cityId, String startDate,String endDate, int limit) {
		List<ViewpointCountVO> rlist = null;		
		StringBuffer sb = new StringBuffer();
		sb.append("select v.name,count(distinct a.id) nums from from article a");		
		sb.append(" left join article_viewpoint av on a.id=av.article_id");
		sb.append(String.format(" left join viewpoint v on v.id=av.viewpoint_id where a.serial_id='%d'",carSerialId));
		
		if(affection>0){
			sb.append(String.format(" and v.affection='%d'", affection));
		}
		if(areaId>0){
			sb.append(String.format(" and a.area_id='%d'", areaId));
		}
		if(provinceId>0){
			sb.append(String.format(" and a.provice_id='%d'", provinceId));
		}
		if(cityId>0){
			sb.append(String.format(" and a.city_id='%d'", cityId));
		}
		
		if(StringUtils.isNotBlank(startDate) && startDate.length()==10 && StringUtils.isNotBlank(endDate)&& endDate.length()==10 ){
			sb.append(String.format(" and a.article_date>='%s'", startDate));
			sb.append(String.format(" and a.article_date<='%s'", endDate));
		} else if(startDate!=null && startDate.length()==10){
			sb.append(String.format(" and a.article_date='%s'", startDate));
		} else if(endDate!=null&&endDate.length()==10){
			sb.append(String.format(" and a.article_date='%s'", endDate));
		}
		sb.append(" group by v.id");
		if(limit>0){
			sb.append(" limit "+limit);
		}
		List<Map<String,Object>> list = genericDao.findListBySql(sb.toString());		
		if(list!=null && list.size()>0){
			rlist  = RowMapperUtils.map2List(list,ViewpointCountVO.class);
		}
		return rlist;
	}

}
