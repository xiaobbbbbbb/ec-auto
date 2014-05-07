package com.ecarinfo.auto.vo;

import java.io.Serializable;
import java.util.Date;

public class StatisticsVO implements Serializable {
	
	private static final long serialVersionUID = -7536345161726908354L;
	
	public Integer id;
	public String name;
	public Integer num;
	public Date date;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}	
}
