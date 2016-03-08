package com.frc.scorpiowf.qixiong.processor;

import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.frc.scorpiowf.qixiong.QXConstants;
import com.frc.scorpiowf.qixiong.QiXiongHttpClient;
import com.frc.scorpiowf.qixiong.bean.QiXiongConfig;
import com.frc.scorpiowf.qixiong.bean.QiXiongStatus;

public class ExitProcessor extends AbstractActionProcessor {

	public DefaultHttpClient httpclient = null;
	
	@Override
	public String process() throws Exception {
		QiXiongStatus qxStatus = qxContext.getQxStatus();
		QiXiongConfig qxConfig = qxContext.getQxConfig();
		httpclient = QiXiongHttpClient.createHttpClient(qxConfig.getQqNumber());
		
		Document doc = qxStatus.getHomeDoc();
		if (doc != null) {
			String query = String.format("a:contains(%s)", QXConstants.HOME_EXIT);
			Element exitLnk = doc.select(query).first();
			String url = exitLnk.attr("href");
			QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
		}
		
		return "done";
	}

}
