package com.springBoot.Bibliotheek.controller;

import java.util.List;

import com.springBoot.Bibliotheek.model.Author;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FormAuthorsWrapper {
	private List<Author> authorsWrapper;
	private String errorMessage;

}
