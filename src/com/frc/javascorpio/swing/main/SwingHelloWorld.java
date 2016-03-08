package com.frc.javascorpio.swing.main;

import javax.swing.JFrame;
import javax.swing.JPasswordField;

public class SwingHelloWorld {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("Hello World!");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(400, 400);
		
		
		JPasswordField txtPwd= new JPasswordField(20);
		txtPwd.setEchoChar('#');
		frame.add(txtPwd);
		txtPwd.setLocation(50, 50);
		txtPwd.setSize(200, 20);
		
		frame.setVisible(true);

		}

}
