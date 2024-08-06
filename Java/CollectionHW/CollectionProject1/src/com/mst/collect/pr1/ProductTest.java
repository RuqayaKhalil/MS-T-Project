package com.mst.collect.pr1;

public class ProductTest {

	public static void main(String[] args) {
		Product product = new Product("aa",350);
		product.display();
		System.out.println("full price: " +product.getPriceIncludingVAT(product.getPrice()));
	}
}
