package com.skillstorm.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Captain;

@Repository
public interface CaptainRepository extends CrudRepository<Captain, Integer> {
	
	// Case sensitivity check
	boolean existsByCaptainNameIgnoreCase(String captainName);
	
	// To check if supplied crew has already been assigned to another crew
	boolean existsByCrew_CrewId(int crewId);
	
	/*
	 * FOR PUT REQUEST
	 * 	- To update the Crew associated with the Captain
	 * 	- Must update captain_id in Crew to associate with this Captain
	 * 	- Then null the captain_id for the Crew the Captain is 
	 */
	// To update the Captain associated with supplied Crew
	@Modifying
	@Query(value = "UPDATE crew SET captain_id = ?1, has_captain = true WHERE crew_id = ?2", nativeQuery = true)
	int updateCaptainCrew(int captainId, int crewId);
	
	// To update the Crew's current associated captain to the newly supplied one
	@Modifying
	@Query(value = "UPDATE crew SET captain_id = NULL, has_captain = false WHERE captain_id = ?1", nativeQuery = true)
	int removeCrewNotRequested(int captainId);
}
