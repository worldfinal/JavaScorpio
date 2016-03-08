package com.frc.javascorpio.swing.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 读消息线程和处理方法
 * 
 * @author way
 * 
 */
public class InputThread extends Thread {
	private Socket socket;// socket对象
	private OutputThread out;// 传递进来的写消息线程，因为我们要给用户回复消息啊
	private OutputThreadMap map;//写消息线程缓存器
	private ObjectInputStream ois;//对象输入流
	private boolean isStart = true;//是否循环读消息

	public InputThread(Socket socket, OutputThread out, OutputThreadMap map) {
		this.socket = socket;
		this.out = out;
		this.map = map;
		try {
			ois = new ObjectInputStream(socket.getInputStream());//实例化对象输入流
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setStart(boolean isStart) {//提供接口给外部关闭读消息线程
		this.isStart = isStart;
	}

	@Override
	public void run() {
		try {
			while (isStart) {
				// 读取消息
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
     * 读消息以及处理消息，抛出异常 
     *  
     * @throws IOException 
     * @throws ClassNotFoundException 
     */  
    public void readMessage() throws IOException, ClassNotFoundException {  
        Object readObject = ois.readObject();// 从流中读取对象   
        System.out.println("[Server] ReadMessage: " + readObject);
        if (readObject != null && readObject instanceof TranObject) {  
            TranObject read_tranObject = (TranObject) readObject;// 转换成传输对象   
            switch (read_tranObject.getType()) {  
            case REGISTER:// 如果用户是注册   
                break;  
            case LOGIN: 
                break;  
            case LOGOUT:// 如果是退出，更新数据库在线状态，同时群发告诉所有在线用户   
               
                break;  
            case MESSAGE:// 如果是转发消息（可添加群发）   
                // 获取消息中要转发的对象id，然后获取缓存的该对象的写线程   
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