package com.ecarinfo.auto.vo;

import java.io.Serializable;
import java.util.Date;

import com.ecarinfo.auto.po.AdvertisingDetail;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;

/**
 * @author Administrator
 * 
 */
public class AdvertingDetailVO extends AdvertisingDetail implements Serializable {

	private static final long serialVersionUID = 4298199173346157115L;
	private String mediaName;//投放媒体
	private String pageName;//投放版面
	public String getMediaName() {
		return mediaName;
	}
	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	
	public String getStime() {
		return DateUtils.dateToString(super.getPlanStime(), TimeFormatter.FORMATTER2);
	}
}
