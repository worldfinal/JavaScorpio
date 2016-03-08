package com.frc.javascorpio.scheduletest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;

public class TimerTest {
	public static Scanner cin=new Scanner(System.in);
	public static void main(String[] args) throws ParseException {
		Timer timer = new Timer();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date2 = format.parse("2013-02-28 23:20:00");
		Date date1 = new Date();
		long d = date2.getTime() - date1.getTime();
		System.out.println(d);
		timer.schedule(new MyTask(), 0, 5000);
		while (true) {
			String str = cin.next();
			if (str.equals("done")) {
				timer.cancel();
				break;
			} else if (str.startsWith("ac")) {
				Timer acm = new Timer();
				TestBean tb = new TestBean();
				tb.setName(str);
				tb.setValue("acm");
				MySecondTask mst = new MySecondTask(tb, acm);
				acm.schedule(mst, 2000, 1000);
			}
			Date date = new Date();
			d = date2.getTime() - date.getTime();
			System.out.println(d + " ms left");
		}
	}
}
