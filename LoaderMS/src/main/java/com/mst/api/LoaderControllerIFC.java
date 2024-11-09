package com.mst.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.mst.beans.EntryInfo;
import com.mst.beans.Metric;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

public interface LoaderControllerIFC {

	
	@Operation(summary = " load and read csv-files from gitHub ", tags = {"platformInformation" , "POST"})
	@ApiResponses({
		@ApiResponse(responseCode = "200" , content = {@Content (schema = @Schema(implementation = EntryInfo.class), mediaType = "application/json")}),
		@ApiResponse(responseCode = "500" , content = { @Content(schema = @Schema()) }) })

	public ResponseEntity<String> loadReadFiles();
	
	
	@Operation(summary = " check if metrics meet the condition ", tags = {"platformInformation" , "POST"})
	@ApiResponses({
		@ApiResponse(responseCode = "200" , content = {@Content (schema = @Schema(implementation = EntryInfo.class), mediaType = "application/json")}),
		@ApiResponse(responseCode = "500" , content = { @Content(schema = @Schema()) }) })
	
	public ResponseEntity<HashMap<Integer, Boolean>> checkIfMetricsMeetTheCondition(
			@RequestBody List<Metric> metricsToCheck);
	
	
	@Operation(summary = " developer with the most occurrences of a label ", tags = {"platformInformation" , "GET"})
	@ApiResponses({
		@ApiResponse(responseCode = "200" , content = {@Content (schema = @Schema(implementation = EntryInfo.class), mediaType = "application/json")}),
		@ApiResponse(responseCode = "500" , content = { @Content(schema = @Schema()) }) })
	
	public ResponseEntity<String> developerMostOccurrence(@RequestParam String label, @RequestParam String since);
	
	
	@Operation(summary = " aggregation of labels for developer ", tags = {"platformInformation" , "GET"})
	@ApiResponses({
		@ApiResponse(responseCode = "200" , content = {@Content (schema = @Schema(implementation = EntryInfo.class), mediaType = "application/json")}),
		@ApiResponse(responseCode = "500" , content = { @Content(schema = @Schema()) }) })
	public ResponseEntity<Map<String, Integer>> aggregationOfLabel(@PathVariable String developer_id,
			@RequestParam String since);
	
	
	@Operation(summary = " total tasks for developer ", tags = {"platformInformation" , "GET"})
	@ApiResponses({
		@ApiResponse(responseCode = "200" , content = {@Content (schema = @Schema(implementation = EntryInfo.class), mediaType = "application/json")}),
		@ApiResponse(responseCode = "500" , content = { @Content(schema = @Schema()) }) })
	public ResponseEntity<Long> totalTasks(@PathVariable String developer_id, @RequestParam String since);
}
