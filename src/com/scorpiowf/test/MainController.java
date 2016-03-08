package com.scorpiowf.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainController implements Runnable {
	private final int N = 4;
	private boolean started = false;
	private boolean isRunning = true;
	public List<FRCTask> taskList = null;
	private int currentId;
	private int alreadyRun;
	protected static Object obj = new Object();
	
	public static void main(String[] args) throws InterruptedException, IOException {
		MainController ctrl = new MainController();
		ctrl.init();
		new Thread(ctrl).start();
	}
	
	public void process(FRCTask task) throws InterruptedException, IOException {
		synchronized (obj) {
			while (isRunning) {
				if (!started || currentId != task.getId()) {
					// 任务未开始，或者还没轮到，则[等待]
					obj.wait();
				} else {
					// 任务开始，并且轮到自己，则[写文件]
					String fileName = String.format("d:\\%c.txt", ('A'+alreadyRun));
					FileOutputStream out = new FileOutputStream(new File(fileName), true);
					out.write(task.getMsg().getBytes());
					out.close();
					
					System.out.println(String.format("Writing [%s] into file: \"%s\"", task.getMsg(), fileName));
					
					increseID();
					increseRun();
					
					// 如果已经完成N个，则设置标记，让所有子线程暂停（包括自己）
					if (alreadyRun >= N) {
						started = false;
					}
					obj.notifyAll();
				}
			}
			System.out.println("===Thred end " + task + "===");
		}
		
	}

	public void kickStart() {
		isRunning = true;
		
		// 共10轮
		for (int i = 0; i < 10; i++) {
			started = true;
			currentId = i % N;	// 由currentId的递增，生成字符串A,B,C,D
			alreadyRun = 0;		// 每一轮运行N=4次，分别对应4个OutputStream
			synchronized (obj) {
				obj.notifyAll();
			}
			while (alreadyRun < taskList.size()) {
				synchronized (obj) {
					try {
						obj.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println("Round " + i + " end");
		}
		
		isRunning = false;
		synchronized (obj) {
			obj.notifyAll();
		}
		System.out.println("Done all");
	}

	public void run() {
		System.out.println("start");
		kickStart();
	}
	public void init() throws IOException {
		started = false;	// 未开始任务，所有子线程会处于等待状态
		
		// 先清空文件
		for (int i = 0; i < N; i++) {
			String fileName = String.format("d:\\%c.txt", ('A'+i));
			FileOutputStream out = new FileOutputStream(new File(fileName), false);
			out.close();
		}
		
		taskList = new ArrayList<FRCTask>();
		
		for (int i = 0; i < N; i++) {
			String msg = String.format("%c ", 'A'+i);
			FRCTask player = new FRCTask(i, msg, this);
			taskList.add(player);
		}

		for (FRCTask task : taskList) {
			Thread thread = new Thread(task);
			thread.start();
		}
		// 此时所有子线程相继开始执行，并且处于等待状态
	}
	
	protected synchronized void increseID() {
		currentId = (currentId + 1) % N;
	}
	protected synchronized void increseRun() {
		alreadyRun++;
	}

}