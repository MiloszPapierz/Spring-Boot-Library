package com.springBoot.Bibliotheek.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springBoot.Bibliotheek.model.Location;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long>{
	
	@Query("SELECT l FROM Location l WHERE l.book.id = :bookid")
	List<Location> findByBookId(@Param("bookid") Long bookid);
}
