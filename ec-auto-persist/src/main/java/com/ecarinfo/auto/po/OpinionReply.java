package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class OpinionReply implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private Integer opid;
	private String content;
	private Date pubtime;
	private Date ctime;
	private String replyMD5;
	private String author;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getOpid () {
        return opid;
    }

    public void setOpid (Integer opid) {
        this.opid = opid;
    }

    public String getContent () {
        return content;
    }

    public void setContent (String content) {
        this.content = content;
    }

    public Date getPubtime () {
        return pubtime;
    }

    public void setPubtime (Date pubtime) {
        this.pubtime = pubtime;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }

    public String getReplyMD5 () {
        return replyMD5;
    }

    public void setReplyMD5 (String replyMD5) {
        this.replyMD5 = replyMD5;
    }

    public String getAuthor () {
        return author;
    }

    public void setAuthor (String author) {
        this.author = author;
    }
}