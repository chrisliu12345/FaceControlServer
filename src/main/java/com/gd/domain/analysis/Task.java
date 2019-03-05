package com.gd.domain.analysis;

import java.sql.Timestamp;
import java.util.List;

public class Task {
 private Integer taskID;
 private String taskName;
 private Integer taskType;
 private Integer camID;
 private String sipID;
 private Integer videoType;
 private String camUrl;
 private Timestamp videoStartTime;
 private Timestamp videoEndTime;
 private Integer serviceID;
 private Integer priorty;
 private Integer taskStatus;
 private Timestamp commitTime;
 private Timestamp beginTime;
 private Timestamp expectEndTime;
 private Integer progress;
 private List<AnalysisRule> analysisRules;


 public Integer getTaskID() {
  return taskID;
 }

 public void setTaskID(Integer taskID) {
  this.taskID = taskID;
 }

 public String getTaskName() {
  return taskName;
 }

 public void setTaskName(String taskName) {
  this.taskName = taskName;
 }

 public Integer getTaskType() {
  return taskType;
 }

 public void setTaskType(Integer taskType) {
  this.taskType = taskType;
 }

 public Integer getCamID() {
  return camID;
 }

 public void setCamID(Integer camID) {
  this.camID = camID;
 }

 public String getSipID() {
  return sipID;
 }

 public void setSipID(String sipID) {
  this.sipID = sipID;
 }

/* public Integer getVideoType() {
  return videoType;
 }

 public void setVideoType(Integer videoType) {
  this.videoType = videoType;
 }*/


 public Integer getServiceID() {
  return serviceID;
 }

 public void setServiceID(Integer serviceID) {
  this.serviceID = serviceID;
 }

 public Integer getPriorty() {
  return priorty;
 }

 public void setPriorty(Integer priorty) {
  this.priorty = priorty;
 }

 public Integer getTaskStatus() {
  return taskStatus;
 }

 public void setTaskStatus(Integer taskStatus) {
  this.taskStatus = taskStatus;
 }

 public List<AnalysisRule> getAnalysisRules() {
  return analysisRules;
 }

 public void setAnalysisRules(List<AnalysisRule> analysisRules) {
  this.analysisRules = analysisRules;
 }

 public String getCamUrl() {
  return camUrl;
 }

 public void setCamUrl(String camUrl) {
  this.camUrl = camUrl;
 }

 public Timestamp getVideoStartTime() {
  return videoStartTime;
 }

 public void setVideoStartTime(Timestamp videoStartTime) {
  this.videoStartTime = videoStartTime;
 }

 public Timestamp getVideoEndTime() {
  return videoEndTime;
 }

 public void setVideoEndTime(Timestamp videoEndTime) {
  this.videoEndTime = videoEndTime;
 }

 public Timestamp getCommitTime() {
  return commitTime;
 }

 public void setCommitTime(Timestamp commitTime) {
  this.commitTime = commitTime;
 }

 public Timestamp getBeginTime() {
  return beginTime;
 }

 public void setBeginTime(Timestamp beginTime) {
  this.beginTime = beginTime;
 }

 public Timestamp getExpectEndTime() {
  return expectEndTime;
 }

 public void setExpectEndTime(Timestamp expectEndTime) {
  this.expectEndTime = expectEndTime;
 }

 public Integer getProgress() {
  return progress;
 }

 public void setProgress(Integer progress) {
  this.progress = progress;
 }

 public Integer getVideoType() {
  return videoType;
 }

 public void setVideoType(Integer videoType) {
  this.videoType = videoType;
 }
}
