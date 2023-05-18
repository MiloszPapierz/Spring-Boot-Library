package com.springBoot.Bibliotheek.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springBoot.Bibliotheek.model.Book;
import com.springBoot.Bibliotheek.model.Location;
import com.springBoot.Bibliotheek.repository.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService{
	
	@Autowired
	private LocationRepository locationRepository;

	@Override
	public List<Location> getLocationsByBookId(Long bookId) {
		return locationRepository.findByBookId(bookId);
	}

	@Override
	public List<Location> addLocationsForBook(Collection<Location> locations,Book book) {
		locations.stream().forEach(location -> location.setBook(book));
		
	    Iterable<Location> iterable = locationRepository.saveAll(locations);
	    return StreamSupport.stream(iterable.spliterator(), false)
	                                       .collect(Collectors.toUnmodifiableList()); 
	}
}
