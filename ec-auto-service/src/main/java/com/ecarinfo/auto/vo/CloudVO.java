package com.ecarinfo.auto.vo;

import java.io.Serializable;

public class CloudVO implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private String text;
	private Integer weight;// 热度级别
	private String url;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}