package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class OpinionClassify implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;//ID
	private Integer oid;
	private String satisfaction;//满意
	private String negative;//不满意
	private String facade;//外观
	private String space;//空间
	private String power;//动力
	private String control;//操控性
	private String oli;//油耗
	private String comfort;//舒适度
	private String upholstery;//内饰
	private String price;//性价比
	private Date ctime;//插入时间
	private String overall;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getOid () {
        return oid;
    }

    public void setOid (Integer oid) {
        this.oid = oid;
    }

    public String getSatisfaction () {
        return satisfaction;
    }

    public void setSatisfaction (String satisfaction) {
        this.satisfaction = satisfaction;
    }

    public String getNegative () {
        return negative;
    }

    public void setNegative (String negative) {
        this.negative = negative;
    }

    public String getFacade () {
        return facade;
    }

    public void setFacade (String facade) {
        this.facade = facade;
    }

    public String getSpace () {
        return space;
    }

    public void setSpace (String space) {
        this.space = space;
    }

    public String getPower () {
        return power;
    }

    public void setPower (String power) {
        this.power = power;
    }

    public String getControl () {
        return control;
    }

    public void setControl (String control) {
        this.control = control;
    }

    public String getOli () {
        return oli;
    }

    public void setOli (String oli) {
        this.oli = oli;
    }

    public String getComfort () {
        return comfort;
    }

    public void setComfort (String comfort) {
        this.comfort = comfort;
    }

    public String getUpholstery () {
        return upholstery;
    }

    public void setUpholstery (String upholstery) {
        this.upholstery = upholstery;
    }

    public String getPrice () {
        return price;
    }

    public void setPrice (String price) {
        this.price = price;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }

    public String getOverall () {
        return overall;
    }

    public void setOverall (String overall) {
        this.overall = overall;
    }
}