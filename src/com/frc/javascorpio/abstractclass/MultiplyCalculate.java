package com.frc.javascorpio.abstractclass;

public class MultiplyCalculate extends AbastractCalculate {

	@Override
	protected double calculate(double x, double y) {
		return x * y;
	}

	@Override
	protected boolean validate(double x, double y) {
		if (x * y < 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected void outputError() {
		System.out.println("【失败】乘法类发现错误： 两个数必须同号");
	}

	@Override
	protected void outputResult(double result) {
		System.out.println("乘法结果 ********* " + result + " *****");
	}

}
