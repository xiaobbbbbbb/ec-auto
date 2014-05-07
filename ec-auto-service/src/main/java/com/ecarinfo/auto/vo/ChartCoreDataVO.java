package com.ecarinfo.auto.vo;

import java.io.Serializable;

/**
 * HighChart 基础数据
 * @author jamie
 *
 */
public class ChartCoreDataVO implements Serializable{
	private static final long serialVersionUID = -535327354010008634L;
	
	public Object name;
	public Integer value;
	
	public Object getName() {
		return name;
	}
	public void setName(Object name) {
		this.name = name;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "ChartCoreDataVO [name=" + name + ", value=" + value + "]";
	}
	
}
