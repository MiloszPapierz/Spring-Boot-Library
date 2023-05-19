package com.springBoot.Bibliotheek.service;

import java.util.List;

import com.springBoot.Bibliotheek.model.Book;
import com.springBoot.Bibliotheek.model.FavoriteBook;

public interface BookService {

	List<Book> getBooks();
	Book getBookById(Long id);
	Book getBookByIsbn(String isbn);
	Book addBook(Book book);
	List<Book> getFavoriteBooks();
	List<FavoriteBook> getFavoriteBooksByUsername(String username);
	void removeFavoriteBook(FavoriteBook fb);
	FavoriteBook addFavoriteBook(FavoriteBook fb);
	List<FavoriteBook> getFavoriteBooksByBookId(Long bookId);
	Long getNumberOfFavoritesForBookByBookId(Long bookId);
}
