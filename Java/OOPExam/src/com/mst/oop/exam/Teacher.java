package com.mst.oop.exam;

public class Teacher extends Person {
	private Profession profession;

	public Teacher(String name, int age, Profession profession) {
		super(name, age);
		this.profession = profession;
	}

	public Profession getProfession() {
		return profession;
	}

	@Override
	public String toString() {
		return super.toString()+ " , Teacher [profession=" + profession + "]";
	}
	
	
}
