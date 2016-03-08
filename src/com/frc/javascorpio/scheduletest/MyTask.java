package com.frc.javascorpio.scheduletest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class MyTask extends TimerTask {

	@Override
	public void run() {
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		String now = sdf.format(new Date());
		System.out.println(now);
	}

}
