package com.frc.javascorpio.swing.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * �ͻ���д��Ϣ�߳�
 * 
 * @author way
 * 
 */
public class ClientOutputThread extends Thread {
	private Socket socket;
	private ObjectOutputStream oos;
	private boolean isStart = true;
	private TranObject msg;

	public ClientOutputThread(Socket socket) {
		this.socket = socket;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	// ���ﴦ�����������һ����
	public void setMsg(TranObject msg) {
		this.msg = msg;
		synchronized (this) {
			notify();
		}
	}

	@Override
	public void run() {
		try {
			while (isStart) {
				if (msg != null) {
					oos.writeObject(msg);
					oos.flush();
					if (msg.getType() == TranObjectType.LOGOUT) {// ����Ƿ������ߵ���Ϣ����ֱ������ѭ��
						break;
					}
					synchronized (this) {
						wait();// ��������Ϣ���߳̽���ȴ�״̬
					}
				}
			}
			oos.close();// ѭ�������󣬹ر��������socket
			if (socket != null)
				socket.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}