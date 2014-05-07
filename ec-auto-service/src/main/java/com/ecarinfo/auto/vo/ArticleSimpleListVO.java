package com.ecarinfo.auto.vo;

import java.io.Serializable;
import java.util.Date;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;

/**
 * @author Administrator
 * 
 */
public class ArticleSimpleListVO implements Serializable {

	private static final long serialVersionUID = 4298199173346157115L;
	private Long id;
	private String title;
	private String brand_name;// 车牌名
	private String affection;// 文章情感值
	private String mediaName;// 来源媒体名字
	private String desTarget;// 责任对象
	private Long aboutCount;// 相关条数
	private String serialName;//车系名称
	// http://wiki.otra.cn/index.php?title=Persist
	private Date pubDate;// 发布时间 select left(createtime,10) as create_date
	private Date createTime;
	private String url;

	private String content;// 文章内容
	
	private String viewPoints;//文章观点 以逗号分隔

	public String getViewPoints() {
		return viewPoints;
	}

	public void setViewPoints(String viewPoints) {
		this.viewPoints = viewPoints;
	}

	public String getCtime() {
		return DateUtils.dateToString(createTime, TimeFormatter.FORMATTER2);
	}
	
	public String getPtime() {
		return DateUtils.dateToString(pubDate, TimeFormatter.FORMATTER2);
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public Long getId() {
		return id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAffection() {
		return affection;
	}

	public void setAffection(String affection) {
		this.affection = affection;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public String getDesTarget() {
		return desTarget;
	}

	public void setDesTarget(String desTarget) {
		this.desTarget = desTarget;
	}

	public Long getAboutCount() {
		return aboutCount;
	}

	public void setAboutCount(Long aboutCount) {
		this.aboutCount = aboutCount;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getSerialName() {
		return serialName;
	}

	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}

	@Override
	public String toString() {
		return "ArticleSimpleListVO [id=" + id + ", title=" + title
				+ ", brand_name=" + brand_name + ", affection=" + affection
				+ ", mediaName=" + mediaName + ", desTarget=" + desTarget
				+ ", aboutCount=" + aboutCount + ", serialName=" + serialName
				+ ", pubDate=" + pubDate + ", createTime=" + createTime
				+ ", url=" + url + ", content=" + content + ", viewPoints="
				+ viewPoints + "]";
	}

}
