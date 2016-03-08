package com.hsbc.frc.SevenHero;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.exolab.castor.builder.SourceGeneratorMain;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import com.hsbc.frc.SevenHero.beans.Hero;
import com.hsbc.frc.SevenHero.beans.Heros;
import com.hsbc.frc.SevenHero.beans.SevenHeroConfig;

public class BaseHero {
  public final String beforeLogonUrl = "http://pt.3g.qq.com/";
  public final String logonUrl = "";
	public String sid;
	
	protected final String hsbcProxyString = "intpxy1.hk.hsbc";
	protected final String myProxyString = "133.13.162.149";
	protected final HttpHost PC_Proxy = new HttpHost(hsbcProxyString, 8080);
	protected final AuthScope authScope = new AuthScope(hsbcProxyString, 8080);
	protected final UsernamePasswordCredentials upc = new UsernamePasswordCredentials("43668069", "wf^O^2013");
	protected final NTCredentials creds = new NTCredentials("43668069", "wf^O^2013", "wf", "HBAP");
	protected static DefaultHttpClient httpclient = null;
	protected Map<String, DefaultHttpClient>httpPool = new HashMap<String, DefaultHttpClient>();
	public static Object lock = new Object();
	protected static Logger l = Logger.getLogger(BaseHero.class);
	protected static Log log  = LogFactory.getLog(BaseHero.class); 
	
	public BaseHero() {
		
	}
	
	public DefaultHttpClient createHttpClient(String number) {
		DefaultHttpClient httpclient = null;
		httpclient = httpPool.get(number);
		if (httpclient == null) {
			httpclient = new DefaultHttpClient();
	//		httpclient.getCredentialsProvider().setCredentials(AuthScope.ANY, creds);	
	//		httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, PC_Proxy);
			httpPool.put(number, httpclient);
		}	
//		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 40000);
//		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 40000);
		return httpclient;
	}
	
	public String getSid(String str, String end) {
		int idx = str.indexOf("sid=");
		if (idx == -1) {
			return "";
		}
		String result = str.substring(idx);
		idx = result.indexOf(end);
		if (idx == -1) {
			return "";
		}
		return result.substring(0, idx);
	}
	
	public void buildHttpGet(HttpGet httpget) {
		httpget.addHeader("User-Agent", Constant.useragnet);
		httpget.addHeader("Accept", Constant.accept);
		httpget.addHeader("Accept-Encoding", Constant.acceptEncoding);
		httpget.addHeader("Accept-Language", Constant.acceptLanguage);
		httpget.addHeader("Accept-Charset", Constant.acceptCharset);
		httpget.addHeader("Cache-Control", "max-age=0");
	}
	public void buildHttpPost(HttpPost httppost) {
		httppost.addHeader("User-Agent", Constant.useragnet);
		httppost.addHeader("Accept", Constant.accept);
		httppost.addHeader("Accept-Encoding", Constant.acceptEncoding);
		httppost.addHeader("Accept-Language", Constant.acceptLanguage);
		httppost.addHeader("Accept-Charset", Constant.acceptCharset);
	}
	public String getHTMLresponse(HttpResponse response) throws IllegalStateException, IOException {
		HttpEntity entity = response.getEntity();
		byte[] data = EntityUtils.toByteArray(entity);
		String rt = new String(data, "utf8");
		return rt;
	}

	protected void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static SevenHeroConfig readXML() {
		InputStream is = null;
		try {
			is = new FileInputStream("source/SevenHero.xml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Reader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		SevenHeroConfig wf = null;
		try {
			wf = SevenHeroConfig.unmarshal(reader);
		} catch (MarshalException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
		}
		return wf;
	}
	public static void saveXML(SevenHeroConfig shc) {
		OutputStream os = null;
		try {
			os = new FileOutputStream("source/SevenHero.xml");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			shc.marshal(out);
		} catch (MarshalException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
		}
		if (out != null) {
	//		System.out.println(out.toString());
		}
	}
	private static void generate7xBeans() {
		String xsdFilePath = "source\\SevenHero.xsd";
		String packageName = "com.hsbc.frc.SevenHero.beans";
		String arr[] = new String[]{"-i", xsdFilePath
				, "-package", packageName
				, "-dest", "c:\\acm"
				};
		SourceGeneratorMain.main(arr);
	}
	public static void main(String args[]) throws IOException {
		log.info("acmicpc");
		l.info("iiiii");
		BaseHero b= new BaseHero();
		
//		generate7xBeans();
	}
	
	public int getDelay(String number) {
		if (0 < 1) {
			return -1;
		}
		String url = "http://api.plnkr.co/plunks/i84M98tuAZaUThpnVBPx";
		String html = "";
		try {
			html = sendGetRequest(url);
		} catch (IOException e) {
			e.printStackTrace();
			return -2;
		}
		int idx1 = html.indexOf("acmicpcstart") + "acmicpcstart".length();
		int idx2 = html.indexOf("acmicpcend");
		html = html.substring(idx1, idx2);
		html = html.replace("\\n", "\n").trim();
		String arr[] = html.split("\n");
		for (int i = 0; i < arr.length; i++) {
			String msgs = arr[i];
			String msg[] = msgs.split("=");
			if (msg.length >= 2) {
				if (msg[0].equals(number)) {
					String data[] = msg[1].split(",");
					if (data.length >= 2) {
						if ("false".equals(data[1])) {
							return -1;
						}
						return Integer.parseInt(data[0]);
					}
				}
			}
		}
		return -1;
	}
	public void test() throws IOException {
		
		httpclient = createHttpClient("111");
		String url = "http://api.plnkr.co/plunks/i84M98tuAZaUThpnVBPx";
		String html = sendGetRequest(url);
		int idx1 = html.indexOf("acmicpcstart") + "acmicpcstart".length();
		int idx2 = html.indexOf("acmicpcend");
		html = html.substring(idx1, idx2);
		html = html.replace("\\n", "\n").trim();
		String arr[] = html.split("\n");
		for (int i = 0; i < arr.length; i++) {
			String msgs = arr[i];
			String msg[] = msgs.split("=");
			if (msg.length >= 2) {
				System.out.println("number: " + msg[0]);
				String data[] = msg[1].split(",");
				for (int j = 0; j < data.length; j++) {
					System.out.println(j + " " + data[j]);
				}
			}
		}
		System.out.println(html);
	}
	public String sendGetRequest(String url) throws IOException {

		sleep(500);
		HttpGet httpget = new HttpGet(url);
		buildHttpGet(httpget);

		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
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
}
