package com.frc.scorpiowf.qixiong.bean;

public class QiXiongMessage {
	private QiXiongContext qxContext;
	private String status;
	private int action;
	
	public QiXiongContext getQxContext() {
		return qxContext;
	}
	public void setQxContext(QiXiongContext qxContext) {
		this.qxContext = qxContext;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	
}
