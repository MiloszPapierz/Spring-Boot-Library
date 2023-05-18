package com.springBoot.Bibliotheek.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springBoot.Bibliotheek.model.Book;

public class IsbnValidation implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Book.class.isAssignableFrom(clazz);
	}

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        String isbn = book.getIsbn().replaceAll("[\\s-]", "");

        // Controleer of het ISBN-nummer 13-cijferig is
        if (isbn.length() != 13) {
            errors.rejectValue("isbn", "validation.bookIsbn.minChar", "isbn min char");
        }

        // Controleer het ISBN-nummer volgens de Wikipedia-validatieregels
        if (!isValidIsbn(isbn)) {
            errors.rejectValue("isbn", "validation.bookIsbn.false", "isbn validation failed");
        }
    }

    // Hulpmethode om het ISBN-nummer te valideren volgens de Wikipedia-regels
    private boolean isValidIsbn(String isbn) {

        // Controleer of het ISBN-nummer bestaat uit 13 cijfers
        if (!isbn.matches("\\d{13}")) {
            return false;
        }

        // Controleer het controlecijfer
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int checksum = 10 - (sum % 10);
        if (checksum == 10) {
            checksum = 0;
        }
        int lastDigit = Character.getNumericValue(isbn.charAt(12));
        return lastDigit == checksum;
    }
}

