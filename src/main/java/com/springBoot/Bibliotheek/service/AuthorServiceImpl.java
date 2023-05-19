package com.springBoot.Bibliotheek.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springBoot.Bibliotheek.exceptions.AuthorNotFoundException;
import com.springBoot.Bibliotheek.model.Author;
import com.springBoot.Bibliotheek.repository.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService{

	@Autowired
	private AuthorRepository authorRepository;
	
	@Override
	public Author getAuthorById(Long authorId) {
		Optional<Author> author = authorRepository.findById(authorId);
		
		if(!author.isPresent()) {
			throw new AuthorNotFoundException(authorId);
		}
		
		return author.get();
	}

}
