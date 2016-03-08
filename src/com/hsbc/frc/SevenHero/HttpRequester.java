package com.hsbc.frc.SevenHero;

import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;  
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;  
import java.nio.charset.Charset;  
import java.util.HashMap;
import java.util.Map;  
import java.util.Vector;  

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.Proxy.Type;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Vector;
 
/**
 * HTTP请求对象
 * 
 * @author YYmmiinngg
 */
public class HttpRequester {
	private String defaultContentEncoding;
	public Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress("133.13.162.149", 8080));
//	intpxy1.hk.hsbc
	//	133.13.162.149 
	public HttpRequester() {
		this.defaultContentEncoding = Charset.defaultCharset().name();
	}
 
	/**
	 * 发送GET请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpRespons sendGet(String urlString) throws IOException {
		return this.send(urlString, "GET", null, null);
	}
 
	/**
	 * 发送GET请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpRespons sendGet(String urlString, Map<String, String> params)
			throws IOException {
		return this.send(urlString, "GET", params, null);
	}
 
	/**
	 * 发送GET请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @param propertys
	 *            请求属性
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpRespons sendGet(String urlString, Map<String, String> params,
			Map<String, String> propertys) throws IOException {
		return this.send(urlString, "GET", params, propertys);
	}
 
	/**
	 * 发送POST请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpRespons sendPost(String urlString) throws IOException {
		return this.send(urlString, "POST", null, null);
	}
 
	/**
	 * 发送POST请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpRespons sendPost(String urlString, Map<String, String> params)
			throws IOException {
		return this.send(urlString, "POST", params, null);
	}
 
	/**
	 * 发送POST请求
	 * 
	 * @param urlString
	 *            URL地址
	 * @param params
	 *            参数集合
	 * @param propertys
	 *            请求属性
	 * @return 响应对象
	 * @throws IOException
	 */
	public HttpRespons sendPost(String urlString, Map<String, String> params,
			Map<String, String> propertys) throws IOException {
		return this.send(urlString, "POST", params, propertys);
	}
 
	/**
	 * 发送HTTP请求
	 * 
	 * @param urlString
	 * @return 响映对象
	 * @throws IOException
	 */
	private HttpRespons send(String urlString, String method,
			Map<String, String> parameters, Map<String, String> propertys)
			throws IOException {
		HttpURLConnection urlConnection = null;
 
		if (method.equalsIgnoreCase("GET") && parameters != null) {
			StringBuffer param = new StringBuffer();
			int i = 0;
			for (String key : parameters.keySet()) {
				if (i == 0)
					param.append("?");
				else
					param.append("&");
				param.append(key).append("=").append(parameters.get(key));
				i++;
			}
			urlString += param;
		}
		
		URL url = new URL(urlString);
		urlConnection = (HttpURLConnection) url.openConnection(proxy);
		/*
		String sessionId = "";
		String ackey = "";
		String cookieVal = null;
		System.out.println("=============Cookie==================");
			for (int i = 1; (ackey = urlConnection.getHeaderFieldKey(i)) != null; i++) {
				System.out.println(ackey + " : " + urlConnection.getHeaderField(i));
				if (ackey.equalsIgnoreCase("set-cookie")) {
					cookieVal = urlConnection.getHeaderField(i);
					if (cookieVal.indexOf(";") > -1) {
						cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));
					}
					sessionId = sessionId + cookieVal + ";";
				}
			}
	//		InputStream in = urlConnection.getInputStream();
			System.out.println("=============Cookie==================");
			System.out.println(sessionId);
		

		urlConnection = (HttpURLConnection) url.openConnection(proxy);
		urlConnection.setRequestProperty("Cookie", sessionId);
		*/
		urlConnection.setRequestMethod(method);
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.setUseCaches(false);
		urlConnection.setInstanceFollowRedirects(true);
//		urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.162 Safari/535.19");
		
	//	urlConnection.connect();
 
		if (propertys != null)
			for (String key : propertys.keySet()) {
				urlConnection.addRequestProperty(key, propertys.get(key));
			}
 
		if (method.equalsIgnoreCase("POST") && parameters != null) {
			StringBuffer param = new StringBuffer();
			for (String key : parameters.keySet()) {
				param.append("&");
				param.append(key).append("=").append(parameters.get(key));
			}
			urlConnection.getOutputStream().write(param.toString().getBytes());
			urlConnection.getOutputStream().flush();
			urlConnection.getOutputStream().close();
		}
 
		return this.makeContent(urlString, urlConnection);
	}
 
	/**
	 * 得到响应对象
	 * 
	 * @param urlConnection
	 * @return 响应对象
	 * @throws IOException
	 */
	private HttpRespons makeContent(String urlString,
			HttpURLConnection urlConnection) throws IOException {
		HttpRespons httpResponser = new HttpRespons();
		try {
			InputStream in = urlConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(in));
			httpResponser.contentCollection = new Vector<String>();
			StringBuffer temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				httpResponser.contentCollection.add(line);
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
 
			String ecod = urlConnection.getContentEncoding();
			if (ecod == null)
				ecod = this.defaultContentEncoding;
 
			httpResponser.urlString = urlString;
 
			httpResponser.defaultPort = urlConnection.getURL().getDefaultPort();
			httpResponser.file = urlConnection.getURL().getFile();
			httpResponser.host = urlConnection.getURL().getHost();
			httpResponser.path = urlConnection.getURL().getPath();
			httpResponser.port = urlConnection.getURL().getPort();
			httpResponser.protocol = urlConnection.getURL().getProtocol();
			httpResponser.query = urlConnection.getURL().getQuery();
			httpResponser.ref = urlConnection.getURL().getRef();
			httpResponser.userInfo = urlConnection.getURL().getUserInfo();
 
			httpResponser.content = new String(temp.toString().getBytes(), ecod);
			httpResponser.contentEncoding = ecod;
			httpResponser.code = urlConnection.getResponseCode();
			httpResponser.message = urlConnection.getResponseMessage();
			httpResponser.contentType = urlConnection.getContentType();
			httpResponser.method = urlConnection.getRequestMethod();
			httpResponser.connectTimeout = urlConnection.getConnectTimeout();
			httpResponser.readTimeout = urlConnection.getReadTimeout();
			
			String setcookie = urlConnection.getHeaderField("set-cookie");
			System.out.println(setcookie);

			return httpResponser;
		} catch (IOException e) {
			throw e;
		} finally {
			if (urlConnection != null)
				urlConnection.disconnect();
		}
	}
 
	/**
	 * 默认的响应字符集
	 */
	public String getDefaultContentEncoding() {
		return this.defaultContentEncoding;
	}
 
	/**
	 * 设置默认的响应字符集
	 */
	public void setDefaultContentEncoding(String defaultContentEncoding) {
		this.defaultContentEncoding = defaultContentEncoding;
	}
	
	public static void main(String[] args) {  
        try {  
            HttpRequester request = new HttpRequester();  
            Map<String, String>map = new HashMap<String, String>();
            map.put("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.162 Safari/535.19");
            HttpRespons hr = request.sendGet("http://pt.3g.qq.com/", null, map);  
   
            System.out.println(hr.getUrlString());  
            System.out.println(hr.getProtocol());  
            System.out.println(hr.getHost());  
            System.out.println(hr.getPort());  
            System.out.println(hr.getContentEncoding());  
            System.out.println(hr.getMethod());  
              
            System.out.println(hr.getContent());  
   
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
	
	public HttpRespons wfsend(String urlString, String method,
			String parameters, Map<String, String> propertys)
			throws IOException {
		HttpURLConnection urlConnection = null;
 
		if (method.equalsIgnoreCase("GET") && parameters != null) {
			StringBuffer param = new StringBuffer(parameters);
			urlString += param;
		}

		URL url = new URL(urlString);
		urlConnection = (HttpURLConnection) url.openConnection(proxy);
		
		urlConnection.setRequestMethod(method);
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.setUseCaches(false);
//		urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.162 Safari/535.19");
		
	//	urlConnection.connect();
 
		if (propertys != null)
			for (String key : propertys.keySet()) {
				urlConnection.addRequestProperty(key, propertys.get(key));
			}
 
		if (method.equalsIgnoreCase("POST") && parameters != null) {
			StringBuffer param = new StringBuffer(parameters);
			urlConnection.getOutputStream().write(param.toString().getBytes());
			urlConnection.getOutputStream().flush();
			urlConnection.getOutputStream().close();
		}
 
		return this.makeContent(urlString, urlConnection);
	}
}
