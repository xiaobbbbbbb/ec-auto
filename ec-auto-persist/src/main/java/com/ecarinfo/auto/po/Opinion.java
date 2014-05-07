package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class Opinion implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;//口碑数据id
	private String title;//口碑数据-标题
	private String content;//口碑数据-评价内容
	private Integer score;
	private Integer bid;
	private String site;
	private String authorInfo;//作者地址
	private String author;//口碑数据-作者
	private Integer replycount = 0;//评论数
	private String url;//口碑数据-采集URL
	private String urlmd5;//URL-MD5
	private Date pubtime;//发布时间
	private Date intime;//插入时间

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
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

    public Integer getScore () {
        return score;
    }

    public void setScore (Integer score) {
        this.score = score;
    }

    public Integer getBid () {
        return bid;
    }

    public void setBid (Integer bid) {
        this.bid = bid;
    }

    public String getSite () {
        return site;
    }

    public void setSite (String site) {
        this.site = site;
    }

    public String getAuthorInfo () {
        return authorInfo;
    }

    public void setAuthorInfo (String authorInfo) {
        this.authorInfo = authorInfo;
    }

    public String getAuthor () {
        return author;
    }

    public void setAuthor (String author) {
        this.author = author;
    }

    public Integer getReplycount () {
        return replycount;
    }

    public void setReplycount (Integer replycount) {
        this.replycount = replycount;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public String getUrlmd5 () {
        return urlmd5;
    }

    public void setUrlmd5 (String urlmd5) {
        this.urlmd5 = urlmd5;
    }

    public Date getPubtime () {
        return pubtime;
    }

    public void setPubtime (Date pubtime) {
        this.pubtime = pubtime;
    }

    public Date getIntime () {
        return intime;
    }

    public void setIntime (Date intime) {
        this.intime = intime;
    }
}