package com.ecarinfo.auto.backend.web.vo;

import java.util.List;

import com.ecarinfo.persist.paging.ECPage;

public class EasyUIPage {
	private Long total;
	private List<?> rows;
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
	public EasyUIPage(Long total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	
	public EasyUIPage(ECPage<?> ecpage) {
		super();
		this.total = ecpage.getTotalRows();
		this.rows = ecpage.getList();
	}
	
	public static EasyUIPage from(ECPage<?> ecpage) {
		return new EasyUIPage(ecpage.getTotalRows(), ecpage.getList());
	}
}
