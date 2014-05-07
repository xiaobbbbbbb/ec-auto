package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class Cloud implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private String title;
	private Integer hotGrade;//热度级别
	private String keywords;
	private Date ctime;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public Integer getHotGrade () {
        return hotGrade;
    }

    public void setHotGrade (Integer hotGrade) {
        this.hotGrade = hotGrade;
    }

    public String getKeywords () {
        return keywords;
    }

    public void setKeywords (String keywords) {
        this.keywords = keywords;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }
}