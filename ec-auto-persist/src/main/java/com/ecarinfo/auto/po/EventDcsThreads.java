package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class EventDcsThreads implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;//自增id 事件论坛数据每日统计
	private Integer siteId;
	private Date recordDate;
	private Integer publishNum;
	private Integer insertNum;
	private Integer picNum;
	private Date updateTime;
	private Integer eid;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getSiteId () {
        return siteId;
    }

    public void setSiteId (Integer siteId) {
        this.siteId = siteId;
    }

    public Date getRecordDate () {
        return recordDate;
    }

    public void setRecordDate (Date recordDate) {
        this.recordDate = recordDate;
    }

    public Integer getPublishNum () {
        return publishNum;
    }

    public void setPublishNum (Integer publishNum) {
        this.publishNum = publishNum;
    }

    public Integer getInsertNum () {
        return insertNum;
    }

    public void setInsertNum (Integer insertNum) {
        this.insertNum = insertNum;
    }

    public Integer getPicNum () {
        return picNum;
    }

    public void setPicNum (Integer picNum) {
        this.picNum = picNum;
    }

    public Date getUpdateTime () {
        return updateTime;
    }

    public void setUpdateTime (Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getEid () {
        return eid;
    }

    public void setEid (Integer eid) {
        this.eid = eid;
    }
}