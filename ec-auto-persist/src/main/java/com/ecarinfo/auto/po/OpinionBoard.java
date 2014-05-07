package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class OpinionBoard implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer id;
	private String name;
	private Integer status;
	private String host;
	private String hostMD5;
	private String url;
	private String urlMD5;
	private Date ctime;
	private Date updatetime;

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

    public Integer getStatus () {
        return status;
    }

    public void setStatus (Integer status) {
        this.status = status;
    }

    public String getHost () {
        return host;
    }

    public void setHost (String host) {
        this.host = host;
    }

    public String getHostMD5 () {
        return hostMD5;
    }

    public void setHostMD5 (String hostMD5) {
        this.hostMD5 = hostMD5;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public String getUrlMD5 () {
        return urlMD5;
    }

    public void setUrlMD5 (String urlMD5) {
        this.urlMD5 = urlMD5;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }

    public Date getUpdatetime () {
        return updatetime;
    }

    public void setUpdatetime (Date updatetime) {
        this.updatetime = updatetime;
    }
}