package com.springBoot.Bibliotheek.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springBoot.Bibliotheek.model.Book;
import com.springBoot.Bibliotheek.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public List<Book> getBooks() {
	    Iterable<Book> iterable = bookRepository.findAll();
	    return StreamSupport.stream(iterable.spliterator(), false)
	                                       .collect(Collectors.toUnmodifiableList());

	}
	
	@Override
	public Book getBookById(Long id) {
		Optional<Book> book = bookRepository.findById(id);
		
		if(book.isPresent()) {
			return book.get();
		}
	return null;
	}
}
