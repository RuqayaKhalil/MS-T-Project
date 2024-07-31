package com.mst.exception;

public class SalesPerson extends Employee implements Comparable<SalesPerson>{
	String certificatitonNumber;

	public SalesPerson(String name, int age, double salary, String certificatitonNumber) throws InvalidEmployeeCertException {
		super(name, age, salary);
		this.certificatitonNumber =  requireValidCertificatitonNumber(certificatitonNumber);
	}
	

	public String getCertificatitonNumber() {
		return certificatitonNumber;
	}

	
	private static boolean checkValidCertificatitonNumber(String certNum) {
		if(certNum.length() !=7 || certNum.indexOf('-') != 3 
				|| certNum.substring(0,3).matches("[0-9]{3}") == false
				|| certNum.substring(4,7).matches("[a-zA-Z]{3}") == false) {
			return false;
		} else {		
			return true;
		}
	}
	
	private static String requireValidCertificatitonNumber(String certNum) throws InvalidEmployeeCertException {
		if(!checkValidCertificatitonNumber(certNum)) {
			throw new InvalidEmployeeCertException("Invalid Certification Number!");
		} 		
		return certNum;
	}


	@Override
	public int compareTo(SalesPerson o) {
		if(this.getSalary() > o.getSalary()) {
			return 1;
		} else if(this.getSalary() < o.getSalary()) {
			return -1;
		}
		else {
			return 0;
		}
		
	}


	@Override
	public String toString() {
		return "SalesPerson [certificatitonNumber=" + certificatitonNumber + "] " + super.toString() ;
	}



	


}
