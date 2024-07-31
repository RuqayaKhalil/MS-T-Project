package com.mst.exception;

public class Main {

	public static void main(String[] args) {
		try {
			SalesPerson p1 = new SalesPerson("p1", 90,800,"123-abc"); //valid
			System.out.println(p1);
		} catch (InvalidEmployeeCertException e) {
			System.out.println("p1: Invalid certNumber");
		}catch(InvalidEmployeeSalaryException e) {
			System.out.println("p1: Invalid Salary");
		}
		
		try {
			SalesPerson p2 = new SalesPerson("p2", 47, 100004,"456-def");//certNum not valid
			System.out.println(p2);
		} catch (InvalidEmployeeCertException e) {
			System.out.println("p2: Invalid certNumber");
		}catch(InvalidEmployeeSalaryException e) {
			System.out.println("p2: Invalid Salary");
		}
		
		try {
			SalesPerson p3 = new SalesPerson("p3", 70, 10900,"7889-ghi");//4 digits 
			System.out.println(p3);
		} catch (InvalidEmployeeCertException e) {
			System.out.println("p3: Invalid certNumber");
		}catch(InvalidEmployeeSalaryException e) {
			System.out.println("p3: Invalid Salary");
		}
		
		try {
			SalesPerson p4 = new SalesPerson("p4", 33, 7000,"0a0-jkl"); //not 3 numbers
			System.out.println(p4);
		} catch (InvalidEmployeeCertException e) {
			System.out.println("p4: Invalid certNumber");
		}catch(InvalidEmployeeSalaryException e) {
			System.out.println("p4: Invalid Salary");
		}
		
		try {
			SalesPerson p5 = new SalesPerson("p5", 56, 6500,"010-jklg"); // 4 characters
			System.out.println(p5);
		} catch (InvalidEmployeeCertException e) {
			System.out.println("p5: Invalid certNumber");
		}catch(InvalidEmployeeSalaryException e) {
			System.out.println("p5: Invalid Salary");
		}
		
		try {
			SalesPerson p6 = new SalesPerson("p6", 56, 6500,"010-j9g"); //not 3 characters
			System.out.println(p6);
		} catch (InvalidEmployeeCertException e) {
			System.out.println("p6: Invalid certNumber");
		}catch(InvalidEmployeeSalaryException e) {
			System.out.println("p6: Invalid Salary");
		}
		
		try{
		
			SalesPerson p11 = new SalesPerson("p11",30,8888,"111-aaa");
			SalesPerson p22 = new SalesPerson("p22",55,23456,"222-bbb");
			SalesPerson p33 = new SalesPerson("p33",34,5678,"333-ccc");
			SalesPerson p44 = new SalesPerson("p44",54,4566,"444-ddd");
			SalesPerson p55 = new SalesPerson("p55",23,35677,"555-eee");

			SalesPerson min = p11;
			
			SalesPerson[] persons = {p11,p22,p33,p44,p55};
			for(SalesPerson p : persons) {
				if(p.compareTo(min) == -1) {
					min = p;
				}
			}
	
			System.out.println();
			System.out.println(min);
		
			}catch(InvalidEmployeeCertException e) {
				System.out.println("Invalid certNumber");
			}catch(InvalidEmployeeSalaryException e) {
				System.out.println("Invalid Salary");
			}
		
		

		}
}
