package com.springBoot.Bibliotheek.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springBoot.Bibliotheek.model.Author;
import com.springBoot.Bibliotheek.service.AuthorService;

import lombok.extern.log4j.Log4j;

@Log4j
@RestController
@RequestMapping(value="/api/author")
public class AuthorRestController {
	
	@Autowired
	private AuthorService authorService;
	private static final Logger log = LoggerFactory.getLogger(BookController.class);
	
	@GetMapping(value = "/{authorId}")
	public Author getAuthorById(@PathVariable Long authorId) {
		log.info(String.format("GET author with id %d", authorId));
		
		return authorService.getAuthorById(authorId);
	}
	
}
