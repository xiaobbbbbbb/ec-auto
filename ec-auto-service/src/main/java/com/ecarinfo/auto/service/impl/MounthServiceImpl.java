package com.ecarinfo.auto.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.ecarinfo.auto.service.MouthService;
import com.ecarinfo.auto.vo.ArticleMouthVO;
import com.ecarinfo.auto.vo.ChartCoreDataVO;
import com.ecarinfo.auto.vo.MouthData;
import com.ecarinfo.auto.vo.MouthSerialName;
import com.ecarinfo.persist.exdao.GenericDao;
import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.paging.Paginable;
import com.ecarinfo.persist.paging.PagingManager;
import com.ecarinfo.persist.util.RowMapperUtils;
@Service
public class MounthServiceImpl implements MouthService {
	private static final Logger logger = Logger.getLogger(MounthServiceImpl.class);
	
	@Resource
	GenericDao genericDao;

	@Override
	public List<ChartCoreDataVO> getViewpointGroup(Integer serialId,
			Integer affection) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(a.id) value,t.name from article a LEFT JOIN article_viewpoint av ON a.id = av.article_id "+
				" LEFT JOIN viewpoint v ON ( v.id= av.viewpoint_id and v.is_valid=1 )"+
				" LEFT JOIN viewpoint_type t on v.viewpoint_type_id=t.id"+
				" where a.`status`=1  and a.has_viewpoint =1 " );
		if(serialId!=null&&serialId>0){
			sql.append(	String.format(" and a.serial_id=%s ",serialId));
		}
		sql.append(String.format(" and v.affection=%s",affection));
			sql.append(" GROUP BY t.id ORDER BY value desc LIMIT 10 ");
			
		logger.debug("[getViewpointGroup]sql:"+sql.toString());
		
		List<Map<String,Object>> tmp_list = genericDao.findListBySql(sql.toString());
		List<ChartCoreDataVO> list = RowMapperUtils.map2List(tmp_list,ChartCoreDataVO.class);
		
		return list;
	}

	@Override
	public List<ChartCoreDataVO> getViewpointCounts(Integer serialId ) {

		StringBuffer sql = new StringBuffer();
		sql.append(	"select affection name,count(id) value  from mouth_analyzing ");
		if(serialId!=null&&serialId>0){
				sql.append(	String.format(" where serial_id=%s ",serialId));
		}
		sql.append("GROUP BY affection");
	
		logger.debug("[getViewpointCountsByAffection]sql:"+sql.toString());
		List<Map<String,Object>> tmp_list = genericDao.findListBySql(sql.toString());
		List<ChartCoreDataVO> list = RowMapperUtils.map2List(tmp_list,ChartCoreDataVO.class);
		return list;
	}

	@Override
	public ECPage<ArticleMouthVO> queryViewpointArticleList(Integer[] serialId,
			int viewpiontTypeId, int affection, int provinceId,int cityId,String startDate,
			String endDate,  int pageNo, int pageSize) {
		try {			
			final StringBuffer sql = new StringBuffer();
			
			sql.append("select a.id,a.article_date,a.title,a.url,v.affection,t.`name` as viewpoint_name,s.`name` serial_name,c.`name` as city_name from article_viewpoint av"+
					" LEFT JOIN article a on a.id =av.article_id "+
					" LEFT JOIN viewpoint v on (v.id =av.viewpoint_id and v.is_valid=1)"+
					" LEFT JOIN viewpoint_type t on v.viewpoint_type_id = t.id"+
					" LEFT JOIN dict_car_serial s on a.serial_id= s.id"+
					" LEFT JOIN dict_city c on a.city_id = c.id"+
					" where a.`status`=1 AND a.has_viewpoint>0 ");
			
			final StringBuffer sqlCount = new StringBuffer();
			sqlCount.append("select count( a.id) as num  from article_viewpoint av  "+
					" LEFT JOIN article a  on a.id =av.article_id"+
					" LEFT JOIN viewpoint v on (v.id =av.viewpoint_id and v.is_valid=1)"+
					" LEFT JOIN viewpoint_type t on v.viewpoint_type_id = t.id"+
					" LEFT JOIN dict_car_serial s on a.serial_id= s.id"+
					" LEFT JOIN dict_city c on a.city_id = c.id"+
					" where a.`status`=1 AND a.has_viewpoint>0 ");
			
			final StringBuffer conStr = new StringBuffer();
			StringBuffer ids = new StringBuffer();
			int i=0;
			for (Integer serial : serialId) {
				ids.append(serial);
				i++;
				if(i<serialId.length){
					ids.append(",");
				}
			}
			if(serialId!=null && serialId.length>0){			
				conStr.append(String.format("  and a.serial_id in (%s)",ids.toString()));
			}
			if (viewpiontTypeId > 0) {
				conStr.append(String.format(" and v.viewpoint_type_id='%d'",viewpiontTypeId));				
			}
			if (provinceId > 0) {
				conStr.append(String.format(" and a.province_id='%d'", provinceId));
			}	
			if (cityId > 0) {
				conStr.append(String.format(" and a.city_id='%d'", cityId));
			}			
			if (affection > 0) {
				conStr.append(String.format(" and v.affection='%d'", affection));
			}			
			if (startDate != null && startDate.length()==10 && endDate != null && startDate.length()==10) {
				conStr.append(String.format(" and a.article_date>='%s'",startDate));
				conStr.append(String.format(" and a.article_date<='%s'",endDate));
			} else if (startDate != null && startDate.length()==10) {
				conStr.append(String.format(" and a.article_date='%s'",startDate));
			} else if (endDate != null && startDate.length()==10) {
				conStr.append(String.format(" and a.article_date='%s'",endDate));
			}						
			ECPage<ArticleMouthVO> page = PagingManager
					.list(new Paginable<ArticleMouthVO>(pageNo,
							pageSize) {
						@Override
						public List<ArticleMouthVO> findList() {
							sql.append(conStr.append(" order By a.article_date desc "));							
							sql.append(String.format(" limit %d,%d", this.getOffset(),this.getRowsPerPage()));
							
							System.err.println("sql:"+sql.toString());
							
							List<Map<String, Object>> list = genericDao.findListBySql(sql.toString());
							List<ArticleMouthVO> vos = RowMapperUtils.map2List(list, ArticleMouthVO.class);
							
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
			logger.error("queryAegativeArticles error "+e.getMessage());
		}
		return null;
	}

	@Override
	public long getViewpointNums(Integer serialId, String viewpoint,
			Integer affection) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(id) num from mouth_analyzing where "+viewpoint+" = '1' ");
		if(serialId>0){
			sql.append(String.format(" and serial_id = %d ",serialId));
		}if(affection>0){
			sql.append(String.format(" and "+viewpoint+"_affection = %d ",affection));
		}
		Map<String,Object> data = genericDao.findOneBySql(sql.toString());
		return (Long) data.get("num");
	}
	
	@Override
	public long getViewpointNums(Integer serialId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(id) num from mouth_analyzing where 1 = '1' ");
		if(serialId>0){
			sql.append(String.format(" and serial_id = %d ",serialId));
		}
		Map<String,Object> data = genericDao.findOneBySql(sql.toString());
		return (Long) data.get("num");
	}

	@Override
	public ECPage<ArticleMouthVO> queryViewpointArticleList(Integer[] serialId,
			String viewpiontTypeId, int affection, int provinceId, int cityId,
			String startDate, String endDate, int pageNo, int pageSize) {
		try {			
			final StringBuffer sql = new StringBuffer();
			if(StringUtils.isEmpty(viewpiontTypeId)){
				viewpiontTypeId = "affection";
			}
			sql.append("select a.id,a.pub_time articleDate,a.type,a.content ,a.title,a.url,a.");
			if (viewpiontTypeId.equals("affection")) {
				sql.append(viewpiontTypeId);
			}else{
				sql.append(viewpiontTypeId+"_affection affection ");
			}
			sql.append(",s.`name` serial_name,c.`name` as city_name "+
					" from mouth_analyzing a  "+
					" LEFT JOIN dict_car_serial s on a.serial_id= s.id"+
					" LEFT JOIN dict_city c on a.city_id = c.id");
			if(!viewpiontTypeId.equals("affection")){
				sql.append(	" where  a."+viewpiontTypeId+"=1 ");
			}else{
				sql.append(" where 1= 1 ");
			}
			
			final StringBuffer sqlCount = new StringBuffer();
			sqlCount.append("select count(a.id) num "+
					" from mouth_analyzing a  "+
					" LEFT JOIN dict_car_serial s on a.serial_id= s.id"+
					" LEFT JOIN dict_city c on a.city_id = c.id");
			if(!viewpiontTypeId.equals("affection")){
				sqlCount.append(" where  a."+viewpiontTypeId+"=1 ");
			}else{
				sqlCount.append(" where 1= 1 ");
			}
			
			final StringBuffer conStr = new StringBuffer();
			StringBuffer ids = new StringBuffer();
			int i=0;
			for (Integer serial : serialId) {
				ids.append(serial);
				i++;
				if(i<serialId.length){
					ids.append(",");
				}
			}
			if(serialId!=null && serialId.length>0){			
				conStr.append(String.format("  and a.serial_id in (%s)",ids.toString()));
			}
			if (provinceId > 0) {
				conStr.append(String.format(" and a.province_id='%d'", provinceId));
			}	
			if (cityId > 0) {
				conStr.append(String.format(" and a.city_id='%d'", cityId));
			}			
			if (affection > 0) {
				if(viewpiontTypeId.equals("affection")){
					conStr.append(String.format(" and a.affection='%d'", affection));
				}else{
					conStr.append(String.format(" and a."+viewpiontTypeId+"_affection='%d'", affection));
				}
			}			
			if (startDate != null && startDate.length()==10 && endDate != null && startDate.length()==10) {
				conStr.append(String.format(" and a.pub_time>='%s'",startDate+" 00:00:00"));
				conStr.append(String.format(" and a.pub_time<='%s'",endDate+" 23:59:59"));
			} else if (startDate != null && startDate.length()==10) {
				conStr.append(String.format(" and a.pub_time='%s'",startDate+" 00:00:00"));
			} else if (endDate != null && startDate.length()==10) {
				conStr.append(String.format(" and a.pub_time='%s'",endDate+" 23:59:59"));
			}						
			System.err.println(sql.toString());
			ECPage<ArticleMouthVO> page = PagingManager
					.list(new Paginable<ArticleMouthVO>(pageNo,
							pageSize) {
						@Override
						public List<ArticleMouthVO> findList() {
							sql.append(conStr.append(" order By a.pub_time desc "));							
							sql.append(String.format(" limit %d,%d", this.getOffset(),this.getRowsPerPage()));
							
							System.err.println("sql:"+sql.toString());
							
							List<Map<String, Object>> list = genericDao.findListBySql(sql.toString());
							List<ArticleMouthVO> vos = RowMapperUtils.map2List(list, ArticleMouthVO.class);
							
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
			logger.error("queryAegativeArticles error "+e.getMessage());
		}
		return null;
	}

	@Override
	public String getMouthKeywords(Integer serialId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT GROUP_CONCAT(keyword ORDER BY nums desc ) keywords from mouth_keyword  ");
		if(serialId>0){
			sql.append(String.format(" where serial_id = %d ",serialId));
		}
		sql.append("GROUP BY serial_id");
		Map<String,Object> data = genericDao.findOneBySql(sql.toString());
		return  data!=null?(String) data.get("keywords"):"";
	}

	@Override
	public MouthData mouthDate(Integer serialId,Integer affection) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT  serial_id, "+
			" sum(case appearance_affection when "+affection+"  then 1 else 0 end ) as appearance_gcounts,  "+
//			" sum(case appearance_affection when 2 then 1 else 0 end ) as appearance_mcount,  "+
//			" sum(case appearance_affection when 3 then 1 else 0 end ) as appearance_bcount,"+
			
			" sum(case attitude_affection when "+affection+"  then 1 else 0 end ) as attitude_gcounts,  "+
//			"sum(case attitude_affection when 2 then 1 else 0 end ) as attitude_mcount,  "+
//			"sum(case attitude_affection when 3 then 1 else 0 end ) as attitude_bcount,"+
			
			"sum(case interior_affection when "+affection+"  then 1 else 0 end ) as interior_gcounts,  "+
//			"sum(case interior_affection when 2 then 1 else 0 end ) as interior_mcount,  "+
//			"sum(case interior_affection when 3 then 1 else 0 end ) as interior_bcount,"+
			
			"sum(case space_affection when "+affection+"  then 1 else 0 end ) as space_gcounts,  "+
//			"sum(case space_affection when 2 then 1 else 0 end ) as space_mcount,  "+
//			"sum(case space_affection when 3 then 1 else 0 end ) as space_bcount,"+
			
			"sum(case power_affection when "+affection+"  then 1 else 0 end ) as power_gcounts,  "+
//			"sum(case power_affection when "+affection+"  then 1 else 0 end ) as power_mcount,  "+
//			"sum(case power_affection when 3 then 1 else 0 end ) as power_bount,"+
			
			"sum(case operate_affection when "+affection+"  then 1 else 0 end ) as operate_gcounts,  "+
//			"sum(case operate_affection when 2 then 1 else 0 end ) as operate_mcount,  "+
//			"sum(case operate_affection when 3 then 1 else 0 end ) as operate_bount,"+
			
			"sum(case power_affection when "+affection+"  then 1 else 0 end ) as power_gcounts,  "+
//			"sum(case power_affection when 2 then 1 else 0 end ) as power_mcount,  "+
//			"sum(case power_affection when 3 then 1 else 0 end ) as power_bount,"+
			
			"sum(case oil_affection when "+affection+"  then 1 else 0 end ) as oil_gcounts,  "+
//			"sum(case oil_affection when 2 then 1 else 0 end ) as oil_mcount,  "+
//			"sum(case oil_affection when 3 then 1 else 0 end ) as oil_bount,"+
			
			"sum(case comfort_affection when "+affection+"  then 1 else 0 end ) as comfort_gcounts,  "+
//			"sum(case comfort_affection when 2 then 1 else 0 end ) as comfort_mcount,  "+
//			"sum(case comfort_affection when 3 then 1 else 0 end ) as comfort_bount,"+
			
			"sum(case configure_affection when "+affection+"  then 1 else 0 end ) as configure_gcounts,  "+
//			"sum(case configure_affection when 2 then 1 else 0 end ) as configure_mcount,  "+
//			"sum(case configure_affection when 3 then 1 else 0 end ) as configure_bount,"+
			
			"sum(case price_affection when "+affection+"  then 1 else 0 end ) as price_gcounts,  "+
//			"sum(case price_affection when 2 then 1 else 0 end ) as price_mcount,  "+
//			"sum(case price_affection when 3 then 1 else 0 end ) as price_bount,"+
			
			"sum(case quality_affection when "+affection+"  then 1 else 0 end ) as quality_gcounts,  "+
//			"sum(case quality_affection when 2 then 1 else 0 end ) as quality_mcount,  "+
//			"sum(case quality_affection when 3 then 1 else 0 end ) as quality_bount,"+
			
			"sum(case cost_affection when "+affection+"  then 1 else 0 end ) as cost_gcounts,  "+
//			"sum(case cost_affection when 2 then 1 else 0 end ) as cost_mcount,  "+
//			"sum(case cost_affection when 3 then 1 else 0 end ) as cost_bount,"+
			
			"sum(case maintenance_affection when "+affection+"  then 1 else 0 end ) as maintenance_gcounts,  "+
//			"sum(case maintenance_affection when 2 then 1 else 0 end ) as maintenance_mcount,  "+
//			"sum(case maintenance_affection when 3 then 1 else 0 end ) as maintenance_bount,"+
			
			"sum(case facility_affection when "+affection+"  then 1 else 0 end ) as facility_gcounts, "+
//			"sum(case facility_affection when 2 then 1 else 0 end ) as facility_mcount, "+ 
//			"sum(case facility_affection when 3 then 1 else 0 end ) as facility_bount,"+
			
			"sum(case stafflevel_affection when "+affection+"  then 1 else 0 end ) as stafflevel_gcounts "+
//			"sum(case stafflevel_affection when 2 then 1 else 0 end ) as stafflevel_mcount, "+ 
//			"sum(case stafflevel_affection when 3 then 1 else 0 end ) as stafflevel_bount "+
			" FROM mouth_analyzing "+
			
			" WHERE serial_id = "+serialId+" GROUP BY serial_id");
		System.err.println("sql:=="+sql.toString());
		MouthData data =  this.genericDao.findOneBySql(MouthData.class, sql.toString());
		return data;
	}
	

	@Override
	public List<MouthSerialName> getMouthKeywords() {
		String sql = "SELECT mk.serial_id as id,cs.`name` as serial_name FROM mouth_keyword AS mk, dict_car_serial AS cs WHERE "
				+ "mk.serial_id = cs.id GROUP BY serial_id ORDER BY mk.serial_id";
		List<Map<String, Object>> maps=genericDao.findListBySql(sql);
		List<MouthSerialName> dtos=RowMapperUtils.map2List(maps, MouthSerialName.class);
		return dtos;
	}

}
