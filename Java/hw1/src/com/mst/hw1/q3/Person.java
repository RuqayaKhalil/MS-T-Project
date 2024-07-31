package com.mst.hw1.q3;

public abstract class Person {
	private String name, familyName;

	public Person() {}

	public Person(String name, String familyName) {
		this.setName(name);
		this.setFamilyName(familyName);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", familyName=" + familyName + "]";
	}
	
	public void getDetails() {
		System.out.println(this);
	}
}
