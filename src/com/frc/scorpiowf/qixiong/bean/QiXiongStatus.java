package com.frc.scorpiowf.qixiong.bean;

import java.util.Date;

import org.jsoup.nodes.Document;

public class QiXiongStatus {
	private Date lastUpdateTime;
	private String sid;
	private String errorCode;
	private Document homeDoc;
	private Document farmDoc;
	private Document taskDoc;
	private Document activityDoc;
	private Document buildDoc;

	public QiXiongStatus() {
		this.lastUpdateTime = new Date();
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public Document getHomeDoc() {
		return homeDoc;
	}
	public void setHomeDoc(Document homeDoc) {
		this.homeDoc = homeDoc;
	}
	public Document getFarmDoc() {
		return farmDoc;
	}
	public void setFarmDoc(Document farmDoc) {
		this.farmDoc = farmDoc;
	}
	public Document getTaskDoc() {
		return taskDoc;
	}
	public void setTaskDoc(Document taskDoc) {
		this.taskDoc = taskDoc;
	}
	public Document getActivityDoc() {
		return activityDoc;
	}
	public void setActivityDoc(Document activityDoc) {
		this.activityDoc = activityDoc;
	}
	public Document getBuildDoc() {
		return buildDoc;
	}
	public void setBuildDoc(Document buildDoc) {
		this.buildDoc = buildDoc;
	}
}
