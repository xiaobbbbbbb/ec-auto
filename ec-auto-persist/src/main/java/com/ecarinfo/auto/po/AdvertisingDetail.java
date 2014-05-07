package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class AdvertisingDetail implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private Integer pid;//广告父类ID 关联Advertising
	private String title;//标题
	private String content;//广告内容
	private Integer mediaId;//媒体ID
	private String url;//投放地址
	private Integer pageId;//投放版面
	private Date planStime;//计划投放开始时间
	private Date planEtime;//计划投放结束时间
	private Date realStime;//实际投放开始时间
	private Date realEtime;//时间投放结束时间
	private Integer status;//投放状态 1.未投放，2已投放，3投放完成
	private String realUrl;//原文链接
	private Integer transmitNum = 0;//转发数量
	private Integer commentNum = 0;//评论数量
	private Boolean isStandard = true;//是否达标
	private Date ctime;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getPid () {
        return pid;
    }

    public void setPid (Integer pid) {
        this.pid = pid;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getContent () {
        return content;
    }

    public void setContent (String content) {
        this.content = content;
    }

    public Integer getMediaId () {
        return mediaId;
    }

    public void setMediaId (Integer mediaId) {
        this.mediaId = mediaId;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public Integer getPageId () {
        return pageId;
    }

    public void setPageId (Integer pageId) {
        this.pageId = pageId;
    }

    public Date getPlanStime () {
        return planStime;
    }

    public void setPlanStime (Date planStime) {
        this.planStime = planStime;
    }

    public Date getPlanEtime () {
        return planEtime;
    }

    public void setPlanEtime (Date planEtime) {
        this.planEtime = planEtime;
    }

    public Date getRealStime () {
        return realStime;
    }

    public void setRealStime (Date realStime) {
        this.realStime = realStime;
    }

    public Date getRealEtime () {
        return realEtime;
    }

    public void setRealEtime (Date realEtime) {
        this.realEtime = realEtime;
    }

    public Integer getStatus () {
        return status;
    }

    public void setStatus (Integer status) {
        this.status = status;
    }

    public String getRealUrl () {
        return realUrl;
    }

    public void setRealUrl (String realUrl) {
        this.realUrl = realUrl;
    }

    public Integer getTransmitNum () {
        return transmitNum;
    }

    public void setTransmitNum (Integer transmitNum) {
        this.transmitNum = transmitNum;
    }

    public Integer getCommentNum () {
        return commentNum;
    }

    public void setCommentNum (Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Boolean getIsStandard () {
        return isStandard;
    }

    public void setIsStandard (Boolean isStandard) {
        this.isStandard = isStandard;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }
}