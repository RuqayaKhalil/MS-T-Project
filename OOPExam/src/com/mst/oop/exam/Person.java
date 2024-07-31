package com.mst.oop.exam;

public class Person {
	private String name;
	private int age; //values 20-120
	
	public Person(String name, int age) {
		this.name = name;
		if(age < 20 || age > 120) {
			System.out.println("Age not in required range");
		}else {
			this.age = age;
		}
	}

	public String getName() {
		return name;
	}
	
	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}
	
	
	
}
