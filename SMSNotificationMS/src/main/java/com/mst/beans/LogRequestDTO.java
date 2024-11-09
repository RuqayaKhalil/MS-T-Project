package com.mst.beans;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogRequestDTO {
	private String serviceName;
	private LogLevel logLevel;
	private String message;

}