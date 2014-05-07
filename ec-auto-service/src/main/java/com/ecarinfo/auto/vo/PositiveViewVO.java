package com.ecarinfo.auto.vo;

import java.io.Serializable;
import java.util.Date;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;

/**
 * @author yinql  负面分析
 *
 */
public class PositiveViewVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String url;
	private String title;
	private Integer grade;
	private Date articleDate;
	private String serialName;
	private String provinceName;
	private String cityName;
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
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public Date getArticleDate() {
		return articleDate;
	}
	public void setArticleDate(Date articleDate) {
		this.articleDate = articleDate;
	}
	public String getSerialName() {
		return serialName;
	}
	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getAtime(){
		return DateUtils.dateToString(articleDate, TimeFormatter.FORMATTER2);
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getStitle() {
		if(this.getTitle()!=null && this.getTitle().length()>=20){
			return this.getTitle().substring(0,20)+"……";
		}else{
			return this.getTitle();
		}
	}
}
