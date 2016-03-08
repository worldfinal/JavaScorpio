package com.frc.scorpiowf.qixiong.processor;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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

public class LogonProcessor extends AbstractActionProcessor {
	public String LOGON_URL = "http://pt.3g.qq.com";
	public static String OPEN_URL = "&aid=nLogin&bid=4&bidfrom=sqq&go_url=http%3A%2F%2F7.3g.qq.com%2Fs%3Faid%3Dselect%26amp%3BsFrom%3Dwap";
	public final static String GD11_URL = "http://dg.7.3g.qq.com/s?aid=login&svrId=42&";
	
	public final static String LOGON_GD11 = "¹ã¶«11";
	
	public DefaultHttpClient httpclient = null;
	
	@Override
	public String process() throws Exception{
		QiXiongStatus qxStatus = qxContext.getQxStatus();
		QiXiongConfig qxConfig = qxContext.getQxConfig();
		
		HttpClientContext context = QiXiongHttpClient.retrieveContext(qxConfig.getQqNumber());
		httpclient = QiXiongHttpClient.createHttpClient(qxConfig.getQqNumber());
		
		HttpGet httpget = new HttpGet(LOGON_URL);
		QiXiongHttpClient.buildHttpGet(httpget);
		
		httpget.setHeader("Cookie", "JSESSIONID=aaanLX3sK42zP_BK_O5Hu; info_gqq=CA77lCMd; g_ut=2; 3g_pt_cvdata=xEqj1410790058268");
				
		
		CloseableHttpResponse response = httpclient.execute(httpget, context);
		String htmlResponse = QiXiongHttpClient.getHTMLresponse(response);
		
		Header ct = response.getFirstHeader("Content-Type");
		response.close();
		System.out.println(ct.getValue());
		
//		httpget.releaseConnection();
	
		// Completed: access pt.3g.qq.com
		
		String sid = QXutils.getSid(htmlResponse, "&");
		qxStatus.setSid(sid);
		if ("".equals(sid)) {
			qxStatus.setErrorCode(QXConstants.ERR_OPEN_URL);
			return "error";
		}
		// Completed: Get the sid
		
		// Action: access logon form
		String open_url = LOGON_URL + "/s?" + sid + OPEN_URL;
		httpget = new HttpGet(open_url);
		QiXiongHttpClient.buildHttpGet(httpget);
		response = httpclient.execute(httpget, context);
		htmlResponse = QiXiongHttpClient.getHTMLresponse(response);
		response.close();
//		httpget.releaseConnection();
		// Completed: access logon form
		
		// Action: parse [form name] & [form action]
		Document doc = Jsoup.parse(htmlResponse);
		Element qq_loginform = doc.getElementById("qq_loginform");
		if (qq_loginform == null) {
			qxStatus.setErrorCode(QXConstants.ERR_PARSE_FORM);
			return "error";
		}
		String logonurl = qq_loginform.attr("action");
		if (logonurl == null || "".equals(logonurl)) {
			qxStatus.setErrorCode(QXConstants.ERR_PARSE_FORM);
			return "error";
		}
		// Completed: parsing
		
		// Action: logon
		Elements inputs = qq_loginform.getElementsByTag("input");
		HttpHost targetHost = new HttpHost("pt.3g.qq.com");
		
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (int i = 0; i < inputs.size(); i++) {
			String name = inputs.get(i).attr("name");
			String value = inputs.get(i).attr("value");
			// value = URLEncoder.encode(value, "utf-8");
			if ("qq".equals(name)) {
				value = qxConfig.getQqNumber();
			} else if ("pwd".equals(name)) {
				value = qxConfig.getPassword();
//				value = URLEncoder.encode(value, "utf-8");
			} else if ("login_url".equals(name)) {
				// loginurl = value;
				// continue;
			}
			NameValuePair n1 = new BasicNameValuePair(name, value);
			list.add(n1);
		}
		NameValuePair n1 = new BasicNameValuePair("loginType", "3");
		list.add(n1);
		
		URIBuilder builder = new URIBuilder(logonurl);
		URI uri = builder.build();
		HttpPost httppost = new HttpPost(uri);

		HttpEntity requestHttpEntity = new UrlEncodedFormEntity(list);
		httppost.setEntity(requestHttpEntity);
		QiXiongHttpClient.buildHttpPost(httppost);

		response = httpclient.execute(targetHost, httppost, context);
		String htmlString = QiXiongHttpClient.getHTMLresponse(response);
		Header header = response.getLastHeader("location");
//		httppost.releaseConnection();
		response.close();
		
		if (header != null) {
			htmlString = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), header.getValue());
			
			doc = Jsoup.parse(htmlString);
			Element logon = doc.select(
					String.format("a:contains(%s)", LOGON_GD11)).first();
			if (logon != null) {
				String html = QiXiongHttpClient.sendGetRequest(qxConfig.getQqNumber(), logon.attr("href"));
				FileUtil.writeFile(qxConfig.getQqNumber(), html, "GD11.html");
				doc = Jsoup.parse(html);
				qxStatus.setHomeDoc(doc);
			} else {
				qxStatus.setErrorCode(QXConstants.ERR_LOGON);
				return "error";
			}
		} else {
			qxStatus.setErrorCode(QXConstants.ERR_LOGON);
			return "error";
		}
		
		return "done";
	}

}
