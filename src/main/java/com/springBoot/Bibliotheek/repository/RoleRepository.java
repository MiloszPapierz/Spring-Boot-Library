package com.springBoot.Bibliotheek.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springBoot.Bibliotheek.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
	Optional<Role> findByName(String name);
}
