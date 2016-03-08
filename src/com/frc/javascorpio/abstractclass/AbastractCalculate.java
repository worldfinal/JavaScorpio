package com.frc.javascorpio.abstractclass;

public abstract class AbastractCalculate {
	public void doCalculate(double x, double y) {
		if (validate(x, y) == false) {
			outputError();
		} else {
			double result = calculate(x, y);
			outputResult(result);
		}
	}
	abstract protected double calculate(double x, double y);
	abstract protected boolean validate(double x, double y);
	abstract protected void outputError();
	abstract protected void outputResult(double result);
}
