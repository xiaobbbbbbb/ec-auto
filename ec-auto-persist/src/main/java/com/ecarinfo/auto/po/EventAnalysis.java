package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class EventAnalysis implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer eaid;
	private Integer eid;
	private Integer sourcetype;
	private Integer sourceid;
	private Date pubtime;
	private String content;
	private Integer refLevel;
	private Integer reliable;
	private Integer eacid;
	private String url;

    public Integer getEaid () {
        return eaid;
    }

    public void setEaid (Integer eaid) {
        this.eaid = eaid;
    }

    public Integer getEid () {
        return eid;
    }

    public void setEid (Integer eid) {
        this.eid = eid;
    }

    public Integer getSourcetype () {
        return sourcetype;
    }

    public void setSourcetype (Integer sourcetype) {
        this.sourcetype = sourcetype;
    }

    public Integer getSourceid () {
        return sourceid;
    }

    public void setSourceid (Integer sourceid) {
        this.sourceid = sourceid;
    }

    public Date getPubtime () {
        return pubtime;
    }

    public void setPubtime (Date pubtime) {
        this.pubtime = pubtime;
    }

    public String getContent () {
        return content;
    }

    public void setContent (String content) {
        this.content = content;
    }

    public Integer getRefLevel () {
        return refLevel;
    }

    public void setRefLevel (Integer refLevel) {
        this.refLevel = refLevel;
    }

    public Integer getReliable () {
        return reliable;
    }

    public void setReliable (Integer reliable) {
        this.reliable = reliable;
    }

    public Integer getEacid () {
        return eacid;
    }

    public void setEacid (Integer eacid) {
        this.eacid = eacid;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }
}