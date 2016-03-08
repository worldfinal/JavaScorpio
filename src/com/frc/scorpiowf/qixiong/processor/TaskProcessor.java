package com.frc.scorpiowf.qixiong.processor;

import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.frc.scorpiowf.qixiong.QXConstants;
import com.frc.scorpiowf.qixiong.QiXiongHttpClient;
import com.frc.scorpiowf.qixiong.bean.QiXiongConfig;
import com.frc.scorpiowf.qixiong.bean.QiXiongStatus;

public class TaskProcessor extends AbstractActionProcessor {

	public DefaultHttpClient httpclient = null;
	
	@Override
	public String process() throws Exception {
		QiXiongStatus qxStatus = qxContext.getQxStatus();
		QiXiongConfig qxConfig = qxContext.getQxConfig();
		httpclient = QiXiongHttpClient.createHttpClient(qxConfig.getQqNumber());
		
		Document taskDoc = qxStatus.getTaskDoc();
		if (taskDoc == null) {
			qxStatus.setErrorCode(QXConstants.ERR_NO_TASK_DOC);
			return "error";
		}
		
		String query = String.format("a:contains(%s)", QXConstants.TASK_PROCESS);
		Elements lnks = taskDoc.select(query);
		if (lnks == null || lnks.size() == 0) {
			System.out.println("无任务");
			return "done";
		}
		
		String url = lnks.get(0).attr("href");
		String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
		Document doc = Jsoup.parse(html);
		
		query = String.format("a:contains(%s)", QXConstants.TASK_CONFIRM_PROCESS);
		lnks = doc.select(query);
		if (lnks == null || lnks.size() == 0) {
			System.out.println("无任务");
			return "done";
		}
		url = lnks.get(0).attr("href");
		html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
		
		System.out.println("执行任务成功");
		return "done";
	}

}
