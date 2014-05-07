package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class EventTransport implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private Integer eid;
	private Integer parentNodeId;
	private Integer childNodeId;
	private String childNodeidList;
	private Date pubdate;
	private Integer relationcount;
	private Boolean type;
	private Boolean isroot;
	private Date inserttime;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getEid () {
        return eid;
    }

    public void setEid (Integer eid) {
        this.eid = eid;
    }

    public Integer getParentNodeId () {
        return parentNodeId;
    }

    public void setParentNodeId (Integer parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    public Integer getChildNodeId () {
        return childNodeId;
    }

    public void setChildNodeId (Integer childNodeId) {
        this.childNodeId = childNodeId;
    }

    public String getChildNodeidList () {
        return childNodeidList;
    }

    public void setChildNodeidList (String childNodeidList) {
        this.childNodeidList = childNodeidList;
    }

    public Date getPubdate () {
        return pubdate;
    }

    public void setPubdate (Date pubdate) {
        this.pubdate = pubdate;
    }

    public Integer getRelationcount () {
        return relationcount;
    }

    public void setRelationcount (Integer relationcount) {
        this.relationcount = relationcount;
    }

    public Boolean getType () {
        return type;
    }

    public void setType (Boolean type) {
        this.type = type;
    }

    public Boolean getIsroot () {
        return isroot;
    }

    public void setIsroot (Boolean isroot) {
        this.isroot = isroot;
    }

    public Date getInserttime () {
        return inserttime;
    }

    public void setInserttime (Date inserttime) {
        this.inserttime = inserttime;
    }
}