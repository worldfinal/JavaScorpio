package com.frc.javascorpio.acm;

import java.util.Comparator;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class Main {
	class AC implements Comparator{
		public int x, y;
		public int idx;
		@Override
		public int compare(Object object1, Object object2) {
			AC a = (AC)object1;
			AC b = (AC)object2;
			if (a.x > b.y) {
				return 1;
			} else {
				return -1;
			}
		}
	}

	public static Scanner cin=new Scanner(System.in);
	public int[][] a = new int[520][520];
	public int[][] b = new int[520][520];
	public int[][] c = new int[520][520];
	public int[] d = new int[520];
	public int[] left = new int[520];
	public int[] right = new int[520];
	public int n;
	
	public void input() {
		n = cin.nextInt();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				a[i][j] = cin.nextInt();
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				b[i][j] = cin.nextInt();
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				c[i][j] = cin.nextInt();
			}
		}
	}
	public void init() {
		Random ramdom = new Random();
		for (int i = 0; i < 500; i++) {
			d[i] = ramdom.nextInt(20);
		}
	}
	
	public void solve(int tc) {
		for (int i = 0; i < n; i++) {
			right[i] = 0;
			left[i] = 0;
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				right[i] += c[i][j] * d[j];
				left[i] += b[i][j] * d[j];
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				right[i] -= a[i][j] * left[j];
			}
		}
		for (int i = 0; i < n; i++) {
			if (right[i] != 0){
				System.out.println("NO");
				return;
			}
		}
		System.out.println("YES");
	}
	
	
	public void acm(){
		init();
		while (cin.hasNext()) {
			input();
			solve(1);
		}
	}

	public static void main(String args[]) throws Exception{
		Main ac = new Main();
	//	ac.acm();
		long l = new Date().getTime();
		System.out.println(l);
	}
}

