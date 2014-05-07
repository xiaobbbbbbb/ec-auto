package com.ecarinfo.auto.vo;

import java.io.Serializable;

/**
 * Y轴
 */

public class SeriesVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;

	private String data;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "SeriesVO [name=" + name + ", data=" + data + "]";
	}
}
