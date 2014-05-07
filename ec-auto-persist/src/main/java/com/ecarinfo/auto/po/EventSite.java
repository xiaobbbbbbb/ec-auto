package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class EventSite implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer esid;
	private Integer eid;
	private String siteName;
	private String channelName;
	private String url;
	private Integer type;
	private Date updateTime;

    public Integer getEsid () {
        return esid;
    }

    public void setEsid (Integer esid) {
        this.esid = esid;
    }

    public Integer getEid () {
        return eid;
    }

    public void setEid (Integer eid) {
        this.eid = eid;
    }

    public String getSiteName () {
        return siteName;
    }

    public void setSiteName (String siteName) {
        this.siteName = siteName;
    }

    public String getChannelName () {
        return channelName;
    }

    public void setChannelName (String channelName) {
        this.channelName = channelName;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public Integer getType () {
        return type;
    }

    public void setType (Integer type) {
        this.type = type;
    }

    public Date getUpdateTime () {
        return updateTime;
    }

    public void setUpdateTime (Date updateTime) {
        this.updateTime = updateTime;
    }
}