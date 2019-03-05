package com.gd.domain.analysis;

public class AnalysisRule {
    private Integer taskID;
    private Integer ruleID;
    private String ruleName;
    private Integer ruleCode;
    private Integer sensitiveness;
    private float hmin;
    private String vertex;
    private String interParams;
    private Integer camID;
    private Integer controlStatus;

    public Integer getTaskID() {
        return taskID;
    }

    public void setTaskID(Integer taskID) {
        this.taskID = taskID;
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

    public Integer getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(Integer ruleCode) {
        this.ruleCode = ruleCode;
    }

    public Integer getSensitiveness() {
        return sensitiveness;
    }

    public void setSensitiveness(Integer sensitiveness) {
        this.sensitiveness = sensitiveness;
    }

    public float getHmin() {
        return hmin;
    }

    public void setHmin(float hmin) {
        this.hmin = hmin;
    }

    public String getVertex() {
        return vertex;
    }

    public void setVertex(String vertex) {
        this.vertex = vertex;
    }

    public String getInterParams() {
        return interParams;
    }

    public void setInterParams(String interParams) {
        this.interParams = interParams;
    }

    public Integer getCamID() {
        return camID;
    }

    public void setCamID(Integer camID) {
        this.camID = camID;
    }

    public Integer getControlStatus() {
        return controlStatus;
    }

    public void setControlStatus(Integer controlStatus) {
        this.controlStatus = controlStatus;
    }
}
