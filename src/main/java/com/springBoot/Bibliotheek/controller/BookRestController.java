package com.springBoot.Bibliotheek.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api")
public class BookRestController {
	
	@GetMapping(value = "/book/{isbn}")
	public boolean getBookByIsbn() {
		return true;
	}
}
