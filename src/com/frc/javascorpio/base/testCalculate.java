package com.frc.javascorpio.base;

public class testCalculate {
	public static void main(String[] args) {
		BaseCalculate add = new AddCalculate();
		BaseCalculate div = new DivideCalculate();
		add.calculate(19.1, 20.9);
		div.calculate(20.0, 50.0);
		System.out.println("=========================");
		
		BaseCalculate bcadd = ObjectFactory.createCalculater("add");
		bcadd.calculate(10, 20);
		BaseCalculate bcdiv = ObjectFactory.createCalculater("divide");
		bcdiv.calculate(10, 20);
	}
}
