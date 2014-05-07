package com.ecarinfo.auto.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ecarinfo.auto.po.DictMediaType;
import com.ecarinfo.auto.po.MediaInfo;
import com.ecarinfo.auto.service.impl.CommonServiceImpl;
import com.ecarinfo.persist.exdao.GenericDao;

public  class AutoUtils {
	@Resource
	static CommonServiceImpl commonServiceImpl;
	
	@Resource
	static GenericDao genericDao;
	
	static AutoUtils autoUtils;
	public static Map<Integer,String> mediaInfoMap =new HashMap<Integer,String>();
	public static Map<Integer,String> mediaTypeMap =new HashMap<Integer,String>();
	
	private AutoUtils() {  
	        super();  
	    }  
	  
	    /** 
	     * 取得AutoUtils的一个实例 
	     */  
	public static AutoUtils getInstance() {  
        if (autoUtils == null){  
        	autoUtils = new AutoUtils();
        }
        if(mediaInfoMap==null){
        	getMediaInfoMap();
        }
        if(mediaTypeMap==null){
        	getDictMediaTypeMap();
        }
        
        return autoUtils;// 
        
	 }  
	
	public void removeMediaInfoMap(){
		mediaInfoMap.clear();
	}
	
	public void removeMediaTypeMap(){
		mediaTypeMap.clear();
	}
	
	static Map<Integer,String> getMediaInfoMap(){
		Map<Integer,String> map = new HashMap<Integer,String>();
		List<MediaInfo>  list =genericDao.findAll(MediaInfo.class);
		for(MediaInfo mediaInfo:list){
			map.put(mediaInfo.getId(), mediaInfo.getName());
		}
		return map;
	}
	
	
	static Map<Integer,String> getDictMediaTypeMap(){
		Map<Integer,String> map = new HashMap<Integer,String>();
		List<DictMediaType>  list =genericDao.findAll(DictMediaType.class);
		for(DictMediaType mediaInfo:list){
			map.put(mediaInfo.getId(), mediaInfo.getName());
		}
		return map;
	}
	
}
