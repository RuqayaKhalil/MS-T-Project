package com.mst.oop.task2;

public class Date {
	private int month,day,year;

	public Date(int month, int day, int year) {
		checkMonth(month);
		checkDay(day);
		this.year = year;
	}

	
	
	
	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getYear() {
		return year;
	}


	public void checkMonth(int month) {
		if(month >=1 && month <=12){
			this.month = month;
		} else {
			System.out.println("Invalid Month!");
			this.month = 1;
		}
	}
	
	public void checkDay(int day) {
		if(day >= 1 && day <=30) {
			this.day = day;
		} else {
			System.out.println("Invalid Day!");
			this.day = 1;
		}
	}
	
	
	@Override
	public String toString() {
		return "Date [month=" + month + ", day=" + day + ", year=" + year + "]";
	}
	
	
	
	

}
