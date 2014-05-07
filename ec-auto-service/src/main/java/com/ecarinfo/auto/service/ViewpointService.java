package com.ecarinfo.auto.service;

import java.util.Date;
import java.util.List;

import com.ecarinfo.auto.vo.ViewpointCountVO;
import com.ecarinfo.auto.vo.ViewpointVO;
import com.ecarinfo.persist.paging.ECPage;

/**
 * 观点表
 * @author laochen
 *
 */
public interface ViewpointService {
		
	
	/**
	 * 模块：品牌分析->品牌口碑
	 * 根据厂牌ID查询一段时间内容某个区域的观点数据清单
	 * @param carBrandId
	 * @param affection
	 * @param areaId
	 * @param provinceId
	 * @param cityId
	 * @param startDate
	 * @param endDate
	 * @param limit
	 * @return
	 */
	public List<ViewpointVO> getViewpointsByCarBrandId(int carBrandId,int affection,int areaId,int provinceId,int cityId,Date startDate,Date endDate,int pageSize);
	
	
	/**
	 * 模块：品牌分析->产品口碑
	 * 根据车系ID查询一段时间内容某个区域的观点数据清单
	 * @param carSerialId
	 * @param affection
	 * @param areaId
	 * @param provinceId
	 * @param cityId
	 * @param startDate
	 * @param endDate
	 * @param limit
	 * @return
	 */
	public List<ViewpointVO> getViewpointsBySerialId(Integer[] carSerialId,int affection,int areaId,int provinceId,int cityId,Date startDate,Date endDate,int pageSize);
	
	
	
	/**
	 * 模块： 网友评论列表
	 * 获取所有观点内容
	 * @param startDate
	 * @param endDate
	 * @param affection 情感
	 * @param areaId
	 * @param provinceId
	 * @param cityId
	 * @param offset
	 * @param limit
	 * @return
	 */
	public ECPage<ViewpointVO> getViewpoints(int carBrandId,int carSerialId,  Date startDate,Date endDate,int affection,int areaId,int provinceId,int cityId,int pageNo,int pageSize);
	
	/**
	 * 模块产品评价：区域观点分布
	 */
	
	public List<ViewpointVO> getViewpointsByProvince(int carSerialId,int viewpointId,Date startDate,Date endDate);
	
	
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
	 * @return List<Map<String,Integer>>
	 */
	public List<ViewpointCountVO> getViewpointsStatisticsByCarSerial(int carSerialId,int affection,int areaId,int provinceId,int cityId,String startDate,String endDate,int pageSize);
}
