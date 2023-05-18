package com.springBoot.Bibliotheek.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springBoot.Bibliotheek.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	Optional<User> findByUsername(String username);
}
