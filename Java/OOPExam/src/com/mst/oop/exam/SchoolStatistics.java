package com.mst.oop.exam;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class SchoolStatistics {

	//section 6 in bonus q
	public static List<Student> sportsTeam(School school){
		List<Student> students = new ArrayList<>();
		for(ClassRoom classroom : school.getClassroom()) {
			for(Student student: classroom.getStudents()) {
				if(student.getGrades()[Profession.sports.ordinal()].getScore() >= 90) {
					students.add(student);
				}
			}
		}
		return students;
	}
	
	//section 3 in bonus q
	public static double ageAvg(School school) {
		double ageSum = 0;
		for(ClassRoom classroom : school.getClassroom()) {
			for(Student student: classroom.getStudents()) {
				ageSum += student.getAge();
			}
		}
		return ageSum/(15*5);
	}
	
	//sections 4+5 in bonus q
	public static void teacherNum(School school) {
		int physicsMathTeachersNumber = 0, sportsTeacherNumber =0, literatureTeacherNumber =0;
		for(ClassRoom classroom : school.getClassroom()) {
			if(classroom.getTeacher().getProfession() == Profession.math ||
					classroom.getTeacher().getProfession() == Profession.phisycs) {
						physicsMathTeachersNumber++;
			} else if(classroom.getTeacher().getProfession() == Profession.sports) {
				sportsTeacherNumber++;
			} else if(classroom.getTeacher().getProfession() == Profession.literature) {
				literatureTeacherNumber++;
			}
			
		}
	}
	
	
	public static boolean ageBetween20and30(int age) {
		return age >=20 && age <=30;
	}
	
	public static boolean ageEqualGreater31(int age) {
		return age >=31;
	}
	
	//sections 1+2 in bonus q
	public static void studentsNum203031(School school) {
		int studentsNumber = 0;
		double studentsAvgSum =0;
		int age;
		int studentsNumber31 = 0;
		double studentsAvgSum31 =0;
		for(ClassRoom classroom : school.getClassroom()) {
			for(Student student: classroom.getStudents()) {
				age = student.getAge();
				if(ageBetween20and30(age)) {
					studentsNumber += age;
					studentsAvgSum += getEachStudentAvg(student);
				}
				else if(ageEqualGreater31(age)) {
					studentsNumber31 += age;
					studentsAvgSum31 += getEachStudentAvg(student);
				}
			}
		}
	}
	
	//calculate for class its total avg
	//each class has 15 students
	public static double getEachClassAvg(ClassRoom classroom) {
		double totalGrades = 0;
		for(Student student : classroom.getStudents()) { 
			totalGrades += getEachStudentAvg(student);
		}
		return totalGrades/15;
	}
	
	//calculate for each student its avg
	//each student has 6 scores
	public static double getEachStudentAvg(Student student) {
		int sumGrades = 0;
		for(Grade grade: student.getGrades()) {
			sumGrades += grade.getScore(); 
		}
		return (double)(sumGrades/6);
	}
	
	//find total avg of school
	public static void schoolTotalAvg(School school) {
		double totalAvg = 0;
		for(ClassRoom classroom : school.getClassroom()) {
			totalAvg += getEachClassAvg(classroom);
		}
		System.out.println("School Average: " + totalAvg);
	}
	
	public static void ClassTotalAvg(School school) {
		for(ClassRoom classroom: school.getClassroom()) {
			System.out.println("ClassRoom "+ classroom.getName() + " Average: " + getEachClassAvg(classroom));
		}
	}
	
	public static void SchoolProfessionAvg(School school) {
		
		double[] professions = new double[6];
		for(ClassRoom classroom : school.getClassroom()) {
			for(Student student: classroom.getStudents()) {
				for(Grade grade: student.getGrades()) { // add the score of each student in each profession
					professions[grade.getProfession().ordinal()]  += grade.getScore(); 
				}
			}
		}
		for(int i=0; i<6 ;i++) {
			professions[i] = professions[i]/(15*5);//15*5 is the number of students in school
			System.out.println("Profession " + i + " Average: " + professions[i]);
		}
	}
	
	public static void main(String[] args) {
		ClassRoom[] classrooms = new ClassRoom[5];
		
		
		for(int i=0 ; i < 5; i++) {
			Student[] tempStudents = new Student [15];
			for(int j=0; j<15; j++) {
				Grade [] tempGrades = new Grade[6];
				for(int k=0; k<6; k++) {
					tempGrades[k] = new Grade(Profession.values()[k], (int)(Math.random()*61)+40);//can change to use set fields instead of constructor
				}
				tempStudents[j] = new Student("Student"+i+"-"+j,(int)(Math.random()*101)+20,tempGrades);
			}
			classrooms[i] = new ClassRoom("Class"+i,new Teacher("Teacher"+i,50,Profession.values()[(int)(Math.random()*6)]),tempStudents);
		}
		
		School school = new School(classrooms);
		System.out.println(school);
		System.out.println();
		schoolTotalAvg(school);
		ClassTotalAvg(school);
		SchoolProfessionAvg(school);
		
	} 
}
