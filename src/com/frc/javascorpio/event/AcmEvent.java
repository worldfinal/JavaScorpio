package com.frc.javascorpio.event;

import org.springframework.context.ApplicationEvent;

public class AcmEvent extends ApplicationEvent {
	private String whatever;
	
	public AcmEvent(Object source, String whatever) {
		super(source);
		this.whatever = whatever;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String getWhatever() {
		return whatever;
	}
}
