package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class EventProcess implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private Integer eventId;
	private Integer addNums;//新增公关数
	private Date processDate;//公关时间
	private Integer createTime;

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

    public Integer getAddNums () {
        return addNums;
    }

    public void setAddNums (Integer addNums) {
        this.addNums = addNums;
    }

    public Date getProcessDate () {
        return processDate;
    }

    public void setProcessDate (Date processDate) {
        this.processDate = processDate;
    }

    public Integer getCreateTime () {
        return createTime;
    }

    public void setCreateTime (Integer createTime) {
        this.createTime = createTime;
    }
}