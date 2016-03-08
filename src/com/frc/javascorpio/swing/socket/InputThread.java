package com.frc.javascorpio.swing.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * ����Ϣ�̺߳ʹ�����
 * 
 * @author way
 * 
 */
public class InputThread extends Thread {
	private Socket socket;// socket����
	private OutputThread out;// ���ݽ�����д��Ϣ�̣߳���Ϊ����Ҫ���û��ظ���Ϣ��
	private OutputThreadMap map;//д��Ϣ�̻߳�����
	private ObjectInputStream ois;//����������
	private boolean isStart = true;//�Ƿ�ѭ������Ϣ

	public InputThread(Socket socket, OutputThread out, OutputThreadMap map) {
		this.socket = socket;
		this.out = out;
		this.map = map;
		try {
			ois = new ObjectInputStream(socket.getInputStream());//ʵ��������������
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setStart(boolean isStart) {//�ṩ�ӿڸ��ⲿ�رն���Ϣ�߳�
		this.isStart = isStart;
	}

	@Override
	public void run() {
		try {
			while (isStart) {
				// ��ȡ��Ϣ
				readMessage();
			}
			if (ois != null)
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
     * ����Ϣ�Լ�������Ϣ���׳��쳣 
     *  
     * @throws IOException 
     * @throws ClassNotFoundException 
     */  
    public void readMessage() throws IOException, ClassNotFoundException {  
        Object readObject = ois.readObject();// �����ж�ȡ����   
        System.out.println("[Server] ReadMessage: " + readObject);
        if (readObject != null && readObject instanceof TranObject) {  
            TranObject read_tranObject = (TranObject) readObject;// ת���ɴ������   
            switch (read_tranObject.getType()) {  
            case REGISTER:// ����û���ע��   
                break;  
            case LOGIN: 
                break;  
            case LOGOUT:// ������˳����������ݿ�����״̬��ͬʱȺ���������������û�   
               
                break;  
            case MESSAGE:// �����ת����Ϣ�������Ⱥ����   
                // ��ȡ��Ϣ��Ҫת���Ķ���id��Ȼ���ȡ����ĸö����д�߳�   
            	TranObject<String>txt = new TranObject<String>(TranObjectType.MESSAGE);
                txt.setObject("server sending msg:" + read_tranObject.getObject().toString());
                txt.setFromUser(0);
                out.setMessage(txt);
                break;  
            case REFRESH:  
               
                break;  
            default:  
                break;  
            }  
            
        }  
    }  
}