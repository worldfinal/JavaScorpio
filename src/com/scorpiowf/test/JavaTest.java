package com.scorpiowf.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.internet.MimeUtility;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.scorpiowf.utils.IOUtil;

public class JavaTest {
	@Test
	public void testEncode() {
		String str = IOUtil.readTxtFile("d:\\sudoku.txt");
		sun.misc.BASE64Decoder encoder = new sun.misc.BASE64Decoder();
		byte[] data = null;
		try {
			data = encoder.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		IOUtil.writeBytesToFile(data, "d:\\sudoku.zip");
	}
	@Test
	public void nls() throws UnsupportedEncodingException {
		String s1 = "中文，不应该乱码的,WF";
		String s2 = new String("中文，不应该乱码的,WF".getBytes("UTF8"));
		String s0 = MimeUtility.encodeText(s1);
		String s3 = MimeUtility.encodeText(s2);
		System.out.println(s0);
		System.out.println(s3);
	}
	@Test
	public void cal() {
		int a = 1, b = 2;
		int d = 2;
		int n = 100;
		int an = a + (n-1) * d;
		int bn = b + (n-1) * d;
		System.out.println("an = " + an);
		System.out.println("bn = " + bn);
		int sn1 = (a + an) * n / 2;
		int sn2 = (b + bn) * n / 2;
		System.out.println("sn1 = " + sn1);
		System.out.println("sn2 = " + sn2);
		int ans = (a + an) * n / 2 + an + (b + bn) * n / 2;
		System.out.println(ans);
	}
	@Test
	public void aboutString() {
		String str = "13123@danny";
		int idx = str.indexOf("@");
		String rs = str.substring(idx+1);
		System.out.println(rs);
	}

	
	@Test
	public void testjson() {
		ParamBean bean = new ParamBean();
		bean.setCountryCode("USA");
		bean.setCurrency("CNY");
		JSONObject obj = JSONObject.fromObject(bean);
		System.out.println(obj.toString());
		String str = "{\"currency\":\"danny\"}";
		obj = JSONObject.fromObject(str);
		System.out.println(obj);
		bean = (ParamBean)JSONObject.toBean(obj, ParamBean.class);
		System.out.println(bean.getCurrency());
	}
}
