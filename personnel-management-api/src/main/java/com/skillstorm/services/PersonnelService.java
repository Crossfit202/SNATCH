package com.skillstorm.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillstorm.dtos.PersonnelDTO;
import com.skillstorm.models.Crew;
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
	
	public ResponseEntity<Object> findById(int personnelId) {
		if (repo.existsById(personnelId)) {
			return ResponseEntity.status(HttpStatus.OK)
				     			 .body(repo.findById(personnelId).get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                  			 .body(String.format("Personnel with ID %d does not exist!", personnelId));
		}
	}
	
	
//	CREATE ONE - POST
	public ResponseEntity<Object> addOne(PersonnelDTO personnelDTO) {
		if(repo.existsByPersonnelNameIgnoreCase(personnelDTO.getPersonnelName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
								 .body(String.format("Personnel with name '%s' already exists!", personnelDTO.getPersonnelName()));
		}
		
		// get list of personnels for selected crew
		List<Personnel> personnels = repo.findAllPersonnelsByCrewId(personnelDTO.getCrew().getCrewId());
		// testing
		System.out.println(personnels.size());
		System.out.println(repo.findMaxCapByCrewId(personnelDTO.getCrew().getCrewId()));
		
		// check if the crew selected has already hit its max capacity or not
		if(personnels.size() >= repo.findMaxCapByCrewId(personnelDTO.getCrew().getCrewId())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								 .body(String.format("Max capacity reached for Crew. Please assign a different Crew."));
		} else {
			return ResponseEntity.status(HttpStatus.CREATED)
		             .body(repo.save(new Personnel(0, personnelDTO.getPersonnelName(), 
		            		                     	  personnelDTO.getSpecies(),
		            		                     	  personnelDTO.getProfileImg(),
		            		                     	  personnelDTO.isAssigned(),
		            		                     	  personnelDTO.getCrew(),
		            		                     	  null)));
		}
	}
	
	
//	PUT
		public ResponseEntity<Personnel> updateOne(int personnelId, PersonnelDTO personnelDTO) {
		    if (repo.existsById(personnelId))
		    	return ResponseEntity.status(HttpStatus.OK)
		    			             .body(repo.save(new Personnel(personnelId, personnelDTO.getPersonnelName(), 
		    			            		                                    personnelDTO.getSpecies(),
		    			            		                                    personnelDTO.getProfileImg(),
		    			            		                                    personnelDTO.isAssigned(),
		    			            		                                    personnelDTO.getCrew(),
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
