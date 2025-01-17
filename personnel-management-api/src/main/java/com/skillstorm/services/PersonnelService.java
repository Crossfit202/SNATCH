package com.skillstorm.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillstorm.dtos.PersonnelDTO;
import com.skillstorm.models.Personnel;
import com.skillstorm.repositories.PersonnelRepository;


@Service
public class PersonnelService {
	private PersonnelRepository repo;

	public PersonnelService(PersonnelRepository repo) {
		super();
		this.repo = repo;
	}

//	SERVICE METHODS
	
//	GET ALL
	public Iterable<Personnel> findAll() {
		return repo.findAll();
	}
	
//	FIND BY ID
	
	public ResponseEntity<Personnel> findById(int personnelId) {
		if (repo.existsById(personnelId))
			return ResponseEntity.status(HttpStatus.OK)
							     .body(repo.findById(personnelId).get());
		else return ResponseEntity.status(HttpStatus.NOT_FOUND)
				                  .body(null);
	}
	
	
//	CREATE ONE - POST
	public ResponseEntity<Personnel> addOne(PersonnelDTO personnelDTO) {
		return ResponseEntity.status(HttpStatus.CREATED)
				             .body(repo.save(new Personnel(0, personnelDTO.getPersonnelName(), 
				            		                     personnelDTO.getSpecies(),
				            		                     personnelDTO.getProfileImg(),
				            		                     personnelDTO.isAssigned(),
				            		                     null,
				            		                     null)));
	}
	
	
//	PUT
		public ResponseEntity<Personnel> updateOne(int personnelId, PersonnelDTO personnelDTO) {
		    if (repo.existsById(personnelId))
		    	return ResponseEntity.status(HttpStatus.OK)
		    			             .body(repo.save(new Personnel(personnelId, personnelDTO.getPersonnelName(), 
		    			            		                                    personnelDTO.getSpecies(),
		    			            		                                    personnelDTO.getProfileImg(),
		    			            		                                    personnelDTO.isAssigned(),
		    			            		                                    null,
		    			            		                                    null)));
		    	
		    else
		    	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		    			             .body(null);
		        
		}
	
	
//  DELETE
		public ResponseEntity<Void> deleteById(int personnelId) {
		        repo.deleteById(personnelId);  
		        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		    } 
		
}
