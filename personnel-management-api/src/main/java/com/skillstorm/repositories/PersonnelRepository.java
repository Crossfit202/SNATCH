package com.skillstorm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Personnel;

@Repository
public interface PersonnelRepository extends CrudRepository<Personnel, Integer>{

	boolean existsByPersonnelNameIgnoreCase(String personnelName);
	
	@Query(value = "SELECT * FROM personnel WHERE crew_id = ?1", nativeQuery = true)
	List<Personnel> findAllPersonnelsByCrewId(int crewId);
	
	@Query(value = "SELECT max_capacity FROM crew WHERE crew_id = ?1", nativeQuery = true)
	int findMaxCapByCrewId(int crewId);
	
}
