package com.frc.scorpiowf.qixiong.processor;

import java.io.IOException;

import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.frc.scorpiowf.qixiong.QXConstants;
import com.frc.scorpiowf.qixiong.QiXiongHttpClient;
import com.frc.scorpiowf.qixiong.bean.QiXiongConfig;
import com.frc.scorpiowf.qixiong.bean.QiXiongStatus;
import com.frc.scorpiowf.qixiong.utils.FileUtil;
import com.frc.scorpiowf.qixiong.utils.QXutils;

public class FarmProcessor extends AbstractActionProcessor {

	public DefaultHttpClient httpclient = null;
	
	@Override
	public String process() throws Exception {
		QiXiongStatus qxStatus = qxContext.getQxStatus();
		QiXiongConfig qxConfig = qxContext.getQxConfig();
		httpclient = QiXiongHttpClient.createHttpClient(qxConfig.getQqNumber());
		
		Document farmDoc = qxStatus.getFarmDoc();
		if (farmDoc == null) {
			qxStatus.setErrorCode(QXConstants.ERR_NO_FARM_DOC);
			return "error";
		}
		
		Elements links = farmDoc.select("a");
		if (links == null || links.size() == 0) {
			qxStatus.setErrorCode(QXConstants.ERR_FARM_NO_LINKS);
			return "error";
		}
		
		for (Element lnk: links) {
			String txt = lnk.text();
			if (QXConstants.FARM_WATER.equals(txt)) {
				//浇水
				String url = lnk.attr("href");
				String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
			} else if (QXConstants.FARM_FEED.equals(txt)) {
				//喂食
				String url = lnk.attr("href");
				String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
			}  else if (QXConstants.FARM_FRIEND.equals(txt)) {
				//好友资源地
				String url = lnk.attr("href");
				String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
				FileUtil.writeFile(qxConfig.getQqNumber(), html, "waterFriend.html");
				Document waterFriendDoc = Jsoup.parse(html);
				waterFriend(waterFriendDoc);
			}
		}
				
		return "done";
	}
	
	private String waterFriend(Document doc) throws IOException {
		QiXiongConfig qxConfig = qxContext.getQxConfig();
		
		Elements links = doc.select("a");
		if (links == null || links.size() == 0) {
			return "done";
		}
		
		int waterCount = 0;
		for (Element lnk: links) {
			String txt = lnk.text();
			if (QXConstants.FARM_WATER.equals(txt)) {
				//浇水
				String url = lnk.attr("href");
				String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
				waterCount++;
			} else if (QXConstants.FARM_FEED_CHICKEN.equals(txt)) {
				//喂食
				String url = lnk.attr("href");
				String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
				FileUtil.writeFile(qxConfig.getQqNumber(), html, "feedFriend.html");
				Document feedFriendDoc = Jsoup.parse(html);
				feedFriend(feedFriendDoc);
			}
		}
		
		System.out.println("总共浇水：" + waterCount + "次");
		
		return "done";
	}
	
	private String feedFriend(Document doc) throws IOException {
		QiXiongConfig qxConfig = qxContext.getQxConfig();
		
		Elements links = doc.select("a");
		if (links == null || links.size() == 0) {
			return "done";
		}
		
		int feedCount = 0;
		for (Element lnk: links) {
			String txt = lnk.text();
			if (QXConstants.FARM_FEED_CHICKEN.equals(txt)) {
				//喂食
				String url = lnk.attr("href");
				String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
				feedCount++;
			}
		}
		
		System.out.println("总共喂鸡：" + feedCount + "次");
		
		return "done";
	}

}
