package com.mst.oop.task1;

public class Main {

	public static void main(String[] args) {
		Point p1 = new Point(2,3);
		Point p2 = new Point(4,6);
		Line line1 = new Line(p1,p2);
		Line line2 = new Line(line1);
		System.out.println("First line is: "+ line1);
		System.out.println("Second line is: "+ line2);

	}

}
