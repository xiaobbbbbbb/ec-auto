package com.ecarinfo.auto.po;
import java.io.Serializable;

public class ArticleKeyword implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private Long articleId;
	private Long keywordId;

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

    public Long getKeywordId () {
        return keywordId;
    }

    public void setKeywordId (Long keywordId) {
        this.keywordId = keywordId;
    }
}