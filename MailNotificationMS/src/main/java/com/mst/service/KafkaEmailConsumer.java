////package com.mst.service;
////
////import org.apache.kafka.clients.consumer.ConsumerConfig;
////import org.apache.kafka.clients.consumer.ConsumerRecord;
////import org.apache.kafka.clients.consumer.KafkaConsumer;
////import org.springframework.scheduling.annotation.Scheduled;
////import org.springframework.stereotype.Component;
////import org.springframework.stereotype.Service;
////
////import com.fasterxml.jackson.databind.JsonDeserializer;
////import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
////
////import jakarta.annotation.PostConstruct;
////
////import org.apache.kafka.clients.consumer.ConsumerRecords;
////
////import java.time.Duration;
////import java.util.Collections;
////import java.util.Properties;
////
////@Service
////public class KafkaEmailConsumer {
////	 private KafkaConsumer<String, String> consumer;
////	 @PostConstruct
////    public void init() {
////    	System.out.println("ENTER HERE CHECK");
////    	// create consumer configs
////        Properties properties = new Properties();
////        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "${spring.kafka.bootstrap-servers}");
////        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
////        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
////        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "${spring.kafka.consumer.group-id}");
////        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
////       
////        consumer = new KafkaConsumer<>(properties);
////        consumer.subscribe(Collections.singletonList("${kafka.topic.name}"));
////
////
////        EmailNotificationService emailService = new EmailNotificationService();
////        String recipient = "ruqayakamal18199@gmail.com";
////
////        // Consume messages and send email
////        while (true) {
////            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));  // Preferred method
////            for (ConsumerRecord<String, String> record : records) {
////                System.out.printf("Received message: %s%n", record.value());
////
////                // Send email when a message is received
////                emailService.sendEmail(recipient, "New Kafka Message", record.value());
////            }
////        }
////    }
////}
////
//
//package com.mst.service;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.springframework.stereotype.Service;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//
//import jakarta.annotation.PostConstruct;
//import java.time.Duration;
//import java.util.Collections;
//import java.util.Properties;
//
//@Service
//public class KafkaEmailConsumer {
//    private KafkaConsumer<String, String> consumer;
//
//    @PostConstruct
//    public void init() {
//        System.out.println("ENTER HERE CHECK");
//        
//        // Create consumer configs
//        Properties properties = new Properties();
//        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");  // replace with actual server address
//        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class.getName());
//        properties.put("spring.deserializer.value.delegate.class", JsonDeserializer.class.getName());
//        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "email-group");  // replace with your group ID
//        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//
//        consumer = new KafkaConsumer<>(properties);
//        consumer.subscribe(Collections.singletonList("email-notification"));  // replace with your actual topic name
//
//        EmailNotificationService emailService = new EmailNotificationService();
//        String recipient = "ruqayakamal18199@gmail.com";
//
//        System.out.println("BEFORE READING MEASSGAES");
//        // Consume messages and send email
//        while (true) {
//        	System.out.println("TRY TO SEND EMAIL");
//            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000)); 
//            System.out.println("AFTER RECORDS");
//            for (ConsumerRecord<String, String> record : records) {
//            	System.out.println("FOR LOOP IN");
//                System.out.printf("Received message: %s%n", record.value());
//
//                // Send email when a message is received
//                emailService.sendEmail(recipient, "New Kafka Message", record.value());
//            }
//        }
//    }
//}
//

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
//        emailService.sendEmail("ruqayakamal18199@gmail.com", "New Kafka Message", message);
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


