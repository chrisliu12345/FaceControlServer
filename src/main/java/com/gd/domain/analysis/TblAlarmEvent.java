package com.gd.domain.analysis;

import java.util.Date;

public class TblAlarmEvent {
    //
    private String alarmid;
    //
    private String deviceid;
    //
    private Integer alarmpriority;
    //
    private Integer alarmmethod;
    //
    private Date datetime;
    //
    private String alarmdescription;
    //
    private Double longitude;
    //
    private Double latitude;
    //
    private Integer alarmtype;
    //
    private Integer alarmtypeparam;
    //
    private Integer eventtype;

    /**
     * 设置：
     */
    public void setAlarmid(String alarmid) {
        this.alarmid = alarmid;
    }
    /**
     * 获取：
     */
    public String getAlarmid() {
        return alarmid;
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
    public void setAlarmpriority(Integer alarmpriority) {
        this.alarmpriority = alarmpriority;
    }
    /**
     * 获取：
     */
    public Integer getAlarmpriority() {
        return alarmpriority;
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
    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
    /**
     * 获取：
     */
    public Date getDatetime() {
        return datetime;
    }
    /**
     * 设置：
     */
    public void setAlarmdescription(String alarmdescription) {
        this.alarmdescription = alarmdescription;
    }
    /**
     * 获取：
     */
    public String getAlarmdescription() {
        return alarmdescription;
    }
    /**
     * 设置：
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    /**
     * 获取：
     */
    public Double getLongitude() {
        return longitude;
    }
    /**
     * 设置：
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    /**
     * 获取：
     */
    public Double getLatitude() {
        return latitude;
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
    public void setAlarmtypeparam(Integer alarmtypeparam) {
        this.alarmtypeparam = alarmtypeparam;
    }
    /**
     * 获取：
     */
    public Integer getAlarmtypeparam() {
        return alarmtypeparam;
    }
    /**
     * 设置：
     */
    public void setEventtype(Integer eventtype) {
        this.eventtype = eventtype;
    }
    /**
     * 获取：
     */
    public Integer getEventtype() {
        return eventtype;
    }
}
