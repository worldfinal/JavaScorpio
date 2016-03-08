package com.frc.javascorpio.abstractclass;

public class testAbstractCalculate {
	public static void main(String[] args) {
		MultiplyCalculate mc = new MultiplyCalculate();
		mc.doCalculate(1, 6);
		mc.doCalculate(2, -3);
		
		System.out.println("=================================");
		SubstractCalculate sc = new SubstractCalculate();
		sc.doCalculate(123, 23);
		sc.doCalculate(-1, -1);
	}
}
