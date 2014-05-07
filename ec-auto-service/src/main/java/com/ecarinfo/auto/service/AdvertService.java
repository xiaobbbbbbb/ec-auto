package com.ecarinfo.auto.service;

import java.util.List;

import com.ecarinfo.auto.vo.AdvertingDetailVO;
import com.ecarinfo.auto.vo.AdvertingVO;
import com.ecarinfo.persist.paging.ECPage;

/**
 * 广宣配置
 */
public interface AdvertService {
	
	/**
	 * 广宣活动列表
	 */
	public ECPage<AdvertingVO> getAdvertList(int pageNo,int pageSize);
	
	/**
	 *根据广宣活动大类查询广宣配置详情
	 */
	public List<AdvertingDetailVO> getAdvertingDetailByPid(Integer pid);
}
