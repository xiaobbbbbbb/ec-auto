package com.ecarinfo.auto.weixin.vo;

import java.util.List;

import com.ecarinfo.persist.paging.ECPage;
import com.ecarinfo.persist.simple.PageHolder;

public class MobilePage {
	private Long total;
	private List<?> list;
	private Integer pageSize = 10;
	private Integer page;
	private Integer prePage;
	private Integer nextPage;
	private Integer totalPage;
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	
	public MobilePage(Long total, List<?> rows, Integer page, Integer totalPage) {
		super();
		this.total = total;
		this.list = rows;
		this.page = page;
		this.prePage = page>1?page-1:page;
		this.nextPage = page<totalPage?page+1:totalPage;
		this.totalPage = totalPage;	
	}
	
	public MobilePage(ECPage<?> ecpage) {
		super();
		this.total = ecpage.getTotalRows();
		this.list = ecpage.getList();
	}
	
	public static MobilePage from(ECPage<?> ecpage) {
		return new MobilePage(ecpage.getTotalRows(), ecpage.getList(), (int)ecpage.getCurrentPage(), (int)ecpage.getTotalPage());
	}
	
	public static MobilePage from(PageHolder<?> ecpage) {
		return new MobilePage((long)ecpage.getTotalRows(), ecpage.getList(), (int)ecpage.getCurrentPage(), (int)ecpage.getTotalPage());
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPrePage() {
		return prePage;
	}
	public void setPrePage(Integer prePage) {
		this.prePage = prePage;
	}
	public Integer getNextPage() {
		return nextPage;
	}
	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}		
}
