package com.ecarinfo.auto.weixin.vo;

import java.util.Date;

public class OneSerialOneDayArticleCount {
	private Integer serialId;
	private Date articleDate;
	private Integer articleCount;
	public Integer getSerialId() {
		return serialId;
	}
	public void setSerialId(Integer serialId) {
		this.serialId = serialId;
	}
	public Date getArticleDate() {
		return articleDate;
	}
	public void setArticleDate(Date articleDate) {
		this.articleDate = articleDate;
	}
	public Integer getArticleCount() {
		return articleCount;
	}
	public void setArticleCount(Integer articleCount) {
		this.articleCount = articleCount;
	}	
}
