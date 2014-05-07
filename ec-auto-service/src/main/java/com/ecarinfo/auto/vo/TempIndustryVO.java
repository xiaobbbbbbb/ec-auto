package com.ecarinfo.auto.vo;
import java.io.Serializable;
import com.ecarinfo.auto.po.TmpIndustryArticle;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;

public class TempIndustryVO   extends TmpIndustryArticle implements Serializable{
	private static final long serialVersionUID = 1L;
	private String mediaName;// 来源媒体名字
	private Integer startCount;
	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	
	public String getPtime() {
		return DateUtils.dateToString(this.getPubTime(), TimeFormatter.FORMATTER8);
	}

	@Override
	public String toString() {
		return "TempIndustryVO [mediaName=" + mediaName + ", getId()="
				+ getId() + ", getTitle()=" + getTitle() + ", getUrl()="
				+ getUrl() + ", getBrandId()=" + getBrandId()
				+ ", getMediaId()=" + getMediaId() + ", getPubTime()="
				+ getPubTime() + ", getHotCount()=" + getHotCount()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	public String getStitle() {
		if(this.getTitle()!=null && this.getTitle().length()>=20){
			return this.getTitle().substring(0,20)+"……";
		}else{
			return this.getTitle();
		}
	}

	public Integer getStartCount() {
		return startCount;
	}

	public void setStartCount(Integer startCount) {
		this.startCount = startCount;
	}

	
}
