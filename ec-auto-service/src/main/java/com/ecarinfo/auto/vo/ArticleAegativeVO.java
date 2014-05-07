package com.ecarinfo.auto.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 用于存放厂牌、车系对应的信息及其文章的统计数量
 * @author laochen
 *
 */
public class ArticleAegativeVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private Date mediaDate;
	private Date date;
	private Integer articleNums;
	
	
	public Date getMediaDate() {
		return mediaDate;
	}
	public void setMediaDate(Date mediaDate) {
		this.mediaDate = mediaDate;
	}

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
	public Integer getArticleNums() {
		return articleNums;
	}
	public void setArticleNums(Integer articleNums) {
		this.articleNums = articleNums;
	}
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "ArticleAegativeVO [id=" + id + ", name=" + name+",mediaDate"+mediaDate
				+ ", articleNums=" + articleNums + "]";
	}
	
}
