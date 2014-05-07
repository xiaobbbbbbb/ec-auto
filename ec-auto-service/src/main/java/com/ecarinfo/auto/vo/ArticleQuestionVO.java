package com.ecarinfo.auto.vo;

import java.io.Serializable;
import java.util.Date;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;

/**
 * @author jamie
 *
 */
public class ArticleQuestionVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String title;
	private String provinceName;
	private String cityName;
	private String questionName;
	private Date articleDate;
	private String url;
	private Integer type;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getQuestionName() {
		return questionName;
	}
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
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
	@Override
	public String toString() {
		return "ArticleQuestionVO [id=" + id + ", title=" + title
				+ ", provinceName=" + provinceName + ", cityName=" + cityName
				+ ", articleDate=" + articleDate + "]";
	}
	
}
