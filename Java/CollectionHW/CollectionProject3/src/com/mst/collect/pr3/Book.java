package com.mst.collect.pr3;

public class Book<T> {
	
	private String bookName, author;
	private double price;
	private int pagesNumber;
	T cover;
	

	 public Book() {
		 
	 }
	 
	public Book(String bookName, String author, double price, int pagesNumber, T cover) {
		this.bookName = bookName;
		this.author = author;
		this.price = price;
		this.pagesNumber = pagesNumber;
		this.cover = cover;
	}


	public String getBookName() {
		return bookName;
	}


	public void setBookName(String bookName) {
		this.bookName = bookName;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getPagesNumber() {
		return pagesNumber;
	}


	public void setPagesNumber(int pagesNumber) {
		this.pagesNumber = pagesNumber;
	}


	public T getCover() {
		return cover;
	}


	public void setCover(T cover) {
		this.cover = cover;
	}

	
	

	@Override
	public String toString() {
		return "Book [bookName=" + bookName + ", author=" + author + ", price=" + price + ", pagesNumber=" + pagesNumber
				+ ", cover=" + cover + "]";
	}
	
	public void display() {
		System.out.println(this);
	}
}
