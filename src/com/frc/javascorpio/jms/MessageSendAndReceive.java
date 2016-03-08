package com.frc.javascorpio.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;

public class MessageSendAndReceive {
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ActiveMQConnectionFactory(
				"vm://localhost");
		Connection connection = factory.createConnection();
		connection.start();
		Queue queue = new ActiveMQQueue("testQueue");
		final Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Message message = session.createTextMessage("Hello JMS!");
		MessageProducer producer = session.createProducer(queue);
		producer.send(message);
		System.out.println("Send Message Completed!");
		MessageConsumer comsumer = session.createConsumer(queue);
		comsumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message m) {
				TextMessage textMsg = (TextMessage) m;                 
				try {                      
					System.out.println("get: " + textMsg.getText());                
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Test
	public void send() {
		try {
			ConnectionFactory factory = new ActiveMQConnectionFactory(
					"vm://localhost");
			Connection connection = factory.createConnection();
			connection.start();
			Queue queue = new ActiveMQQueue("testQueue");
			final Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			Message message = session.createTextMessage("Hello JMS!");
			MessageProducer producer = session.createProducer(queue);
			producer.send(message);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}