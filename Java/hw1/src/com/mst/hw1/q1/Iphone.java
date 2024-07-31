package com.mst.hw1.q1;

public class Iphone extends Device {
	private int numOfApps;
	
	public Iphone() {
		super();
	}
	
	public Iphone(int model, String manufacturer, int screenSize, int numOfApps) {
		super(model, manufacturer, screenSize);
		this.numOfApps = numOfApps;
	}
	
	public int getNumOfApps() {
		return numOfApps;
	}

	public void setNumOfApps(int numOfApps) {
		this.numOfApps = numOfApps;
	}
	
	@Override
	public String toString() {
		return "Iphone[numOfApps=" + numOfApps + "] " + super.toString();
	}

	
	public void showLogo() {
		System.out.println("        .:'"); 
		System.out.println("     __ :'__"); 
		System.out.println("  .'`__`-'__``."); 
		System.out.println(" :__________.-'"); 
		System.out.println(" :_________:"); 
		System.out.println("  :_________`-;"); 
		System.out.println("    `.__.-.__.'");
	}
	

}
