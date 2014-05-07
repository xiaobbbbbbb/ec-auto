package com.ecarinfo.auto.backend.web.vo;

import com.ecarinfo.auto.po.Viewpoint;

public class ViewpointVo extends Viewpoint {
	private boolean select;
	private String showStatus;
	private String showCtime;
	private String showAffection;	
	private Long sqId;
	public boolean getSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public String getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
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

	public String getShowAffection() {
		return showAffection;
	}

	public void setShowAffection(String showAffection) {
		this.showAffection = showAffection;
	}
	
}
