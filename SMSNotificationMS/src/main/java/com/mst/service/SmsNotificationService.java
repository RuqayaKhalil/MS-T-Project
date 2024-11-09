package com.mst.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.mst.beans.LogLevel;
import com.mst.beans.LogRequestDTO;
import com.mst.beans.Notification;
import com.mst.controller.LoggerClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsNotificationService {


    @Value("${twilio.phone.number}")
    private String fromNumber;
    
    @Value("${recipient.phone.number}")
    private String recipientPhoneNumber;
    
    @Autowired
	private LoggerClient loggerClient;
    
    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public ResponseEntity<String> listen(Notification notification) {
    	System.out.println("Notification recieved with message " + notification.getMessage() + " and destination "
				+ notification.getDestination());
    	System.out.println("LISTEN FUNC OPEN");
        
        // Send SMS with the received Kafka message
       try { 
    	   sendSms(recipientPhoneNumber, notification.getMessage());
    	   System.out.println("LISTEN FUNCTION DONE");
           LogRequestDTO loggerMessage = LogRequestDTO.builder() 
           		.serviceName("smsNotification-service") 
           		.message("The message " + notification.getMessage()+ " was sent successfully to phone number " + recipientPhoneNumber) 
           		.logLevel(LogLevel.INFO) 
           		.build(); 
           loggerClient.createLogMessage(loggerMessage);
           return ResponseEntity.ok("message sent successfully");
       }catch(Exception e) {
    	   System.err.println("ERROR SENDING SMS: " + e.getMessage());
    	   LogRequestDTO loggerMessage = LogRequestDTO.builder() 
           		.serviceName("smsNotification-service") 
           		.message("ERROR SENDING SMS to phone number "+ recipientPhoneNumber + "\nERROR"+ e.getMessage()) 
           		.logLevel(LogLevel.INFO) 
           		.build(); 
           loggerClient.createLogMessage(loggerMessage);
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("error occurred while sending message: " + e.getMessage());
       }       
      
    }
    

    public ResponseEntity<String> sendSms(String to, String body) {
    	System.out.println("SEND SMS TO PHONE NUMBER");
        Message.creator(
            new PhoneNumber(to),
            new PhoneNumber(fromNumber),
            body
        ).create();
        System.out.println("SUCCESS SENDING SMS ");
        return ResponseEntity.ok("SUCCESS SENDING SMS");
    }
}

