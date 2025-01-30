package com.skillstorm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Personnel;

@Repository
public interface PersonnelRepository extends CrudRepository<Personnel, Integer>{

	// Case sensitivity check
	boolean existsByPersonnelNameIgnoreCase(String personnelName);
	
	// Get all Personnel associated with requested Crew
	@Query(value = "SELECT * FROM personnel WHERE crew_id = ?1", nativeQuery = true)
	List<Personnel> findAllPersonnelsByCrewId(int crewId);
	
	// Get the max capacity for requested Crew
	@Query(value = "SELECT max_capacity FROM crew WHERE crew_id = ?1", nativeQuery = true)
	int findMaxCapByCrewId(int crewId);
	
}
