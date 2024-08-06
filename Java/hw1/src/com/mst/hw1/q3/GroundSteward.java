package com.mst.hw1.q3;

public class GroundSteward extends Steward {
	private String role;

	public GroundSteward() {
		super();
	}


	public GroundSteward(String name, String familyName, int seniorityYears, String countryOfOrigin, String role) {
		super(name, familyName, seniorityYears, countryOfOrigin);
		this.role = role;
	}


	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "GroundSteward [role=" + role + "] " + super.toString();
	}


}
