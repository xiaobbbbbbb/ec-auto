package com.ecarinfo.auto.po;
import java.io.Serializable;

public class EventIdProcessed implements Serializable {

	private static final long serialVersionUID = -2260388125919493487L;
	private Integer eid;
	private Integer infotype;
	private Integer processAutoId;
	private Integer kid;

    public Integer getEid () {
        return eid;
    }

    public void setEid (Integer eid) {
        this.eid = eid;
    }

    public Integer getInfotype () {
        return infotype;
    }

    public void setInfotype (Integer infotype) {
        this.infotype = infotype;
    }

    public Integer getProcessAutoId () {
        return processAutoId;
    }

    public void setProcessAutoId (Integer processAutoId) {
        this.processAutoId = processAutoId;
    }

    public Integer getKid () {
        return kid;
    }

    public void setKid (Integer kid) {
        this.kid = kid;
    }
}