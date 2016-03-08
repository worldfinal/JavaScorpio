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
				//����
				String url = lnk.attr("href");
				String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
				FileUtil.writeFile(qxConfig.getQqNumber(), html, "LianKuang.html");
				
				startLianKuang(Jsoup.parse(html));
			} else if (QXConstants.ACTIVITY_CL.equals(txt)) {
				//�´���¥
				/*
				String url = lnk.attr("href");
				String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
				FileUtil.writeFile(qxConfig.getQqNumber(), html, "ChongLou.html");
				*/
			} else if (QXConstants.ACTIVITY_HW.equals(txt)) {
				//��ԯ����
				
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
			System.out.println("����ʧ�ܣ��Ҳ���p.tips");
			return "error";
		}
		List<Node> list = tip.childNodes();
		for (Node node: list) {
			String nodeName = node.nodeName();
			if (nodeName != null && nodeName.contains("text")) {
				String txt = node.outerHtml();
				String arr[] = txt.split("��");
				if (arr.length >= 2 && arr[0].contains("���տ��������")) {
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
			System.out.println("ʣ��������� = " + rest);
			return "done";
		}
		
		Elements links = doc.select("a");
		if (links == null || links.size() == 0) {
			System.out.println("���󡣡����Ҳ����κ�����<a>");
			return "done";
		}
		
		for (Element lnk: links) {
			String txt = lnk.text();
			if (QXConstants.ACTIVITY_START_LK.equals(txt)) {
				//��ʼ����
				while (rest > 0) {
					String url = lnk.attr("href");
					String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
					rest--;
				}
			}
		}
		
		System.out.println("�������");
		
		return "done";
	}
	
	private String startWuhui(Document doc) throws IOException {
		QiXiongConfig qxConfig = qxContext.getQxConfig();
		
		Date now = new Date();
		int h = now.getHours();
		System.out.println("test����ǰСʱ_" + h);
		if (h < 9 || h > 22) {
			System.out.println("��ʱ���ϣ�������ԯ����");
			return "error";
		}
		
		Element tip = doc.select("p.tips").first();
		if (tip == null) {
			System.out.println("��ԯ����ʧ�ܣ��Ҳ���p.tips");
			return "error";
		}
		
		String html = "", url = "";
		Document whDoc = null;
		int rest = 0;
		
		List<Node> list = tip.childNodes();
		for (Node node: list) {
			String nodeName = node.nodeName();
			if (nodeName.contains("text") && node.outerHtml().contains("ʣ����ս����")) {
				String arr[] = node.outerHtml().split(":");
				if (arr.length != 2) {
					System.out.println("��ԯ��᣺�����ı�ʧ��[1]");
					return "error";
				}
				int idx = arr[1].indexOf("��");
				if (idx < 0) {
					System.out.println("��ԯ��᣺�����ı�ʧ��[2]");
					return "error";
				}
				try {
					rest = Integer.parseInt(arr[1].substring(0, idx));
				} catch (Exception e) {
				}
				if (rest == 0) {
					System.out.println("��ԯ��᣺ʣ�����Ϊ0");
					
					String query = String.format("a:contains(ÿ�ս���)");
					Element lnk = whDoc.select(query).first();
					if (lnk == null) {
						System.out.println("��ԯ��᷵�غ󣬲�����ȡ[0]");
						return "error";
					}
					
					html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), lnk.attr("href"));
					FileUtil.writeFile(qxConfig.getQqNumber(), html, "WHdone.html");
					System.out.println("��ԯ��᣺��ȡ������");
					return "done";
				}
			}
		}
		
		String query = String.format("a:contains(%s)", QXConstants.ACTIVITY_START_HW);
		Elements links = doc.select(query);
		if (links == null || links.size() == 0) {
			System.out.println("��ʼ��ս�������Ҳ����κ�����<a>");
			return "done";
		}
		url = links.get(0).attr("href");
		html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
		FileUtil.writeFile(qxConfig.getQqNumber(), html, "startHW.html");
		
		whDoc = Jsoup.parse(html);
		query = String.format("a:contains(%s)", QXConstants.ACTIVITY_CHOOSE_ENIMY);
		links = whDoc.select(query);
		if (links == null || links.size() == 0) {
			System.out.println("ѡ����սĿ�ꡣ�����Ҳ����κ�����<1>");
			return "done";
		}
		url = links.get(0).attr("href");
		html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
		
		whDoc = Jsoup.parse(html);
		query = String.format("a:contains(%s)", QXConstants.ACTIVITY_GO);
		links = whDoc.select(query);
		if (links == null || links.size() == 0) {
			System.out.println("ѡ����սĿ�ꡣ�����Ҳ����κ�����<2>");
			return "done";
		}
		
		System.out.println("info: ��ԯ���ʣ����ս������" + rest);
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
		query = String.format("a:contains(����)");
		Element lnk = whDoc.select(query).first();
		if (lnk == null) {
			System.out.println("��ԯ�����ɺ󣬲��ܷ���[1]");
			return "error";
		}
		
		html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), lnk.attr("href"));
		FileUtil.writeFile(qxConfig.getQqNumber(), html, "returnXYWH.html");
		
		whDoc = Jsoup.parse(html);
		query = String.format("a:contains(ÿ�ս���)");
		lnk = whDoc.select(query).first();
		if (lnk == null) {
			System.out.println("��ԯ��᷵�غ󣬲�����ȡ");
			return "error";
		}
		
		html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), lnk.attr("href"));
		FileUtil.writeFile(qxConfig.getQqNumber(), html, "WHdone.html");
		
		System.out.println("��ԯ������");
		return "done";
	}

}
