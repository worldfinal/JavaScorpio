package com.frc.javascorpio.base;

public class AddCalculate implements BaseCalculate {

	@Override
	public double calculate(double x, double y) {
		double result = x + y;
		System.out.println("do ADD method,  answer is " + result);
		return result;
	}

}
