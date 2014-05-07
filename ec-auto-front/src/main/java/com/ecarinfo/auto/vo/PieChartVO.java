package com.ecarinfo.auto.vo;

import java.io.Serializable;

public class PieChartVO implements Serializable {

	private static final long serialVersionUID = 8382791007139723103L;

	// 图表坐标的描述
	public String chartName;

	// 图表坐标的值
	public Object chartValue;

	public String getChartName() {
		return chartName;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}

	public Object getChartValue() {
		return chartValue;
	}

	public void setChartValue(Object chartValue) {
		this.chartValue = chartValue;
	}
}
