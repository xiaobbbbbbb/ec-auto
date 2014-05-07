package com.ecarinfo.auto.vo;

import java.util.List;

public class HighChartVO {
	private Integer id;
	
	private String title;
	
	private List<ArticleAegativeVO> list;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ArticleAegativeVO> getList() {
		return list;
	}

	public void setList(List<ArticleAegativeVO> list) {
		this.list = list;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
