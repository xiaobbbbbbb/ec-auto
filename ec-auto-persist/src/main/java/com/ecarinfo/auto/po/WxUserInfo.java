package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class WxUserInfo implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;//ID
	private Integer userId;//userId
	private String openId;//openId
	private Integer subscribe;//订阅状态
	private String nickname;//昵称
	private Integer sex;//性别
	private String language;//语言
	private String city;
	private String province;
	private String country;
	private Date subscribeTime;//添加时间
	private Date unsubscribeTime;//添加用户
	private String headImageUrl;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public Integer getUserId () {
        return userId;
    }

    public void setUserId (Integer userId) {
        this.userId = userId;
    }

    public String getOpenId () {
        return openId;
    }

    public void setOpenId (String openId) {
        this.openId = openId;
    }

    public Integer getSubscribe () {
        return subscribe;
    }

    public void setSubscribe (Integer subscribe) {
        this.subscribe = subscribe;
    }

    public String getNickname () {
        return nickname;
    }

    public void setNickname (String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex () {
        return sex;
    }

    public void setSex (Integer sex) {
        this.sex = sex;
    }

    public String getLanguage () {
        return language;
    }

    public void setLanguage (String language) {
        this.language = language;
    }

    public String getCity () {
        return city;
    }

    public void setCity (String city) {
        this.city = city;
    }

    public String getProvince () {
        return province;
    }

    public void setProvince (String province) {
        this.province = province;
    }

    public String getCountry () {
        return country;
    }

    public void setCountry (String country) {
        this.country = country;
    }

    public Date getSubscribeTime () {
        return subscribeTime;
    }

    public void setSubscribeTime (Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public Date getUnsubscribeTime () {
        return unsubscribeTime;
    }

    public void setUnsubscribeTime (Date unsubscribeTime) {
        this.unsubscribeTime = unsubscribeTime;
    }

    public String getHeadImageUrl () {
        return headImageUrl;
    }

    public void setHeadImageUrl (String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }
}