package com.mst.hw1.q3;

public class Pilot extends AirportStaff {
	private int licenseNumber; 

	public Pilot() {
		super();
	}

	public Pilot(String name, String familyName, int seniorityYears, int licenseNumber) {
		super(name, familyName, seniorityYears);
		this.licenseNumber = licenseNumber;
	}



	public int getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(int licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	@Override
	public String toString() {
		return "Pilot [licenseNumber=" + licenseNumber + "] " + super.toString();
	}
	

}
