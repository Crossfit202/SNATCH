package com.skillstorm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Crew;

@Repository
public interface CrewRepository extends CrudRepository<Crew, Integer> {

	boolean existsByCrewNameIgnoreCase(String crewName);
	
	boolean existsByCaptain_CaptainId(int captainId);
}
