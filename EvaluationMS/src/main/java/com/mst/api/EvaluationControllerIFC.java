package com.mst.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.mst.beans.Notification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface EvaluationControllerIFC {
	
	
	@Operation(summary = " developer with the most occurrences of a label ", tags = {"GET"})
	@ApiResponses({
		@ApiResponse(responseCode = "200" , content = {@Content (schema = @Schema())}),
		@ApiResponse(responseCode = "500" , content = { @Content(schema = @Schema()) }) })
	
	public ResponseEntity<Notification> developerMostOccurrence(@RequestParam String label, @RequestParam String since);
	
	
	@Operation(summary = " aggregation of labels for developer ", tags = {"GET"})
	@ApiResponses({
		@ApiResponse(responseCode = "200" , content = {@Content (schema = @Schema())}),
		@ApiResponse(responseCode = "500" , content = { @Content(schema = @Schema()) }) })
	
	public ResponseEntity<Notification> aggregationOfLabel(@PathVariable String developer_id, @RequestParam String since);
	
	
	@Operation(summary = " total tasks for developer ", tags = {"GET"})
	@ApiResponses({
		@ApiResponse(responseCode = "200" , content = {@Content (schema = @Schema())}),
		@ApiResponse(responseCode = "500" , content = { @Content(schema = @Schema()) }) })
	
	public ResponseEntity<Notification> totalTasks(@PathVariable String developer_id, @RequestParam String since);

}
