package com.ecarinfo.auto.po;
import java.io.Serializable;

public class DictProvince implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String name;//省
	private Integer areaId;//鍖哄煙ID
	private String areaName;//区域名称
	private String simpleName;//省简写
	private Boolean isValid = true;//0:无效 1:有效

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

    public Integer getAreaId () {
        return areaId;
    }

    public void setAreaId (Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName () {
        return areaName;
    }

    public void setAreaName (String areaName) {
        this.areaName = areaName;
    }

    public String getSimpleName () {
        return simpleName;
    }

    public void setSimpleName (String simpleName) {
        this.simpleName = simpleName;
    }

    public Boolean getIsValid () {
        return isValid;
    }

    public void setIsValid (Boolean isValid) {
        this.isValid = isValid;
    }
}