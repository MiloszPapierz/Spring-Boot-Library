package com.springBoot.Bibliotheek.exceptions;

public class AuthorNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public AuthorNotFoundException(Long authorId) {
		super(String.format("Could not find author with id %s", authorId));
	}
}
