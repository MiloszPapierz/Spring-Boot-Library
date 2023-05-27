package com.springBoot.Bibliotheek.controller;

import java.util.List;

import com.springBoot.Bibliotheek.model.Location;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FormLocationsWrapper {
	private List<Location> locationsWrapper;
	private String errorMessage;
}
