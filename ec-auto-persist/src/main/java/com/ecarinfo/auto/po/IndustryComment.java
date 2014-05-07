package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class IndustryComment implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private Long industryArticleId;
	private String content;
	private Boolean affection = false;//情感（1好评，0中评，-1差评）
	private Date ctime;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public Long getIndustryArticleId () {
        return industryArticleId;
    }

    public void setIndustryArticleId (Long industryArticleId) {
        this.industryArticleId = industryArticleId;
    }

    public String getContent () {
        return content;
    }

    public void setContent (String content) {
        this.content = content;
    }

    public Boolean getAffection () {
        return affection;
    }

    public void setAffection (Boolean affection) {
        this.affection = affection;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }
}