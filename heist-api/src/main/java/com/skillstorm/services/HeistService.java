package com.skillstorm.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillstorm.dtos.HeistDTO;
import com.skillstorm.models.Heist;
import com.skillstorm.repositories.HeistRepository;


@Service
public class HeistService {
	private HeistRepository repo;

	public HeistService(HeistRepository repo) {
		super();
		this.repo = repo;
	}

//	SERVICE METHODS
	
//	GET ALL
	public Iterable<Heist> findAll() {
		return repo.findAll();
	}
	
//	FIND BY ID
	
	public ResponseEntity<Heist> findById(int heistId) {
		if (repo.existsById(heistId))
			return ResponseEntity.status(HttpStatus.OK)
							     .body(repo.findById(heistId).get());
		else return ResponseEntity.status(HttpStatus.NOT_FOUND)
				                  .body(null);
	}
	
	
//	CREATE ONE - POST
	public ResponseEntity<Heist> addOne(HeistDTO heistDTO) {
		return ResponseEntity.status(HttpStatus.CREATED)
				             .body(repo.save(new Heist(0, heistDTO.getDescription(), 
				            		                      heistDTO.getLocation(),
				            		                      heistDTO.isAssigned(),
				            		                      heistDTO.getStatus(),
				            		                      heistDTO.getCrewId())));
	}
	
	
//	PUT
		public ResponseEntity<Heist> updateOne(int heistId, HeistDTO heistDTO) {
		    if (repo.existsById(heistId))
		    	return ResponseEntity.status(HttpStatus.OK)
		    			.body(repo.save(new Heist(heistId, heistDTO.getDescription(), 
  		                      heistDTO.getLocation(),
  		                      heistDTO.isAssigned(),
  		                      heistDTO.getStatus(),
  		                      heistDTO.getCrewId())));
		    	
		    else
		    	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		    			             .body(null);
		        
		}
	
	
//  DELETE
		public ResponseEntity<Void> deleteById(int heistId) {
		        repo.deleteById(heistId);  
		        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		    } 
		
}
