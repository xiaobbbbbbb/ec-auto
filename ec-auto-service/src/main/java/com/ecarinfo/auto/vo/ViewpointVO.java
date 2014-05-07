package com.ecarinfo.auto.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 口碑对像
 * 
 * @author Administrator
 * 
 */
public class ViewpointVO implements Serializable {
	private static final long serialVersionUID = 10L;
	private Integer id;
	public String name;
	private String title;
	public Boolean isManual;
	public Integer affection;
	public Date ctime;
	public Integer articleNums;
	private String mediaNme;// 网站名称
	private String rate;// 所占比例

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getIsManual() {
		return isManual;
	}

	public void setIsManual(Boolean isManual) {
		this.isManual = isManual;
	}

	public Integer getAffection() {
		return affection;
	}

	public void setAffection(Integer affection) {
		this.affection = affection;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Integer getArticleNums() {
		return articleNums;
	}

	public void setArticleNums(Integer articleNums) {
		this.articleNums = articleNums;
	}

	public String getMediaNme() {
		return mediaNme;
	}

	public void setMediaNme(String mediaNme) {
		this.mediaNme = mediaNme;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "ViewpointVO [id=" + id + ", name=" + name + ", isManual="
				+ isManual + ", affection=" + affection + ", ctime=" + ctime
				+ ",title=" + title + ", articleNums=" + articleNums
				+ ", rate=" + rate + ",mediaNme=" + mediaNme + "]";

	}

}
