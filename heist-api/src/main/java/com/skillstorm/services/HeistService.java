package com.skillstorm.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillstorm.apis.CrewClient;
import com.skillstorm.dtos.CrewDTO;
import com.skillstorm.dtos.HeistDTO;
import com.skillstorm.dtos.HeistWithCrewDTO;
import com.skillstorm.models.Heist;
import com.skillstorm.repositories.HeistRepository;


@Service
public class HeistService {
	private HeistRepository repo;
	private CrewClient crewClient;

	public HeistService(HeistRepository repo, CrewClient crewClient) {
		super();
		this.repo = repo;
		this.crewClient = crewClient;
	}

//	SERVICE METHODS
	
//	GET ALL
	public ResponseEntity<Iterable<HeistWithCrewDTO>> findAll() {
		Iterable<Heist> heists = repo.findAll();
		
		List<HeistWithCrewDTO> heistsWithCrew = new ArrayList<>();
		

	    // Loop through each Heist and fetch the associated CrewDTO
	    for (Heist heist : heists) {
	        CrewDTO crew = crewClient.findCrewByCrewId(heist.getCrewId()).getBody();

	        // If crew is not found, return heist only with null for crew
	        if (crew == null) {
	            heistsWithCrew.add(new HeistWithCrewDTO(heist, null));
	        } else {
	            heistsWithCrew.add(new HeistWithCrewDTO(heist, crew));
	        }
	    }

	    return ResponseEntity.status(HttpStatus.OK).body(heistsWithCrew);
	}
	
//	FIND BY ID
	
//	public ResponseEntity<Heist> findById(int heistId) {
//		if (repo.existsById(heistId)) {
//			return ResponseEntity.status(HttpStatus.OK)
//				     .body(repo.findById(heistId).get());
//		} else {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND)
//				                  .body(null);
//		}
//	}
	
	public ResponseEntity<Object> findById(int heistId) {
		if (repo.existsById(heistId)) {
			Heist heist = repo.findById(heistId).get();
			
			CrewDTO crew = crewClient.findCrewByCrewId(heist.getCrewId()).getBody();
			
	        if (crew == null) {
	            return ResponseEntity.status(HttpStatus.OK)
	                                 .body(new HeistWithCrewDTO(heist, null));
	        }
	        // Return the heist with the crew data
	        return ResponseEntity.status(HttpStatus.OK)
	                             .body(new HeistWithCrewDTO(heist, crew));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
				                  .body(String.format("Heist with ID %d does not exist", heistId));
		}
	}
	
	
// GET HEIST BY CREW ID (FIX THIS METHOD)
	public ResponseEntity<Heist> findByCrewId(int crewId) {
		Heist heist = repo.findByCrewId(crewId);
		if (heist != null) {
			return ResponseEntity.status(HttpStatus.OK)
								 .body(heist);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
								 .body(null);
		}
	}
	
	
//	CREATE ONE - POST
	public ResponseEntity<Object> addOne(HeistDTO heistDTO) {
		CrewDTO crew = crewClient.findCrewByCrewId(heistDTO.getCrewId()).getBody();
		
	    if (crew == null) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                             .body(String.format("Crew with ID %d does not exist. Please assign a different Crew.", heistDTO.getCrewId()));
	    } else {
		    Heist heist = repo.save(new Heist(0, heistDTO.getDescription(), 
					 							 heistDTO.getLocation(),
					 							 heistDTO.isAssigned(),
					 							 heistDTO.getStatus(),
					 							 heistDTO.getCrewId()));

		    HeistWithCrewDTO heistWithCrew = new HeistWithCrewDTO(heist, crew);

		    return ResponseEntity.status(HttpStatus.CREATED)
		    					 .body(heistWithCrew);
	    }
	    

	}
	
	
//	PUT
		public ResponseEntity<Object> updateOne(int heistId, HeistDTO heistDTO) {
		    if (repo.existsById(heistId)) {
		    	 CrewDTO crew = crewClient.findCrewByCrewId(heistDTO.getCrewId()).getBody();
		    	 
		         if (crew == null) {
		             return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		                                  .body(String.format("Crew with ID %d does not exist. Please assign a different Crew.", heistDTO.getCrewId()));
		         }
		         
		         Heist updatedHeist = repo.save(new Heist(heistId, heistDTO.getDescription(), 
	                      										   heistDTO.getLocation(),
	                      										   heistDTO.isAssigned(),
	                      										   heistDTO.getStatus(),
	                      										   heistDTO.getCrewId()));
		         
		         // Wrap the saved Heist and CrewDTO in a HeistWithCrewDTO and return it
		         HeistWithCrewDTO heistWithCrew = new HeistWithCrewDTO(updatedHeist, crew);
		         
		         return ResponseEntity.status(HttpStatus.OK).body(heistWithCrew);
		         
		    } else {
		    	
		    	return ResponseEntity.status(HttpStatus.NOT_FOUND)
		    						 .body(String.format("Heist with ID %d does not exist", heistId));
		    }
		       
		 
		}
	
	
//  DELETE
		public ResponseEntity<Void> deleteById(int heistId) {
		        repo.deleteById(heistId);  
		        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		    } 
		
}
