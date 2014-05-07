package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class Keyword implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private String name;
	private Integer type = 0;//1:article 2:industry_article 3:属于两者
	private Date ctime;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Integer getType () {
        return type;
    }

    public void setType (Integer type) {
        this.type = type;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }
}