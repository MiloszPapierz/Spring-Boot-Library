package com.springBoot.Bibliotheek.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springBoot.Bibliotheek.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long>{
	
	@Query("SELECT b FROM Book b WHERE b.isbn = :isbn")
	Book findByIsbn(String isbn);
}
