package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class Advertising implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String content;//活动说明
	private Integer status = 0;//1有效，0无效
	private Date ctime;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getContent () {
        return content;
    }

    public void setContent (String content) {
        this.content = content;
    }

    public Integer getStatus () {
        return status;
    }

    public void setStatus (Integer status) {
        this.status = status;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }
}