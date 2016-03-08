package com.frc.scorpiowf.qixiong.utils;

public class QXutils {
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
}
