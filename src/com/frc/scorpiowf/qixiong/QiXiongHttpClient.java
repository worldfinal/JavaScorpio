package com.frc.scorpiowf.qixiong;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


public class QiXiongHttpClient {
	protected static Map<String, DefaultHttpClient>httpPool = new HashMap<String, DefaultHttpClient>();
	protected static Map<String, HttpClientContext>contextPool = new HashMap<String, HttpClientContext>();
	
//	public final static String useragnet = "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MI 3 Build/KOT49H) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/9.9.2.467 U3/0.8.0 Mobile Safari/533.1";
	public final static String useragnet = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5";
	public final static String accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";
	public final static String acceptEncoding = "sdch";
	public final static String acceptLanguage = "zh-CN,zh;q=0.8";
	public final static String acceptCharset = "ISO-8859-1,UTF-8;q=0.7,*;q=0.3";
	
	protected final static HttpHost PC_Proxy = new HttpHost("127.0.0.1", 8888);
	
	public static DefaultHttpClient createHttpClient(String number) {
		DefaultHttpClient httpclient = null;
		httpclient = httpPool.get(number);
		if (httpclient == null) {
			httpclient = new DefaultHttpClient();
			HttpClientContext context = HttpClientContext.create();
//			httpclient.getCredentialsProvider().setCredentials(AuthScope.ANY, creds);	
//			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, PC_Proxy);
			httpPool.put(number, httpclient);
			contextPool.put(number, context);
		}	
		return httpclient;
	}
	
	public static HttpClientContext retrieveContext(String number) {
		return contextPool.get(number);
	}
	
	public static void buildHttpGet(HttpGet httpget) {
		httpget.addHeader("User-Agent", useragnet);
		httpget.addHeader("Accept", accept);
		httpget.addHeader("Accept-Encoding", acceptEncoding);
		httpget.addHeader("Accept-Language", acceptLanguage);
//		httpget.addHeader("Accept-Charset", acceptCharset);
		httpget.addHeader("Cache-Control", "max-age=0");
	}
	public static void buildHttpPost(HttpPost httppost) {
		httppost.addHeader("User-Agent", useragnet);
		httppost.addHeader("Accept", accept);
		httppost.addHeader("Accept-Encoding", acceptEncoding);
		httppost.addHeader("Accept-Language", acceptLanguage);
		httppost.addHeader("Accept-Charset", acceptCharset);
	}
	
	public static String getHTMLresponse(HttpResponse response) throws IllegalStateException, IOException {
		HttpEntity entity = response.getEntity();
		String charSet = EntityUtils.getContentCharSet(entity);
		byte[] data = EntityUtils.toByteArray(entity);
		String rt = new String(data, "UTF-8");
		return rt;
	}
	
	public static String sendGetRequest(String qqNumber, String url) throws IOException {
		DefaultHttpClient httpclient = null;
		httpclient = createHttpClient(qqNumber);
		
		HttpClientContext context = contextPool.get(qqNumber);
		HttpGet httpget = new HttpGet(url);
		buildHttpGet(httpget);

		HttpResponse response;
		try {
			sleep(500);
			response = httpclient.execute(httpget, context);
			String html = getHTMLresponse(response);
			httpget.releaseConnection();
			return html;
		} catch (ClientProtocolException e) {
			System.out.println("!!--" + url.length() + " " + url);			
			throw e;
		} catch (IOException e) {
			throw e;
		}
	}
	
	protected static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
