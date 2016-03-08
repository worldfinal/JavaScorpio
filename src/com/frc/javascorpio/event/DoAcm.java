package com.frc.javascorpio.event;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DoAcm implements ApplicationEventPublisherAware {
	private ApplicationEventPublisher applicationEventPublisher;
	
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher arg0) {
		this.applicationEventPublisher = arg0;
	}
	
	@Test
	public void doMatch() {
		System.out.println("do match");
		AcmEvent event = new AcmEvent(this, "er... whatever~");
		applicationEventPublisher.publishEvent(event);
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("test.xml");
		DoAcm doacm = (DoAcm) context.getBean("doacm");
		doacm.doMatch();
	}

}
