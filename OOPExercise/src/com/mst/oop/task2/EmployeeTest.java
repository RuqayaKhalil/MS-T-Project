package com.mst.oop.task2;

public class EmployeeTest {

	public static void main(String[] args) {
		Date birth = new Date(27,11,1986);
		Date hire = new Date(3,12,2010);
		Employee employee = new Employee("Rany", "Albeg Wein", birth, hire);
		
		System.out.println(employee);

	}

}
