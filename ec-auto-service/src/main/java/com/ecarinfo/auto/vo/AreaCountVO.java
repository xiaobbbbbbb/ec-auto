package com.ecarinfo.auto.vo;

import java.io.Serializable;

/**
 * @author jamie
 *
 */
public class AreaCountVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String provinceName;
	private String cityName;
	private Integer counts;
	
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public Integer getCounts() {
		return counts;
	}
	public void setCounts(Integer counts) {
		this.counts = counts;
	}
	@Override
	public String toString() {
		return "AreaCountVO [provinceName=" + provinceName + ", cityName=" + cityName
				+ ", counts=" + counts + "]";
	}
	
}
