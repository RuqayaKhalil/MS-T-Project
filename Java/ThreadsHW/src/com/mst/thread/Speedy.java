package com.mst.thread;

public class Speedy implements Runnable{
	private boolean flag = false;
	private Speedy speedy; 
	private int count = 0;
		
	public Speedy() {
		
	}
	
	public Speedy(Speedy speedy) {
		this.speedy = speedy;
	}
	
	public int getCount() {
		return count;
	}
	
	public void stop() { // the other competitor(this.sppedy) stops the speedy run.
		flag = true;
	}
	
	public void setSpeedy(Speedy speedy) {
		this.speedy = speedy;
	}

	@Override
	public void run() {
		try {
			for(int i=1; i <=100 & !flag; i++) {
				count = i;
				System.out.println(Thread.currentThread().getName() + " : " + i);
				Thread.sleep((long)(Math.random()*1000));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
<<<<<<< HEAD:ThreadsHW/src/com/mst/thread/Speedy.java
		
		//synchronized(this){
			if (!flag) {
				speedy.stop();
				System.out.println(Thread.currentThread().getName() + " counted to 100 and stopped the another speedy");
			} else {
				System.out.println(Thread.currentThread().getName() + " counted to " + getCount()
						+ " and is stopped by another speedy");
			}
		//}
=======
			
		if (!flag) {
			speedy.stop();
			System.out.println(Thread.currentThread().getName() + " counted to 100 and stopped the another speedy");
		} else {
			System.out.println(Thread.currentThread().getName() + " counted to " + getCount()
						+ " and is stopped by another speedy");
		}
>>>>>>> main:Java/ThreadsHW/src/com/mst/thread/Speedy.java
	}
}
