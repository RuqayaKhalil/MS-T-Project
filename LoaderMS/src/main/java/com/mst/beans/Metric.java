package com.mst.beans;
import lombok.Data;

@Data
public class Metric {

	private Integer id;

	private String name;
    
	private String label; 
//	private LabelType label; //TODO:change to enum
	
	private int threshold;

	private int timeFrameHours;
}