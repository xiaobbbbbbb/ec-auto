package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class TmpIndustryArticle implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;//id
	private String title;//标题
	private String url;//原文链接
	private Integer brandId = 0;//厂牌id
	private Integer mediaId;//媒体id
	private Date pubTime;//发布时间
	private Integer hotCount = 0;//热度

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

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public Integer getBrandId () {
        return brandId;
    }

    public void setBrandId (Integer brandId) {
        this.brandId = brandId;
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

    public Integer getHotCount () {
        return hotCount;
    }

    public void setHotCount (Integer hotCount) {
        this.hotCount = hotCount;
    }
}