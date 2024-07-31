package com.mst.oop.exam;

public class Grade {
	private String profession = "Chemistry"; 
	private int score; //values 40-100
	
	public Grade(String profession, int score) {
		this.profession = profession;
		if(score < 40 || score > 100) {
			System.out.println("Score not in required range");
		}
		else {
			this.score = score;
		}
	}

	public String getProfession() {
		return profession;
	}

	public int getScore() {
		return score;
	}

	@Override
	public String toString() {
		return "Grade [profession=" + profession + ", score=" + score + "]\n";
	}
	
	
}
