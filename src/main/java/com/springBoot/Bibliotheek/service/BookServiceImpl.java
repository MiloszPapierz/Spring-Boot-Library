package com.springBoot.Bibliotheek.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springBoot.Bibliotheek.exceptions.BookNotFoundException;
import com.springBoot.Bibliotheek.exceptions.CustomGenericException;
import com.springBoot.Bibliotheek.model.Book;
import com.springBoot.Bibliotheek.model.FavoriteBook;
import com.springBoot.Bibliotheek.repository.AuthorRepository;
import com.springBoot.Bibliotheek.repository.BookRepository;
import com.springBoot.Bibliotheek.repository.FavoriteBookRepository;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private FavoriteBookRepository favoriteBookRepository;
	
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
		
		throw new CustomGenericException("Test","Test");
	}

	@Override
	public Book addBook(Book book) {
		authorRepository.saveAll(book.getAuthors());
		return bookRepository.save(book);
	}

	@Override
	public List<Book> getFavoriteBooks() {
		return favoriteBookRepository.findMostFavoritedBooks();
	}

	@Override
	public List<FavoriteBook> getFavoriteBooksByUsername(String username) {
		return favoriteBookRepository.findFavoritesByUsername(username);
	}

	@Override
	public void removeFavoriteBook(FavoriteBook fb) {
		favoriteBookRepository.deleteByUserAndBook(fb.getUser(), fb.getBook());
	}

	@Override
	public FavoriteBook addFavoriteBook(FavoriteBook fb) {
		return favoriteBookRepository.save(fb);
	}

	@Override
	public List<FavoriteBook> getFavoriteBooksByBookId(Long bookId) {
		return favoriteBookRepository.findByBookId(bookId);
	}

	@Override
	public Long getNumberOfFavoritesForBookByBookId(Long bookId) {
		return favoriteBookRepository.countByBookId(bookId);
	}

	@Override
	public Book getBookByIsbn(String isbn) {
		Book book =  bookRepository.findByIsbn(isbn);
		
		if(book == null) {
			throw new BookNotFoundException(isbn);
		}
		
		return book;
	}
}
