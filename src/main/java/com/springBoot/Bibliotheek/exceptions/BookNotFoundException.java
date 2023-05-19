package com.springBoot.Bibliotheek.exceptions;

public class BookNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public BookNotFoundException(String isbn) {
		super(String.format("Could not find book with isbn %s", isbn));
	}
}
