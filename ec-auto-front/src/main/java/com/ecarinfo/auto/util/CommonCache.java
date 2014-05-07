package com.ecarinfo.auto.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecarinfo.auto.front.helper.RWLockWraper;
import com.ecarinfo.auto.po.DictArea;
import com.ecarinfo.auto.po.DictCarBrand;
import com.ecarinfo.auto.po.DictCarSerial;
import com.ecarinfo.auto.po.DictCity;
import com.ecarinfo.auto.po.DictProvince;
import com.ecarinfo.auto.po.MediaInfo;
import com.ecarinfo.auto.po.Question;
import com.ecarinfo.auto.po.Viewpoint;
import com.ecarinfo.auto.po.ViewpointType;
import com.ecarinfo.auto.rm.DictCarBrandRM;
import com.ecarinfo.auto.rm.DictCarSerialRM;
import com.ecarinfo.auto.rm.DictProvinceRM;
import com.ecarinfo.auto.rm.MediaInfoRM;
import com.ecarinfo.auto.rm.QuestionRM;
import com.ecarinfo.auto.rm.ViewpointRM;
import com.ecarinfo.auto.rm.ViewpointTypeRM;
import com.ecarinfo.persist.criteria.Criteria;
import com.ecarinfo.persist.criteria.Criteria.CondtionSeparator;
import com.ecarinfo.persist.criteria.Criteria.OrderType;
import com.ecarinfo.persist.service.GenericService;

/**
 * 对少量常用的数据缓存类
 * @author zhanglu
 *
 */
@Component("commonCache")
public class CommonCache {
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
	private static Map<Integer, Viewpoint> viewpointsMap = null;
	private static Map<Integer, String> affectionMap = null;
	private static Map<Integer, String> destTargetMap = null;
	private static Map<Integer,String> questionMap = null;
	private static Map<Class<? extends Serializable>, Map<Class<?>, Class<? extends Serializable>>> field2EntityMaps = new HashMap<Class<? extends Serializable>, Map<Class<?>, Class<? extends Serializable>>>();
	private static Map<Class<? extends Serializable>, Map<Class<?>, List<Class<? extends Serializable>>>> grouppedEntitysMaps = new HashMap<Class<? extends Serializable>, Map<Class<?>, List<Class<? extends Serializable>>>>();
	
	private static List<DictCarBrand> brands = null;
	private static List<DictArea> areas = null;
	private static List<DictProvince> provinces = null;
	private static List<MediaInfo> mediaInfos = null;
	private static List<Question> questions = null;
	private static List<ViewpointType> viewpointType = null;
	private static DictCarBrand defaultCarBrand = null;
	
	
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
				List<MediaInfo> medias = baseService.findList(MediaInfo.class,new Criteria().eq(MediaInfoRM.isValid,1));
				for (MediaInfo mediaInfo : medias) {
					mediaMap.put(mediaInfo.getId(), mediaInfo.getName());
				}
			}
		}
		return mediaMap;
	}
	
	public  Map<Integer, String> getQuestionMap() {
		if (questionMap==null) {
			synchronized (this) {
				if (questionMap==null) {
					questionMap = new HashMap<Integer, String>();
				}
				List<Question> questions = baseService.findList(Question.class,new Criteria().eq(QuestionRM.status,1));
				for (Question question : questions) {
					questionMap.put(question.getId(), question.getName());
				}
			}
		}
		return questionMap;
	}
	
	public  Map<Integer, Viewpoint> getViewpointsMap() {
		if (viewpointsMap==null) {
			synchronized (this) {
				if (viewpointsMap==null) {
					viewpointsMap = new HashMap<Integer, Viewpoint>();
				}
				List<Viewpoint> list = baseService.findList(Viewpoint.class,new Criteria().eq(ViewpointRM.isValid,1).eq(ViewpointRM.status, 1, CondtionSeparator.AND));
				for (Viewpoint viewpoint : list) {
					viewpointsMap.put(viewpoint.getId(),viewpoint);
				}
			}
		}
		return viewpointsMap;
	}
	
	public  Map<Integer, String> getAffectionMap() {
		
		if (affectionMap==null) {
			affectionMap = new HashMap<Integer, String>();
			affectionMap.put(1, "差");
			affectionMap.put(2, "中");
			affectionMap.put(3, "好");
		}
		return affectionMap;
	}
	
	public  Map<Integer, String> getDestTargetMap() {
		
		if (destTargetMap==null) {
			destTargetMap = new HashMap<Integer, String>();
			destTargetMap.put(1, "主机厂");
			destTargetMap.put(2, "4S店");
			destTargetMap.put(3, "其它");
		}
		return destTargetMap;
	}
	public void reloadCarBrandOrSerialsCaches() {
		synchronized (this) { 
			brands = null;
			brandMap = null;
			serialMap = null;
			brandId2SerialsMap = null;
			defaultCarBrand = null;
		}	
	}
	
	public  List<DictCarBrand> getBrands() {
		if (brands==null) {
			synchronized (this) {
				if (brands==null) {
					brands = new ArrayList<DictCarBrand>();
				}
				brands = baseService.findList(DictCarBrand.class, new Criteria().eq(DictCarBrandRM.isValid,"1").orderBy(DictCarBrandRM.type,OrderType.ASC));
			}
		}
		return brands;
	}
	
	public  List<Question> getQuestions() {
		if (questions==null) {
			synchronized (this) {
				if (questions==null) {
					questions = new ArrayList<Question>();
				}
				questions = baseService.findList(Question.class, new Criteria().eq(QuestionRM.status,"1"));
			}
		}
		return questions;
	}
	public  List<DictProvince> getProvinces() {
		if (provinces==null) {
			synchronized (this) {
				if (provinces==null) {
					provinces = new ArrayList<DictProvince>();
				}
				provinces = baseService.findList(DictProvince.class, new Criteria().eq(DictProvinceRM.isValid,"1"));
			}
		}
		return provinces;
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
	
	public  List<MediaInfo> getMediaInfos() {
		if (mediaInfos==null) {
			synchronized (this) {
				if (mediaInfos==null) {
					mediaInfos = new ArrayList<MediaInfo>();
				}
				mediaInfos = baseService.findList(MediaInfo.class,new Criteria().eq(MediaInfoRM.isValid,1));
			}
		}
		return mediaInfos;
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
				List<DictCarSerial> medias = baseService.findList(DictCarSerial.class, new Criteria().eq(DictCarSerialRM.isValid, 1).orderBy(DictCarSerialRM.id, OrderType.ASC));
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

	public DictCarBrand getDefaultCarBrand(){
		if (defaultCarBrand==null) {
			synchronized (this) {
				if (defaultCarBrand==null) {
					defaultCarBrand =baseService.findOne(DictCarBrand.class, new Criteria().eq(DictCarBrandRM.type,1).eq(DictCarBrandRM.isValid, 1, CondtionSeparator.AND));
				}
				
			}
		}
		return defaultCarBrand;
	}
	
	public List<ViewpointType> getViewpointTypeList(){
		if (viewpointType==null) {
			synchronized (this) {
				if (viewpointType==null) {
					viewpointType = new ArrayList<ViewpointType>();
				}
				viewpointType = baseService.findList(ViewpointType.class, new Criteria().eq(ViewpointTypeRM.status, 1));
			}
		}
		return viewpointType;
	}
}