package com.mst.oop.exam;

import java.util.Arrays;

public class Student extends Person {
	Grade[] grades = new Grade[6];

	public Student(String name, int age, Grade[] grades) {
		super(name, age);
		this.grades = grades; // ??new Grade[6]
	}

	public Grade[] getGrades() {
		return grades;
	}

	@Override
	public String toString() {
		return super.toString() +" , Student [grades=\n" + Arrays.toString(grades) + "] \n";
	}
	
	
}
