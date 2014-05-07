package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class OriginalArticle implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private String content;
	private String url;
	private Boolean isIndustry = false;
	private Integer mediaId;
	private Date pubTime;
	private Date ctime;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getContent () {
        return content;
    }

    public void setContent (String content) {
        this.content = content;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public Boolean getIsIndustry () {
        return isIndustry;
    }

    public void setIsIndustry (Boolean isIndustry) {
        this.isIndustry = isIndustry;
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
}