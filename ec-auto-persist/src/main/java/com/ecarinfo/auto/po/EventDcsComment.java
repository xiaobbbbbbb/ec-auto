package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class EventDcsComment implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;//自增id  事件每日评论数据统计
	private Date recordDate;
	private Integer commentNum;
	private Date updateTime;
	private Integer eid;
	private Integer commentType;//1.新闻 2.博客 3.论坛 4.微博

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public Date getRecordDate () {
        return recordDate;
    }

    public void setRecordDate (Date recordDate) {
        this.recordDate = recordDate;
    }

    public Integer getCommentNum () {
        return commentNum;
    }

    public void setCommentNum (Integer commentNum) {
        this.commentNum = commentNum;
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

    public Integer getCommentType () {
        return commentType;
    }

    public void setCommentType (Integer commentType) {
        this.commentType = commentType;
    }
}