package com.springBoot.Bibliotheek.perform;

import org.springframework.web.reactive.function.client.WebClient;

import com.springBoot.Bibliotheek.model.Author;
import com.springBoot.Bibliotheek.model.Book;

public class PerformRestExample {
	private static final String SERVER_URI = "http://localhost:8080/api";
	private WebClient webClient = WebClient.create();
	
	public PerformRestExample() throws Exception{
		System.out.println("-------------GET BOOK 1-------------");
		getAnBook("9781649374042");
		
		try {
			System.out.println("-------------GET BOOK 9999-------------");
			getAnBook("9999");
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		System.out.println("-------------GET AUTHOR 1-------------");
		getAnAuthor("1");
		
		try {
			System.out.println("-------------GET AUTHOR 9999-------------");
			getAnAuthor("9999");
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	private void getAnBook(String number) {
		webClient.get()
		.uri(SERVER_URI + "/book/"+number)
		.retrieve()
		.bodyToMono(Book.class)
		.doOnSuccess(book -> printBookData(book))
		.block();
	}
	
	private void getAnAuthor(String authorid) {
		webClient.get()
		.uri(SERVER_URI + "/author/" + authorid)
		.retrieve()
		.bodyToMono(Author.class)
		.doOnSuccess(author -> printAuthorData(author))
		.block();
	}
	
	private void printAuthorData(Author author) {
		System.out.println(author);
	}
	
	private void printBookData(Book book) {
		System.out.println(book);
	}
}
