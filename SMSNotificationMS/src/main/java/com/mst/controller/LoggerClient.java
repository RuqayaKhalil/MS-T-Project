package com.mst.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.mst.beans.LogRequestDTO;

@FeignClient(name="logger-service", url = "${logger.service.url}")
public interface LoggerClient {
	
	@PostMapping("/create")
	public void createLogMessage(LogRequestDTO logRequest);

}
