package com.springBoot.Bibliotheek.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springBoot.Bibliotheek.model.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

}
