package com.springBoot.Bibliotheek.service;

import java.util.Collection;
import java.util.List;

import com.springBoot.Bibliotheek.model.Book;
import com.springBoot.Bibliotheek.model.Location;

public interface LocationService {
	List<Location> getLocationsByBookId(Long bookID);
	List<Location> addLocationsForBook(Collection<Location> locations,Book book);
}
