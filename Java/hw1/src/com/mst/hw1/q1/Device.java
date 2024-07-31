package com.mst.hw1.q1;

public abstract class Device {
	private int model;
	private String manufacturer;
	private int screenSize;


public Device() {
	
}
	
public Device(int model, String manufacturer, int screenSize) {
	this.model = model;
	this.manufacturer = manufacturer;
	checkScreenSize(screenSize);
}

public  void checkScreenSize(int screenSize) {
if(screenSize >= 5 && screenSize <= 20) {
	this.screenSize = screenSize;
	}else {
		System.out.println("Invalid Screen Size!");
	}
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


public int getScreenSize() {
	return screenSize;
}


public void setScreenSize(int screenSize) {
	this.screenSize = screenSize;
}

@Override
public String toString() {
	return "Device[model=" + model + ", manufacturer=" + manufacturer + ", screenSize=" + screenSize + "]";
}

public void showDetails() {
	System.out.println(this);
}

public abstract void showLogo();

}
