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
		System.out.println("��ʧ�ܡ��˷��෢�ִ��� ����������ͬ��");
	}

	@Override
	protected void outputResult(double result) {
		System.out.println("�˷���� ********* " + result + " *****");
	}

}
