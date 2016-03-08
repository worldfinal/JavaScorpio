package com.frc.scorpiowf.qixiong.processor;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.frc.scorpiowf.qixiong.QXConstants;
import com.frc.scorpiowf.qixiong.QiXiongHttpClient;
import com.frc.scorpiowf.qixiong.bean.QiXiongConfig;
import com.frc.scorpiowf.qixiong.bean.QiXiongStatus;
import com.frc.scorpiowf.qixiong.utils.FileUtil;

public class ActivityProcessor extends AbstractActionProcessor {

	public DefaultHttpClient httpclient = null;
	
	@Override
	public String process() throws Exception {
		QiXiongStatus qxStatus = qxContext.getQxStatus();
		QiXiongConfig qxConfig = qxContext.getQxConfig();
		httpclient = QiXiongHttpClient.createHttpClient(qxConfig.getQqNumber());
		HttpClientContext context = QiXiongHttpClient.retrieveContext(qxConfig.getQqNumber());
		
		Document doc = qxStatus.getActivityDoc();
		if (doc == null) {
			qxStatus.setErrorCode(QXConstants.ERR_NO_ACTIVITY_DOC);
			return "error";
		}
		
		Elements links = doc.select("a"); 
		for (Element lnk : links) {
			String txt = lnk.text();
			if (QXConstants.ACTIVITY_LK.equals(txt)) {
				//炼矿
				String url = lnk.attr("href");
				String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
				FileUtil.writeFile(qxConfig.getQqNumber(), html, "LianKuang.html");
				
				startLianKuang(Jsoup.parse(html));
			} else if (QXConstants.ACTIVITY_CL.equals(txt)) {
				//勇闯重楼
				/*
				String url = lnk.attr("href");
				String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
				FileUtil.writeFile(qxConfig.getQqNumber(), html, "ChongLou.html");
				*/
			} else if (QXConstants.ACTIVITY_HW.equals(txt)) {
				//轩辕会武
				
				String url = lnk.attr("href");
				String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
				FileUtil.writeFile(qxConfig.getQqNumber(), html, "Huiwu.html");
				
				startWuhui(Jsoup.parse(html));
				
			}
		}
		
		return "done";
	}
	
	private String startLianKuang(Document doc) throws IOException {
		QiXiongConfig qxConfig = qxContext.getQxConfig();
		
		int rest = 0;
		Element tip = doc.select("p.tips").first();
		if (tip == null) {
			System.out.println("炼矿失败：找不到p.tips");
			return "error";
		}
		List<Node> list = tip.childNodes();
		for (Node node: list) {
			String nodeName = node.nodeName();
			if (nodeName != null && nodeName.contains("text")) {
				String txt = node.outerHtml();
				String arr[] = txt.split("：");
				if (arr.length >= 2 && arr[0].contains("今日可炼矿次数")) {
					String strCount = arr[1];
					System.out.println("testing, " + arr[0] + "," + arr[1]);
					try{
						rest = Integer.parseInt(strCount);
						break;
					}catch(Exception e) {
						
					}
				}
			}
		}
		if (rest <= 0) {
			System.out.println("剩余炼矿次数 = " + rest);
			return "done";
		}
		
		Elements links = doc.select("a");
		if (links == null || links.size() == 0) {
			System.out.println("炼矿。。。找不到任何连接<a>");
			return "done";
		}
		
		for (Element lnk: links) {
			String txt = lnk.text();
			if (QXConstants.ACTIVITY_START_LK.equals(txt)) {
				//开始炼矿
				while (rest > 0) {
					String url = lnk.attr("href");
					String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
					rest--;
				}
			}
		}
		
		System.out.println("炼矿完成");
		
		return "done";
	}
	
	private String startWuhui(Document doc) throws IOException {
		QiXiongConfig qxConfig = qxContext.getQxConfig();
		
		Date now = new Date();
		int h = now.getHours();
		System.out.println("test：当前小时_" + h);
		if (h < 9 || h > 22) {
			System.out.println("天时不合，不能轩辕会武");
			return "error";
		}
		
		Element tip = doc.select("p.tips").first();
		if (tip == null) {
			System.out.println("轩辕会武失败：找不到p.tips");
			return "error";
		}
		
		String html = "", url = "";
		Document whDoc = null;
		int rest = 0;
		
		List<Node> list = tip.childNodes();
		for (Node node: list) {
			String nodeName = node.nodeName();
			if (nodeName.contains("text") && node.outerHtml().contains("剩余挑战次数")) {
				String arr[] = node.outerHtml().split(":");
				if (arr.length != 2) {
					System.out.println("轩辕武会：解释文本失败[1]");
					return "error";
				}
				int idx = arr[1].indexOf("次");
				if (idx < 0) {
					System.out.println("轩辕武会：解释文本失败[2]");
					return "error";
				}
				try {
					rest = Integer.parseInt(arr[1].substring(0, idx));
				} catch (Exception e) {
				}
				if (rest == 0) {
					System.out.println("轩辕武会：剩余次数为0");
					
					String query = String.format("a:contains(每日奖励)");
					Element lnk = whDoc.select(query).first();
					if (lnk == null) {
						System.out.println("轩辕武会返回后，不能领取[0]");
						return "error";
					}
					
					html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), lnk.attr("href"));
					FileUtil.writeFile(qxConfig.getQqNumber(), html, "WHdone.html");
					System.out.println("轩辕武会：领取奖励了");
					return "done";
				}
			}
		}
		
		String query = String.format("a:contains(%s)", QXConstants.ACTIVITY_START_HW);
		Elements links = doc.select(query);
		if (links == null || links.size() == 0) {
			System.out.println("开始挑战。。。找不到任何连接<a>");
			return "done";
		}
		url = links.get(0).attr("href");
		html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
		FileUtil.writeFile(qxConfig.getQqNumber(), html, "startHW.html");
		
		whDoc = Jsoup.parse(html);
		query = String.format("a:contains(%s)", QXConstants.ACTIVITY_CHOOSE_ENIMY);
		links = whDoc.select(query);
		if (links == null || links.size() == 0) {
			System.out.println("选择挑战目标。。。找不到任何连接<1>");
			return "done";
		}
		url = links.get(0).attr("href");
		html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
		
		whDoc = Jsoup.parse(html);
		query = String.format("a:contains(%s)", QXConstants.ACTIVITY_GO);
		links = whDoc.select(query);
		if (links == null || links.size() == 0) {
			System.out.println("选择挑战目标。。。找不到任何连接<2>");
			return "done";
		}
		
		System.out.println("info: 轩辕武会剩余挑战次数：" + rest);
		int i = 0;
		while (rest > 0) {
			rest--;
			url = links.get(i).attr("href");
			html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
			i++;
		}
		//test write file:
		FileUtil.writeFile(qxConfig.getQqNumber(), html, "completeXYWH.html");
		
		whDoc = Jsoup.parse(html);
		query = String.format("a:contains(返回)");
		Element lnk = whDoc.select(query).first();
		if (lnk == null) {
			System.out.println("轩辕武会完成后，不能返回[1]");
			return "error";
		}
		
		html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), lnk.attr("href"));
		FileUtil.writeFile(qxConfig.getQqNumber(), html, "returnXYWH.html");
		
		whDoc = Jsoup.parse(html);
		query = String.format("a:contains(每日奖励)");
		lnk = whDoc.select(query).first();
		if (lnk == null) {
			System.out.println("轩辕武会返回后，不能领取");
			return "error";
		}
		
		html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), lnk.attr("href"));
		FileUtil.writeFile(qxConfig.getQqNumber(), html, "WHdone.html");
		
		System.out.println("轩辕武会完成");
		return "done";
	}

}
