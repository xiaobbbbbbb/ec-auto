package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class WeiboAnalysis implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String name;//微博名称
	private String url;
	private Integer mediaId;//微博类型
	private Integer fansCount;//粉丝数
	private Integer aboutArticleCount;//相关文章数
	private Double avgTransmitCount;//文章平均转发数
	private Double avgCommentCount;//平均评论数
	private Integer provinceId;//省份
	private Integer cityId;//城市
	private Date ctime;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public Integer getMediaId () {
        return mediaId;
    }

    public void setMediaId (Integer mediaId) {
        this.mediaId = mediaId;
    }

    public Integer getFansCount () {
        return fansCount;
    }

    public void setFansCount (Integer fansCount) {
        this.fansCount = fansCount;
    }

    public Integer getAboutArticleCount () {
        return aboutArticleCount;
    }

    public void setAboutArticleCount (Integer aboutArticleCount) {
        this.aboutArticleCount = aboutArticleCount;
    }

    public Double getAvgTransmitCount () {
        return avgTransmitCount;
    }

    public void setAvgTransmitCount (Double avgTransmitCount) {
        this.avgTransmitCount = avgTransmitCount;
    }

    public Double getAvgCommentCount () {
        return avgCommentCount;
    }

    public void setAvgCommentCount (Double avgCommentCount) {
        this.avgCommentCount = avgCommentCount;
    }

    public Integer getProvinceId () {
        return provinceId;
    }

    public void setProvinceId (Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId () {
        return cityId;
    }

    public void setCityId (Integer cityId) {
        this.cityId = cityId;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }
}