package com.springBoot.Bibliotheek.service;

import java.util.List;

import com.springBoot.Bibliotheek.model.Location;

public interface LocationService {
	List<Location> getLocationsByBookId(Long bookID);
}
