package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class EventKeywordSearch implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer ksid;//自增id
	private Integer eid;
	private String keywords;
	private Date insertTime = new Date();

    public Integer getKsid () {
        return ksid;
    }

    public void setKsid (Integer ksid) {
        this.ksid = ksid;
    }

    public Integer getEid () {
        return eid;
    }

    public void setEid (Integer eid) {
        this.eid = eid;
    }

    public String getKeywords () {
        return keywords;
    }

    public void setKeywords (String keywords) {
        this.keywords = keywords;
    }

    public Date getInsertTime () {
        return insertTime;
    }

    public void setInsertTime (Date insertTime) {
        this.insertTime = insertTime;
    }
}