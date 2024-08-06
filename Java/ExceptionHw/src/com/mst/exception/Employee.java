package com.mst.exception;

public class Employee {
	private static final double MAXIMUM_SALARY = 40000.0;
	private final String name;
	private final int age ;
	private final double salary;
	
	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}
	
	public double getSalary() {
		return salary;
	}

	public Employee(String name, int age, double salary) throws InvalidEmployeeSalaryException{
		this.name = name;
		this.age = age;
		if(salary > MAXIMUM_SALARY) {
			throw new InvalidEmployeeSalaryException("Salary cannot exceed " + MAXIMUM_SALARY);
		} 
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", age=" + age + ", salary=" + salary + "]";
	}
	
	
}
