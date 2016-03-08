package com.frc.scorpiowf.qixiong;

import javax.jms.Message;
import javax.jms.MessageListener;

import com.frc.scorpiowf.qixiong.bean.QiXiongMessage;

public class QiXiongMessageListener implements MessageListener {

	@Override
	public void onMessage(Message arg0) {
		QiXiongMessage message = (QiXiongMessage)arg0;
	}

}
