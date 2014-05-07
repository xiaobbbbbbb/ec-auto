package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class MouthAnalyzing implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Long id;
	private Long optionId;//原文对应id
	private String title;
	private String content;//内容
	private String url;
	private Integer brandId = 0;//厂牌ID
	private Integer serialId = 0;//车系id
	private Integer provinceId = 0;//省份id
	private Integer cityId = 0;//城市id
	private Integer mediaId = 0;//媒体Id
	private Integer type;//文章分类，综合，论坛，微博等。
	private Integer affection = 2;//综合情感 1好2中3差
	private Integer points = 0;//综合评价
	private Boolean appearance = false;//外观1有，0无
	private Integer appearanceAffection;//情感，1好，2中，3差
	private Boolean interior = false;//内饰 1有 0无
	private Integer interiorAffection;
	private Boolean space = false;//空间
	private Integer spaceAffection;
	private Boolean operate = false;//操控
	private Integer operateAffection;
	private Boolean power = false;//动力
	private Integer powerAffection;
	private Boolean oil = false;
	private Integer oilAffection;
	private Boolean comfort = false;//舒适性
	private Integer comfortAffection;
	private Boolean configure = false;//配置
	private Integer configureAffection;
	private Boolean price = false;//价格
	private Integer priceAffection;
	private Boolean quality = false;
	private Integer qualityAffection;
	private Boolean cost = false;//性价比
	private Integer costAffection;
	private Boolean maintenance = false;//维修保养
	private Integer maintenanceAffection;//维修保养
	private Boolean attitude = false;//服务态度
	private Integer attitudeAffection;
	private Boolean facility = false;//配套设施
	private Integer facilityAffection;
	private Boolean stafflevel = false;//人员水平
	private Integer stafflevelAffection;
	private Date pubTime;//发布时间

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public Long getOptionId () {
        return optionId;
    }

    public void setOptionId (Long optionId) {
        this.optionId = optionId;
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

    public Integer getMediaId () {
        return mediaId;
    }

    public void setMediaId (Integer mediaId) {
        this.mediaId = mediaId;
    }

    public Integer getType () {
        return type;
    }

    public void setType (Integer type) {
        this.type = type;
    }

    public Integer getAffection () {
        return affection;
    }

    public void setAffection (Integer affection) {
        this.affection = affection;
    }

    public Integer getPoints () {
        return points;
    }

    public void setPoints (Integer points) {
        this.points = points;
    }

    public Boolean getAppearance () {
        return appearance;
    }

    public void setAppearance (Boolean appearance) {
        this.appearance = appearance;
    }

    public Integer getAppearanceAffection () {
        return appearanceAffection;
    }

    public void setAppearanceAffection (Integer appearanceAffection) {
        this.appearanceAffection = appearanceAffection;
    }

    public Boolean getInterior () {
        return interior;
    }

    public void setInterior (Boolean interior) {
        this.interior = interior;
    }

    public Integer getInteriorAffection () {
        return interiorAffection;
    }

    public void setInteriorAffection (Integer interiorAffection) {
        this.interiorAffection = interiorAffection;
    }

    public Boolean getSpace () {
        return space;
    }

    public void setSpace (Boolean space) {
        this.space = space;
    }

    public Integer getSpaceAffection () {
        return spaceAffection;
    }

    public void setSpaceAffection (Integer spaceAffection) {
        this.spaceAffection = spaceAffection;
    }

    public Boolean getOperate () {
        return operate;
    }

    public void setOperate (Boolean operate) {
        this.operate = operate;
    }

    public Integer getOperateAffection () {
        return operateAffection;
    }

    public void setOperateAffection (Integer operateAffection) {
        this.operateAffection = operateAffection;
    }

    public Boolean getPower () {
        return power;
    }

    public void setPower (Boolean power) {
        this.power = power;
    }

    public Integer getPowerAffection () {
        return powerAffection;
    }

    public void setPowerAffection (Integer powerAffection) {
        this.powerAffection = powerAffection;
    }

    public Boolean getOil () {
        return oil;
    }

    public void setOil (Boolean oil) {
        this.oil = oil;
    }

    public Integer getOilAffection () {
        return oilAffection;
    }

    public void setOilAffection (Integer oilAffection) {
        this.oilAffection = oilAffection;
    }

    public Boolean getComfort () {
        return comfort;
    }

    public void setComfort (Boolean comfort) {
        this.comfort = comfort;
    }

    public Integer getComfortAffection () {
        return comfortAffection;
    }

    public void setComfortAffection (Integer comfortAffection) {
        this.comfortAffection = comfortAffection;
    }

    public Boolean getConfigure () {
        return configure;
    }

    public void setConfigure (Boolean configure) {
        this.configure = configure;
    }

    public Integer getConfigureAffection () {
        return configureAffection;
    }

    public void setConfigureAffection (Integer configureAffection) {
        this.configureAffection = configureAffection;
    }

    public Boolean getPrice () {
        return price;
    }

    public void setPrice (Boolean price) {
        this.price = price;
    }

    public Integer getPriceAffection () {
        return priceAffection;
    }

    public void setPriceAffection (Integer priceAffection) {
        this.priceAffection = priceAffection;
    }

    public Boolean getQuality () {
        return quality;
    }

    public void setQuality (Boolean quality) {
        this.quality = quality;
    }

    public Integer getQualityAffection () {
        return qualityAffection;
    }

    public void setQualityAffection (Integer qualityAffection) {
        this.qualityAffection = qualityAffection;
    }

    public Boolean getCost () {
        return cost;
    }

    public void setCost (Boolean cost) {
        this.cost = cost;
    }

    public Integer getCostAffection () {
        return costAffection;
    }

    public void setCostAffection (Integer costAffection) {
        this.costAffection = costAffection;
    }

    public Boolean getMaintenance () {
        return maintenance;
    }

    public void setMaintenance (Boolean maintenance) {
        this.maintenance = maintenance;
    }

    public Integer getMaintenanceAffection () {
        return maintenanceAffection;
    }

    public void setMaintenanceAffection (Integer maintenanceAffection) {
        this.maintenanceAffection = maintenanceAffection;
    }

    public Boolean getAttitude () {
        return attitude;
    }

    public void setAttitude (Boolean attitude) {
        this.attitude = attitude;
    }

    public Integer getAttitudeAffection () {
        return attitudeAffection;
    }

    public void setAttitudeAffection (Integer attitudeAffection) {
        this.attitudeAffection = attitudeAffection;
    }

    public Boolean getFacility () {
        return facility;
    }

    public void setFacility (Boolean facility) {
        this.facility = facility;
    }

    public Integer getFacilityAffection () {
        return facilityAffection;
    }

    public void setFacilityAffection (Integer facilityAffection) {
        this.facilityAffection = facilityAffection;
    }

    public Boolean getStafflevel () {
        return stafflevel;
    }

    public void setStafflevel (Boolean stafflevel) {
        this.stafflevel = stafflevel;
    }

    public Integer getStafflevelAffection () {
        return stafflevelAffection;
    }

    public void setStafflevelAffection (Integer stafflevelAffection) {
        this.stafflevelAffection = stafflevelAffection;
    }

    public Date getPubTime () {
        return pubTime;
    }

    public void setPubTime (Date pubTime) {
        this.pubTime = pubTime;
    }
}