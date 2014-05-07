package com.ecarinfo.auto.vo;

import java.io.Serializable;
import java.util.Date;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;

/**
 * @author jamie
 */
public class ArticleMouthVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String title;
	private String content;
	private String viewpointName;
	private String cityName;
	private String provinceName;
	private Integer affection;//情感 3好评，2中评，1差评
	private Date articleDate;
	private String url;
	private String serialName;
	private Integer type;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getSerialName() {
		return serialName;
	}
	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}
	public String getViewpointName() {
		return viewpointName;
	}
	public void setViewpointName(String viewpointName) {
		this.viewpointName = viewpointName;
	}

	public Integer getAffection() {
		return affection;
	}
	public void setAffection(Integer affection) {
		this.affection = affection;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Date getArticleDate() {
		return articleDate;
	}
	public void setArticleDate(Date articleDate) {
		this.articleDate = articleDate;
	}
	public String getPtime() {
		return DateUtils.dateToString(articleDate, TimeFormatter.FORMATTER2);
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "ArticleMouthVO [id=" + id + ", title=" + title
				+ ", viewpointName=" + viewpointName + ", cityName=" + cityName
				+ ", provinceName=" + provinceName + ", affection=" + affection
				+ ", articleDate=" + articleDate + ", url=" + url
				+ ", serialName=" + serialName + ", type=" + type + "]";
	}

}
