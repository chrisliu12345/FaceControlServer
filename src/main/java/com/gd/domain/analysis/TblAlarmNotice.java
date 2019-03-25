package com.gd.domain.analysis;

public class TblAlarmNotice {

    //
    private String eventId;
    //
    private String linkId;
    //
    private String notifyUser;
    //
    private Integer linkMethod;
    //
    private Integer status;

    private String deviceId;



    private String notifyUserName;

    //与tbl_alarm_linkage表关联信息
    private String linkageInfo;
    private String linkageCamera;
    private String linkageMethod;

    //与tbl_alarm_event表关联查询的信息
    private String alarmDescription ;

    private String dateTime;

    private String alarmPriority;

    private String alarmMethod;
    /**
     * 设置：
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    /**
     * 获取：
     */
    public String getEventId() {
        return eventId;
    }
    /**
     * 设置：
     */
    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }
    /**
     * 获取：
     */
    public String getLinkId() {
        return linkId;
    }
    /**
     * 设置：
     */
    public void setNotifyUser(String notifyUser) {
        this.notifyUser = notifyUser;
    }
    /**
     * 获取：
     */
    public String getNotifyUser() {
        return notifyUser;
    }
    /**
     * 设置：
     */
    public void setLinkMethod(Integer linkMethod) {
        this.linkMethod = linkMethod;
    }
    /**
     * 获取：
     */
    public Integer getLinkMethod() {
        return linkMethod;
    }
    /**
     * 设置：
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    /**
     * 获取：
     */
    public Integer getStatus() {
        return status;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getNotifyUserName() {
        return notifyUserName;
    }

    public void setNotifyUserName(String notifyUserName) {
        this.notifyUserName = notifyUserName;
    }

    public String getLinkageInfo() {
        return linkageInfo;
    }

    public void setLinkageInfo(String linkageInfo) {
        this.linkageInfo = linkageInfo;
    }

    public String getLinkageCamera() {
        return linkageCamera;
    }

    public void setLinkageCamera(String linkageCamera) {
        this.linkageCamera = linkageCamera;
    }

    public String getLinkageMethod() {
        return linkageMethod;
    }

    public void setLinkageMethod(String linkageMethod) {
        this.linkageMethod = linkageMethod;
    }

    public String getAlarmDescription() {
        return alarmDescription;
    }

    public void setAlarmDescription(String alarmDescription) {
        this.alarmDescription = alarmDescription;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getAlarmPriority() {
        return alarmPriority;
    }

    public void setAlarmPriority(String alarmPriority) {
        this.alarmPriority = alarmPriority;
    }

    public String getAlarmMethod() {
        return alarmMethod;
    }

    public void setAlarmMethod(String alarmMethod) {
        this.alarmMethod = alarmMethod;
    }
}
