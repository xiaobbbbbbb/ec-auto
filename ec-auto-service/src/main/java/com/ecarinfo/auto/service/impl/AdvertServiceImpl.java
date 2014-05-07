package com.ecarinfo.auto.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ecarinfo.auto.service.AdvertService;
import com.ecarinfo.auto.vo.AdvertingDetailVO;
import com.ecarinfo.auto.vo.AdvertingVO;
import com.ecarinfo.auto.vo.ChartCoreDataVO;
import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.Paginable;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.persist.util.RowMapperUtils;

@Service
public class AdvertServiceImpl implements AdvertService {
	private static final Logger logger = LoggerFactory.getLogger(AdvertServiceImpl.class);
	@Resource
	private GenericDao genericDao;
	@Override
	public ECPage<AdvertingVO> getAdvertList(int pageNo,int pageSize) {
		
		try{
			final StringBuffer sql = new StringBuffer();
			sql .append( "select id,content,ctime from advertising where status=1 order by ctime desc ");
			
			ECPage<AdvertingVO> page = PagingManager
					.list(new Paginable<AdvertingVO>(pageNo,
							pageSize) {
						@Override
						public List<AdvertingVO> findList() {
							sql.append(String.format(" limit %d,%d", this.getOffset(),this.getRowsPerPage()));
							System.err.println("sql:"+sql.toString());
							List<Map<String, Object>> list = genericDao.findListBySql(sql.toString());
							List<AdvertingVO> vos = RowMapperUtils.map2List(list, AdvertingVO.class);
							return vos;
						}
						@Override
						public Long count() {
							Map<String,Object> data = genericDao.findOneBySql("select count(id) nums from advertising where status=1");
							return (Long) data.get("nums");
						}
					});
			return page;
		}catch(Exception e){
			logger.error("query error:",e);
		}
		return null;
	}
	@Override
	public List<AdvertingDetailVO> getAdvertingDetailByPid(Integer pid) {
		StringBuffer sql = new StringBuffer();
		sql.append("select ad.*,m.`name` media_name from advertising_detail ad LEFT JOIN media_info  m on ad.media_id = m.id where ad.status =1  ");
		sql.append(String.format(" and ad.pid=%d",pid));
		sql.append(" order by ctime desc");
		logger.debug("[getQuestionChartList]sql:"+sql.toString());
		
		List<Map<String,Object>> tmp_list = genericDao.findListBySql(sql.toString());
		List<AdvertingDetailVO> list = RowMapperUtils.map2List(tmp_list,AdvertingDetailVO.class);
		
		return list;
	}

}
