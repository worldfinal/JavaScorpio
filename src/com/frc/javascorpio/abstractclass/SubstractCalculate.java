package com.frc.javascorpio.abstractclass;

public class SubstractCalculate extends AbastractCalculate {

	@Override
	protected double calculate(double x, double y) {
		return x - y;
	}

	@Override
	protected boolean validate(double x, double y) {
		if (x < 0 || y < 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected void outputError() {
		System.out.println("【失败】减法类发现错误：两个数都必须大于等于0");
	}

	@Override
	protected void outputResult(double result) {
		System.out.println("减法。。。。。 结果系 " + result);
	}

}
