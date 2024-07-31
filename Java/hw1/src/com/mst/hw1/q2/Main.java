package com.mst.hw1.q2;

public class Main {


	public static void main(String[] args) {
		Instruments[] instruments = new Instruments[4]; 
		instruments[0] = new Guitar(11,"gg", "black",5);
		instruments[1] = new Drum(10, "dd", "brown", 10);
		instruments[2] = new Piano(11,"pp","blue", 30);
		instruments[3] = new GrandPiano(50,"grand","white",70,200);
		
		for(Instruments instrument : instruments) {
			instrument.display();
			instrument.makeSound();
			System.out.println();
		}

	}

}
