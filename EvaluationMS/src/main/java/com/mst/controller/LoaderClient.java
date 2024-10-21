package com.mst.controller;

import java.util.HashMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loader-service", url = "${loader.service.url}")
public interface LoaderClient {

	@GetMapping("/mostlabel")
	public String developerMostOccurrence(@RequestParam String label, @RequestParam String since);

	@GetMapping("/{developer_id}/labelaggregate")
	public HashMap<String, Integer> aggregationOfLabel(@PathVariable("developer_id") String developer_id,
			@RequestParam String since);

	@GetMapping("/{developer_id}/task-amount")
	public int totalTasks(@PathVariable("developer_id") String developer_id, @RequestParam String since);
}
