package com.mst.hw1.q1;

public class Main {

	public static void main(String[] args) {
		Iphone iphone1 = new Iphone(11, "Steve Jobs", 15, 20);
		Iphone iphone2= new Iphone(8, "Steve Jobs", 10, 1);
		Samsung samsung1= new Samsung(5, "BBB", 14, false);
		Samsung samsung2= new Samsung(12, "CCC", 13, true);
		
		Device[] devices = {iphone1, iphone2, samsung1, samsung2};
		for(Device device : devices){
			device.showDetails();
			device.showLogo();
			System.out.println();
		}	
	}
}
