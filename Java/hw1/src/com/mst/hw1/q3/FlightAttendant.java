package com.mst.hw1.q3;

public class FlightAttendant extends Steward {
	private String classAirplane; 

	public FlightAttendant() {
		super();
	}


	public FlightAttendant(String name, String familyName, int seniorityYears, String countryOfOrigin,
			String classAirplane) {
		super(name, familyName, seniorityYears, countryOfOrigin);
		this.classAirplane = classAirplane;
	}


	public String getClassAirplane() {
		return classAirplane;
	}

	public void setClassAirplane(String classAirplane) {
		this.classAirplane = classAirplane;
	}

	@Override
	public String toString() {
		return "FlightAttendant [classAirplane=" + classAirplane + "] " + super.toString();
	}

}
