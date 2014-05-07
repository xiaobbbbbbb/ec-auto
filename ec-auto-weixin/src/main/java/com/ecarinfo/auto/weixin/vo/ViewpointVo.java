package com.ecarinfo.auto.weixin.vo;

import com.ecarinfo.auto.po.Viewpoint;

public class ViewpointVo extends Viewpoint {
	private Integer articleCount;
	private Float articlePercent =0f;
	public Integer getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(Integer articleCount) {
		this.articleCount = articleCount;
	}

	public Float getArticlePercent() {
		return articlePercent;
	}

	public void setArticlePercent(Float articlePercent) {
		this.articlePercent = articlePercent;
	}
	
}
