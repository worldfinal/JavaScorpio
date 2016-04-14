package com.frc.javascorpio.acm;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.junit.Test;
import org.springframework.util.Base64Utils;

public class JavaTest {
	@Test
	public void test1() {
		String s = "20124411";
		System.out.println(s.substring(0,4));
	}
	@Test
	public void testGenerateFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMmm'T'hhmmss'Z'");
		Date date = new Date();
		String str = sdf.format(date);
		String rs = str;
		for (int i = 0; i < 4; i++) {
			int r = (int)(Math.random() * 100123);
			r %= 26;
			rs += (char)(r+'A');
		}
		System.out.println(rs);
	}
	@Test
	public void testPattern() {
		String pattern = "[\\x00-\\xff\\u4e00-\\u9fa5]*";
		String s = "";
		System.out.println(s.matches(pattern));
		s = new String(Base64Utils.decode(s.getBytes()));
		System.out.println(s.matches(pattern));
	}
	@Test
	public void testEncode() {
		String s = "77+977+977+977+9xa4uanBn";
		String arr[] = new String[]{
				"77+977+91qrKssO077+92Lfvv73vv73vv73vv73vv73vv73vv70gIDA3MzB3OS5qcGc=",
				"77+977+977+977+9xa4uanBn",
				"77+977+977+9MjAuanBn",
				"77+977+977+9MjMuanBn",
				"77+977+9LmpwZw=="
		};
		for (int i = 0; i < arr.length; i++) {
			s = arr[i];
			byte[] data = Base64.getDecoder().decode(s.getBytes());
			s = new String(data);
			System.out.println(s);
		}
		
	}
	@Test
	public void testArray() {
		double arr[][] = new double[3][];
		for (int i = 0; i < 3; i++) {
			arr[i] = new double[2 * i + 3];
			System.out.println(arr[i].length);
		}
	}
	
	@Test
	public void testCL() {
		System.out.println(org.springframework.aop.Advisor.class.getProtectionDomain().getCodeSource());
	}
}
