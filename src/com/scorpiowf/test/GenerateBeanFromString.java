package com.scorpiowf.test;

import java.util.Scanner;

import org.junit.Test;

public class GenerateBeanFromString {
	public Scanner cin = new Scanner(System.in);
	
	@Test
	public void test() {
		while (cin.hasNext()) {
			String param = cin.next();
			String comment = cin.next();
			String str = "protected String ";
			str += param + ";" + "\t //" + comment;
			System.out.println(str);
		}
	}
}
