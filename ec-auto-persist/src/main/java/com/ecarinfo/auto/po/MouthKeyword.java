package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class MouthKeyword implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String keyword;
	private Integer nums;//观点的数量
	private Integer brandId;//厂牌Id
	private Integer serialId;//车系ID
	private Integer affection;//情感 1好 2中 3差
	private Date ctime;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getKeyword () {
        return keyword;
    }

    public void setKeyword (String keyword) {
        this.keyword = keyword;
    }

    public Integer getNums () {
        return nums;
    }

    public void setNums (Integer nums) {
        this.nums = nums;
    }

    public Integer getBrandId () {
        return brandId;
    }

    public void setBrandId (Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getSerialId () {
        return serialId;
    }

    public void setSerialId (Integer serialId) {
        this.serialId = serialId;
    }

    public Integer getAffection () {
        return affection;
    }

    public void setAffection (Integer affection) {
        this.affection = affection;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }
}