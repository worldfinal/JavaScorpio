package com.frc.javascorpio.http;

import org.apache.commons.codec.EncoderException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.scorpiowf.utils.IOUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;
public class WmcloudDemo {
    //创建http client
    private static CloseableHttpClient httpClient = createHttpsClient();
    private static final String ACCESS_TOKEN = "9c9decb3bbe7bac25c1de53d06b352a49319245e38b95815f8923e51bd55f192";

    
    public static void main(String[] args) throws IOException, EncoderException {
        //根据api store页面上实际的api url来发送get请求，获取数据
        String url = "https://api.datayes.com:443/data/v1/api/market/getMktEqud.json?field=&beginDate=&endDate=&secID=&ticker=&tradeDate=";
        url = "https://gw.wmcloud.com/dataquery//dataapi/?input=url+";
//        String param = "/api/market/getMktEqud.json?field=&beginDate=&endDate=&secID=&ticker=&tradeDate=20160408";
        String param = "/api/subject/getNewsInfoByTime.json?field=&beginTime=&endTime=&newsPublishDate=20160408";
        param = "/api/fund/getFundHoldings.json?field=&beginDate=&endDate=&secID=&ticker=000545&reportDate=";
        param = "/api/equity/getEquIPO.json?field=&ticker=000100&secID=&eventProcessCD=";
        param = "/api/market/getMktEqud.json?field=&beginDate=20160101&endDate=20160408&secID=&ticker=000100&tradeDate=";
        param = "/api/market/getStockFactorsOneDay.json?field=&secID=&ticker=000100&tradeDate=20160104";
        param = java.net.URLEncoder.encode(param, "UTF-8");
        url += param;
//        url = "https://gw.wmcloud.com/dataquery//dataapi/?input=url+%2Fapi%2Ffund%2FgetFundRating.csv%3Ffield%3D%26ticker%3D161610%26secID%3D%26beginDate%3D%26endDate%3D%26callback%3D&pretty=true";
        System.out.println(url);
        HttpGet httpGet = new HttpGet(url);
        //在header里加入 Bearer {token}，添加认证的token，并执行get请求获取json数据
        httpGet.addHeader("Authorization", "Bearer " + ACCESS_TOKEN);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
//        System.out.println(body);
        
        JSONObject js = JSONObject.fromObject(body);
        JSONArray arr = (JSONArray)js.get("results");
        for (int i = 0; i < arr.size(); i++) {
        	JSONObject obj = arr.getJSONObject(i);
        	String data = (String)obj.get("data");
        	IOUtil.writeStringToFile("d:\\TCL_20160104_因子数据", data);
            System.out.println(data);
        }
        
        
    }
    //创建http client
    public static CloseableHttpClient createHttpsClient() {
        X509TrustManager x509mgr = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] xcs, String string) {
            }
            @Override
            public void checkServerTrusted(X509Certificate[] xcs, String string) {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        //因为java客户端要进行安全证书的认证，这里我们设置ALLOW_ALL_HOSTNAME_VERIFIER来跳过认证，否则将报错
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509mgr}, null);
            sslsf = new SSLConnectionSocketFactory(
                    sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }
}