package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class BaiduHotRank implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String name;//品牌
	private Integer num;//搜索次数
	private Date ctime;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Integer getNum () {
        return num;
    }

    public void setNum (Integer num) {
        this.num = num;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }
}