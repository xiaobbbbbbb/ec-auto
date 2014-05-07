package com.ecarinfo.auto.service;

import com.ecarinfo.auto.vo.CarSerialVO;
import com.ecarinfo.auto.vo.CarbrandVO;
import com.ecarinfo.persist.paging.ECPage;


/**
 * 后台管理
 * @author yinql
 *
 */
public interface BackendService {
	/**
	 * 厂牌查询
	 * @param name
	 * @param type
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public ECPage<CarbrandVO> getCarbrandList(String name,Integer type);
	 
	
	/**
	 * 车系查询
	 * @param name
	 * @param type
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public ECPage<CarSerialVO> getCarSerialList(Integer brandId,String name,Integer type);
}