package com.ecarinfo.auto.vo;

import java.io.Serializable;

public class ColumnChartVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String categories;
	private String data;

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
