package com.ecarinfo.auto.weixin.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecarinfo.auto.po.DictArea;
import com.ecarinfo.auto.po.DictCarBrand;
import com.ecarinfo.auto.po.DictCarSerial;
import com.ecarinfo.auto.po.DictCity;
import com.ecarinfo.auto.po.DictProvince;
import com.ecarinfo.auto.po.MediaInfo;
import com.ecarinfo.auto.weixin.util.EntityUtils;
import com.ecarinfo.persist.service.GenericService;

/**
 * 对少量常用的数据缓存类
 * @author zhanglu
 *
 */
@Component("commonDataCache")
public class CommonDataCache {
	static Map<String,RWLockWraper> lockMap = new HashMap<String, RWLockWraper>();
	private static Map<Integer, String> mediaMap = null;
	private static Map<Integer, DictCarSerial>  serialMap = null;
	private static Map<Integer, DictCarBrand>  brandMap = null;
	private static Map<Integer, DictArea>  areaMap = null;
	private static Map<Integer, DictProvince>  provinceMap = null;
	private static Map<Integer, DictCity>  cityMap = null;
	private static Map<Integer, List<DictCarSerial>>  brandId2SerialsMap = null;
	private static Map<Integer, List<DictProvince>>  areaId2ProvincesMap = null;
	private static Map<Integer, List<DictCity>>  provinceId2CitysMap = null;
	
	private static Map<Class<? extends Serializable>, Map<Class<?>, Class<? extends Serializable>>> field2EntityMaps = new HashMap<Class<? extends Serializable>, Map<Class<?>, Class<? extends Serializable>>>();
	private static Map<Class<? extends Serializable>, Map<Class<?>, List<Class<? extends Serializable>>>> grouppedEntitysMaps = new HashMap<Class<? extends Serializable>, Map<Class<?>, List<Class<? extends Serializable>>>>();
	
	private static List<DictCarBrand> brands = null;
	private static List<DictArea> areas = null;
	
	@Resource
	protected GenericService baseService;
	
	public GenericService getBaseService() {
		return baseService;
	}

	public void setBaseService(GenericService baseService) {
		this.baseService = baseService;
	}	
	
	public <T1, T2 extends Serializable> Map<T1, T2> getField2EntityMap(Class<T1> fieldClazz, String fieldname, Class<T2> entityClazz) {
		Map<T1, T2> map = (Map<T1, T2>) field2EntityMaps.get(entityClazz);
		if (map==null) {
			synchronized (this) {				
				List<T2> medias = baseService.findAll(entityClazz);
				map = EntityUtils.getField2EntityMap(medias, fieldname, fieldClazz);
				field2EntityMaps.put(entityClazz, (Map<Class<?>, Class<? extends Serializable>>) map);
			}
		}
		return map;
	}
	
	public <T1, T2 extends Serializable> Map<T1, List<T2>> getEntitysGrouppedByField(Class<T1> fieldClazz, String fieldname, Class<T2> entityClazz) {
		return null;		
	}
	
	public  Map<Integer, String> getMediaMap() {
		if (mediaMap==null) {
			synchronized (this) {
				if (mediaMap==null) {
					mediaMap = new HashMap<Integer, String>();
				}
				List<MediaInfo> medias = baseService.findAll(MediaInfo.class);
				for (MediaInfo mediaInfo : medias) {
					mediaMap.put(mediaInfo.getId(), mediaInfo.getName());
				}
			}
		}
		return mediaMap;
	}
	
	public void reloadCarBrandOrSerialsCaches() {
		synchronized (this) { 
			brands = null;
			brandMap = null;
			serialMap = null;
			brandId2SerialsMap = null;
		}	
	}
	
	public  List<DictCarBrand> getBrands() {
		if (brands==null) {
			synchronized (this) {
				if (brands==null) {
					brands = new ArrayList<DictCarBrand>();
				}
				brands = baseService.findAll(DictCarBrand.class);
			}
		}
		return brands;
	}
	
	public  List<DictArea> getAreas() {
		if (areas==null) {
			synchronized (this) {
				if (areas==null) {
					areas = new ArrayList<DictArea>();
				}
				areas = baseService.findAll(DictArea.class);
			}
		}
		return areas;
	}
	
	public  Map<Integer, DictCarSerial> getSerialMap() {
		if (serialMap==null) {
			synchronized (this) {
				if (serialMap==null) {
					serialMap = new HashMap<Integer, DictCarSerial>();
				}
				List<DictCarSerial> medias = baseService.findAll(DictCarSerial.class);
				for (DictCarSerial mediaInfo : medias) {
					serialMap.put(mediaInfo.getId(), mediaInfo);
				}
			}
		}
		return serialMap;
	}
	
	public  Map<Integer, DictCarBrand> getBrandMap() {
		if (brandMap==null) {
			synchronized (this) {
				if (brandMap==null) {
					brandMap = new HashMap<Integer, DictCarBrand>();
				}
				List<DictCarBrand> medias = baseService.findAll(DictCarBrand.class);
				for (DictCarBrand mediaInfo : medias) {
					brandMap.put(mediaInfo.getId(), mediaInfo);
				}
			}
		}
		return brandMap;
	}
	
	public  Map<Integer, DictArea> getAreaMap() {
		if (areaMap==null) {
			synchronized (this) {
				if (areaMap==null) {
					areaMap = new HashMap<Integer, DictArea>();
				}
				List<DictArea> medias = baseService.findAll(DictArea.class);
				for (DictArea mediaInfo : medias) {
					areaMap.put(mediaInfo.getId(), mediaInfo);
				}
			}
		}
		return areaMap;
	}
	
	public  Map<Integer, DictProvince> getProvinceMap() {
		if (provinceMap==null) {
			synchronized (this) {
				if (provinceMap==null) {
					provinceMap = new HashMap<Integer, DictProvince>();
				}
				List<DictProvince> medias = baseService.findAll(DictProvince.class);
				for (DictProvince mediaInfo : medias) {
					provinceMap.put(mediaInfo.getId(), mediaInfo);
				}
			}
		}
		return provinceMap;
	}
	
	public  Map<Integer, DictCity> getCityMap() {
		if (cityMap==null) {
			synchronized (this) {
				if (cityMap==null) {
					cityMap = new HashMap<Integer, DictCity>();
				}
				List<DictCity> medias = baseService.findAll(DictCity.class);
				for (DictCity mediaInfo : medias) {
					cityMap.put(mediaInfo.getId(), mediaInfo);
				}
			}
		}
		return cityMap;
	}
	public  Map<Integer, List<DictCarSerial>> getBrandId2SerialsMap() {
		if (brandId2SerialsMap==null) {
			synchronized (this) {
				if (brandId2SerialsMap==null) {
					brandId2SerialsMap = new HashMap<Integer, List<DictCarSerial>>();
				}
				List<DictCarSerial> medias = baseService.findAll(DictCarSerial.class);
				for (DictCarSerial d : medias) {
					List<DictCarSerial> list = brandId2SerialsMap.get(d.getBrandId());
					if (list==null) {
						list = new ArrayList<DictCarSerial>();
						brandId2SerialsMap.put(d.getBrandId(), list);
					}
					list.add(d);
				}
			}
		}
		return brandId2SerialsMap;
	}

	public  Map<Integer, List<DictProvince>> getAreaId2ProvincesMap() {
		if (areaId2ProvincesMap==null) {
			synchronized (this) {
				if (areaId2ProvincesMap==null) {
					areaId2ProvincesMap = new HashMap<Integer, List<DictProvince>>();
				}
				List<DictProvince> medias = baseService.findAll(DictProvince.class);
				for (DictProvince d : medias) {
					List<DictProvince> list = areaId2ProvincesMap.get(d.getAreaId());
					if (list==null) {
						list = new ArrayList<DictProvince>();
						areaId2ProvincesMap.put(d.getAreaId(), list);
					}
					list.add(d);
				}
			}
		}
		return areaId2ProvincesMap;
	}
	
	public  Map<Integer, List<DictCity>> getProvinceId2CitysMap() {
		if (provinceId2CitysMap==null) {
			synchronized (this) {
				if (provinceId2CitysMap==null) {
					provinceId2CitysMap = new HashMap<Integer, List<DictCity>>();
				}
				List<DictCity> medias = baseService.findAll(DictCity.class);
				for (DictCity d : medias) {
					List<DictCity> list = provinceId2CitysMap.get(d.getProId());
					if (list==null) {
						list = new ArrayList<DictCity>();
						provinceId2CitysMap.put(d.getProId(), list);
					}
					list.add(d);
				}
			}
		}
		return provinceId2CitysMap;
	}

}