package com.mst.api;

import org.springframework.http.ResponseEntity;

import com.mst.beans.Notification;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface SmsNotificationControllerIFC {
	@Operation(summary = " send new sms-notification ", tags = {"POST"})
	@ApiResponses({
		@ApiResponse(responseCode = "200" , content = {@Content (schema = @Schema())}),
		@ApiResponse(responseCode = "500" , content = { @Content(schema = @Schema()) }) })
	
	public ResponseEntity<String> sendSmsNotification(@RequestBody Notification notification);
}
