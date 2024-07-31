package com.mst.collect.pr2;

public class Service {
	
	public static double getAverage(int a, int b, int c) {
		return ((double)(a+b+c))/((double)3);
	}
	
	public static int getRandomNumber (int min, int max) {
		return (int)(min + Math.random()*max);
	} 
	
	public static boolean isEven (int num) {
		return num%2 == 0;
	} 
	
	public static boolean isOdd (int num) {
		return num%2 != 0;
	}
}
