package com.springBoot.Bibliotheek.validator;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springBoot.Bibliotheek.controller.FormLocationsWrapper;
import com.springBoot.Bibliotheek.model.Location;

public class FormLocationsWrapperValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return FormLocationsWrapper.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormLocationsWrapper wrapper = (FormLocationsWrapper) target;
		List<Location> locations = wrapper.getLocationsWrapper();

		locations = locations.stream()
				.filter(loc -> !loc.getPlacename().isBlank() && loc.getPlacecode1() != 0 && loc.getPlacecode2() != 0)
				.collect(Collectors.toList());

		if (locations.size() == 0) {
			errors.rejectValue("errorMessage", "empty.locations", "Er moet minstens 1 locatie ingevuld zijn.");
		} else {
			for (int i = 0; i < locations.size(); i++) {
				Location location = locations.get(i);

				// Validatie voor plaatscode1
				if (location.getPlacecode1() < 50 || location.getPlacecode1() > 300) {
					errors.rejectValue("errorMessage", "invalid.plaatscode1",
							"Plaatscode1 moet een geheel getal zijn tussen 50 en 300.");
				}

				// Validatie voor plaatscode2
				if (location.getPlacecode2() < 50 || location.getPlacecode2() > 300) {
					errors.rejectValue("errorMessage", "invalid.plaatscode2",
							"Plaatscode2 moet een geheel getal zijn tussen 50 en 300.");
				}

				// Validatie voor het verschil tussen plaatscode1 en plaatscode2
				if (Math.abs(location.getPlacecode1() - location.getPlacecode2()) < 50) {
					errors.rejectValue("errorMessage", "invalid.plaatscode2",
							"Het verschil tussen plaatscode1 en plaatscode2 moet minstens 50 zijn.");
				}

				// Validatie voor plaatsnaam
				if (!location.getPlacename().matches("[a-zA-Z]+")) {
					errors.rejectValue("errorMessage", "invalid.plaatsnaam",
							"Plaatsnaam mag enkel uit letters bestaan.");
				}
			}
		}

	}
}
