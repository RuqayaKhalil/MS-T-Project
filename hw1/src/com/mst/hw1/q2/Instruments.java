package com.mst.hw1.q2;

public abstract class Instruments {
	private int model;
	private String manufacturer, color;

	public Instruments() {}
	
	public Instruments(int model, String manufacturer, String color) {
		this.model = model;
		this.manufacturer = manufacturer;
		this.color = color;
	}
	
	public int getModel() {
		return model;
	}

	public void setModel(int model) {
		this.model = model;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Instruments [model=" + model + ", manufacturer=" + manufacturer + ", color=" + color + "]";
	}
	
	public void display() {
		System.out.println(this);
	}
	
	public abstract void makeSound();
}
