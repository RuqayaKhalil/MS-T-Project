package com.mst.oop.task2;

public class Employee {
	private String name, familyName;
	private Date birthDate, hireDate;
	
	public Employee(String name, String familyName, Date birthDate, Date hireDate) {
		this.name = name;
		this.familyName = familyName;
		this.birthDate = birthDate;
		this.hireDate = hireDate;
	}

	
	public String getName() {
		return name;
	}

	public String getFamilyName() {
		return familyName;
	}


	public Date getBirthDate() {
		return birthDate;
	}


	public Date getHireDate() {
		return hireDate;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", familyName=" + familyName + ", birthDate=" + birthDate + ", hireDate="
				+ hireDate + "]";
	}
	
	
	

}
