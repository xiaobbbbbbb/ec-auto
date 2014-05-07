package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class EventAnalysisConf implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer eacid;
	private Integer eid;
	private Integer type;
	private String content;
	private Date intime;
	private Date updatetime;

    public Integer getEacid () {
        return eacid;
    }

    public void setEacid (Integer eacid) {
        this.eacid = eacid;
    }

    public Integer getEid () {
        return eid;
    }

    public void setEid (Integer eid) {
        this.eid = eid;
    }

    public Integer getType () {
        return type;
    }

    public void setType (Integer type) {
        this.type = type;
    }

    public String getContent () {
        return content;
    }

    public void setContent (String content) {
        this.content = content;
    }

    public Date getIntime () {
        return intime;
    }

    public void setIntime (Date intime) {
        this.intime = intime;
    }

    public Date getUpdatetime () {
        return updatetime;
    }

    public void setUpdatetime (Date updatetime) {
        this.updatetime = updatetime;
    }
}