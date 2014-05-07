package com.ecarinfo.auto.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ecarinfo.auto.po.NetworkMediaAnalysis;
import com.ecarinfo.auto.service.MediaAnalyzeService;
import com.ecarinfo.auto.vo.ArticleSimpleListVO;
import com.ecarinfo.auto.vo.WeiboAnalysisVO;
import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.Paginable;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.persist.util.RowMapperUtils;
@Service
public class MediaAnalyzeServiceImpl implements MediaAnalyzeService {
	private static final Logger logger = LoggerFactory.getLogger(MediaAnalyzeServiceImpl.class);
	@Resource
	GenericDao genericDao;

	@Override
	public List<NetworkMediaAnalysis> getNetworkMediaTop10() {
		String sql = "select  * from network_media_analysis order by `visit_count` desc limit 10" ;
		logger.debug("[getNetworkMediaTop10]sql:"+sql);
		List<Map<String,Object>> tmp_list = genericDao.findListBySql(sql.toString());
		List<NetworkMediaAnalysis> list = RowMapperUtils.map2List(tmp_list,NetworkMediaAnalysis.class);
		return list;
	}

	@Override
	public List<WeiboAnalysisVO> getWeiboAnalysisTop10() {
		String sql = "select w.id,w.name ,w.url,w.media_id,w.fans_count,w.about_article_count," +
					"w.avg_transmit_count,w.avg_comment_count ,w.province_id ,w.city_id ,w.ctime, " +
					"dc.name cityName,dp.name provinceName ,m.name mediaName from weibo_analysis w "+
					"LEFT JOIN   media_info m on m.id = w.media_id "+
					"LEFT JOIN   dict_province dp on dp.id = w.province_id "+
					"LEFT JOIN   dict_city dc on dc.id = w.city_id "+
					"order by `about_article_count` desc limit 10" ;
		System.err.println("--sql:"+sql);
		logger.debug("[getWeiboAnalysisTop10]sql:"+sql);
		List<Map<String,Object>> tmp_list = genericDao.findListBySql(sql.toString());
		List<WeiboAnalysisVO> list = RowMapperUtils.map2List(tmp_list,WeiboAnalysisVO.class);
		return list;
	}

	@Override
	public ECPage<WeiboAnalysisVO> getWeibolist(int pageNo, int pageSize) {
		try {			
			final StringBuffer sql = new StringBuffer();
			sql.append("select w.id,w.name ,w.url,w.media_id,w.fans_count,w.about_article_count," +
					"w.avg_transmit_count,w.avg_comment_count ,w.province_id ,w.city_id ,w.ctime, " +
					"dc.name cityName,dp.name provinceName ,m.name mediaName from weibo_analysis w "+
					"LEFT JOIN   media_info m on m.id = w.media_id "+
					"LEFT JOIN   dict_province dp on dp.id = w.province_id "+
					"LEFT JOIN   dict_city dc on dc.id = w.city_id "+
					"order by `about_article_count` desc " );
			final StringBuffer sqlCount = new StringBuffer();
			sqlCount.append("select count(id) nums from weibo_analysis " );
			ECPage<WeiboAnalysisVO> page = PagingManager
					.list(new Paginable<WeiboAnalysisVO>(pageNo,
							pageSize) {
						@Override
						public List<WeiboAnalysisVO> findList() {
							sql.append(String.format(" limit %d,%d", this.getOffset(),this.getRowsPerPage()));
							
							System.err.println("sql:"+sql.toString());
							
							List<Map<String, Object>> list = genericDao.findListBySql(sql.toString());
							List<WeiboAnalysisVO> vos = RowMapperUtils.map2List(list, WeiboAnalysisVO.class);
							
							return vos;
						}
						@Override
						public Long count() {
							System.err.println("sqlCount:"+sqlCount.toString());
							Map<String,Object> data = genericDao.findOneBySql(sqlCount.toString());
							return (Long) data.get("nums");
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
	public ECPage<NetworkMediaAnalysis> getMedialist(int pageNo, int pageSize) {
		try {			
			final StringBuffer sql = new StringBuffer();
			sql.append("select  * from network_media_analysis order by `visit_count` desc " );
			final StringBuffer sqlCount = new StringBuffer();
			sqlCount.append("select count(id) nums from network_media_analysis " );
			ECPage<NetworkMediaAnalysis> page = PagingManager
					.list(new Paginable<NetworkMediaAnalysis>(pageNo,
							pageSize) {
						@Override
						public List<NetworkMediaAnalysis> findList() {
							sql.append(String.format(" limit %d,%d", this.getOffset(),this.getRowsPerPage()));
							
							System.err.println("sql:"+sql.toString());
							
							List<Map<String, Object>> list = genericDao.findListBySql(sql.toString());
							List<NetworkMediaAnalysis> vos = RowMapperUtils.map2List(list, NetworkMediaAnalysis.class);
							
							return vos;
						}
						@Override
						public Long count() {
							System.err.println("sqlCount:"+sqlCount.toString());
							Map<String,Object> data = genericDao.findOneBySql(sqlCount.toString());
							return (Long) data.get("nums");
						}
					});
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("query error "+e.getMessage());
		}
		return null;
	}

}
