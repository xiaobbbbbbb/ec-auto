package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private String titile;//主题
	private String keywords;
	private Integer type;//类型，1.社会热点，2行业事件，3我的品牌事件，4其它品牌事件
	private Date startTime;//起始时间
	private Date endTime;//结束时间
	private String contents;// 事件描述
	private Boolean status = true;//事件开关，0关闭，1开启
	private String imgUrl;//图片地址
	private Long firstArticleId;
	private Date firstArticleTime;//第一次报道
	private Date ctime;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getTitile () {
        return titile;
    }

    public void setTitile (String titile) {
        this.titile = titile;
    }

    public String getKeywords () {
        return keywords;
    }

    public void setKeywords (String keywords) {
        this.keywords = keywords;
    }

    public Integer getType () {
        return type;
    }

    public void setType (Integer type) {
        this.type = type;
    }

    public Date getStartTime () {
        return startTime;
    }

    public void setStartTime (Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime () {
        return endTime;
    }

    public void setEndTime (Date endTime) {
        this.endTime = endTime;
    }

    public String getContents () {
        return contents;
    }

    public void setContents (String contents) {
        this.contents = contents;
    }

    public Boolean getStatus () {
        return status;
    }

    public void setStatus (Boolean status) {
        this.status = status;
    }

    public String getImgUrl () {
        return imgUrl;
    }

    public void setImgUrl (String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getFirstArticleId () {
        return firstArticleId;
    }

    public void setFirstArticleId (Long firstArticleId) {
        this.firstArticleId = firstArticleId;
    }

    public Date getFirstArticleTime () {
        return firstArticleTime;
    }

    public void setFirstArticleTime (Date firstArticleTime) {
        this.firstArticleTime = firstArticleTime;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }
}