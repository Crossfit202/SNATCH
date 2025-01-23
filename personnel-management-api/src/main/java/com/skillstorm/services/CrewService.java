package com.skillstorm.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillstorm.apis.HeistClient;
import com.skillstorm.dtos.CrewDTO;
import com.skillstorm.dtos.HeistDTO;
import com.skillstorm.models.Crew;
import com.skillstorm.repositories.CrewRepository;



@Service
public class CrewService {
	private CrewRepository repo;
	private HeistClient heistClient;

	public CrewService(CrewRepository repo, HeistClient heistClient) {
		super();
		this.repo = repo;
		this.heistClient = heistClient;
	}

//	SERVICE METHODS
	
//	GET ALL
	public Iterable<Crew> findAll() {
		return repo.findAll();
	}
	
//	FIND BY ID
	
	public ResponseEntity<Object> findById(int crewId) {
		if (repo.existsById(crewId)) {
			return ResponseEntity.status(HttpStatus.OK)
				     			 .body(repo.findById(crewId).get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                  			 .body(String.format("Crew with ID %d does not exist!", crewId));
		}
	}
	
//	GET HEIST BY CREW ID
	public ResponseEntity<HeistDTO> findHeistByCrewId(int crewId) {
		if (repo.existsById(crewId)) {
			return heistClient.findByCrewId(crewId);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
								 .body(null);
		}
	}
	
	
//	CREATE ONE - POST
	public ResponseEntity<Object> addOne(CrewDTO crewDTO) {
		if(repo.existsByCrewNameIgnoreCase(crewDTO.getCrewName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
								 .body(String.format("Crew with name '%s' already exists!", crewDTO.getCrewName()));
		} 
		if(crewDTO.getCaptain() != null && repo.existsByCaptain_CaptainId(crewDTO.getCaptain().getCaptainId())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(String.format("Captain with ID '%d' is already assigned to a crew!", crewDTO.getCaptain().getCaptainId()));
		} else {
			return ResponseEntity.status(HttpStatus.CREATED)
		             .body(repo.save(new Crew(0, crewDTO.getCrewName(),
		            		                     crewDTO.getMaxCapacity(),
		            		                     crewDTO.isAvailability(),
		            		                     crewDTO.isHasCaptain(),
		            		                     crewDTO.getCaptain(), 
		            		                     null
		            		                     )));
		}
	
	}
	
	
//	PUT
		public ResponseEntity<Object> updateOne(int crewId, CrewDTO crewDTO) {
			// check if the crew ID exists in DB, else exit
		    if(!repo.existsById(crewId)) {
		    	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			             .body(String.format("Failed to update! Crew with ID %d does not exist. Ensure the ID is correct or create a new Crew.", crewId));
		    }
		    
		    // get the current crew's ID
		    Crew currentCrew = repo.findById(crewId).get();
			
		    // check if the captain ID is already assigned to a crew
		    // and is not the one you are currently assigned to
		    boolean captainExists = repo.existsByCaptain_CaptainId(crewDTO.getCaptain().getCaptainId()) 
		    						&& (currentCrew.getCaptain().getCaptainId() != crewDTO.getCaptain().getCaptainId());
		    
		    
		    // check if the crew name already exists in the DB
		    // and is not the name of the one are currently updating
		    boolean crewNameExists = repo.existsByCrewNameIgnoreCase(crewDTO.getCrewName()) 
		    						 && !currentCrew.getCrewName().equalsIgnoreCase(crewDTO.getCrewName());
		    
		    // if both the captain chosen and crew name conflicts with existing data
		    if(captainExists && crewNameExists) {
		    	return ResponseEntity.status(HttpStatus.CONFLICT)
		    						 .body(String.format("Failed to update! Crew with with name '%s' already exists, and Captain with ID '%d' is already assigned to a crew!", 
		    								 crewDTO.getCrewName(), crewDTO.getCaptain().getCaptainId()));
		    	
		    // if only the captain chosen conflicts
		    } else if(captainExists) {
		    	return ResponseEntity.status(HttpStatus.CONFLICT)
		    						 .body(String.format("Failed to update! Captain with ID '%d' is already assigned to a crew!", crewDTO.getCaptain().getCaptainId()));
		    
		    // if only the name conflicts
		    } else if(crewNameExists) {
		    	return ResponseEntity.status(HttpStatus.CONFLICT)
	                    .body(String.format("Failed to update! Crew with with name '%s' already exists!", crewDTO.getCrewName()));
		    }
		    
		    // if no conflicts, update the crew!
		    return ResponseEntity.status(HttpStatus.OK)
			             .body(repo.save(new Crew(crewId, crewDTO.getCrewName(),
   		                                              crewDTO.getMaxCapacity(),
   		                                              crewDTO.isAvailability(),
   		                                              crewDTO.isHasCaptain(),
   		                                              crewDTO.getCaptain(),
   		                                              null)));
		}
	
	
//  DELETE
		public ResponseEntity<Void> deleteById(int crewId) {
		        repo.deleteById(crewId);  
		        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		    } 
		
}
