package com.mst.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mst.api.MailNotificationControllerIFC;
import com.mst.beans.Notification;
import com.mst.service.MailNotificationService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name ="MailNotification service" , description = "MailNotification APIs")
@RestController
@RequestMapping("/mailnotification")
public class MailNotificationController implements MailNotificationControllerIFC{

	@Autowired
	private MailNotificationService mailNotificationService;
	
    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmailNotification(@RequestBody Notification notification) {
        try {
            mailNotificationService.sendEmail(
                notification.getDestination(),
                "New Notification",
                notification.getMessage()
            );
            return ResponseEntity.ok("Email sent successfully!");
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);//"Failed to send email: " + e.getMessage());
        }
    }

}


