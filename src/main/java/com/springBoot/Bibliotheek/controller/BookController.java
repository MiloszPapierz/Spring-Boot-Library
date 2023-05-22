package com.springBoot.Bibliotheek.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springBoot.Bibliotheek.model.Author;
import com.springBoot.Bibliotheek.model.Book;
import com.springBoot.Bibliotheek.model.FavoriteBook;
import com.springBoot.Bibliotheek.model.Location;
import com.springBoot.Bibliotheek.model.User;
import com.springBoot.Bibliotheek.service.BookService;
import com.springBoot.Bibliotheek.service.CustomUserDetailsService;
import com.springBoot.Bibliotheek.service.LocationService;
import com.springBoot.Bibliotheek.validator.FormAuthorsWrapperValidation;
import com.springBoot.Bibliotheek.validator.FormLocationsWrapperValidation;
import com.springBoot.Bibliotheek.validator.IsbnValidation;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;
	@Autowired
	private LocationService locationService;
	@Autowired
	private CustomUserDetailsService userService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IsbnValidation isbnValidator;
	@Autowired
	private FormAuthorsWrapperValidation formAuthorsWrapperValidation;
	@Autowired
	private FormLocationsWrapperValidation formLocationsWrapperValidation;
	private static final Logger log = LoggerFactory.getLogger(BookController.class);

	@GetMapping
	public String getBooksPage(Model model,@RequestParam(value="message",required = false) String message) {
		log.info("Get books page");

		if(message != null) {
			model.addAttribute("message",message);
		}
		
		model.addAttribute("books", bookService.getBooks());

		return "books";
	}

	@GetMapping(value = "/favorites")
	public String getFavoriteBooksPage(Model model) {
		log.info("Get favorite books page");

		model.addAttribute("books", bookService.getFavoriteBooks());
		model.addAttribute("favorite", true);

		return "books";
	}

	@GetMapping(value = "/{id}")
	public String getBookDetailsPage(@PathVariable Long id, Model model, Principal principal) {
		log.info("Get detail page of book with id " + id);

		User user = userService.getLoggedUser(principal.getName());

		List<FavoriteBook> listOfUserFavoriteBooks = bookService.getFavoriteBooksByUsername(user.getUsername());
		boolean alreadyAdded = listOfUserFavoriteBooks.stream().filter(fb -> fb.getBook().getId() == id).collect(Collectors.toList()).size() > 0;
		boolean hasReachedMaximum = listOfUserFavoriteBooks.size() >= user.getMaxFavorites();
		
		Long numberOfFavorites = bookService.getNumberOfFavoritesForBookByBookId(id);
		
		model.addAttribute("numberOfFavorites",numberOfFavorites);
		model.addAttribute("alreadyAdded", alreadyAdded);
		model.addAttribute("reachedMaximum", hasReachedMaximum);
		model.addAttribute("locations", locationService.getLocationsByBookId(id));
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

		model.addAttribute("book", new Book());
		model.addAttribute("authors", wrapper);
		model.addAttribute("locations", locationsWrapper);

		return "addBook";
	}

	@PostMapping(path ="/add")
	public String postAddBookForm(@Valid @ModelAttribute Book book,BindingResult resultBook,@ModelAttribute FormAuthorsWrapper formAuthorsWrapper,BindingResult resultFormAuthorsWrapper,
			@ModelAttribute FormLocationsWrapper formLocationsWrapper,BindingResult resultFormLocationsWrapper,Model model) {
			isbnValidator.validate(book, resultBook);
			formAuthorsWrapperValidation.validate(formAuthorsWrapper, resultFormAuthorsWrapper);
			formLocationsWrapperValidation.validate(formLocationsWrapper, resultFormLocationsWrapper);
			
		if(resultBook.hasErrors() || resultFormAuthorsWrapper.hasErrors() || resultFormLocationsWrapper.hasErrors()) {
			model.addAttribute("book", book);
			model.addAttribute("authors", formAuthorsWrapper);
			model.addAttribute("locations", formLocationsWrapper);

			
			return "addBook";
		}
		
		book.getAuthors().addAll(formAuthorsWrapper.getAuthorsWrapper());
		book.setImgUrl(
				"https://upload.wikimedia.org/wikipedia/commons/thumb/d/d1/Image_not_available.png/640px-Image_not_available.png"); //Random URL for now. Will add image upload later.

		Book addedBook = bookService.addBook(book);

		locationService.addLocationsForBook(formLocationsWrapper.getLocationsWrapper(), addedBook);

		return "redirect:/books";
	}
	
	@PostMapping(value = "/favorites/remove") 
	public String postRemoveBookFromFavorites(@RequestParam("bookId") Long bookId,Principal principal,RedirectAttributes redirectAttributes) {
		log.info("Removing book with id" + bookId + " from favorites for user " + principal.getName());
		
		User user = userService.getLoggedUser(principal.getName());
		Book book = bookService.getBookById(bookId);
		
		FavoriteBook fb = new FavoriteBook(user,book);
		
		bookService.removeFavoriteBook(fb);
		
		redirectAttributes.addAttribute("message", messageSource.getMessage("bookspage.favoriteRemoved", new Object[]{book.getName()}, LocaleContextHolder.getLocale()));
		
		return "redirect:/books";
	}
	
	@PostMapping(value = "/favorites/add") 
	public String postAddBookToFavorites(@RequestParam("bookId") Long bookId,Principal principal,RedirectAttributes redirectAttributes) {
		log.info("Adding book with id" + bookId + " to favorites for user " + principal.getName());
		
		User user = userService.getLoggedUser(principal.getName());
		Book book = bookService.getBookById(bookId);
		
		FavoriteBook fb = new FavoriteBook(user,book);
		
		bookService.addFavoriteBook(fb);
		
		redirectAttributes.addAttribute("message", messageSource.getMessage("bookspage.favoriteAdded", new Object[]{book.getName()}, LocaleContextHolder.getLocale()));

		
		return "redirect:/books";
	}
	
}