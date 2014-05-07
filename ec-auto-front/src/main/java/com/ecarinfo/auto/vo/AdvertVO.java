package com.ecarinfo.auto.vo;

import java.io.Serializable;
import java.util.List;

import com.ecarinfo.auto.po.Advertising;
import com.ecarinfo.auto.po.AdvertisingDetail;

public class AdvertVO extends AdvertisingDetail implements Serializable {

	private static final long serialVersionUID = 1881386111979845191L;

	private String descontent;
	private String startTime;
	private String endTime;

	public String getDescontent() {
		return descontent;
	}

	public void setDescontent(String descontent) {
		this.descontent = descontent;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "AdvertVO [descontent=" + descontent + ", startTime="
				+ startTime + ", endTime=" + endTime + ", getId()=" + getId()
				+ ", getPid()=" + getPid() + ", getTitle()=" + getTitle()
				+ ", getContent()=" + getContent() + ", getMediaId()="
				+ getMediaId() + ", getUrl()=" + getUrl() + ", getPageId()="
				+ getPageId() + ", getPlanStime()=" + getPlanStime()
				+ ", getPlanEtime()=" + getPlanEtime() + ", getRealStime()="
				+ getRealStime() + ", getRealEtime()=" + getRealEtime()
				+ ", getStatus()=" + getStatus() + ", getRealUrl()="
				+ getRealUrl() + ", getCtime()=" + getCtime() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
