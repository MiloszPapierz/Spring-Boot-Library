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
				.filter(loc -> filterLocations(loc))
				.collect(Collectors.toList());
		
		wrapper.setLocationsWrapper(locations);
		
		if (locations.size() == 0) {
			errors.rejectValue("errorMessage", "validation.locations.min", "At least 1 location needs to be filled in");
		} else {
			for (int i = 0; i < locations.size(); i++) {
				Location location = locations.get(i);

				// Validatie voor plaatscode1
				if (location.getPlacecode1() < 50 || location.getPlacecode1() > 300) {
					errors.rejectValue("errorMessage", "validation.locations.placecode1",
							"Placecode1 has to be a number between 50 and 300");
				}

				// Validatie voor plaatscode2
				if (location.getPlacecode2() < 50 || location.getPlacecode2() > 300) {
					errors.rejectValue("errorMessage", "validation.locations.placecode2",
							"Placecode2 has to be a number between 50 and 300");
				}

				// Validatie voor het verschil tussen plaatscode1 en plaatscode2
				if (Math.abs(location.getPlacecode1() - location.getPlacecode2()) < 50) {
					errors.rejectValue("errorMessage", "validation.locations.placecode",
							"The difference between placecode1 and placecode2 has to be at least 50");
				}

				// Validatie voor plaatsnaam
				if (!location.getPlacename().matches("[a-zA-Z]+")) {
					errors.rejectValue("errorMessage", "validation.locations.placename",
							"Placename can only contain characters");
				}
			}
		}

	}
	
	public boolean filterLocations(Location location) {
		if(location.getPlacename().isBlank() || location.getPlacecode1() == 0 || location.getPlacecode2() == 0) {
			return false;
		} 
		return true;
	}
}
