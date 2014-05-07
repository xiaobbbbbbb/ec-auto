package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class ArticleComment implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private Long articleId;//文章ID
	private String content;//内容
	private Integer affection = 0;//情感说明 3:正面 2:中性 1:负面
	private Date ctime;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public Long getArticleId () {
        return articleId;
    }

    public void setArticleId (Long articleId) {
        this.articleId = articleId;
    }

    public String getContent () {
        return content;
    }

    public void setContent (String content) {
        this.content = content;
    }

    public Integer getAffection () {
        return affection;
    }

    public void setAffection (Integer affection) {
        this.affection = affection;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }
}