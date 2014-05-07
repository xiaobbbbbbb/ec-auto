package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class EventThreadsRef implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;//事件论坛ref表
	private String site;
	private Date pubtime;
	private Integer reliable;
	private Integer alarmLevel;
	private Integer cjGenus;
	private Integer fxGenus;
	private Integer isPic;
	private Integer cusScore;
	private Integer cmtCount;
	private String summary;
	private String alarmKeyword;
	private Integer supportLevel;
	private Integer eid;
	private Integer refLevel;
	private String title;
	private String sentence;
	private Date pubdate;
	private Integer idxId;//索引使用的自增字段
	private String content;
	private String author;
	private String url;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getSite () {
        return site;
    }

    public void setSite (String site) {
        this.site = site;
    }

    public Date getPubtime () {
        return pubtime;
    }

    public void setPubtime (Date pubtime) {
        this.pubtime = pubtime;
    }

    public Integer getReliable () {
        return reliable;
    }

    public void setReliable (Integer reliable) {
        this.reliable = reliable;
    }

    public Integer getAlarmLevel () {
        return alarmLevel;
    }

    public void setAlarmLevel (Integer alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public Integer getCjGenus () {
        return cjGenus;
    }

    public void setCjGenus (Integer cjGenus) {
        this.cjGenus = cjGenus;
    }

    public Integer getFxGenus () {
        return fxGenus;
    }

    public void setFxGenus (Integer fxGenus) {
        this.fxGenus = fxGenus;
    }

    public Integer getIsPic () {
        return isPic;
    }

    public void setIsPic (Integer isPic) {
        this.isPic = isPic;
    }

    public Integer getCusScore () {
        return cusScore;
    }

    public void setCusScore (Integer cusScore) {
        this.cusScore = cusScore;
    }

    public Integer getCmtCount () {
        return cmtCount;
    }

    public void setCmtCount (Integer cmtCount) {
        this.cmtCount = cmtCount;
    }

    public String getSummary () {
        return summary;
    }

    public void setSummary (String summary) {
        this.summary = summary;
    }

    public String getAlarmKeyword () {
        return alarmKeyword;
    }

    public void setAlarmKeyword (String alarmKeyword) {
        this.alarmKeyword = alarmKeyword;
    }

    public Integer getSupportLevel () {
        return supportLevel;
    }

    public void setSupportLevel (Integer supportLevel) {
        this.supportLevel = supportLevel;
    }

    public Integer getEid () {
        return eid;
    }

    public void setEid (Integer eid) {
        this.eid = eid;
    }

    public Integer getRefLevel () {
        return refLevel;
    }

    public void setRefLevel (Integer refLevel) {
        this.refLevel = refLevel;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getSentence () {
        return sentence;
    }

    public void setSentence (String sentence) {
        this.sentence = sentence;
    }

    public Date getPubdate () {
        return pubdate;
    }

    public void setPubdate (Date pubdate) {
        this.pubdate = pubdate;
    }

    public Integer getIdxId () {
        return idxId;
    }

    public void setIdxId (Integer idxId) {
        this.idxId = idxId;
    }

    public String getContent () {
        return content;
    }

    public void setContent (String content) {
        this.content = content;
    }

    public String getAuthor () {
        return author;
    }

    public void setAuthor (String author) {
        this.author = author;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }
}