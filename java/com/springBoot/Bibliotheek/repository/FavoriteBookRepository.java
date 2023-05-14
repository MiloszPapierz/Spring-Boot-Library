package com.springBoot.Bibliotheek.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.springBoot.Bibliotheek.model.Book;
import com.springBoot.Bibliotheek.model.FavoriteBook;
import com.springBoot.Bibliotheek.model.User;

import jakarta.transaction.Transactional;

public interface FavoriteBookRepository extends CrudRepository<FavoriteBook, Long>{
	
    @Query("SELECT fb.book FROM FavoriteBook fb GROUP BY fb.book ORDER BY COUNT(fb.book) DESC, fb.book.name")
    List<Book> findMostFavoritedBooks();
    @Query("SELECT f FROM FavoriteBook f WHERE f.user.username = :username")
    List<FavoriteBook> findFavoritesByUsername(@Param("username") String username);
    @Transactional
    void deleteByUserAndBook(User user, Book book);
    List<FavoriteBook> findByBookId(@Param("bookid") Long bookId);
    @Query("SELECT COUNT(fb) FROM FavoriteBook fb WHERE fb.book.id = :bookId")
    long countByBookId(@Param("bookId") Long bookId);
}
