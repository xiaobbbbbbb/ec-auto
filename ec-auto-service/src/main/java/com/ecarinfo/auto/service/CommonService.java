/**
 * 
 */
package com.ecarinfo.auto.service;


import java.util.List;

import com.ecarinfo.auto.po.BaiduHotRank;
import com.ecarinfo.auto.po.DictArea;
import com.ecarinfo.auto.po.DictCarBrand;
import com.ecarinfo.auto.po.DictCarSerial;
import com.ecarinfo.auto.po.DictCity;
import com.ecarinfo.auto.po.DictMediaType;
import com.ecarinfo.auto.po.DictProvince;
import com.ecarinfo.auto.po.MediaInfo;
import com.ecarinfo.auto.po.Viewpoint;


/**
 * @author zb
 *
 */
public interface CommonService {
	
	
	//获取品牌列表
	List<DictCarBrand> getAllDictCarBrand();
	
	//获取品牌下的车系
	List<DictCarSerial> getAllDictCarSerialByCarBrandId(Integer brandId);
	
	//获取区域列表
	List<DictArea> getDictArea();
	
	//获取省份列表
	List<DictProvince> getDictProvince();
	
	//通过区域获取身份列表
	List<DictProvince> getDictProvinceByAreaId(Integer areaId);
	
	//获取城市列表 
	List<DictCity> getDictCityByProvinceId(Integer provinceId);

	//获取观点类别
	List<Viewpoint> getViewpointList();
	
	//获取媒体类型
	List<DictMediaType> getDictMediaType();
	
	//获取媒体来源
	List<MediaInfo> getMediaInfo();
	
	//获取媒体来源by mediaTypeId
	List<MediaInfo> getMediaInfoByMediaTypeId(Integer mediaTypeId);
	
	//百度排行榜 
	List<BaiduHotRank> getBaiduHotRank();
	
}
