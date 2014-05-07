package com.ecarinfo.auto.vo;

import com.ecarinfo.auto.po.Article;

public class ReviewVo extends Article {	
	private static final long serialVersionUID = -8230932976496416198L;
	private String name;//观点名称
	private Integer reviewAffection;//情感说明 1:正面 0:中性 -1：负面
	private Integer commentCout ;//评论数量
	private Float percent;//观点占比
	
	public Float getPercent() {
		return percent;
	}
	public void setPercent(Float percent) {
		this.percent = percent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getReviewAffection() {
		return reviewAffection;
	}
	public void setReviewAffection(Integer reviewAffection) {
		this.reviewAffection = reviewAffection;
	}
	public Integer getCommentCout() {
		return commentCout;
	}
	public void setCommentCout(Integer commentCout) {
		this.commentCout = commentCout;
	}
}
