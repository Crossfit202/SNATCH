package com.skillstorm.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
			
			// Original
//			return ResponseEntity.status(HttpStatus.CREATED)
//					 .body(repo.save(new Captain(0, captainDTO.getCaptainName(), captainDTO.getLeader(), null)));
			
			// Get Leader Object from input Leader ID
			Leader leader = leaderRepo.findById(captainDTO.getLeader().getLeaderId())
		                			  .orElse(null);
			
			// Get Crew Object from input Crew ID
	        Crew crew = crewRepo.findById(captainDTO.getCrew().getCrewId())
	                			.orElse(null);
			
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
	public ResponseEntity<Object> updateOne(int captainId, CaptainDTO captainDTO) {
		if(repo.existsById(captainId)) {
			
			// Get Leader Object from input Leader ID
			Leader leader = leaderRepo.findById(captainDTO.getLeader().getLeaderId())
      			  					  .orElse(null);
			
			// Get Crew Object from input Crew ID
	        Crew crew = crewRepo.findById(captainDTO.getCrew().getCrewId())
        						.orElse(null);
	        
	        // Save the updated Captain
	        Captain updatedCaptain = repo.save(new Captain(captainId, captainDTO.getCaptainName(), leader, crew));
	        
	        // Retrieve the full updated Captain
	        Captain fullUpdatedCaptain = repo.findById(updatedCaptain.getCaptainId()).orElse(null);
			
			return ResponseEntity.status(HttpStatus.OK)
			 			 		 .body(fullUpdatedCaptain);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								 .body(String.format("Failed to update! Captain with ID %d does not exist. Ensure the ID is correct or create a new Captain.", captainId));
		}
	}
	
	// DELETE ONE
	public ResponseEntity<Void> deleteOne(int captainId) {
		repo.deleteById(captainId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
							 .body(null);
	}
	
}
