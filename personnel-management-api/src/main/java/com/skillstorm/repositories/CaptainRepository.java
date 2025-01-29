package com.skillstorm.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Captain;

@Repository
public interface CaptainRepository extends CrudRepository<Captain, Integer> {
	boolean existsByCaptainNameIgnoreCase(String captainName);
	
	boolean existsByCrew_CrewId(int crewId);
	
	@Modifying
	@Query(value = "UPDATE crew SET captain_id = ?1, has_captain = true WHERE crew_id = ?2", nativeQuery = true)
	int updateCaptainCrew(int captainId, int crewId);
	
	@Modifying
	@Query(value = "UPDATE crew SET captain_id = NULL, has_captain = false WHERE captain_id = ?1", nativeQuery = true)
	int removeCrewNotRequested(int captainId);
}
