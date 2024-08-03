package com.mst.oop.exam;

import java.util.Arrays;

public class ClassRoom {
	private String name;
	private Teacher teacher;
	private Student[] students = new Student[15];
	
	public ClassRoom(String name, Teacher teacher, Student[] students) {
		this.name = name;
		this.teacher = teacher;
		this.students = students;
	}

	public String getName() {
		return name;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public Student[] getStudents() {
		return students;
	}

	@Override
	public String toString() {
		return "\n\nClassRoom [name=" + name + ", teacher=" + teacher + "\n, students=\n" + Arrays.toString(students) + "]";
	}
	
}
