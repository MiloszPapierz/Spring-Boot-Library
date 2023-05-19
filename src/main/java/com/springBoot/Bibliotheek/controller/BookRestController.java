package com.springBoot.Bibliotheek.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springBoot.Bibliotheek.model.Book;
import com.springBoot.Bibliotheek.service.BookService;
import com.springBoot.Bibliotheek.validator.IsbnValidation;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j;

@RestController
@Log4j
@RequestMapping(value="/api")
public class BookRestController {
	
	@Autowired
	private BookService bookService;
	@Autowired
	private IsbnValidation isbnValidator;
	private static final Logger log = LoggerFactory.getLogger(BookController.class);
	
	@GetMapping(value = "/book/{isbn}")
	public Book getBookByIsbn(@PathVariable String isbn) {
		log.info("REST: get request for book with isbn:" + isbn);
		
		return bookService.getBookByIsbn(isbn);
	}
}
