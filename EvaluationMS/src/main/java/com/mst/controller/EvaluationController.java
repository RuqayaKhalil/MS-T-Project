package com.mst.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import com.mst.service.EvaluationService;

@RestController
@RequestMapping("/evaluation/developer")
public class EvaluationController {

//	@Autowired
//	EvaluationService evaluationService;

	@Autowired
	private LoaderClient loaderClient;

	@GetMapping("/mostlabel")
	public ResponseEntity<String> developerMostOccurrence(@RequestParam String label, @RequestParam String since) {
		String developerID = loaderClient.developerMostOccurrence(label, since);
		return ResponseEntity.ok("");
	}

	@GetMapping("/{developer_id}/labelaggregate")
	public ResponseEntity<String> aggregationOfLabel(@PathVariable String developer_id, @RequestParam String since) {
		HashMap<String, Integer> labelAggregationList = loaderClient.aggregationOfLabel(developer_id, since);
		return ResponseEntity.ok("");
	}

	@GetMapping("/{developer_id}/task-amount")
	public ResponseEntity<String> totalTasks(@PathVariable String developer_id, @RequestParam String since) {
		int totalTasks = loaderClient.totalTasks(developer_id, since);
		return ResponseEntity.ok("");
	}

}
