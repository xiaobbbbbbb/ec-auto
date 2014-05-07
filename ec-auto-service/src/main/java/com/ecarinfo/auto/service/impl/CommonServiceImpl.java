package com.ecarinfo.auto.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ecarinfo.auto.po.BaiduHotRank;
import com.ecarinfo.auto.po.DictArea;
import com.ecarinfo.auto.po.DictCarBrand;
import com.ecarinfo.auto.po.DictCarSerial;
import com.ecarinfo.auto.po.DictCity;
import com.ecarinfo.auto.po.DictMediaType;
import com.ecarinfo.auto.po.DictProvince;
import com.ecarinfo.auto.po.MediaInfo;
import com.ecarinfo.auto.po.Viewpoint;
import com.ecarinfo.auto.rm.DictCarBrandRM;
import com.ecarinfo.auto.rm.DictCarSerialRM;
import com.ecarinfo.auto.rm.DictCityRM;
import com.ecarinfo.auto.rm.DictMediaTypeRM;
import com.ecarinfo.auto.rm.DictProvinceRM;
import com.ecarinfo.auto.rm.MediaInfoRM;
import com.ecarinfo.auto.rm.ViewpointRM;
import com.ecarinfo.auto.service.CommonService;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.exdao.GenericDao;

@Service
public class CommonServiceImpl implements CommonService {

	@Resource
	private GenericDao genericDao;

	@Override
	public List<DictCarBrand> getAllDictCarBrand() {
		return this.genericDao.findList(DictCarBrand.class,new Criteria().eq(DictCarBrandRM.isValid, 1).orderBy(DictCarBrandRM.orderNo,OrderType.DESC));
	}

	@Override
	public List<DictArea> getDictArea() {
		return this.genericDao.findAll(DictArea.class);
	}

	@Override
	public List<DictProvince> getDictProvince() {
		return this.genericDao.findList(DictProvince.class,new Criteria().eq(DictProvinceRM.isValid, 1));
	}

	@Override
	public List<Viewpoint> getViewpointList() {
		return this.genericDao.findList(Viewpoint.class,new Criteria().eq(ViewpointRM.isValid,1));
	}

	@Override
	public List<DictMediaType> getDictMediaType() {
		return this.genericDao.findList(DictMediaType.class,new Criteria().eq(DictMediaTypeRM.isValid,1));
	}

	@Override
	public List<MediaInfo> getMediaInfo() {
		return this.genericDao.findList(MediaInfo.class,new Criteria().eq(MediaInfoRM.isValid,1));
	}

	@Override
	public List<BaiduHotRank> getBaiduHotRank() {
		return this.genericDao.findAll(BaiduHotRank.class);
	}

	@Override
	public List<DictCarSerial> getAllDictCarSerialByCarBrandId(Integer brandId) {
	
		return this.genericDao.findList(DictCarSerial.class, new Criteria().eq(DictCarSerialRM.brandId, brandId)
				.eq(DictCarSerialRM.isValid, 1, CondtionSeparator.AND).orderBy(DictCarSerialRM.orderNo, OrderType.DESC)
				);
	}

	@Override
	public List<DictProvince> getDictProvinceByAreaId(Integer areaId) {
		return this.genericDao.findList(DictProvince.class, new Criteria().eq(DictProvinceRM.areaId, areaId)
				.eq(DictProvinceRM.isValid,1,CondtionSeparator.AND));
	}

	@Override
	public List<DictCity> getDictCityByProvinceId(Integer provinceId) {
		return this.genericDao.findList(DictCity.class, new Criteria().eq(DictCityRM.proId, provinceId)
				.eq(DictCityRM.status, 1, CondtionSeparator.AND));
	}

	@Override
	public List<MediaInfo> getMediaInfoByMediaTypeId(Integer mediaTypeId) {
		
		return this.genericDao.findList(MediaInfo.class, new Criteria().eq(MediaInfoRM.mediaTypeId, mediaTypeId)
				.eq(MediaInfoRM.isValid, 1, CondtionSeparator.AND));
	}

}
