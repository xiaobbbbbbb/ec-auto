package com.ecarinfo.auto.vo;

public class ViewpointCountVO {
	private long allCounts ;//所有
	private long goodCounts;//好
	private long middleCounts;//中
	private long badCounts;//差
	public long getAllCounts() {
		return allCounts;
	}
	public void setAllCounts(long allCounts) {
		this.allCounts = allCounts;
	}
	public long getGoodCounts() {
		return goodCounts;
	}
	public void setGoodCounts(long goodCounts) {
		this.goodCounts = goodCounts;
	}
	public long getMiddleCounts() {
		return middleCounts;
	}
	public void setMiddleCounts(long middleCounts) {
		this.middleCounts = middleCounts;
	}
	public long getBadCounts() {
		return badCounts;
	}
	public void setBadCounts(long badCounts) {
		this.badCounts = badCounts;
	}
	@Override
	public String toString() {
		return "ViewpointCountVO [allCounts=" + allCounts + ", goodCounts="
				+ goodCounts + ", middleCounts=" + middleCounts
				+ ", badCounts=" + badCounts + "]";
	}

}
