package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class NetworkMediaAnalysis implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String name;//名称
	private String url;//url
	private Integer synthesisRanking;//综合排名
	private Integer visitCount;//访问量
	private Integer pageCountRanking;//页面访问量
	private Double avgBrowseCount;//平均每用户浏览页面数
	private Date ctime;//创建时间

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

    public Integer getSynthesisRanking () {
        return synthesisRanking;
    }

    public void setSynthesisRanking (Integer synthesisRanking) {
        this.synthesisRanking = synthesisRanking;
    }

    public Integer getVisitCount () {
        return visitCount;
    }

    public void setVisitCount (Integer visitCount) {
        this.visitCount = visitCount;
    }

    public Integer getPageCountRanking () {
        return pageCountRanking;
    }

    public void setPageCountRanking (Integer pageCountRanking) {
        this.pageCountRanking = pageCountRanking;
    }

    public Double getAvgBrowseCount () {
        return avgBrowseCount;
    }

    public void setAvgBrowseCount (Double avgBrowseCount) {
        this.avgBrowseCount = avgBrowseCount;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }
}