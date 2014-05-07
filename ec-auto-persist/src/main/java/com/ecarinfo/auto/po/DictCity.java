package com.ecarinfo.auto.po;
import java.io.Serializable;

public class DictCity implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String name;
	private Integer proId;
	private Integer areaId;
	private String keyTitle;
	private Integer status = 1;//是否有效，1为有效，0未无效
	private String noInfo;

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

    public Integer getProId () {
        return proId;
    }

    public void setProId (Integer proId) {
        this.proId = proId;
    }

    public Integer getAreaId () {
        return areaId;
    }

    public void setAreaId (Integer areaId) {
        this.areaId = areaId;
    }

    public String getKeyTitle () {
        return keyTitle;
    }

    public void setKeyTitle (String keyTitle) {
        this.keyTitle = keyTitle;
    }

    public Integer getStatus () {
        return status;
    }

    public void setStatus (Integer status) {
        this.status = status;
    }

    public String getNoInfo () {
        return noInfo;
    }

    public void setNoInfo (String noInfo) {
        this.noInfo = noInfo;
    }
}