package com.ecarinfo.auto.vo;

import java.io.Serializable;
import java.util.Date;

import com.ecarinfo.auto.po.DictCarBrand;
import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;

/**
 * @author yinql
 *
 */
public class CarbrandVO extends DictCarBrand implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String editName;
	private Date editTime;
	public String getEditName() {
		return editName;
	}
	public void setEditName(String editName) {
		this.editName = editName;
	}
	public Date getEditTime() {
		return editTime;
	}
	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}
	public String getEtime() {
		return DateUtils.dateToString(editTime, TimeFormatter.FORMATTER1);
	}
	
}
