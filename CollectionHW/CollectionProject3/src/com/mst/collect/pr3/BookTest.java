package com.mst.collect.pr3;

public class BookTest {
	public static void main(String[] args) {
		Book<String> book1 = new Book<>("Book1","Writer1",234,345,"harry potter and the philosopher's stone, by J.K. Rowling");
		Book<Integer> book2 = new Book<>("Book2","Writer2", 100.4,56,1984);
		System.out.println(book1);
		System.out.println(book2);
	}
}
