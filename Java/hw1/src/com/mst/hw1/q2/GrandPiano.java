package com.mst.hw1.q2;

public class GrandPiano extends Instruments {
	private int numOfKeys;
	private int length; // must be from 0 to 300 cm
	public GrandPiano() {
		super();
	}

	public GrandPiano(int model, String manufacturer, String color, int numOfKeys, int length) {
		super(model, manufacturer, color);
		this.numOfKeys = numOfKeys;
		if(length <= 0 || length > 300) {
			System.out.println("Invalid length");
		} else {
			this.length = length;
		}
	}

	public int getNumOfKeys() {
		return numOfKeys;
	}

	public void setNumOfKeys(int numOfKeys) {
		this.numOfKeys = numOfKeys;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "GrandPiano [numOfKeys=" + numOfKeys + ", length=" + length + "] " + super.toString();
	}

	@Override
	public void makeSound() {
		System.out.println("Making GrandPiano Sound");

	}

}
