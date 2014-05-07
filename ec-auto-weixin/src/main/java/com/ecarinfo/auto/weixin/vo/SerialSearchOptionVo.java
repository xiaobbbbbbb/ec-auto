package com.ecarinfo.auto.weixin.vo;

import java.util.List;

public class SerialSearchOptionVo {
	private Integer brandId;
	private String brandName;
	private Boolean select;
	List<DictCarSerialVo> serials;
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Boolean getSelect() {
		return select;
	}
	public void setSelect(Boolean select) {
		this.select = select;
	}
	public List<DictCarSerialVo> getSerials() {
		return serials;
	}
	public void setSerials(List<DictCarSerialVo> serials) {
		this.serials = serials;
	}
	
	
}
