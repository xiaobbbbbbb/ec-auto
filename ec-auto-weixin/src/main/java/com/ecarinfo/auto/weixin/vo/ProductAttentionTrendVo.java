package com.ecarinfo.auto.weixin.vo;

import java.util.List;

public class ProductAttentionTrendVo {
	private Integer serialId;
	public Integer getSerialId() {
		return serialId;
	}
	public void setSerialId(Integer serialId) {
		this.serialId = serialId;
	}
	private String serialName;
	private List<Integer> articleCounts;
	
	
	public String getSerialName() {
		return serialName;
	}
	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}
	public List<Integer> getArticleCounts() {
		return articleCounts;
	}
	public void setArticleCounts(List<Integer> articleCounts) {
		this.articleCounts = articleCounts;
	}
	
	
}
