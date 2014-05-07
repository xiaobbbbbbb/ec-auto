package com.ecarinfo.auto.po;
import java.io.Serializable;

public class DictArea implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String name;//区域名称
	private String orderNum;//排序ID

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

    public String getOrderNum () {
        return orderNum;
    }

    public void setOrderNum (String orderNum) {
        this.orderNum = orderNum;
    }
}