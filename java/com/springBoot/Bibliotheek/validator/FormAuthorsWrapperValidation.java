package com.springBoot.Bibliotheek.validator;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springBoot.Bibliotheek.controller.FormAuthorsWrapper;
import com.springBoot.Bibliotheek.model.Author;

import io.micrometer.common.util.StringUtils;

public class FormAuthorsWrapperValidation implements Validator{
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return FormAuthorsWrapper.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormAuthorsWrapper wrapper = (FormAuthorsWrapper) target;
		List<Author> authors = wrapper.getAuthorsWrapper();
		
		List<Author> validAuthors = authors.stream()
				.filter(this::isAuthorValid)
				.collect(Collectors.toList());
		
		
		if(validAuthors.isEmpty()) {
			errors.rejectValue("errorMessage", "Test", "isbn validation failed");

		} else {
			wrapper.setAuthorsWrapper(validAuthors);
		}
	}
	
    private boolean isAuthorValid(Author author) {
        String firstname = author.getFirstname();
        String lastname = author.getLastname();
        return !StringUtils.isEmpty(firstname) && !StringUtils.isEmpty(lastname);
    }

}
