package com.ecarinfo.auto.po;
import java.io.Serializable;

public class ArticleViewpoint implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private Integer viewpointId;
	private Long articleId;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getViewpointId () {
        return viewpointId;
    }

    public void setViewpointId (Integer viewpointId) {
        this.viewpointId = viewpointId;
    }

    public Long getArticleId () {
        return articleId;
    }

    public void setArticleId (Long articleId) {
        this.articleId = articleId;
    }
}