package com.ecarinfo.auto.vo;

import java.io.Serializable;

import com.ecarinfo.auto.po.NetworkMediaAnalysis;

/**
 * @author jamie
 *
 */
public class NetworkMediaAnalysisVO extends NetworkMediaAnalysis implements Serializable{
	
 
	private static final long serialVersionUID = 1L;
	private Integer mediaName;
	public Integer getMediaName() {
		return mediaName;
	}
	public void setMediaName(Integer mediaName) {
		this.mediaName = mediaName;
	}
}
