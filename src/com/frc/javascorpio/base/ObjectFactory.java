package com.frc.javascorpio.base;

public class ObjectFactory {
	public static BaseCalculate createCalculater(String type) {
		if (type.equalsIgnoreCase("add")) {
			return new AddCalculate();
		} else if (type.equalsIgnoreCase("divide")) {
			return new DivideCalculate();
		} else {
			return null;
		}
	}
}
