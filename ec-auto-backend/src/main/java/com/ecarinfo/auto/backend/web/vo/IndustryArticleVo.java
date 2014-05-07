package com.ecarinfo.auto.backend.web.vo;

import com.ecarinfo.auto.po.IndustryArticle;

public class IndustryArticleVo extends IndustryArticle{
	private String showMediaId;
	private String showPubTime;
	private String showCtime;
	private Long sqId;
	private String showStatus;
	private String showUrl;
	public String getShowMediaId() {
		return showMediaId;
	}
	public void setShowMediaId(String showMediaId) {
		this.showMediaId = showMediaId;
	}
	public String getShowPubTime() {
		return showPubTime;
	}
	public void setShowPubTime(String showPubTime) {
		this.showPubTime = showPubTime;
	}
	public String getShowCtime() {
		return showCtime;
	}
	public void setShowCtime(String showCtime) {
		this.showCtime = showCtime;
	}
	public Long getSqId() {
		return sqId;
	}
	public void setSqId(Long sqId) {
		this.sqId = sqId;
	}
	public String getShowStatus() {
		return showStatus;
	}
	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}
	public String getShowUrl() {
		return showUrl;
	}
	public void setShowUrl(String showUrl) {
		this.showUrl = showUrl;
	}


}
