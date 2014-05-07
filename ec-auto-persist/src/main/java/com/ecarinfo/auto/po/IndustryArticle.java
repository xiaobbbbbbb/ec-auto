package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class IndustryArticle implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private String title;
	private String simpleContent;//内容
	private String url;//原文链接
	private Integer mediaId;//媒体ID
	private Date pubTime;//文章发布时间
	private Date ctime;
	private Integer status = 0;
	private Long originalId;//ԭʼ
	private Integer brandId;//1.厂牌ID

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

    public String getSimpleContent () {
        return simpleContent;
    }

    public void setSimpleContent (String simpleContent) {
        this.simpleContent = simpleContent;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public Integer getMediaId () {
        return mediaId;
    }

    public void setMediaId (Integer mediaId) {
        this.mediaId = mediaId;
    }

    public Date getPubTime () {
        return pubTime;
    }

    public void setPubTime (Date pubTime) {
        this.pubTime = pubTime;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }

    public Integer getStatus () {
        return status;
    }

    public void setStatus (Integer status) {
        this.status = status;
    }

    public Long getOriginalId () {
        return originalId;
    }

    public void setOriginalId (Long originalId) {
        this.originalId = originalId;
    }

    public Integer getBrandId () {
        return brandId;
    }

    public void setBrandId (Integer brandId) {
        this.brandId = brandId;
    }
}