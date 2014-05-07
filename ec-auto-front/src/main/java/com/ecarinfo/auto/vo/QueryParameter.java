package com.ecarinfo.auto.vo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class QueryParameter implements Serializable {

	private static final long serialVersionUID = -9208212628888988614L;

	private Integer[] brandIds;// 厂牌ID集合

	private Integer brandId;// 厂牌ID

	private Integer areadId;// 区域的ID

	private Date startTime;// 开始时间

	private Date endTime;// 结束时间

	private Integer[] serialIds;// 车系ID集合
	
	private Integer serialId;//车系ID

	private Integer provinceId;// 省份ID

	private Integer grade;// 文章等级

	private String title;
	
	private Integer cityId;
	
	public Integer[] getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(Integer[] brandIds) {
		this.brandIds = brandIds;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getAreadId() {
		return areadId;
	}

	public void setAreadId(Integer areadId) {
		this.areadId = areadId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer[] getSerialIds() {
		return serialIds;
	}

	public Integer getSerialId() {
		return serialId;
	}

	public void setSerialId(Integer serialId) {
		this.serialId = serialId;
	}

	public void setSerialIds(Integer[] serialIds) {
		this.serialIds = serialIds;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	@Override
	public String toString() {
		return "QueryParameter [brandIds=" + Arrays.toString(brandIds)
				+ ", brandId=" + brandId + ", areadId=" + areadId
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", serialIds=" + Arrays.toString(serialIds) + ", serialId="
				+ serialId + ", provinceId=" + provinceId + ", grade=" + grade
				+ ", title=" + title + ", cityId=" + cityId + "]";
	}

	

}
