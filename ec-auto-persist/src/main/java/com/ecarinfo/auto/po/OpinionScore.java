package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class OpinionScore implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private Integer oid;
	private Integer facade;//外观
	private Integer space;//空间
	private Integer power;//动力
	private Integer control;//操控性
	private Integer oli;//油耗
	private Integer comfort;//舒适度
	private Integer upholstery;//内饰
	private Integer price;//性价比
	private Date ctime;//插入时间

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

    public Integer getFacade () {
        return facade;
    }

    public void setFacade (Integer facade) {
        this.facade = facade;
    }

    public Integer getSpace () {
        return space;
    }

    public void setSpace (Integer space) {
        this.space = space;
    }

    public Integer getPower () {
        return power;
    }

    public void setPower (Integer power) {
        this.power = power;
    }

    public Integer getControl () {
        return control;
    }

    public void setControl (Integer control) {
        this.control = control;
    }

    public Integer getOli () {
        return oli;
    }

    public void setOli (Integer oli) {
        this.oli = oli;
    }

    public Integer getComfort () {
        return comfort;
    }

    public void setComfort (Integer comfort) {
        this.comfort = comfort;
    }

    public Integer getUpholstery () {
        return upholstery;
    }

    public void setUpholstery (Integer upholstery) {
        this.upholstery = upholstery;
    }

    public Integer getPrice () {
        return price;
    }

    public void setPrice (Integer price) {
        this.price = price;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }
}