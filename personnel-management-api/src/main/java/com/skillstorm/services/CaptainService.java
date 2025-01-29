package com.skillstorm.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skillstorm.dtos.CaptainDTO;
import com.skillstorm.models.Captain;
import com.skillstorm.models.Crew;
import com.skillstorm.models.Leader;
import com.skillstorm.repositories.CaptainRepository;
import com.skillstorm.repositories.CrewRepository;
import com.skillstorm.repositories.LeaderRepository;

@Service
public class CaptainService {

	private CaptainRepository repo;
	
	// Inject Leader Repo to retrieve Leader JSON Object
	private LeaderRepository leaderRepo;
	
	// Inject Crew Repo to retrieve Crew JSON Object
	private CrewRepository crewRepo;

	// Constructor with constructor injection for repositories
	public CaptainService(CaptainRepository repo, LeaderRepository leaderRepo, CrewRepository crewRepo) {
		super();
		this.repo = repo;
		this.leaderRepo = leaderRepo;
		this.crewRepo = crewRepo;
	}
	
	
	/*
	 * SERVICE METHODS
	 */
	
	
	// GET ALL
	public Iterable<Captain> findAll() {
		return repo.findAll();
	}
	
	// GET BY ID
	public ResponseEntity<Object> findById(int captainId) {
		
		// Check if requested Captain ID exists in the DB
		if(repo.existsById(captainId)) {
			return ResponseEntity.status(HttpStatus.OK)
								 .body(repo.findById(captainId).get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
						  		 .body(String.format("Captain with ID %d does not exist!", captainId));
		}
	}
	
	// CREATE ONE
	public ResponseEntity<Object> addOne(CaptainDTO captainDTO) {
		
		// Check if input for Captain Name is a duplicate
		if(repo.existsByCaptainNameIgnoreCase(captainDTO.getCaptainName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					             .body(String.format("'%s' already exists!", captainDTO.getCaptainName()));
		} else {
			
			// Get Leader Object from input Leader ID
			Leader leader = leaderRepo.findById(captainDTO.getLeader().getLeaderId())
		                			  .orElse(null);
			
			Crew crew = (captainDTO.getCrew() != null) ? crewRepo.findById(captainDTO.getCrew().getCrewId()).orElse(null) : null;

			// Save the new Captain
			Captain savedCaptain = repo.save(new Captain(0, captainDTO.getCaptainName(), leader, crew));
			
			// Retrieve the newly saved Captain Object by ID
			Captain completeCaptain = repo.findById(savedCaptain.getCaptainId()).orElse(null);
			
			// Null handling in case the Captain did not save properly
			if (completeCaptain != null) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        			 .body(completeCaptain);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        			 .body("Error occurred while retrieving the saved Captain.");
            }
		}

		
	}
	
	// UPDATE ONE
	@Transactional
	public ResponseEntity<Object> updateOne(int captainId, CaptainDTO captainDTO) {
		
		// Check if the Captain ID exists in DB, else quick exit (no point in doing the rest!)
		if(!repo.existsById(captainId)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					 .body(String.format("Failed to update! Captain with ID %d does not exist. Ensure the ID is correct or create a new Captain.", captainId));
		}
			
		// Get the current Captain's ID
		Captain currentCaptain = repo.findById(captainId).get();
		
	    // Check if the Crew ID is already assigned to a Captain
	    // and is not the one it is currently assigned to
	    boolean crewAlreadyAssigned = repo.existsByCrew_CrewId(captainDTO.getCrew().getCrewId()) 
	    								&& (currentCaptain.getCrew().getCrewId() != captainDTO.getCrew().getCrewId());
		
		// Check if the Captain name already exists in the DB
		// and is not the name of this one that is currently being updated
		boolean captainNameExists = repo.existsByCaptainNameIgnoreCase(captainDTO.getCaptainName()) 
		    						 && !currentCaptain.getCaptainName().equalsIgnoreCase(captainDTO.getCaptainName());
		
	    // If both the Crew selected and Captain name conflicts with existing data
	    if(crewAlreadyAssigned && captainNameExists) {
	    	return ResponseEntity.status(HttpStatus.CONFLICT)
	    						 .body(String.format("Failed to update! Captain with with name '%s' already exists, and Crew with ID '%d' is already assigned to a captain!", 
	    								 				captainDTO.getCaptainName(), captainDTO.getCrew().getCrewId()));
	    
	    // If only the Crew selected conflicts
	    } else if(crewAlreadyAssigned) {
	    	return ResponseEntity.status(HttpStatus.CONFLICT)
	    						 .body(String.format("Failed to update! Crew with ID '%d' is already assigned to a Captain!", 
	    								 				captainDTO.getCrew().getCrewId()));
	    
	    // If only the name conflicts
	    } else if(captainNameExists) {
	    	return ResponseEntity.status(HttpStatus.CONFLICT)
                    			 .body(String.format("Failed to update! Captain with with name '%s' already exists!", 
                    									captainDTO.getCaptainName()));
	    } else {
	    	
	        // Remove the current crew's captain (if necessary)
	        if (currentCaptain.getCrew() != null) {
	            repo.removeCrewNotRequested(captainId);
	        }

	        // Update the Captain's new Crew assignment
	        if (captainDTO.getCrew() != null) {
	            repo.updateCaptainCrew(captainId, captainDTO.getCrew().getCrewId());
	        }
	    	
			// Get Leader Object from input Leader ID
			Leader leader = leaderRepo.findById(captainDTO.getLeader().getLeaderId()).orElse(null);
				
			// Get Crew Object from input Crew ID
			// Set Crew Object to null if not provided
			Crew crew = captainDTO.getCrew() != null ? crewRepo.findById(captainDTO.getCrew().getCrewId()).orElse(null) : null;
		        
			// Save the updated Captain
			Captain updatedCaptain = repo.save(new Captain(captainId, captainDTO.getCaptainName(), leader, crew));
		        
			// Retrieve the full updated Captain
			Captain fullUpdatedCaptain = repo.findById(updatedCaptain.getCaptainId()).orElse(null);

			// Send the full updated Captain
			return ResponseEntity.status(HttpStatus.OK)
								 .body(fullUpdatedCaptain);
	    }
	}
	
	// DELETE ONE
	public ResponseEntity<Void> deleteOne(int captainId) {
//		repo.deleteById(captainId);
//		return ResponseEntity.status(HttpStatus.NO_CONTENT)
//							 .body(null);
		
	    if (repo.existsById(captainId)) {
	        Captain captain = repo.findById(captainId).get();

	        // Check if the captain has an assigned crew
	        if (captain.getCrew() != null) {
	            Crew crew = captain.getCrew();
	            crew.setCaptain(null); // Remove captain reference from crew
	            crewRepo.save(crew); // Save the crew update
	        }

	        repo.deleteById(captainId);
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
	    
	}
	
}
