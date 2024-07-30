package com.mst.hw1.q3;

public abstract class AirportStaff extends Person {
	private int seniorityYears;
	
	public AirportStaff() {
		super();
	}
	

	public AirportStaff(String name, String familyName, int seniorityYears) {
		super(name, familyName);
		this.seniorityYears = seniorityYears;
	}


	public int getSeniorityYears() {
		return seniorityYears;
	}

	public void setSeniorityYears(int seniorityYears) {
		this.seniorityYears = seniorityYears;
	}

	@Override
	public String toString() {
		return "AirportStaff [seniorityYears=" + seniorityYears + "] "+ super.toString();
	}

}
