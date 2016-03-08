package com.scorpiowf.test;

import java.io.IOException;

public class FRCTask implements Runnable {
    private final int id;
    private String msg;
    private MainController ctrl;

    public FRCTask(int id, String msg, MainController ctrl) {
        this.id = id;
        this.msg = msg;
        this.ctrl = ctrl;
    }


    public String toString() {
        return msg;
    }
    
    public void startTask() throws InterruptedException, IOException{
        ctrl.process(this);
    }

    public void run() {
        try {
            startTask();
        } catch (InterruptedException e) {
        	e.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

	public int getId() {
		return id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}