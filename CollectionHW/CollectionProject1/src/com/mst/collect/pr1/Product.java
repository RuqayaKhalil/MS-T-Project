package com.mst.collect.pr1;

public class Product {
	private static final double VAT = 0.17;
	private String name;
	private int price;


	public Product() {
	}
	
	public Product(String name, int price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}


	public static double getVat() {
		return VAT;
	}
	
	public double getPriceIncludingVAT(double price) {
		return (price * VAT) +  price;
	}
	
	@Override
	public String toString() {
		return "Product [name=" + name + ", price=" + price + "]";
	}
	
	public void display() {
		System.out.println(this);
	}	
}
