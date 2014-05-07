package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private String title;//标题
	private Integer affection = 0;//情感说明 3:正面 2:中性 1：负面'
	private String simpleContent;//摘要内容
	private Integer grade = 0;//内容等级 1一般 2严重
	private String url;//原网页地址
	private Integer brandId;//品牌id
	private Integer serialId;//产品id
	private Integer desTarget = 0;//描述对象（1主机厂，2:4s店，3：其它）
	private Integer desContent = 01;//描述内容（1产品、3服务、4其它）
	private Boolean hasComment = false;//是否有评论（1有，0无）
	private Boolean hasViewpoint = false;//是否有观点
	private Integer mediaType = 0;//传媒分类(1论坛,2新闻,3博客,4,微博，0其它）
	private Integer mediaId;//站点ID
	private Integer cityId;//城市ID
	private Integer provinceId;//省份ID
	private Integer areaId;//区域id
	private Date articleCtime;//文章发布时间
	private Date articleDate;//文章创建时间
	private Date ctime;
	private Integer status = 0;
	private Long originalId;
	private Integer questionId = 0;
	private Integer eventId = 0;//事件ID

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public Integer getAffection () {
        return affection;
    }

    public void setAffection (Integer affection) {
        this.affection = affection;
    }

    public String getSimpleContent () {
        return simpleContent;
    }

    public void setSimpleContent (String simpleContent) {
        this.simpleContent = simpleContent;
    }

    public Integer getGrade () {
        return grade;
    }

    public void setGrade (Integer grade) {
        this.grade = grade;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public Integer getBrandId () {
        return brandId;
    }

    public void setBrandId (Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getSerialId () {
        return serialId;
    }

    public void setSerialId (Integer serialId) {
        this.serialId = serialId;
    }

    public Integer getDesTarget () {
        return desTarget;
    }

    public void setDesTarget (Integer desTarget) {
        this.desTarget = desTarget;
    }

    public Integer getDesContent () {
        return desContent;
    }

    public void setDesContent (Integer desContent) {
        this.desContent = desContent;
    }

    public Boolean getHasComment () {
        return hasComment;
    }

    public void setHasComment (Boolean hasComment) {
        this.hasComment = hasComment;
    }

    public Boolean getHasViewpoint () {
        return hasViewpoint;
    }

    public void setHasViewpoint (Boolean hasViewpoint) {
        this.hasViewpoint = hasViewpoint;
    }

    public Integer getMediaType () {
        return mediaType;
    }

    public void setMediaType (Integer mediaType) {
        this.mediaType = mediaType;
    }

    public Integer getMediaId () {
        return mediaId;
    }

    public void setMediaId (Integer mediaId) {
        this.mediaId = mediaId;
    }

    public Integer getCityId () {
        return cityId;
    }

    public void setCityId (Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getProvinceId () {
        return provinceId;
    }

    public void setProvinceId (Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getAreaId () {
        return areaId;
    }

    public void setAreaId (Integer areaId) {
        this.areaId = areaId;
    }

    public Date getArticleCtime () {
        return articleCtime;
    }

    public void setArticleCtime (Date articleCtime) {
        this.articleCtime = articleCtime;
    }

    public Date getArticleDate () {
        return articleDate;
    }

    public void setArticleDate (Date articleDate) {
        this.articleDate = articleDate;
    }

    public Date getCtime () {
        return ctime;
    }

    public void setCtime (Date ctime) {
        this.ctime = ctime;
    }

    public Integer getStatus () {
        return status;
    }

    public void setStatus (Integer status) {
        this.status = status;
    }

    public Long getOriginalId () {
        return originalId;
    }

    public void setOriginalId (Long originalId) {
        this.originalId = originalId;
    }

    public Integer getQuestionId () {
        return questionId;
    }

    public void setQuestionId (Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getEventId () {
        return eventId;
    }

    public void setEventId (Integer eventId) {
        this.eventId = eventId;
    }
}