package com.mst.oop.task2;

public class Date {
	private int month,day,year;

	public Date(int month, int day, int year) {
		this.year = year;
		this.month = checkMonth(month);
		if(isLeapYear(year)){
			this.day = checkDayInLeapYear(day,month);
		}else {
			this.day = checkDay(day,month);
		}
		
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

	public static int checkDayInLeapYear(int day,int month) {
		if((month == 2 && day >= 1 && day <=29) || 
				(month != 2 && day >= 1 && day <=30)) {
				return day;
		}else {
			System.out.println("Invalid Day!");
			return 1;
		}
	}
	
	public static int checkDay(int day, int month) {
		if((month == 2 && day >= 1 && day <=28) || 
				(month != 2 && day >= 1 && day <=30)) {
				return day;
		}else {
			System.out.println("Invalid Day!");
			return 1;
		}
	}
	public static boolean isLeapYear(int year) { 
		// return ((year%4 == 0 && year%100 != 0) || (year%400 == 0)) 
		if(year%4 == 0) {
			if(year%100 == 0) {
				if(year%400 == 0) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	public static int checkMonth(int month) {
		if(month >=1 && month <=12){
			return month;
		} else {
			System.out.println("Invalid Month!");
			return 1;
		}
	}
		
	@Override
	public String toString() {
		return "Date [month=" + month + ", day=" + day + ", year=" + year + "]";
	}
	
	
	
	

}
