package com.frc.javascorpio.scheduletest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MySecondTask extends TimerTask {
	private TestBean testbean = null;
	private Timer timer = null;
	
	public MySecondTask(TestBean testbean, Timer timer) {
		super();
		this.testbean = testbean;
		this.timer = timer;
	}

	@Override
	public void run() {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		String now = sdf.format(new Date());
		System.out.println(now + "  " + testbean.getName() + " -- " + testbean.getValue());
		timer.cancel();
	}

}
