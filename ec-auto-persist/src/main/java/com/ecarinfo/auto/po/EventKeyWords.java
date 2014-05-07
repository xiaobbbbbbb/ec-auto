package com.ecarinfo.auto.po;
import java.io.Serializable;

public class EventKeyWords implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer kid;//自增id 
	private Integer eid;
	private String keywords;
	private Integer relation;//1.与0.或-1.非

    public Integer getKid () {
        return kid;
    }

    public void setKid (Integer kid) {
        this.kid = kid;
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

    public Integer getRelation () {
        return relation;
    }

    public void setRelation (Integer relation) {
        this.relation = relation;
    }
}