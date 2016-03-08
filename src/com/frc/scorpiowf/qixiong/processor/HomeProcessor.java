package com.frc.scorpiowf.qixiong.processor;

import java.io.IOException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frc.scorpiowf.qixiong.QXConstants;
import com.frc.scorpiowf.qixiong.QiXiongHttpClient;
import com.frc.scorpiowf.qixiong.bean.QiXiongConfig;
import com.frc.scorpiowf.qixiong.bean.QiXiongStatus;
import com.frc.scorpiowf.qixiong.utils.FileUtil;

public class HomeProcessor extends AbstractActionProcessor {
	
	public DefaultHttpClient httpclient = null;
	private Logger log = LoggerFactory.getLogger(HomeProcessor.class);
	@Override
	public String process() throws Exception {
		
		QiXiongStatus qxStatus = qxContext.getQxStatus();
		QiXiongConfig qxConfig = qxContext.getQxConfig();
		httpclient = QiXiongHttpClient.createHttpClient(qxConfig.getQqNumber());
		
		if (qxStatus.getHomeDoc() == null) {
			qxStatus.setErrorCode(QXConstants.ERR_NO_HOME_DOC);
			return "error";
		}
		
		ClickLink(qxStatus.getHomeDoc());
		
		return "done";
	}
	
	private String ClickLink(Document homeDoc) throws Exception {
		QiXiongStatus qxStatus = qxContext.getQxStatus();
		QiXiongConfig qxConfig = qxContext.getQxConfig();
		
		
		Elements es = homeDoc.select(String.format("a"));
		if (es != null && es.size() > 0) {
			for(Element in : es) {
				if (QXConstants.BTN_GIFT.equals(in.text()) ||
						QXConstants.BTN_HARVEST.equals(in.text()) ||
						QXConstants.BTN_TREE.equals(in.text()) ||
						QXConstants.BTN_UPGRADE.equals(in.text()) ||
						QXConstants.BTN_HEATEGG.equals(in.text())) {	//领取,征收,收获,砸蛋
					QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), in.attr("href"));
					log.info(String.format("[%s] %s", qxConfig.getQqNumber(), in.text()));
				} else if (QXConstants.BTN_WAP_GIFT.equals(in.text())) {	//wap登录奖励
					String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), in.attr("href"));
					FileUtil.writeFile(qxConfig.getQqNumber(), html, "wapgift.html");
					
					Document doc = Jsoup.parse(html);
					WapGift(doc);
				} else if (QXConstants.HOME_FARM.equals(in.text())) {
					String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), in.attr("href"));
					FileUtil.writeFile(qxConfig.getQqNumber(), html, "farm.html");
					
					Document doc = Jsoup.parse(html);
					qxStatus.setFarmDoc(doc);
				} else if (QXConstants.HOME_TASK.equals(in.text())) {
					String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), in.attr("href"));
					FileUtil.writeFile(qxConfig.getQqNumber(), html, "task.html");
					
					Document doc = Jsoup.parse(html);
					qxStatus.setTaskDoc(doc);
				} else if (QXConstants.HOME_ACTIVITY.equals(in.text())) {
					String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), in.attr("href"));
					FileUtil.writeFile(qxConfig.getQqNumber(), html, "activity.html");
					
					Document doc = Jsoup.parse(html);
					qxStatus.setActivityDoc(doc);
				} else if (QXConstants.HOME_BUILD.equals(in.text())) {
					String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), in.attr("href"));
					FileUtil.writeFile(qxConfig.getQqNumber(), html, "building.html");
					
					Document doc = Jsoup.parse(html);
					qxStatus.setBuildDoc(doc);
				}
			}
		}
		
		
		return "done";
	}
	
	private String WapGift(Document doc) {
		return "done";
	}

}
