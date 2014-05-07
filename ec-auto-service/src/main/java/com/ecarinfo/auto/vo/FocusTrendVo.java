package com.ecarinfo.auto.vo;

import java.util.Date;

public class FocusTrendVo {
	
	private Integer count;
	private Date date;
	private Integer brangid;
	private String barndName;
	private Integer serialid;
	private String sericalName;
	private Integer trend;//趋势 1上升，0持平，-1下降
	public Integer getSerialid() {
		return serialid;
	}
	public void setSerialid(Integer serialid) {
		this.serialid = serialid;
	}
	public String getSericalName() {
		return sericalName;
	}
	public void setSericalName(String sericalName) {
		this.sericalName = sericalName;
	}
	
	public Integer getTrend() {
		return trend;
	}
	public void setTrend(Integer trend) {
		this.trend = trend;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getBrangid() {
		return brangid;
	}
	public void setBrangid(Integer brangid) {
		this.brangid = brangid;
	}
	public String getBarndName() {
		return barndName;
	}
	public void setBarndName(String barndName) {
		this.barndName = barndName;
	}

}
