package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class EventPr implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private Integer eventId;//事件id
	private Date prDate;//公关日期
	private Integer userId;//操作用户
	private Date ctime;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getEventId () {
        return eventId;
    }

    public void setEventId (Integer eventId) {
        this.eventId = eventId;
    }

    public Date getPrDate () {
        return prDate;
    }

    public void setPrDate (Date prDate) {
        this.prDate = prDate;
    }

    public Integer getUserId () {
        return userId;
    }

    public void setUserId (Integer userId) {
        this.userId = userId;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }
}