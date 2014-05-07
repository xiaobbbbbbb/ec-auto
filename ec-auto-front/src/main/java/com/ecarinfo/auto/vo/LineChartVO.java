package com.ecarinfo.auto.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 折线图
 */
public class LineChartVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String title;// 大标题
	private String subtitle;// 小标题
	
	private String categories;//X轴
	
	private List<SeriesVO> series;
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public List<SeriesVO> getSeries() {
		return series;
	}

	public void setSeries(List<SeriesVO> series) {
		this.series = series;
	}

	@Override
	public String toString() {
		return "LineChartVO [title=" + title + ", subtitle=" + subtitle + ", categories=" + categories + ", series=" + series + "]";
	}
	
	
}
