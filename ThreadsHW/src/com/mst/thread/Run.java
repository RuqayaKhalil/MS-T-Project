package com.mst.thread;

public class Run {

	public static void main(String[] args) {
		Speedy speedy1 = new Speedy();
		Speedy speedy2 = new Speedy(speedy1);
		speedy1.setSpeedy(speedy2);

		Thread thread1 = new Thread(speedy1,"speedy1");
		Thread thread2 = new Thread(speedy2,"speedy2");
		thread1.start();
		thread2.start();
//		thread1.run();
		
		
		//this part is related to main thread run, main cannot be terminated until t1, t2 are terminated! - optional
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Thread.currentThread().interrupt();
		//Thread.currentThread().join();

		
		System.out.println("speedy1 runs:" + speedy1.getCount() +" times");
		System.out.println("speedy2 runs:" + speedy2.getCount() +" times");
		
		
		if(speedy1.getCount() > speedy2.getCount()) {
			System.out.println("Speedy2 is terminated by Speedy1, and it runs " +speedy2.getCount()+" times" );
		}
		else if(speedy1.getCount() < speedy2.getCount()) {
			System.out.println("Speedy1 is terminated by Speedy2, and it runs " +speedy1.getCount()+" times" );
		}
		else {
			System.out.println("Both managed to finish before one stopped the other!");
		}
		
	}

}
