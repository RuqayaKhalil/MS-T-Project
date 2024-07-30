package com.mst.hw1.q1;

public class Samsung extends Device {
	private boolean outbreak;
	
	public Samsung() {
		super();
	}
	
	public Samsung(int model, String manufacturer, int screenSize, boolean outbreak) {
		super(model, manufacturer, screenSize);
		this.outbreak = outbreak;
	}
	
	public boolean isOutbreak() {
		return outbreak;
	}

	public void setOutbreak(boolean outbreak) {
		this.outbreak = outbreak;
	}
	
	@Override
	public String toString() {
		return "Samsung[outbreak=" + outbreak + "] " + super.toString();
	} 
	
	public void showLogo() {
		System.out.println("SAMSUNG");
	}
	
}
