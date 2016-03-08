package com.frc.javascorpio.topcoder;

public class PenguinSledding {
	public boolean use[] = new boolean[100];
	public int degree[] = new int[100];
	public boolean c[][] = new boolean[60][60];
	public int n;
	public long ans,edge;
	public void dfs(int node) {
		use[node] = true;
		long x = degree[node];
		edge += x;
		if (x == 0) {
			return;
		}
		for (int i = 1; i <= n; i++) {
			if (!use[i] && c[node][i]) {
				dfs(i);
			}
		}
		ans += ((long)1 << (long)x) - 1;
	}
	public long countDesigns(int numCheckpoints, int[] checkpoint1, int[] checkpoint2) {
		n = numCheckpoints;
		for (int i = 0; i <= n; i++) {
			use[i] = false;
			degree[i] = 0;
		}
		for (int i = 0; i < checkpoint1.length; i++) {
			degree[checkpoint1[i]]++;
			degree[checkpoint2[i]]++;
		}
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= n; j++) {
				c[i][j] = false;
			}
		}
		for (int i = 0; i < checkpoint1.length;i++) {
			c[checkpoint1[i]][checkpoint2[i]] = true;
			c[checkpoint2[i]][checkpoint1[i]] = true;
		}
		ans = 0;
		long rt = 0;
		for (int i = 1; i <= n; i++) {
			if (!use[i]) {
				ans = 0;
				edge = 0;
				dfs(i);
				rt += (ans - edge /2);
			}
		}
		for (int i = 1; i <= n; i++) {
			for (int j = i + 1; j <= n; j++) {
				for (int k = j + 1; k <= n; k++) {
					if (c[i][j] && c[j][k] && c[i][k]) {
						rt++;
					}
				}
			}
		}
		return rt + 1;
	}
	public static void main(String[] args) {
		PenguinSledding ps = new PenguinSledding();
		int[] checkpoint1 = new int[]{13, 24, 24, 20, 31, 16, 10, 36, 34, 32, 
				 28, 5, 20, 29, 23, 2, 14, 4, 9, 5, 19, 
				 21, 8, 1, 26, 27, 25, 15, 22, 30, 30, 
				 6, 11, 7, 2, 35, 13, 29, 4, 12, 33, 18, 
				 13, 14, 17, 35, 3};
	//	checkpoint1 = new int[]{1,1,2,4,4,5};
		int[] checkpoint2 = new int[]{10, 15, 27, 1, 29, 11, 5, 18, 33, 1, 9,
				 2, 31, 6, 19, 10, 33, 18, 6, 27, 3, 22,
				 4, 12, 31, 30, 34, 16, 7, 24, 3, 28, 15,
				 21, 22, 8, 26, 20, 14, 32, 25, 17, 35,
				 8, 36, 26, 23};
	//	checkpoint2 = new int[]{2,3,3,3,5,3};
		int numCheckpoints = 36;
		long rt = ps.countDesigns(numCheckpoints, checkpoint1, checkpoint2);
		System.out.println(rt);
	}
}
