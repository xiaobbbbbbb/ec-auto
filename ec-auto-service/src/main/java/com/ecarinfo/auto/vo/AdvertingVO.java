package com.ecarinfo.auto.vo;

import java.io.Serializable;
import java.util.List;

import com.ecarinfo.auto.po.Advertising;

/**
 * @author jamie
 *
 */
public class AdvertingVO extends Advertising implements Serializable{
	private static final long serialVersionUID = 1L;
	List<AdvertingDetailVO> detail;
	public List<AdvertingDetailVO> getDetail() {
		return detail;
	}
	public void setDetail(List<AdvertingDetailVO> detail) {
		this.detail = detail;
	}
	
}
