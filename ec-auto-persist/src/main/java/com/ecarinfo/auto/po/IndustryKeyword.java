package com.ecarinfo.auto.po;
import java.io.Serializable;

public class IndustryKeyword implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private Long industryArticleId;
	private Long keywordId;

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

    public Long getKeywordId () {
        return keywordId;
    }

    public void setKeywordId (Long keywordId) {
        this.keywordId = keywordId;
    }
}