package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class DictCarBrand implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;//ID
	private String name;//名称
	private String letter;//字母
	private String img;//图片
	private Integer isValid = 0;//是否有效（1：有效）
	private Integer orderNo;//排序ID
	private Integer type = 0;//类型（1自有品牌/2竞品）
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

    public String getLetter () {
        return letter;
    }

    public void setLetter (String letter) {
        this.letter = letter;
    }

    public String getImg () {
        return img;
    }

    public void setImg (String img) {
        this.img = img;
    }

    public Integer getIsValid () {
        return isValid;
    }

    public void setIsValid (Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getOrderNo () {
        return orderNo;
    }

    public void setOrderNo (Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getType () {
        return type;
    }

    public void setType (Integer type) {
        this.type = type;
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