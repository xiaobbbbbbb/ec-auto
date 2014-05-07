package com.ecarinfo.auto.vo;

import java.io.Serializable;
import java.util.List;

public class PositiveVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private long allNums;//所有负面新闻数量
	private long lowNums;//一般
	private long highNums;//严重
	private long chanping;//产品
	private long fuwu;//服务
	private long qita;//其它
	private List<ChartCoreDataVO> list;
	public long getAllNums() {
		return allNums;
	}
	public void setAllNums(long allNums) {
		this.allNums = allNums;
	}
	public long getLowNums() {
		return lowNums;
	}
	public void setLowNums(long lowNums) {
		this.lowNums = lowNums;
	}
	public long getHighNums() {
		return highNums;
	}
	public void setHighNums(long highNums) {
		this.highNums = highNums;
	}
	public List<ChartCoreDataVO> getList() {
		return list;
	}
	public void setList(List<ChartCoreDataVO> list) {
		this.list = list;
	}
	public long getChanping() {
		return chanping;
	}
	public void setChanping(long chanping) {
		this.chanping = chanping;
	}
	public long getFuwu() {
		return fuwu;
	}
	public void setFuwu(long fuwu) {
		this.fuwu = fuwu;
	}
	public long getQita() {
		return qita;
	}
	public void setQita(long qita) {
		this.qita = qita;
	}
	
}
