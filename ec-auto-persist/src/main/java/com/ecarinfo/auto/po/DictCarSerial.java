package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class DictCarSerial implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;//ID
	private String name;//名称
	private Integer brandId;//厂牌
	private String brandName;
	private Boolean isValid = false;//鏄惁鏈夋晥
	private Integer orderNo;//排序ID
	private Boolean isFollow = true;//是否关注
	private Integer editUid;//编辑人
	private Date editDatetime;//编辑时间

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

    public Integer getBrandId () {
        return brandId;
    }

    public void setBrandId (Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName () {
        return brandName;
    }

    public void setBrandName (String brandName) {
        this.brandName = brandName;
    }

    public Boolean getIsValid () {
        return isValid;
    }

    public void setIsValid (Boolean isValid) {
        this.isValid = isValid;
    }

    public Integer getOrderNo () {
        return orderNo;
    }

    public void setOrderNo (Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Boolean getIsFollow () {
        return isFollow;
    }

    public void setIsFollow (Boolean isFollow) {
        this.isFollow = isFollow;
    }

    public Integer getEditUid () {
        return editUid;
    }

    public void setEditUid (Integer editUid) {
        this.editUid = editUid;
    }

    public Date getEditDatetime () {
        return editDatetime;
    }

    public void setEditDatetime (Date editDatetime) {
        this.editDatetime = editDatetime;
    }
}