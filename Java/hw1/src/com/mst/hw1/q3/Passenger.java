package com.mst.hw1.q3;

public class Passenger extends Person {
	private int passportNumber;

	public Passenger() {
		super();
	}

	public Passenger(String name, String familyName, int passportNumber) {
		super(name, familyName);
		this.setPassportNumber(passportNumber);
	}

	public int getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(int passportNumber) {
		this.passportNumber = passportNumber;
	}

	@Override
	public String toString() {
		return "Passenger [passportNumber=" + passportNumber + "] " + super.toString();
	}

}
