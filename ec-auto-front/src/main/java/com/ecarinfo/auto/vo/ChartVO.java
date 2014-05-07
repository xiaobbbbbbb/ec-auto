package com.ecarinfo.auto.vo;

import java.io.Serializable;

public class ChartVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;

	private Object object;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
}
