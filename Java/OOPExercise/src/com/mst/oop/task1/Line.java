package com.mst.oop.task1;

public class Line {
	private Point p1,p2;

	public Line(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	

	public Line(Line line) {
		this.p1 = line.getP1();
		this.p2 = line.getP2();
	}

	
	public Point getP1() {
		return p1;
	}


	public Point getP2() {
		return p2;
	}

	
	@Override
	public String toString() {
		return "Line [p1=(" + p1.getX() + "," +p1.getY()+")"+ " , p2=(" + p2.getX() + "," + p2.getY() + ")]";
	}
	
	
}
