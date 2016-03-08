package com.frc.javascorpio.swing.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * �ͻ���
 * 
 * @author way
 * 
 */
public class Client {

	private Socket client;
	private ClientThread clientThread;
	private String ip;
	private int port;

	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	public static void main(String[] args) {
		Client acm = new Client("127.0.0.1", 8848);
		acm.start();
		TranObject<String>txt = new TranObject<String>(TranObjectType.MESSAGE);
        
		Scanner cin=new Scanner(System.in);
		while (cin.hasNext()) {
			String str = cin.next();
			txt = new TranObject<String>(TranObjectType.MESSAGE);
			txt.setObject(str);
	        txt.setFromUser(0);
			acm.getClientOutputThread().setMsg(txt);
		}
	}
	public boolean start() {
		try {
			client = new Socket();
			// client.connect(new InetSocketAddress(Constants.SERVER_IP,
			// Constants.SERVER_PORT), 3000);
			client.connect(new InetSocketAddress(ip, port), 3000);
			if (client.isConnected()) {
				// System.out.println("Connected..");
				clientThread = new ClientThread(client);
				clientThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// ֱ��ͨ��client�õ����߳�
	public ClientInputThread getClientInputThread() {
		return clientThread.getIn();
	}

	// ֱ��ͨ��client�õ�д�߳�
	public ClientOutputThread getClientOutputThread() {
		return clientThread.getOut();
	}

	// ֱ��ͨ��clientֹͣ��д��Ϣ
	public void setIsStart(boolean isStart) {
		clientThread.getIn().setStart(isStart);
		clientThread.getOut().setStart(isStart);
	}
	
	public class ClientThread extends Thread {

		private ClientInputThread in;
		private ClientOutputThread out;

		public ClientThread(Socket socket) {
			in = new ClientInputThread(socket);
			out = new ClientOutputThread(socket);
		}

		public void run() {
			in.setStart(true);
			out.setStart(true);
			in.start();
			out.start();
		}

		// �õ�����Ϣ�߳�
		public ClientInputThread getIn() {
			return in;
		}

		// �õ�д��Ϣ�߳�
		public ClientOutputThread getOut() {
			return out;
		}
	}
}