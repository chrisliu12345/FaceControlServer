package com.gd.domain.analysis;

import java.security.Timestamp;

public class AnalysisResult {
    private Integer abnormalID;
    private Integer ruleType;
    private String ruleTypeName;
    private String behaviorName;
    private Integer camID;
    private String camName;
    private Integer ruleID;
    private String ruleName;
    private String behaviorValue;
    private Timestamp occurTime;
    private Integer objectType;
    private Integer objectID;
    private Integer serviceID;
    private String resultPath;

    public Integer getAbnormalID() {
        return abnormalID;
    }

    public void setAbnormalID(Integer abnormalID) {
        this.abnormalID = abnormalID;
    }

    public Integer getRuleType() {
        return ruleType;
    }

    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }

    public String getBehaviorName() {
        return behaviorName;
    }

    public void setBehaviorName(String behaviorName) {
        this.behaviorName = behaviorName;
    }

    public Integer getCamID() {
        return camID;
    }

    public void setCamID(Integer camID) {
        this.camID = camID;
    }

    public String getCamName() {
        return camName;
    }

    public void setCamName(String camName) {
        this.camName = camName;
    }

    public Integer getRuleID() {
        return ruleID;
    }

    public void setRuleID(Integer ruleID) {
        this.ruleID = ruleID;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getBehaviorValue() {
        return behaviorValue;
    }

    public void setBehaviorValue(String behaviorValue) {
        this.behaviorValue = behaviorValue;
    }

    public Timestamp getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Timestamp occurTime) {
        this.occurTime = occurTime;
    }

    public Integer getObjectType() {
        return objectType;
    }

    public void setObjectType(Integer objectType) {
        this.objectType = objectType;
    }

    public Integer getObjectID() {
        return objectID;
    }

    public void setObjectID(Integer objectID) {
        this.objectID = objectID;
    }

    public Integer getServiceID() {
        return serviceID;
    }

    public void setServiceID(Integer serviceID) {
        this.serviceID = serviceID;
    }

    public String getResultPath() {
        return resultPath;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    public String getRuleTypeName() {
        return ruleTypeName;
    }

    public void setRuleTypeName(String ruleTypeName) {
        this.ruleTypeName = ruleTypeName;
    }
}
