package com.springBoot.Bibliotheek.service;

import java.util.List;

import com.springBoot.Bibliotheek.model.Book;

public interface BookService {

	List<Book> getBooks();
	Book getBookById(Long id);
	Book addBook(Book book);
}
