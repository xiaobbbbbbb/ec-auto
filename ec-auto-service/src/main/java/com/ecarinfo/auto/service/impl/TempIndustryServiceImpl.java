package com.ecarinfo.auto.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ecarinfo.auto.dao.TmpIndustryArticleDao;
import com.ecarinfo.auto.rm.TmpIndustryArticleRM;
import com.ecarinfo.auto.service.TempIndustryService;
import com.ecarinfo.auto.util.PageHelper;
import com.ecarinfo.auto.vo.TempIndustryVO;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.util.RowMapperUtils;
@Service
public class TempIndustryServiceImpl implements TempIndustryService{
	private static final Logger logger = LoggerFactory.getLogger(TempIndustryServiceImpl.class);
	@Resource
	private TmpIndustryArticleDao tmpIndustryArticleDao;
	@Override
	public ECPage<TempIndustryVO> getIndustryArticleList(String startDate,String endDate,String title,Integer type,Integer[] carbrand_id,int pageNo, int pageSize) {
		Criteria whereBy = new Criteria();
		whereBy.eq("1", 1);
		if(title!=null && title.length()>0){
			whereBy.like(" tmp."+TmpIndustryArticleRM.title, "%"+title+"%", CondtionSeparator.AND);
		}
		if(type!=null && type==1){  //我的
			whereBy.in(" tmp."+TmpIndustryArticleRM.brandId, carbrand_id, CondtionSeparator.AND);

		}
		if(type!=null && type==2){  //竞品
			whereBy.notIn(" tmp."+TmpIndustryArticleRM.brandId, carbrand_id, CondtionSeparator.AND);
		}
		if (startDate != null && startDate.length()>0 && endDate != null && endDate.length()>0) {
			whereBy.greateThenOrEquals(" tmp."+TmpIndustryArticleRM.pubTime, startDate);
			whereBy.lessThenOrEquals(" tmp."+TmpIndustryArticleRM.pubTime, endDate);
		} 
		long counts = tmpIndustryArticleDao.findTmpIACountsByCriteria(whereBy);
		whereBy.orderBy(" tmp."+TmpIndustryArticleRM.pubTime, OrderType.DESC);
		whereBy.setPage(pageNo, pageSize);
		List<Map<String, Object>> mlist = tmpIndustryArticleDao.findTmpIAByCriteria(whereBy);
		List<TempIndustryVO> list = RowMapperUtils.map2List(mlist,TempIndustryVO.class);
		ECPage<TempIndustryVO> page = PageHelper.list(counts,list, whereBy);
		return page;
	}
	
	@Override
	public long getTempIndustryCounts(String startDate, String endDate,Integer type, Integer[] carbrand_id) {
		Criteria whereBy = new Criteria();
		if (startDate != null && startDate.length()>0 && endDate != null && endDate.length()>0) {
			whereBy.greateThenOrEquals(" tmp."+TmpIndustryArticleRM.pubTime, startDate);
			whereBy.lessThenOrEquals(" tmp."+TmpIndustryArticleRM.pubTime, endDate);
		} 
		if(type!=null && type==1){  //我的
			whereBy.in(" tmp."+TmpIndustryArticleRM.brandId, carbrand_id, CondtionSeparator.AND);

		}
		if(type!=null && type==2){  //竞品
			whereBy.notIn(" tmp."+TmpIndustryArticleRM.brandId, carbrand_id, CondtionSeparator.AND);
		}
		long counts = tmpIndustryArticleDao.findTmpIACountsByCriteria(whereBy);
		return counts;
		 
	}
}
