package com.mst.oop.exam;
import java.lang.Math;

public class SchoolStatistics {
	public static void schoolAvg(School school) {
		System.out.println("School Average: ");
	}
	
	public static void eachClassAvg(School school) {
		for(ClassRoom classroom: school.getClassroom()) {
			System.out.println(classroom.getName() + " Average: ");
		}
	}
	
	public static void SchoolProfessionAvg(School school) {
		for(int i=0; i<6; i++) {
			System.out.println("Profession " + i + " Average: ");
		}
	}
	
	public static void main(String[] args) {
		ClassRoom[] classrooms = new ClassRoom[5];
		for(int i=0 ; i < 5; i++) {
			classrooms[i] = new ClassRoom("Class"+i,new Teacher("Teacher"+i,50,"enum[(int)(Math.random()*5)]"),new Student[15]);
		}
		
		for(int i=0; i<5; i++) {
			for(int j=0; j<15; j++) {
				classrooms[i].getStudents()[j] = new Student("Student"+i+"-"+j,20,new Grade[6]);
				for(int k=0; k<6; k++) {
					classrooms[i].getStudents()[j].getGrades()[k] = new Grade("enum[k]", (int)(Math.random()*60)+40);
				}
			}
		}
		School school = new School(classrooms);
		System.out.println(school);
		schoolAvg(school);
		eachClassAvg(school);
		SchoolProfessionAvg(school);
		
	} 
}
