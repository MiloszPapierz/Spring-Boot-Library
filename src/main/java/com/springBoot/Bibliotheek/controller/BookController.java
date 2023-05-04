package com.springBoot.Bibliotheek.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springBoot.Bibliotheek.service.BookService;
import com.springBoot.Bibliotheek.service.LocationService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	private BookService bookService;
	@Autowired
	private LocationService locationService; 
	private static final Logger log = LoggerFactory.getLogger(BookController.class);
	
	@GetMapping
	public String getBooksPage(Model model) {
		log.info("Get books page");
		
		model.addAttribute("books",bookService.getBooks());
		
		return "books";
	}
	
	@GetMapping(value="/{id}")
	public String getBookDetailsPage(@PathVariable Long id, Model model) {
		log.info("Get detail page of book with id " + id);
		
		model.addAttribute("locations",locationService.getLocationsByBookId(id));
		model.addAttribute("book", bookService.getBookById(id));
		
	    return "book";
	}
}