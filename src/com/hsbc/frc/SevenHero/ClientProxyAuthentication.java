/*
 * ====================================================================
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

package com.hsbc.frc.SevenHero;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;

/**
 * A simple example that uses HttpClient to execute an HTTP request over a
 * secure connection tunneled through an authenticating proxy.
 */
public class ClientProxyAuthentication {

	public static void main(String[] args) throws Exception {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			httpclient.getCredentialsProvider().setCredentials(
					new AuthScope("133.13.162.149", 8080),
					new UsernamePasswordCredentials("43668069", "wf^O^2013"));

			HttpHost targetHost = new HttpHost("pt.3g.qq.com");
			HttpHost proxy = new HttpHost("133.13.162.149", 8080);

			BasicClientCookie netscapeCookie = null;

			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);
			// httpclient.getCookieStore().addCookie(netscapeCookie);
			httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
					CookiePolicy.BROWSER_COMPATIBILITY);

			HttpGet httpget = new HttpGet("/");
			httpget.addHeader(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.162 Safari/535.19");

			System.out
					.println("executing request: " + httpget.getRequestLine());
			System.out.println("via proxy: " + proxy);
			System.out.println("to target: " + targetHost);

			HttpResponse response = httpclient.execute(targetHost, httpget);

			HttpEntity entity = response.getEntity();

			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());

			if (entity != null) {
				System.out.println("Response content length: "
						+ entity.getContentLength());
			}
			InputStream is = entity.getContent();
			StringBuffer sb = new StringBuffer(200);
			byte data[] = new byte[65536];
			int n = 1;
			do {
				n = is.read(data);
				if (n > 0) {
					sb.append(new String(data));
				}
			} while (n > 0);

			// System.out.println(sb);
			EntityUtils.consume(entity);

			System.out.println("----------------------------------------");
			Header[] headerlist = response.getAllHeaders();
			for (int i = 0; i < headerlist.length; i++) {
				Header header = headerlist[i];
				if (header.getName().equals("Set-Cookie")) {
					String setCookie = header.getValue();
					String cookies[] = setCookie.split(";");
					for (int j = 0; j < cookies.length; j++) {
						String cookie[] = cookies[j].split("=");
						CookieManager.cookie.put(cookie[0], cookie[1]);
					}
				}
				System.out.println(header.getName() + ":" + header.getValue());
			}
			String sid = getSid(sb.toString(), "&");
			System.out.println("sid: " + sid);

			httpclient = new DefaultHttpClient();
			httpclient.getCredentialsProvider().setCredentials(
					new AuthScope("133.13.162.149", 8080),
					new UsernamePasswordCredentials("43668069", "wf^O^2013"));
			String url = Constant.openUrl + "&" + sid;
			targetHost = new HttpHost(url);

			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);
			httpget = new HttpGet("/");
			httpget.addHeader(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.162 Safari/535.19");
			Iterator it = CookieManager.cookie.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
				netscapeCookie = new BasicClientCookie((String) (key),
						(String) (value));
				netscapeCookie.setVersion(0);
				netscapeCookie.setDomain(".qq.com");
				netscapeCookie.setPath("/");
				// httpclient.getCookieStore().addCookie(netscapeCookie);
			}

			response = httpclient.execute(targetHost, httpget);

		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
	}

	public static String getSid(String str, String end) {
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

	private static boolean isHeaderEnd(StringBuffer sb) {
		int len = sb.length();
		if (len > 2) {
			if ("\n\n".equals(sb.substring(len - 2))) {
				return true;
			}
		}

		if (len > 4) {
			if ("\r\n\r\n".equals(sb.substring(len - 4))) {
				return true;
			}
		}

		return false;
	}
}
