package com.mst.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

import com.mst.service.KafkaProducerService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;

import com.mst.api.EvaluationControllerIFC;
import com.mst.beans.Notification;

@Tag(name ="Evaluation service" , description = "Evaluation APIs")
@RestController
@RequestMapping("/evaluation/developer")
public class EvaluationController implements EvaluationControllerIFC{

	@Value("${email.destination}")
	private String managerEmail;	
	
	
	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	@Autowired
	private LoaderClient loaderClient;
	
	@Autowired
	private KafkaProducerService kafkaProducer;
	
//	@PostConstruct
//	public void init() {
//	    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&& Loaded managerEmail: " + managerEmail);
//	}

	@GetMapping("/most-label")
	public ResponseEntity<Notification> developerMostOccurrence(@RequestParam String label, @RequestParam String since) {
//		System.out.println("MANAGER EMAIL*****************************************************" + getManagerEmail());
		try{
			String developerID = loaderClient.developerMostOccurrence(label, since);
			Notification notification = Notification.builder()
					.message("developer id: " + developerID)
					.destination(managerEmail).build();
			kafkaProducer.sendMessage(notification);
			return ResponseEntity.ok(notification);
		}catch(Exception e) {
			Notification errorNotification = Notification.builder()
					.message("no developer found: " + e.getMessage())
					.destination(managerEmail).build();
			return new ResponseEntity<>(errorNotification, HttpStatus.BAD_REQUEST);
		}
		
	}

	@GetMapping("/{developer_id}/label-aggregate")
	public ResponseEntity<Notification> aggregationOfLabel(@PathVariable String developer_id, @RequestParam String since) {
		HashMap<String, Integer> labelAggregationList = loaderClient.aggregationOfLabel(developer_id, since);
		Notification notification = Notification.builder()
				.message("return list: " + labelAggregationList)
				.destination(managerEmail).build();
		kafkaProducer.sendMessage(notification);
		return ResponseEntity.ok(notification);
	}

	@GetMapping("/{developer_id}/task-amount")
	public ResponseEntity<Notification> totalTasks(@PathVariable String developer_id, @RequestParam String since) {
		int totalTasks = loaderClient.totalTasks(developer_id, since);
		Notification notification = Notification.builder()
				.message("total tasks: " + totalTasks)
				.destination(managerEmail).build();
		kafkaProducer.sendMessage(notification);
		return ResponseEntity.ok(notification);
	}

}
