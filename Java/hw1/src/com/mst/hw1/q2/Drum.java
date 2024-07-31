package com.mst.hw1.q2;

public class Drum extends Instruments {
	private int diameter;//must be positive to 100 cm

	@Override
	public String toString() {
		return "Drum [diameter=" + diameter + "] " + super.toString();
	}

	public int getDiameter() {
		return diameter;
	}

	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}

	public Drum() {
		super();
	}

	public Drum(int model, String manufacturer, String color, int diameter) {
		super(model, manufacturer, color);
		if(diameter <= 0 || diameter > 100) { 
			System.out.println("Invalid diameter");
		}else {
			this.diameter = diameter;
		}
	}

	@Override
	public void makeSound() {
		System.out.println("Making Drum Sound");

	}

}
