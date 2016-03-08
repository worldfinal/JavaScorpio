package com.frc.javascorpio.swing.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * �ͻ��˶���Ϣ�߳�
 * 
 * @author way
 * 
 */
public class ClientInputThread extends Thread {
	private Socket socket;
	private TranObject msg;
	private boolean isStart = true;
	private ObjectInputStream ois;
	private MessageListener messageListener;// ��Ϣ�����ӿڶ���

	public ClientInputThread(Socket socket) {
		this.socket = socket;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ṩ���ⲿ����Ϣ��������
	 * 
	 * @param messageListener
	 *            ��Ϣ�����ӿڶ���
	 */
	public void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}

	@Override
	public void run() {
		try {
			while (isStart) {
				msg = (TranObject) ois.readObject();
				System.out.println("Client getmsg: " + msg.getObject());
				// ÿ�յ�һ����Ϣ���͵��ýӿڵķ��������������Ϣ�����ⲿ��ʵ�ֽӿڵķ���ʱ���Ϳ��Լ�ʱ���������Ϣ������
				// �Ҳ�֪������˵����û�У�
		//		messageListener.Message(msg);
			}
			ois.close();
			if (socket != null)
				socket.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��Ϣ�����ӿ�
	 * 
	 * @author way
	 * 
	 */
	public interface MessageListener {
		public void Message(TranObject msg);
	}
}