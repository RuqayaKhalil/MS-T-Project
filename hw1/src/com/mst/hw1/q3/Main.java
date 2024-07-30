package com.mst.hw1.q3;

public class Main {

	public static void main(String[] args) {
		Pilot pilot = new Pilot("p1","p1f",10,111111);
		FlightAttendant s1 = new FlightAttendant("s1","s1f",4,"France","Economy");
		GroundSteward s2 = new GroundSteward("s2","s2f",6,"France","Service");
		Passenger passenger = new Passenger("p","pf",44444);
		
		Person[] persons = {pilot, s1, s2, passenger};
		for(Person p : persons) {
			p.getDetails();
			if(p instanceof Steward) {
				System.out.println("steward is senior: " +((Steward) p).isSenior());
			}
			System.out.println();
		}

	}

}
