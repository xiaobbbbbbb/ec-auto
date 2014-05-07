package com.ecarinfo.auto.vo;

import java.io.Serializable;

import com.ecarinfo.auto.po.WeiboAnalysis;

/**
 * @author jamie
 *
 */
public class WeiboAnalysisVO extends  WeiboAnalysis implements Serializable{
	private static final long serialVersionUID = 1L;
	private String provinceName;
	private String cityName;
	private String mediaName;
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getMediaName() {
		return mediaName;
	}
	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	
	
	 
}
