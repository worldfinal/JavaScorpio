package com.frc.javascorpio.base;

public class DivideCalculate implements BaseCalculate {

	@Override
	public double calculate(double x, double y) {
		if (Math.abs(y) < 1e-7) {
			System.out.println("y is tooooooo less");
			return 0;
		}
		double result = x / y;
		System.out.println("do DIVIDE method,  answer is " + result);
		return result;
	}

}
