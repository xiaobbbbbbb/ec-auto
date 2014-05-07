package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class EventBrief implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer eid;//自增id
	private String name;
	private Date startTime;
	private Date endTime;
	private Integer type;
	private Date createTime = new Date();
	private Date lastModify;
	private Integer status = 1;
	private Integer depId;
	private Integer userId;//用户ID
	private Boolean isPublic = false;//是否部门公开，1：是，0：否
	private Integer eventType;
	private String eventDescription;
	private String picPath;

    public Integer getEid () {
        return eid;
    }

    public void setEid (Integer eid) {
        this.eid = eid;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Date getStartTime () {
        return startTime;
    }

    public void setStartTime (Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime () {
        return endTime;
    }

    public void setEndTime (Date endTime) {
        this.endTime = endTime;
    }

    public Integer getType () {
        return type;
    }

    public void setType (Integer type) {
        this.type = type;
    }

    public Date getCreateTime () {
        return createTime;
    }

    public void setCreateTime (Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModify () {
        return lastModify;
    }

    public void setLastModify (Date lastModify) {
        this.lastModify = lastModify;
    }

    public Integer getStatus () {
        return status;
    }

    public void setStatus (Integer status) {
        this.status = status;
    }

    public Integer getDepId () {
        return depId;
    }

    public void setDepId (Integer depId) {
        this.depId = depId;
    }

    public Integer getUserId () {
        return userId;
    }

    public void setUserId (Integer userId) {
        this.userId = userId;
    }

    public Boolean getIsPublic () {
        return isPublic;
    }

    public void setIsPublic (Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Integer getEventType () {
        return eventType;
    }

    public void setEventType (Integer eventType) {
        this.eventType = eventType;
    }

    public String getEventDescription () {
        return eventDescription;
    }

    public void setEventDescription (String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getPicPath () {
        return picPath;
    }

    public void setPicPath (String picPath) {
        this.picPath = picPath;
    }
}