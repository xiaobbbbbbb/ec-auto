package com.ecarinfo.auto.vo;

import java.io.Serializable;
import java.util.List;

public class LineChartDataVO implements Serializable{
	private static final long serialVersionUID = -462825006204052051L;
	public String name;
	public List<ChartCoreDataVO> data;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ChartCoreDataVO> getData() {
		return data;
	}
	public void setData(List<ChartCoreDataVO> data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "LineChartVO [name=" + name + ", data=" + data + "]";
	}	
}
