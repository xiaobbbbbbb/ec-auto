package com.ecarinfo.auto.vo;

import java.io.Serializable;
import java.util.List;

import com.ecarinfo.persist.paging.ECPage;

/**
 * 负面情报
 */
public class NegativeVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String data;

	private ECPage<SeriesVO> page;
//	private List<SeriesVO> series;
	
	
	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}


	public ECPage<SeriesVO> getPage() {
		return page;
	}


	public void setPage(ECPage<SeriesVO> page) {
		this.page = page;
	}


//	public List<SeriesVO> getSeries() {
//		return series;
//	}
//
//
//	public void setSeries(List<SeriesVO> series) {
//		this.series = series;
//	}

//
//	@Override
//	public String toString() {
//		return "NegativeVO [data=" + data + ", series=" + series + "]";
//	}
	
	
}
