package com.skillstorm.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillstorm.dtos.CrewDTO;
import com.skillstorm.models.Crew;
import com.skillstorm.repositories.CrewRepository;



@Service
public class CrewService {
	private CrewRepository repo;

	public CrewService(CrewRepository repo) {
		super();
		this.repo = repo;
	}

//	SERVICE METHODS
	
//	GET ALL
	public Iterable<Crew> findAll() {
		return repo.findAll();
	}
	
//	FIND BY ID
	
	public ResponseEntity<Crew> findById(int crewId) {
		if (repo.existsById(crewId))
			return ResponseEntity.status(HttpStatus.OK)
							     .body(repo.findById(crewId).get());
		else return ResponseEntity.status(HttpStatus.NOT_FOUND)
				                  .body(null);
	}
	
	
//	CREATE ONE - POST
	public ResponseEntity<Crew> addOne(CrewDTO crewDTO) {
		return ResponseEntity.status(HttpStatus.CREATED)
				             .body(repo.save(new Crew(0, crewDTO.getCrewName(),
				            		                     crewDTO.getMaxCapacity(),
				            		                     crewDTO.isAvaiability(),
				            		                     crewDTO.isHasCaptain(),
				            		                     crewDTO.getCaptainId())));
	}
	
	
//	PUT
		public ResponseEntity<Crew> updateOne(int crewId, CrewDTO crewDTO) {
		    if (repo.existsById(crewId))
		    	return ResponseEntity.status(HttpStatus.OK)
		    			             .body(repo.save(new Crew(crewId, crewDTO.getCrewName(),
	            		                                              crewDTO.getMaxCapacity(),
	            		                                              crewDTO.isAvaiability(),
	            		                                              crewDTO.isHasCaptain(),
	            		                                              crewDTO.getCaptainId())));
		    	else
		    	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		    			             .body(null);
		        
		}
	
	
//  DELETE
		public ResponseEntity<Void> deleteById(int crewId) {
		        repo.deleteById(crewId);  
		        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		    } 
		
}
