package com.ecarinfo.auto.vo;

import java.io.Serializable;

public class MouthSerialName implements Serializable {

	private static final long serialVersionUID = 4157128658691774881L;

	private Integer id;

	private String serialName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSerialName() {
		return serialName;
	}

	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}
}
