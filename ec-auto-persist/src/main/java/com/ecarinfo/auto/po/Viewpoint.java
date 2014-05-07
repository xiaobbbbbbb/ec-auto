package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class Viewpoint implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String name;//观点名称
	private Boolean isManual = false;//0: 系统自动 1:手工添加
	private Integer affection = 0;//情感说明 3:正面 2:中性 1:负面
	private Boolean isValid = true;//1:有效 0:无效
	private Date ctime;
	private Integer status = 0;//状态
	private Integer viewpointTypeId;//观点分类ID

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

    public Boolean getIsManual () {
        return isManual;
    }

    public void setIsManual (Boolean isManual) {
        this.isManual = isManual;
    }

    public Integer getAffection () {
        return affection;
    }

    public void setAffection (Integer affection) {
        this.affection = affection;
    }

    public Boolean getIsValid () {
        return isValid;
    }

    public void setIsValid (Boolean isValid) {
        this.isValid = isValid;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }

    public Integer getStatus () {
        return status;
    }

    public void setStatus (Integer status) {
        this.status = status;
    }

    public Integer getViewpointTypeId () {
        return viewpointTypeId;
    }

    public void setViewpointTypeId (Integer viewpointTypeId) {
        this.viewpointTypeId = viewpointTypeId;
    }
}