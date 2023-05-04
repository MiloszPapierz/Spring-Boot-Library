package com.springBoot.Bibliotheek.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
