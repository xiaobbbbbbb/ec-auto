package com.ecarinfo.auto.vo;

import com.ecarinfo.auto.po.MouthAnalyzing;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;

public class MouthAnalyzingVO extends MouthAnalyzing {
	private String mediaName;
	public String getMediaName() {
		return mediaName;
	}
	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	public String getShowPtime() {
		return DateUtils.dateToString(this.getPubTime(), TimeFormatter.FORMATTER1);
	}
	 
}
