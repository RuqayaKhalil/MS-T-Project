package com.mst.hw1.q2;

public class Guitar extends Instruments {
	private int numOfStrings;//must be from 4 to 10
	public Guitar() {
		super();
	}

	public Guitar(int model, String manufacturer, String color, int numOfStrings) {
		super(model, manufacturer, color);
		if(numOfStrings >=4 && numOfStrings <= 10) {
			this.numOfStrings = numOfStrings;
			
		} else {
			System.out.println("Invalid number of strings");
		}
		
	}

	public int getNumOfStrings() {
		return numOfStrings;
	}

	public void setNumOfStrings(int numOfStrings) {
		this.numOfStrings = numOfStrings;
	}

	@Override
	public void makeSound() {
		System.out.println("Making Guitar Sound");

	}

	@Override
	public String toString() {
		return "Guitar [numOfStrings=" + numOfStrings + "] " + super.toString();
	}
	

}
