package com.frc.javascorpio.games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Shudu {
	class SDavailable {
		public int visit[] = new int[SIZE+1];
		public int count, r, c, v;
		public SDavailable() {
			count = 0;
			for (int i = 0; i < visit.length; i++) {
				visit[i] = 0;
			}
		}
	}
	private static final int N = 4;
	private static final int SIZE = N*N;	
	protected SDavailable cols[];
	protected SDavailable rows[];
	protected SDavailable blocks[];
	protected SDavailable matrix[][];
	protected List<SDavailable> list;
	protected boolean flag;	
	protected int data[][];
	
	public static void main(String ar[]) {
		int data[][] = new int[][]{
			{3,0,0,0,0,0,5,0,0},
			{1,0,6,0,4,0,9,0,0},
			{0,0,0,9,0,0,0,6,7},
			{0,0,9,0,2,3,0,5,0},
			{0,0,7,0,0,4,0,0,0},
			{6,0,0,0,0,1,0,0,4},
			{0,0,0,0,6,0,0,2,8},
			{0,3,5,0,1,0,0,0,6},
			{0,0,0,0,0,0,3,0,0}
		};
		
		if (N == 4) {
			data = new int[][]{
					 { 0,0,12,0,6,0,0,0,0,0,2,10,0,0,0,0},
					 { 16,0,0,3,0,10,0,0,0,0,4,5,0,0,11,0},
					 { 0,0,0,13,1,0,2,0,0,9,0,6,4,12,0,0},
					 { 6,0,8,2,4,0,0,3,16,0,1,14,0,0,0,0},
					 { 14,3,0,11,0,0,1,4,0,0,13,0,7,9,0,0},
					 { 0,0,0,0,7,0,0,0,4,3,0,12,6,0,0,0},
					 { 4,0,2,15,0,0,0,0,14,0,0,0,0,8,13,10},
					 { 7,0,5,0,11,0,3,0,0,0,6,9,0,4,1,0},
					 { 0,7,16,0,3,9,0,0,0,4,0,2,0,14,0,13},
					 { 2,13,15,0,0,0,0,14,0,0,0,0,16,3,0,9},
					 { 0,0,0,1,8,0,7,12,0,0,0,3,0,0,0,0},
					 { 0,0,14,9,0,13,0,0,10,11,0,0,12,0,4,7},
					 { 0,0,0,0,10,1,0,7,8,0,0,13,2,11,0,4},
					 { 0,0,11,7,2,0,8,0,0,14,0,4,5,0,0,0},
					 { 0,10,0,0,14,5,0,0,0,0,16,0,13,0,0,6},
					 { 0,0,0,0,16,12,0,0,0,0,0,11,0,15,0,0}
			};
		}
		Shudu sd = new Shudu(data);
		sd.solve();
		sd.printData();
	}
	
	public Shudu(int data[][]) {
		this.data = data;
	}
	
	public int[][] solve() {
		
		init();
		
		Collections.sort(list, new Comparator<SDavailable>(){

			@Override
			public int compare(SDavailable arg0, SDavailable arg1) {
				return arg0.count - arg1.count;
			}});
		
		System.out.println("Cells number to be filled:" + list.size());
		
		while (list.size() > 0 && list.get(0).count == 1) {
			SDavailable sd = list.get(0);
			System.out.println(String.format("GO: [%2d,%2d]=%d", sd.r, sd.c, sd.v));
			fill(sd.r, sd.c, sd.v);
			list.remove(0);
			update();
		}
		
		/*
		for (SDavailable sd : list) {
			System.out.println(String.format("[%2d,%2d], %d", sd.r, sd.c, sd.count));
		}
		System.out.println("Cells number to be filled:" + list.size());
		*/
		
		for (int i = 0; i < 1; i++) {
			checkRowUnique();
			checkColUnique();
			update();
			
			System.out.println("After check unique");
			while (list.size() > 0 && list.get(0).count == 1) {
				SDavailable sd = list.get(0);
				System.out.println(String.format("GO: [%2d,%2d]=%d", sd.r, sd.c, sd.v));
				fill(sd.r, sd.c, sd.v);
				list.remove(0);
				update();
			}
		}
		System.out.println("Now the size is:" + list.size());
		
		init();
		flag = false;
		dfs(0);
		return data;
	}
	
	public void printData() {
		if (flag == false) {
			System.out.println("No solution!");
			return;
		}
		System.out.println("Solution:");
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				System.out.print(data[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	void dfs(int id) {
		
		if (id == list.size()) {
			flag = true;
			return;
		} else if (flag) {
			return;
		}
		int r,  c;
		r = list.get(id).r;
		c = list.get(id).c;
		for (int i = 1; i <= SIZE && !flag; i++) {
			if (rows[r].visit[i] < 0) {
				continue;
			} else if (cols[c].visit[i] < 0) {
				continue;
			} else if (blocks[r/N*N+c/N].visit[i] < 0) {
				continue;
			}
			data[r][c] = i;
			rows[r].visit[i]--;
			cols[c].visit[i]--;
			blocks[r/N*N+c/N].visit[i]--;
			System.out.println(String.format("[%2d]%2d,%2d = %d", id, r, c, data[r][c]));
			dfs(id+1);
			if (flag) {
				return;
			}
			data[r][c] = 0;
			rows[r].visit[i]++;
			cols[c].visit[i]++;
			blocks[r/N*N+c/N].visit[i]++;
		}
	}
	protected void update() {
		int i, j;
		for (SDavailable sd: list) {
			i = sd.r;
			j = sd.c;
			int c = 0;
			for (int k = 1; k <= SIZE; k++) {
				if (rows[i].visit[k] < 0) {
					sd.visit[k] = -1;
					continue;
				} else if (cols[j].visit[k] < 0) {
					sd.visit[k] = -1;
					continue;
				} else if (blocks[i/N*N+j/N].visit[k] < 0) {
					sd.visit[k] = -1;
					continue;
				}
				sd.v = k;
				c++;
			}
			sd.count = c;
		}
		Collections.sort(list, new Comparator<SDavailable>(){

			@Override
			public int compare(SDavailable arg0, SDavailable arg1) {
				return arg0.count - arg1.count;
			}});
	}
	protected void checkRowUnique() {
		//Row unique:所在行只有当前单元格能够填写某数字
		int vis[] = new int[SIZE+1];
		int dig[] = new int[SIZE+1];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j <= SIZE; j++) {
				vis[j] = 0;
				dig[j] = -1;
			}
			for (int j = 0; j < SIZE;j ++) {
				if (data[i][j] != 0) {
					continue;
				}
				for (int k = 1; k <= SIZE; k++) {
					if (matrix[i][j].visit[k] == 0) {
						//可以填k
						vis[k]++;
						dig[k] = i*SIZE+j;
					}
				}
				
			}
			for (int j = 1; j <= SIZE; j++) {
				if (vis[j] == 1) {
					System.out.println(String.format("[Fill Row] [%d, %d] fill %d", dig[j]/SIZE, dig[j]%SIZE, j));
					fill(dig[j]/SIZE, dig[j]%SIZE, j);
				}
			}
		}
	}
	protected void checkColUnique() {
		//Col unique
		int vis[] = new int[SIZE+1];
		int dig[] = new int[SIZE+1];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j <= SIZE; j++) {
				vis[j] = 0;
				dig[j] = -1;
			}
			for (int j = 0; j < SIZE;j ++) {
				if (data[j][i] != 0) {
					continue;
				}
				for (int k = 1; k <= SIZE; k++) {
					if (matrix[j][i].visit[k] == 0) {
						//可以填k
						vis[k]++;
						dig[k] = j*SIZE+i;
					}
				}
				
			}
			for (int j = 1; j <= SIZE; j++) {
				if (vis[j] == 1) {
					System.out.println(String.format("[Fill Col] [%d, %d] fill %d", dig[j]/SIZE, dig[j]%SIZE, j));
					fill(dig[j]/SIZE, dig[j]%SIZE, j);
				}
			}
		}
	}
	protected void checkBlockUnique() {
		//Block unique
		int vis[] = new int[SIZE+1];
		int dig[] = new int[SIZE+1];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j <= SIZE; j++) {
				vis[j] = 0;
				dig[j] = -1;
			}
			for (int j = 0; j < SIZE;j ++) {
				if (data[j][i] != 0) {
					continue;
				}
				for (int k = 1; k <= SIZE; k++) {
					if (matrix[j][i].visit[k] == 0) {
						//可以填k
						vis[k]++;
						dig[k] = j*SIZE+i;
					}
				}
				
			}
			for (int j = 1; j <= SIZE; j++) {
				if (vis[j] == 1) {
					System.out.println(String.format("[Fill Col] [%d, %d] fill %d", dig[j]/SIZE, dig[j]%SIZE, j));
					fill(dig[j]/SIZE, dig[j]%SIZE, j);
				}
			}
		}
	}
	protected void fill(int r, int c, int val) {
		data[r][c] = val;
		rows[r].visit[val] = -1;
		cols[c].visit[val] = -1;
		blocks[getBlock(r, c)].visit[val] = -1;
	}
	protected int getBlock(int r, int c) {
		return r/N*N+c/N;
	}
	protected void init() {
		int n = SIZE;
		cols = new SDavailable[n];
		rows = new SDavailable[n];
		blocks = new SDavailable[n];
		matrix = new SDavailable[n][];
		list = new ArrayList<SDavailable>();
		
		for (int i = 0; i < n; i++) {
			cols[i] = new SDavailable();
			rows[i] = new SDavailable();
			blocks[i] = new SDavailable();
			matrix[i] = new SDavailable[n];
			for (int j = 0; j < n; j++) {
				matrix[i][j] = new SDavailable();
				matrix[i][j].r = i;
				matrix[i][j].c = j;
				if (data[i][j] == 0) {
					list.add(matrix[i][j]);
				}
			}
		}
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				int v = data[i][j];
				if (v == 0) {
					continue;
				}
				rows[i].visit[v]--;
				cols[j].visit[v]--;
				blocks[i/N*N+j/N].visit[v]--;
			}
		}
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (data[i][j] != 0) {
					continue;
				}
				int c = 0;
				for (int k = 1; k <= SIZE; k++) {
					if (rows[i].visit[k] < 0) {
						matrix[i][j].visit[k] = -1;
						continue;
					} else if (cols[j].visit[k] < 0) {
						matrix[i][j].visit[k] = -1;
						continue;
					} else if (blocks[i/N*N+j/N].visit[k] < 0) {
						matrix[i][j].visit[k] = -1;
						continue;
					}
					matrix[i][j].v = k;
					c++;
				}
				matrix[i][j].count = c;
			}
		}
		
		System.out.println("init done!");
	}
}
