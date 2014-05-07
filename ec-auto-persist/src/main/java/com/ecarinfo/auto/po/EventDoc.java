package com.ecarinfo.auto.po;
import java.io.Serializable;

public class EventDoc implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer did;
	private Integer eid;
	private String title;
	private String content;

    public Integer getDid () {
        return did;
    }

    public void setDid (Integer did) {
        this.did = did;
    }

    public Integer getEid () {
        return eid;
    }

    public void setEid (Integer eid) {
        this.eid = eid;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getContent () {
        return content;
    }

    public void setContent (String content) {
        this.content = content;
    }
}