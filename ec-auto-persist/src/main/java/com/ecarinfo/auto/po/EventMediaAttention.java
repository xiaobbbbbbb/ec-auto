package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class EventMediaAttention implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer emid;
	private Integer eid;//事件ID
	private Integer esid;//事件关键词ID，对应event_keyword_search表主键
	private Integer mediaType;//媒体类别，1：新闻；2：博客；3：论坛；4：微博
	private Date pubtime;//关注日期
	private String siteName;//数据来源站点名称
	private Integer dataNumber = 0;//数据量
	private Date updateTime = new Date();//记录更新时间
	private Integer siteid;//站点ID，便于页面查询。1：百度新闻，2：Google新闻，3：新浪微博，4：腾讯微博

    public Integer getEmid () {
        return emid;
    }

    public void setEmid (Integer emid) {
        this.emid = emid;
    }

    public Integer getEid () {
        return eid;
    }

    public void setEid (Integer eid) {
        this.eid = eid;
    }

    public Integer getEsid () {
        return esid;
    }

    public void setEsid (Integer esid) {
        this.esid = esid;
    }

    public Integer getMediaType () {
        return mediaType;
    }

    public void setMediaType (Integer mediaType) {
        this.mediaType = mediaType;
    }

    public Date getPubtime () {
        return pubtime;
    }

    public void setPubtime (Date pubtime) {
        this.pubtime = pubtime;
    }

    public String getSiteName () {
        return siteName;
    }

    public void setSiteName (String siteName) {
        this.siteName = siteName;
    }

    public Integer getDataNumber () {
        return dataNumber;
    }

    public void setDataNumber (Integer dataNumber) {
        this.dataNumber = dataNumber;
    }

    public Date getUpdateTime () {
        return updateTime;
    }

    public void setUpdateTime (Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getSiteid () {
        return siteid;
    }

    public void setSiteid (Integer siteid) {
        this.siteid = siteid;
    }
}