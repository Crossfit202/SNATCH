package com.skillstorm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Crew;

@Repository
public interface CrewRepository extends CrudRepository<Crew, Integer> {

	// Case sensitivity check
	boolean existsByCrewNameIgnoreCase(String crewName);
	
	// To check if supplied Captain has already been assigned to another Crew
	boolean existsByCaptain_CaptainId(int captainId);
	
	/*
	 * FOR PUT REQUEST
	 * 	- To update the Personnel associated with the Crew
	 * 	- Must update crew_id for each Personnel to associate with this Crew
	 * 	- Then null the crew_id for each Personnel no longer on the incoming List to dissociate relationship
	 */
	@Modifying
	@Query(value = "UPDATE personnel SET crew_id = ?1 WHERE personnel_id IN (?2)", nativeQuery = true)
	int updateCrewPersonnel(int crewId, List<Integer> personnelIds);
	
	@Modifying
	@Query(value = "UPDATE personnel SET crew_id = NULL WHERE crew_id = ?1 AND personnel_id NOT IN (?2)", nativeQuery = true)
	int removePersonnelNotInList(int crewId, List<Integer> personnelIds);
}
