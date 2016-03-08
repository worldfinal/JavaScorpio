package com.frc.scorpiowf.qixiong.processor;

import java.io.IOException;

import com.frc.scorpiowf.qixiong.bean.QiXiongContext;

public abstract class AbstractActionProcessor {
	protected QiXiongContext qxContext;
	
	public void setQxContext(QiXiongContext qxContext) {
		this.qxContext = qxContext;
	}

	abstract public String process() throws Exception;
	
}
