package com.mst.hw1.q2;

public class Piano extends Instruments {
	private int numOfKeys;//must be from 20 to 200

	public Piano() {
		super();
	}

	public Piano(int model, String manufacturer, String color, int numOfKeys) {
		super(model, manufacturer, color);
		if(numOfKeys <= 20 || numOfKeys > 200) {
			System.out.println("Invalid number of keys");
		} else {
			this.numOfKeys = numOfKeys; 
	     }
	}

	public int getNumOfKeys() {
		return numOfKeys;
	}

	public void setNumOfKeys(int numOfKeys) {
		this.numOfKeys = numOfKeys;
	}

	@Override
	public String toString() {
		return "Piano [numOfKeys=" + numOfKeys + "] " + super.toString();
	}

	@Override
	public void makeSound() {
		System.out.println("Making Piano Sound");

	}

}
