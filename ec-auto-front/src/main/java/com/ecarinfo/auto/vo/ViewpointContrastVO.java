package com.ecarinfo.auto.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 产品口碑的产品对比
 * @author Dawn
 * 
 */

public class ViewpointContrastVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String serialName;
	
	private Integer carSerialId;

	List<ViewpointVO> viewpoint;

	public String getSerialName() {
		return serialName;
	}

	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}

	public List<ViewpointVO> getViewpoint() {
		return viewpoint;
	}

	public void setViewpoint(List<ViewpointVO> viewpoint) {
		this.viewpoint = viewpoint;
	}

	public Integer getCarSerialId() {
		return carSerialId;
	}

	public void setCarSerialId(Integer carSerialId) {
		this.carSerialId = carSerialId;
	}
}
