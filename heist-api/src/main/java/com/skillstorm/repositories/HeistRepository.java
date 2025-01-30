package com.skillstorm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Heist;



@Repository
public interface HeistRepository extends CrudRepository<Heist, Integer> {

	// To get the Crew associated with the requested Heist
	Heist findByCrewId(int crewId);
	
}
