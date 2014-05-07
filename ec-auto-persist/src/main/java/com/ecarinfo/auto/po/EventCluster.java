package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class EventCluster implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer eventId;
	private Date pubdate;
	private String site;
	private Boolean type;
	private Integer classid;
	private Date inserttime = new Date();

    public Integer getEventId () {
        return eventId;
    }

    public void setEventId (Integer eventId) {
        this.eventId = eventId;
    }

    public Date getPubdate () {
        return pubdate;
    }

    public void setPubdate (Date pubdate) {
        this.pubdate = pubdate;
    }

    public String getSite () {
        return site;
    }

    public void setSite (String site) {
        this.site = site;
    }

    public Boolean getType () {
        return type;
    }

    public void setType (Boolean type) {
        this.type = type;
    }

    public Integer getClassid () {
        return classid;
    }

    public void setClassid (Integer classid) {
        this.classid = classid;
    }

    public Date getInserttime () {
        return inserttime;
    }

    public void setInserttime (Date inserttime) {
        this.inserttime = inserttime;
    }
}