package com.ecarinfo.auto.po;
import java.io.Serializable;

public class EventWeiboRecord implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer eid;
	private String indextime;

    public Integer getEid () {
        return eid;
    }

    public void setEid (Integer eid) {
        this.eid = eid;
    }

    public String getIndextime () {
        return indextime;
    }

    public void setIndextime (String indextime) {
        this.indextime = indextime;
    }
}