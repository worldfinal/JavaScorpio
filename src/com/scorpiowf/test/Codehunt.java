package com.scorpiowf.test;

import org.junit.Test;

public class Codehunt {
	@Test
	public void test() {
		for (int i = 0; i < 10; i++) {
	//		System.out.println(i + "," + (3<<i));
		}
		int rt = 0;
//		rt = Puzzle(new int[] { 4, -10, -3, 11, -7 });
		System.out.println(Puzzle(new int[]{-6, 6, -11, 0, -2}));
		System.out.println(Puzzle(new int[]{0, 0}));
		System.out.println(Puzzle(new int[]{14, 0}));
		System.out.println(Puzzle(new int[]{-32, 0}));
		System.out.println(Puzzle(new int[]{0, 0, 14, 14}));
		System.out.println(Puzzle(new int[]{0, 0, 0, 0, 0}));
		System.out.println(Puzzle(new int[]{4, -10, -3, 11, -7}));
		System.out.println(Puzzle(new int[]{-6, 6, -11, 0, -2}));
		
	}
	public static String Puzzle(String s) {
        s = s.trim();
        String[] arr = s.split(" ");
        String rs = "";
        for (int i = 0; i < arr.length; i++) {
        	String str = "";
            for (int j = arr[i].length() - 1; j >= 0; j--) {
            	str += arr[i].substring(j, j+1);
            }
            rs += str + " ";
        }
        rs = rs.trim();
        return rs;
    }
	public static int Puzzle(int[] a) {
		int n = a.length;
		int ansc, ansd;
		ansc = 0;
		for (int i = 0; i < n; i++) {
			int c = 0, d = 0;
			if (a[i] == 0) {
				continue;
			}
			for (int j = 0; j < n; j++) {
				if (Math.abs(a[i]) == Math.abs(a[j])) {
					c++;
					if (a[j] < 0) {
						d++;
					}
				}
			}
			if (Math.abs(c) > Math.abs(ansc)) {
				ansc = c;
				if (d % 2 == 1) {
					ansc *= -1;
				}
			}
		}
		return ansc;
	}

	public static int Puzzle(int n, int k) {
		int x = (8-k)%8;
       return n << x;
    }
	public static String getRs(char a, char b, String jin) {
		char c = jin.charAt(0);
		char t;
		if (a != b) {
			if (a == c) {
				t = b;
				b = c;
				c = t;
			}
			else if (b == c) {
				t = a;
				a = c;
				c = t;
			}
		}
		if (a == b && c == '0') {
			if (a == '0') {
				return "0";
			} else if (a == '1') {
				return "T1";
			} else if (a == 'T') {
				return "1T";
			}
		} else if (a == b && c == '1') {
			if (a == '0') {
				return "1";
			} else if (a == '1') {
				return "01";
			} else if (a == 'T') {
				return "T";
			}
		}else if (a == b && c == 'T') {
			if (a == '0') {
				return "T";
			} else if (a == '1') {
				return "1";
			} else if (a == 'T') {
				return "0T";
			}
		} else if (a != b && b != c && a != c) {
			return "0";
		}
		return "0";
	}
	public static String Puzzle(String a, String b) {
		int n = a.length();
		int m = b.length();
		int i = n - 1;
		int j = m - 1;
		String s = "", rt = "";
		char c1, c2;
		String jin = "0", ch = "";
		while (i >= 0 || j >= 0) {
			if (i <= -1) {
				c1 = '0';
			} else {
				c1 = a.charAt(i);
			}
			if (j <= -1) {
				c2 = '0';
			} else {
				c2 = b.charAt(j);
			}
			rt = getRs(c1, c2, jin);
			if (rt.length() == 2) {
				s += rt.substring(0, 1);
				jin = rt.substring(1);
			} else {
				s += rt;
				jin = "0";
			}
			i--;
			j--;
		}
		if (!"0".equals(jin)) {
			s += jin;
		}
		rt = "";
		i = s.length() - 1;
		while (i > 0 && s.charAt(i) == '0') {
			i--;
		}
		for (; i >= 0; i--) {
			rt += s.substring(i, i+1);
		}
		return rt;
	}

}
