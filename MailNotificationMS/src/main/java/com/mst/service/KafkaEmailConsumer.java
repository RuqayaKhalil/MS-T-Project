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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaEmailConsumer {

	@Value("${email.destination}")
	private String managerEmail;	
	
//	@Value("${kafka.topic.name}")
//	private String topicName;
//	
//	@Value("${spring.kafka.consumer.group-id}")
//	private String groupId;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) {
        System.out.printf("Received message: %s%n", message);

        // Send email when a message is received
        sendEmail(managerEmail, "New Kafka Message", message);
    }
	public void sendEmail(String recipient, String subject, String text) {
    	String from = "";//TODO:add email
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
                return new PasswordAuthentication(from, "");//TODO: add token
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
        } catch (MessagingException e) {
            e.printStackTrace();
        }
		
	}
}


