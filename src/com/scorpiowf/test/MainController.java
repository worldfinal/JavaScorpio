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
					// ����δ��ʼ�����߻�û�ֵ�����[�ȴ�]
					obj.wait();
				} else {
					// ����ʼ�������ֵ��Լ�����[д�ļ�]
					String fileName = String.format("d:\\%c.txt", ('A'+alreadyRun));
					FileOutputStream out = new FileOutputStream(new File(fileName), true);
					out.write(task.getMsg().getBytes());
					out.close();
					
					System.out.println(String.format("Writing [%s] into file: \"%s\"", task.getMsg(), fileName));
					
					increseID();
					increseRun();
					
					// ����Ѿ����N���������ñ�ǣ����������߳���ͣ�������Լ���
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
		
		// ��10��
		for (int i = 0; i < 10; i++) {
			started = true;
			currentId = i % N;	// ��currentId�ĵ����������ַ���A,B,C,D
			alreadyRun = 0;		// ÿһ������N=4�Σ��ֱ��Ӧ4��OutputStream
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
		started = false;	// δ��ʼ�����������̻߳ᴦ�ڵȴ�״̬
		
		// ������ļ�
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
		// ��ʱ�������߳���̿�ʼִ�У����Ҵ��ڵȴ�״̬
	}
	
	protected synchronized void increseID() {
		currentId = (currentId + 1) % N;
	}
	protected synchronized void increseRun() {
		alreadyRun++;
	}

}