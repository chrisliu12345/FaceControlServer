package com.gd.domain.analysis;

import java.sql.Timestamp;

public class TblAlarmLinkage {
    //
    private Integer id;

    private String alarmEventName;
    //
    private String deviceid;

    private String deviceName;
    //
    private Integer alarmType;

    private String alarmTypeName;

    private String notifiedPerson;

    private String notifiedPersonName;
    //
    private String linkageInfo;

    private Timestamp createTime;

    //创建人
    private String createUser;

    private String createUserName;

    /*//
    private String alarmEventName;
    //
    private Integer inputChannel;
    //
    private Integer alarmmethod;

    //
    private String notifiedPerson;
    //
    private Integer linkageMethod;
    //
    private String linkageCamera;*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public Integer getAlarmtype() {
        return alarmType;
    }

    public void setAlarmtype(Integer alarmtype) {
        this.alarmType = alarmtype;
    }

    public String getLinkageInfo() {
        return linkageInfo;
    }

    public void setLinkageInfo(String linkageInfo) {
        this.linkageInfo = linkageInfo;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getAlarmTypeName() {
        return alarmTypeName;
    }

    public void setAlarmTypeName(String alarmTypeName) {
        this.alarmTypeName = alarmTypeName;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getNotifiedPerson() {
        return notifiedPerson;
    }

    public void setNotifiedPerson(String notifiedPerson) {
        this.notifiedPerson = notifiedPerson;
    }

    public String getNotifiedPersonName() {
        return notifiedPersonName;
    }

    public void setNotifiedPersonName(String notifiedPersonName) {
        this.notifiedPersonName = notifiedPersonName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getAlarmEventName() {
        return alarmEventName;
    }

    public void setAlarmEventName(String alarmEventName) {
        this.alarmEventName = alarmEventName;
    }
}
