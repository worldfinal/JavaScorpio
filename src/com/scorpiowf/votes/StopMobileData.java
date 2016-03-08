package com.scorpiowf.votes;

import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.Test;


public class StopMobileData {
	public static Scanner cin=new Scanner(System.in);
	
	@Test
	public void test() {
		System.out.println("Process start");
		p1();
		cin.next();
		System.out.println("Process end");
	}

	public void p1() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println("P1 is running");
				p2();
				System.out.println("P1 done");
			}

		}, 1000, 500);
	}

	public void p2() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println("P2 is running");
				System.out.println("P2 done");
			}
		}, 3000);
	}

}
