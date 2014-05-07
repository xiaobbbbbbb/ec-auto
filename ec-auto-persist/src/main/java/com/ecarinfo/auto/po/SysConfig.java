package com.ecarinfo.auto.po;
import java.io.Serializable;

public class SysConfig implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String key;
	private String value;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getKey () {
        return key;
    }

    public void setKey (String key) {
        this.key = key;
    }

    public String getValue () {
        return value;
    }

    public void setValue (String value) {
        this.value = value;
    }
}