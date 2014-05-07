package com.ecarinfo.auto.backend.web.vo;

import com.ecarinfo.auto.po.UserInfo;

public class UserInfoVo extends UserInfo {
	private String showLoginName;
	private String showDisplayName;
	private String showEmail;
	private String showBrandId;
	private String showSerialId;
	private String showCtime;
	public String getShowLoginName() {
		return showLoginName;
	}
	public void setShowLoginName(String showLoginName) {
		this.showLoginName = showLoginName;
	}
	public String getShowDisplayName() {
		return showDisplayName;
	}
	public void setShowDisplayName(String showDisplayName) {
		this.showDisplayName = showDisplayName;
	}
	public String getShowEmail() {
		return showEmail;
	}
	public void setShowEmail(String showEmail) {
		this.showEmail = showEmail;
	}
	public String getShowBrandId() {
		return showBrandId;
	}
	public void setShowBrandId(String showBrandId) {
		this.showBrandId = showBrandId;
	}
	public String getShowSerialId() {
		return showSerialId;
	}
	public void setShowSerialId(String showSerialId) {
		this.showSerialId = showSerialId;
	}
	public String getShowCtime() {
		return showCtime;
	}
	public void setShowCtime(String showCtime) {
		this.showCtime = showCtime;
	}
 
	
}
