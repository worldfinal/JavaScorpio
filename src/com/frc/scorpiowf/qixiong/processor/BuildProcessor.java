package com.frc.scorpiowf.qixiong.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
import com.frc.scorpiowf.qixiong.utils.ConverterUtil;
import com.frc.scorpiowf.qixiong.utils.FileUtil;

public class BuildProcessor extends AbstractActionProcessor {

	public DefaultHttpClient httpclient = null;
	
	@Override
	public String process() throws Exception {
		QiXiongStatus qxStatus = qxContext.getQxStatus();
		QiXiongConfig qxConfig = qxContext.getQxConfig();
		httpclient = QiXiongHttpClient.createHttpClient(qxConfig.getQqNumber());
		
		Document doc = qxStatus.getHomeDoc();
		if (doc == null) {
			qxStatus.setErrorCode(QXConstants.ERR_NO_HOME_DOC);
			return "error";
		}
		
		Element tip = doc.select("p.tips").first();
		if (tip == null) {
			System.out.println("建筑失败：找不到p.tips");
			return "error";
		}
		boolean pendingBuild = false;
		int upgradeCount = 0;
		List<Node> list = tip.childNodes();
		for (Node node: list) {
			String nodeName = node.nodeName();
			if (nodeName.contains("a") && node.outerHtml().contains(QXConstants.HOME_BUILD)) {
				Node bNode = node.nextSibling();
				String buildTxt = bNode.outerHtml();
				int startIdx = buildTxt.indexOf("(");
				int sepIdx = buildTxt.indexOf("/");
				int endIdx = buildTxt.indexOf(")");
				if (startIdx == -1 || sepIdx == -1 || endIdx == -1
						|| startIdx >= sepIdx || sepIdx >= endIdx) {
					System.out.println("找不到建筑信息");
					return "error";
				}
				String s1 = buildTxt.substring(startIdx + 1, sepIdx);
				String s2 = buildTxt.substring(sepIdx + 1, endIdx);
				System.out.println(s1 + "/" + s2);
				
				int n1 = ConverterUtil.toInteger(s1);
				int n2 = ConverterUtil.toInteger(s2);
				upgradeCount = n2 - n1;
				if (n1 < n2) {
					pendingBuild = true;
					break;
				} else {
					//Nothing pending build
					return "done";
				}
			}
		}
		
		if (pendingBuild == false) {
			return "done";
		}
		
		List<BuildingInfo> buildingList = retrieveUpgradeUrl();
		
		Collections.sort(buildingList, new Comparator<BuildingInfo>(){

			@Override
			public int compare(BuildingInfo arg0, BuildingInfo arg1) {
				return arg0.weight - arg1.weight;
			}
			
		});
		
//		System.out.println("======== Let's have a sort ============");
		for (int i = 0; i < buildingList.size(); i++) {
			BuildingInfo buildInfo = buildingList.get(i);
///			System.out.println(String.format("[%5s] [%3d] %s", buildInfo.name, buildInfo.level, buildInfo.url));
		}
		
		upgradeBuilding(upgradeCount, buildingList);
		
		return "done";
	}
	
	private String upgradeBuilding(int count, List<BuildingInfo> buildingList) throws IOException {
		QiXiongConfig qxConfig = qxContext.getQxConfig();
		
		int done = 0;
		int topLevel = 1000;
		if (buildingList.get(0).name.contains("官府")) {
			topLevel = buildingList.get(0).level;
		}
		for (int i = 0; i < buildingList.size() && done < count; i++) {
			BuildingInfo buildInfo = buildingList.get(i);
			if (i > 0 && buildInfo.level >= topLevel) {
				continue;
			}
			String rtStr = processUpgrade(buildInfo);
			if (rtStr.equals("done")) {
				done++;
				System.out.println(String.format("[%s (Level%d)]成功升级", buildInfo.name, buildInfo.level));
			}
		}
		if (done == count && done > 0) {
			System.out.println("建筑成功升级！！");
		}
		return "done";
	}
	
	private String processUpgrade(BuildingInfo buildInfo) throws IOException {
		QiXiongConfig qxConfig = qxContext.getQxConfig();
		String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), buildInfo.url);
		FileUtil.writeFile(qxConfig.getQqNumber(), html, String.format("Build_%s_%d.html", buildInfo.name, buildInfo.level));
		
		if (html.contains(QXConstants.BUILD_FORBIT)) {
			return "fail";
		}
		
		Document doc = Jsoup.parse(html);
		Element link = doc.select(String.format("a:contains(%s)", QXConstants.BUILD_CONFIRM)).first();
		if (link == null) {
			return "fail";
		}
		
		html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), link.attr("href"));
		FileUtil.writeFile(qxConfig.getQqNumber(), html, String.format("SuccessBuild_%s_%d.html", buildInfo.name, buildInfo.level));
		if (!html.contains("成功")) {
			return "fail";
		}
		
		return "done";
	}
	
	private List<BuildingInfo> retrieveUpgradeUrl() throws IOException {
		QiXiongStatus qxStatus = qxContext.getQxStatus();
		QiXiongConfig qxConfig = qxContext.getQxConfig();
		
		Document doc = qxStatus.getBuildDoc();
		Elements es = doc.select(String.format("a:contains(%s)", QXConstants.BUILD_DETAIL_LIST));
		if (es == null || es.size() != 1) {
			System.out.println(String.format("'%s' url does not exist", QXConstants.BUILD_DETAIL_LIST));
			return null;
		}
		
		Element e = es.first();
		String url = e.attr("href");
		String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
		FileUtil.writeFile(qxConfig.getQqNumber(), html, "BuildDetail.html");

		List<BuildingInfo> resultList = new ArrayList<BuildingInfo>(); 
				
		doc = Jsoup.parse(html);
		parseBuildingInformation(resultList, doc);
		
		Elements links = doc.select("a:contains(部)");
		for (Element link : links) {
//			System.out.println("===========  " + link.text() + "  ===========");
			url = link.attr("href");
			html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), url);
			
			FileUtil.writeFile(qxConfig.getQqNumber(), html, String.format("Build_%s.html", link.text()));
			
			Document buildListDoc = Jsoup.parse(html);
			String rtStr = parseBuildingInformation(resultList, buildListDoc);
		}
		
		return resultList;
	}
	
	private String parseBuildingInformation(List<BuildingInfo> list, Document doc) {
		Element tip = doc.select("p.tips").first();
		if (tip == null) {
			System.out.println("建筑失败：找不到p.tips");
			return null;
		}
		
		Elements links = doc.select(String.format("a:contains(%s)", QXConstants.BUILD_UPGRADE));
		int idx = 0;
		
		List<Node> nodeList = tip.childNodes();
		for (Node node: nodeList) {
			String nodeName = node.nodeName();
			String txt = node.outerHtml().trim();
//			System.out.println("===>" + nodeName + " ==>" + node.outerHtml());
			if (nodeName.contains("text") && 
					(txt.contains("等级") || txt.contains(QXConstants.BUILD_ARMY) || txt.contains(QXConstants.BUILD_HOUSE))) {
				if (idx >= links.size()) {
					return "Error";
				}
				String url = links.get(idx).attr("href");
				idx++;
				BuildingInfo buildInfo = new BuildingInfo();
				buildInfo.url = url;
				if (txt.contains("等级")) {
					buildInfo.parse(txt);
				} else {
					buildInfo.level = 0;
					buildInfo.name = txt;
					buildInfo.weight = -1000;
				}
				
				list.add(buildInfo);
//				System.out.println(String.format("[%5s] [%3d] %s", buildInfo.name, buildInfo.level, buildInfo.url));
			}
			
		}
		
		return "done";
	}

}

class BuildingInfo {
	public String name;
	public int level;
	public String url;
	public int weight;
	
	public void parse(String txt) {
		this.name = "";
		this.level = -1;
		
		String arrs[] = txt.split(" ");
		if (arrs.length != 2) {
			return;
		}
		this.name = arrs[0];
		
		if (!arrs[1].startsWith("等级")) {
			return;
		}
		
		String strLvl = "";
		if (arrs[1].startsWith("等级:")) {
			strLvl = arrs[1].substring("等级:".length());
		} else {
			strLvl = arrs[1].substring("等级".length());
		}
		this.level = ConverterUtil.toInteger(strLvl.trim());
		if (this.name.contains("官府")) {
			this.weight = -10000;
		} else if (this.name.contains("书院")) {
			this.weight = -9999;
		} else if (this.name.contains("城墙")) {
			this.weight = -9998;
		} else if (this.name.contains("司徒署") || this.name.contains("司马署")) {
			this.weight = -9997;
		} else if (this.name.contains("修炼馆")) {
			this.weight = -9996;
		} else if (this.name.contains("工坊")) {
			this.weight = -9995;
		} else {
			this.weight = this.level;
		}
	}
}
