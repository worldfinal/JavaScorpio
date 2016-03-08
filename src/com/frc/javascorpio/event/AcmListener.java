package com.frc.javascorpio.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class AcmListener implements ApplicationListener {

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof AcmEvent) {
			String whatever = ((AcmEvent)event).getWhatever();
			System.out.println("haha, I get it [" + whatever + "]");
		}
	}

}
