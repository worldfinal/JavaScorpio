package com.scorpiowf.utils;

public class StringUtil {
	public static boolean isEmpty(String str) {
		return str == null;
	}
	public static String getStringValue(String str) {
		return str == null ? "" : str;
	}
}
