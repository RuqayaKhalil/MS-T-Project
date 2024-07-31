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
		
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("speedy1 runs:" + speedy1.getCount() +" times");
		System.out.println("speedy2 runs:" + speedy2.getCount() +" times");
	}

}
