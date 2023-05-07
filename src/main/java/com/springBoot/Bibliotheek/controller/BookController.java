package com.springBoot.Bibliotheek.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springBoot.Bibliotheek.model.Author;
import com.springBoot.Bibliotheek.model.Book;
import com.springBoot.Bibliotheek.model.Location;
import com.springBoot.Bibliotheek.service.BookService;
import com.springBoot.Bibliotheek.service.LocationService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/books")
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
	
	@GetMapping(path = "/add") 
	public String getAddBookForm(Model model) {
		log.info("Get add book form page");
		
		FormAuthorsWrapper wrapper = new FormAuthorsWrapper();
		List<Author> authors = new ArrayList<>();
		authors.add(new Author());
		authors.add(new Author());
		authors.add(new Author());
		wrapper.setAuthorsWrapper(authors);
		
		FormLocationsWrapper locationsWrapper = new FormLocationsWrapper();
		List<Location> locations = new ArrayList<>();
		locations.add(new Location());
		locations.add(new Location());
		locations.add(new Location());
		locationsWrapper.setLocationsWrapper(locations);
		
		model.addAttribute("book",new Book());
		model.addAttribute("authors",wrapper);
		model.addAttribute("locations",locationsWrapper);
		
		return "addBook";
	}
	
	@PostMapping
	public String postAddBookForm(@ModelAttribute Book book,@ModelAttribute FormAuthorsWrapper wrapper,@ModelAttribute FormLocationsWrapper locationsWrapper) {
		book.getAuthors().addAll(wrapper.getAuthorsWrapper());
		book.setImgUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d1/Image_not_available.png/640px-Image_not_available.png");
		
		Book addedBook = bookService.addBook(book);
		
		locationService.addLocationsForBook(locationsWrapper.getLocationsWrapper(),addedBook);
		
		return "redirect:/books";
	}
}