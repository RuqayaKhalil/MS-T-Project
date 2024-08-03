package com.mst.oop.exam;

import java.util.Arrays;

public class School {
	private ClassRoom[] classroom = new ClassRoom[5];

	
	public School() {
	}

	public School(ClassRoom[] classroom) {
		this.classroom = classroom;
	}

	public ClassRoom[] getClassroom() {
		return classroom;
	}

	@Override
	public String toString() {
		return "School \n[classroom=" + Arrays.asList(classroom) + "]";
	}
	
	
}
