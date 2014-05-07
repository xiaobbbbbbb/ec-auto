package com.ecarinfo.auto.vo;
import java.io.Serializable;

import com.ecarinfo.auto.po.IndustryArticle;

public class TempIAVO  extends IndustryArticle implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer count;//数量 热度
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "TempIAVO [count=" + count + ", getId()=" + getId()
				+ ", getTitle()=" + getTitle() + ", getSimpleContent()="
				+ getSimpleContent() + ", getUrl()=" + getUrl()
				+ ", getMediaId()=" + getMediaId() + ", getPubTime()="
				+ getPubTime() + ", getCtime()=" + getCtime()
				+ ", getStatus()=" + getStatus() + ", getOriginalId()="
				+ getOriginalId() + ", getBrandId()=" + getBrandId()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
}
