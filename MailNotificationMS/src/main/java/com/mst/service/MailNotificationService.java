package com.mst.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.mst.beans.LogLevel;
import com.mst.beans.LogRequestDTO;
import com.mst.beans.Notification;
import com.mst.controller.LoggerClient;

@Service
public class MailNotificationService {

	@Autowired
	private LoggerClient loggerClient;
	
	@Value("${email}")
	private String from;
	
	@Value("${password}")
	private String password;

	@KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
	public void listen(Notification notification) {
		System.out.println("Notification recieved with message " + notification.getMessage() + " and destination "
				+ notification.getDestination());
		// Send email when a message is received
		sendEmail(notification.getDestination(), "New notificaion", notification.getMessage());
	}

	public void sendEmail(String recipient, String subject, String text) {
		String host = "smtp.gmail.com";

		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587");

		// Additional properties
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2"); // Enforce TLSv1.2
		properties.put("mail.smtp.starttls.required", "true"); // Require STARTTLS

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject(subject);
			message.setText(text);

			Transport.send(message);
			System.out.println("Email sent successfully!");
			LogRequestDTO loggerMessage = LogRequestDTO.builder().serviceName("mailNotification-service")
					.message("Email sent successfully with message " + text + "to mail: " + recipient)
					.logLevel(LogLevel.INFO).build();
			loggerClient.createLogMessage(loggerMessage);
		} catch (MessagingException e) {
			LogRequestDTO loggerMessage = LogRequestDTO
					.builder().serviceName("mailNotification-service").message("Email failed to send with message "
							+ text + "to mail: " + recipient + "\nERROR: " + e.getMessage())
					.logLevel(LogLevel.INFO).build();
			loggerClient.createLogMessage(loggerMessage);
		}

	}
}
