package com.ecarinfo.auto.front.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ecarinfo.auto.po.MediaInfo;
import com.ecarinfo.persist.service.GenericService;

/**
 * 对少量常用的数据缓存类
 * @author zhanglu
 *
 */
@Component
public class CommonDataCache {
	static Map<String,RWLockWraper> lockMap = new HashMap<String, RWLockWraper>();
	private static Map<Integer, String> mediaMap = null;
	static {
		lockMap.put("orgsMap", new RWLockWraper());
		lockMap.put("groupsMap", new RWLockWraper());		
	}
	   
	@Resource
	protected GenericService baseService;
	
	public GenericService getBaseService() {
		return baseService;
	}

	public void setBaseService(GenericService baseService) {
		this.baseService = baseService;
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
}