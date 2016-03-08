package com.frc.scorpiowf.qixiong.utils;

import org.junit.Test;

public class ConverterUtil {
	public static int toInteger(String str) {
		int rt = 0;
		if (str == null || str.trim().length() == 0) {
			return 0;
		}
		String num = str.trim();
		boolean isValid = num.matches("[0-9]*");
		if (isValid) {
			rt = Integer.parseInt(num);
		}
		return rt;
	}
	
	@Test
	public void test() {
		String str = "";
		
		str = "a1";
		System.out.println(ConverterUtil.toInteger(str));
		
		str = "	";
		System.out.println(ConverterUtil.toInteger(str));
		
		str = " ";
		System.out.println(ConverterUtil.toInteger(str));
		
		str = "3123.2";
		System.out.println(ConverterUtil.toInteger(str));
		
		str = "1";
		System.out.println(ConverterUtil.toInteger(str));
		
		str = "654321789";
		System.out.println(ConverterUtil.toInteger(str));
		
		str = ".1";
		System.out.println(ConverterUtil.toInteger(str));
	}
}
