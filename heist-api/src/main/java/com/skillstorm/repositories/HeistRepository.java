package com.skillstorm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Heist;



@Repository
public interface HeistRepository extends CrudRepository<Heist, Integer> {

	Heist findByCrewId(int crewId);
	
}
