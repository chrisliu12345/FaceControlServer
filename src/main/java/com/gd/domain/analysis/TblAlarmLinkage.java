package com.gd.domain.analysis;

public class TblAlarmLinkage {
    //
    private Integer id;
    //
    private String alarmEventName;
    //
    private String deviceid;
    //
    private Integer inputChannel;
    //
    private Integer alarmmethod;
    //
    private Integer alarmtype;
    //
    private String notifiedPerson;
    //
    private Integer linkageMethod;
    //
    private String linkageCamera;
    //
    private String linkageInfo;

    /**
     * 设置：
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取：
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置：
     */
    public void setAlarmEventName(String alarmEventName) {
        this.alarmEventName = alarmEventName;
    }

    /**
     * 获取：
     */
    public String getAlarmEventName() {
        return alarmEventName;
    }

    /**
     * 设置：
     */
    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    /**
     * 获取：
     */
    public String getDeviceid() {
        return deviceid;
    }

    /**
     * 设置：
     */
    public void setInputChannel(Integer inputChannel) {
        this.inputChannel = inputChannel;
    }

    /**
     * 获取：
     */
    public Integer getInputChannel() {
        return inputChannel;
    }

    /**
     * 设置：
     */
    public void setAlarmmethod(Integer alarmmethod) {
        this.alarmmethod = alarmmethod;
    }

    /**
     * 获取：
     */
    public Integer getAlarmmethod() {
        return alarmmethod;
    }

    /**
     * 设置：
     */
    public void setAlarmtype(Integer alarmtype) {
        this.alarmtype = alarmtype;
    }

    /**
     * 获取：
     */
    public Integer getAlarmtype() {
        return alarmtype;
    }

    /**
     * 设置：
     */
    public void setNotifiedPerson(String notifiedPerson) {
        this.notifiedPerson = notifiedPerson;
    }

    /**
     * 获取：
     */
    public String getNotifiedPerson() {
        return notifiedPerson;
    }

    /**
     * 设置：
     */
    public void setLinkageMethod(Integer linkageMethod) {
        this.linkageMethod = linkageMethod;
    }

    /**
     * 获取：
     */
    public Integer getLinkageMethod() {
        return linkageMethod;
    }

    /**
     * 设置：
     */
    public void setLinkageCamera(String linkageCamera) {
        this.linkageCamera = linkageCamera;
    }

    /**
     * 获取：
     */
    public String getLinkageCamera() {
        return linkageCamera;
    }

    /**
     * 设置：
     */
    public void setLinkageInfo(String linkageInfo) {
        this.linkageInfo = linkageInfo;
    }

    /**
     * 获取：
     */
    public String getLinkageInfo() {
        return linkageInfo;
    }
}
