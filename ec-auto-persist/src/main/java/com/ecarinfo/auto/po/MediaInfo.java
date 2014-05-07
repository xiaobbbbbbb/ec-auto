package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class MediaInfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String name;
	private String url;
	private Integer mediaTypeId = 0;
	private String isValid;//1有效，0无效
	private Date ctime;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public Integer getMediaTypeId () {
        return mediaTypeId;
    }

    public void setMediaTypeId (Integer mediaTypeId) {
        this.mediaTypeId = mediaTypeId;
    }

    public String getIsValid () {
        return isValid;
    }

    public void setIsValid (String isValid) {
        this.isValid = isValid;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }
}