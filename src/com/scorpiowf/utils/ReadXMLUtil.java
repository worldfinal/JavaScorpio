package com.scorpiowf.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

public class ReadXMLUtil {
	
	
	public static void main(String ar[]) {
		
	}
	@Test
	public void test() {
		String str = "<html><div id='t1'>wf</div><div id='t2'>www</div></html>";
		Document doc = Jsoup.parse(str);
		Elements list = doc.getElementsByTag("div");
		System.out.println(list.size());
	}
}
