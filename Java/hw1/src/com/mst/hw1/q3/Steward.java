package com.mst.hw1.q3;

public abstract class Steward extends AirportStaff {
	private String CountryOfOrigin;

	public Steward() {
		super();
	}

	public Steward(String name, String familyName, int seniorityYears, String countryOfOrigin) {
		super(name, familyName, seniorityYears);
		CountryOfOrigin = countryOfOrigin;
	}

	public String getCountryOfOrigin() {
		return CountryOfOrigin;
	}

	public void setCountryOfOrigin(String countryOfOrigin) {
		CountryOfOrigin = countryOfOrigin;
	}

	@Override
	public String toString() {
		return "Steward [CountryOfOrigin=" + CountryOfOrigin + "] " + super.toString();
	}
	
	public boolean isSenior() {
		return this.getSeniorityYears() >= 5;
	}

}
