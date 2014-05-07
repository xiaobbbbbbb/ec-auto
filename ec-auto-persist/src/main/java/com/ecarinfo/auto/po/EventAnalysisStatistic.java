package com.ecarinfo.auto.po;
import java.io.Serializable;
import java.util.Date;

public class EventAnalysisStatistic implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer eid;
	private Integer type;
	private Date dayTime;
	private Integer positive;
	private Integer negative;
	private Integer normal;
	private Integer total;

    public Integer getEid () {
        return eid;
    }

    public void setEid (Integer eid) {
        this.eid = eid;
    }

    public Integer getType () {
        return type;
    }

    public void setType (Integer type) {
        this.type = type;
    }

    public Date getDayTime () {
        return dayTime;
    }

    public void setDayTime (Date dayTime) {
        this.dayTime = dayTime;
    }

    public Integer getPositive () {
        return positive;
    }

    public void setPositive (Integer positive) {
        this.positive = positive;
    }

    public Integer getNegative () {
        return negative;
    }

    public void setNegative (Integer negative) {
        this.negative = negative;
    }

    public Integer getNormal () {
        return normal;
    }

    public void setNormal (Integer normal) {
        this.normal = normal;
    }

    public Integer getTotal () {
        return total;
    }

    public void setTotal (Integer total) {
        this.total = total;
    }
}