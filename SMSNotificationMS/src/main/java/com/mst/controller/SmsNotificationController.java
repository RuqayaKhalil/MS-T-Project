package com.mst.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mst.api.SmsNotificationControllerIFC;
import com.mst.beans.Notification;
import com.mst.service.SmsNotificationService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name ="SMSNotification service" , description = "SMSNotification APIs")
@RestController
@RequestMapping("/smsnotification")
public class SmsNotificationController implements SmsNotificationControllerIFC{

	
	@Autowired
	private SmsNotificationService smsNotificationService;
	


    // EndPoint to manually trigger SMS sending
    @PostMapping("/send")
    public ResponseEntity<String> sendSmsNotification(@RequestBody Notification notification) {
        try {
            smsNotificationService.sendSms(notification.getDestination(), notification.getMessage());
            return ResponseEntity.ok("SMS sent successfully!");
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);//"Failed to send SMS: " + e.getMessage();
        }
    }

}

