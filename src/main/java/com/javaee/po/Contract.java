package com.javaee.po;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Contract {
    private Integer contractId;
    private Integer actorId;
    private Actor actor;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date signStart;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date signEnd;

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Date getSignStart() {
        return signStart;
    }

    public void setSignStart(Date signStart) {
        this.signStart = signStart;
    }

    public Date getSignEnd() {
        return signEnd;
    }

    public void setSignEnd(Date signEnd) {
        this.signEnd = signEnd;
    }
}