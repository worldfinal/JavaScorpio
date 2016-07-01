package com.wf.crack.jellysquare;

import org.junit.Test;

public class CrackJelly {
	public boolean status[] = new boolean[1458954496];
	public int board[][];
	public int d[][];
	public int a[][];
	public int tools[][];
	public STATE ans[];
	public STATE cur[];
	public int row;
	public int col;
	public int n;
	public int pow[];
	public int pow2[];
	public boolean flag;
	public int nCounter;
	public int[] step = new int[200000];
	public int stepCount;
	public int maxStep;

	public void dfs(int lay) {
		if (lay > maxStep) {
			return;
		}
		nCounter++;
		if (nCounter >= 20 && nCounter <= 35) {
//			 printA();
		}
		int s = getStatus();
	
		if (flag || status[s]) {
			return;
		}
		if (checkSucc()) {
			flag = true;
			stepCount = lay;
//			printA();
			for (int i = 0; i < lay; i++) {
				System.out.print(step[i] + " ");
				if (i % 10 == 9) {
					System.out.println();
				}
			}
			System.out.println("\nTotal " + stepCount + " steps");
			return;
		}
		status[s] = true;
		int tmp_a[][] = new int[row + 1][col + 1];
		int tmp_d[][] = new int[row + 1][col + 1];
		for (int i = 0; i < cur.length && !flag; i++) {
			copyArr(tmp_a, a);
			copyArr(tmp_d, d);
			if (moveNext(cur, i)) {				
				updateCur();
				step[lay] = i+1;
				dfs(lay + 1);
				copyArr(a, tmp_a);
				copyArr(d, tmp_d);
				updateCur();
			}
			
		}
	}

	protected void updateCur() {
		for (int i = 1; i <= row; i++) {
			for (int j = 1; j <= col; j++) {
				if (a[i][j] != 0) {
					int k = a[i][j] - 1;
					cur[k].x = i;
					cur[k].y = j;
					cur[k].dir = d[i][j];
				}
			}
		}
	}

	protected int getStatus() {
		int k = row * col * 4;
		int rt = 0;
		for (int i = 1; i <= row; i++) {
			for (int j = 1; j <= col; j++) {
				if (a[i][j] != 0) {
					k = a[i][j];
					rt += pow[k - 1] * ((d[i][j]-1)*(row*col) + (i-1) * col + j-1);
				}
			}
		}
		return rt;
	}

	protected boolean moveNext(STATE[] pos, int idx) {
		int dx = 0, dy = 0;
		switch (pos[idx].dir) {
		case STATE.UP:
			dx = -1;
			break;
		case STATE.LEFT:
			dy = -1;
			break;
		case STATE.RIGHT:
			dy = 1;
			break;
		case STATE.DOWN:
			dx = 1;
			break;
		}
		int x = pos[idx].x;
		int y = pos[idx].y;
		while (x >= 0 && y >= 0 && x <= row && y <= col && a[x][y] != 0) {
			x += dx;
			y += dy;
		}
		if (x <= 0 || y <= 0 || x > row || y > col || a[x][y] != 0) {
			return false;
		}
		doMoveNext(pos[idx].x, pos[idx].y, dx, dy);
		return true;
	}

	protected void doMoveNext(int x, int y, int dx, int dy) {
		if (!(x + dx >= 0 && y + dy >= 0 && x + dx <= row && y + dy <= col)) {
			return;
		}
		if (a[x][y] != 0) {
			doMoveNext(x + dx, y + dy, dx, dy);
		} else {
			return;
		}
		a[x + dx][y + dy] = a[x][y];
		if (tools[x + dx][y + dy] == 0) {
			d[x + dx][y + dy] = d[x][y];
		} else {
			d[x + dx][y + dy] = tools[x + dx][y + dy];
			// System.out.println(String.format("D:%d %d from %d->%d", x+dx,
			// y+dy, d[x][y], tools[x+dx][y+dy]));
		}
		a[x][y] = 0;
		d[x][y] = 0;
	}

	protected STATE[] copySTATE(STATE[] pos) {
		STATE[] rs = new STATE[pos.length];
		for (int i = 0; i < pos.length; i++) {
			rs[i] = new STATE();
			rs[i].dir = pos[i].dir;
			rs[i].x = pos[i].x;
			rs[i].y = pos[i].y;
		}
		return rs;
	}

	protected void copyArr(int[][] to, int[][] from) {
		int x = to.length;
		int y = to[0].length;
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				to[i][j] = from[i][j];
			}
		}
	}

	protected boolean checkSucc() {
		for (int i = 0; i < ans.length; i++) {
			if (a[ans[i].x][ans[i].y] != i + 1) {
				return false;
			}
		}
		return true;
	}

	public CrackJelly(int row, int col, STATE[] cur, STATE[] ans, STATE[] t) {
		flag = false;
		this.row = row;
		this.col = col;
		this.n = cur.length;
		this.cur = cur;
		this.ans = ans;
		board = new int[row + 1][col + 1];
		d = new int[row + 1][col + 1];
		a = new int[row + 1][col + 1];
		tools = new int[row + 1][col + 1];
		pow = new int[n];
		pow2 = new int[4];
		pow[0] = 1;
		nCounter = 0;
		for (int i = 0; i < status.length; i++) {
			status[i] = false;
		}
		pow2[0] = 1;
		for (int i = 1; i < 4; i++) {
			pow2[i] = pow2[i-1] * row * col;
		}
		for (int i = 1; i < n; i++) {
			pow[i] = pow[i - 1] * row * col * 4;
		}
		for (int i = 0; i <= row; i++) {
			for (int j = 0; j <= col; j++) {
				board[i][j] = 0;
				a[i][j] = 0;
				tools[i][j] = 0;
				d[i][j] = 0;
			}
		}
		for (int i = 0; i < cur.length; i++) {
			a[cur[i].x][cur[i].y] = i + 1;
			d[cur[i].x][cur[i].y] = cur[i].dir;
			board[ans[i].x][ans[i].y] = i + 1;
		}
		if (t != null && t.length > 0) {
			for (int i = 0; i < t.length; i++) {
				tools[t[i].x][t[i].y] = t[i].dir;
				// System.out.println(String.format("t[%d][%d]=%d", t[i].x,
				// t[i].y, t[i].dir));
			}
		}
	}

	public void printA() {
		for (int i = 1; i <= row; i++) {
			for (int j = 1; j <= col; j++) {
				if (a[i][j] > 0) {
					System.out.print(a[i][j] + " ");
				} else if (tools[i][j] == STATE.LEFT) {
					System.out.print("L ");
				} else if (tools[i][j] == STATE.RIGHT) {
					System.out.print("R ");
				} else if (tools[i][j] == STATE.UP) {
					System.out.print("U ");
				} else if (tools[i][j] == STATE.DOWN) {
					System.out.print("D ");
				} else {
					System.out.print(a[i][j] + " ");
				}
			}
			System.out.println();
		}
		System.out.println("==== ====");
	}

	@Test
	public void test() {
		int row = 6;
		int col = 6;
		STATE[] cur = new STATE[3];
		STATE[] ans = new STATE[3];
		for (int i = 0; i < 3; i++) {
			cur[i] = new STATE();
			cur[i].x = 3;
			cur[i].y = i + 1;
			cur[i].dir = STATE.DOWN;
		}
		for (int i = 0; i < 3; i++) {
			ans[i] = new STATE();
			ans[i].x = 4;
			ans[i].y = i + 1;
			ans[i].dir = STATE.DOWN;
		}
		CrackJelly obj = new CrackJelly(row, col, cur, ans, null);
		obj.dfs(0);
	}

	public static void main(String[] args) {
		int row = 7;
		int col = 5;
		STATE[] cur = new STATE[3];
		STATE[] ans = new STATE[3];
		STATE[] t = new STATE[4];
		for (int i = 0; i < 3; i++) {
			cur[i] = new STATE();
		}
		for (int i = 0; i < 3; i++) {
			ans[i] = new STATE();
		}
		for (int i = 0; i < 4; i++) {
			t[i] = new STATE();
		}
		// 
		cur[0].x = 6;
		cur[0].y = 5;
		cur[0].dir = STATE.LEFT;
		cur[1].x = 4;
		cur[1].y = 1;
		cur[1].dir = STATE.RIGHT;
		cur[2].x = 6;
		cur[2].y = 3;
		cur[2].dir = STATE.LEFT;
		
		//ans
		ans[0].x = 3;
		ans[0].y = 2;
		ans[1].x = 3;
		ans[1].y = 3;
		ans[2].x = 3;
		ans[2].y = 4;
		
		t[0].x = 4;
		t[0].y = 2;
		t[0].dir = STATE.DOWN;
		t[1].x = 4;
		t[1].y = 5;
		t[1].dir = STATE.LEFT;
		t[2].x = 6;
		t[2].y = 2;
		t[2].dir = STATE.RIGHT;
		t[3].x = 6;
		t[3].y = 4;
		t[3].dir = STATE.UP;
		CrackJelly obj = new CrackJelly(row, col, cur, ans, t);
		obj.printA();
		System.out.println("Start:");
		obj.maxStep = 50;
		obj.dfs(0);
		System.out.println(obj.flag + ", counter=" + obj.nCounter + " / " + obj.maxStep);
	}
}
