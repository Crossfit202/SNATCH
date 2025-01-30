package com.skillstorm.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillstorm.dtos.SkillDTO;
import com.skillstorm.models.Skill;
import com.skillstorm.repositories.SkillRepository;



@Service
public class SkillService {
	private SkillRepository repo;

	public SkillService(SkillRepository repo) {
		super();
		this.repo = repo;
	}

//	SERVICE METHODS
	
//	GET ALL
	public Iterable<Skill> findAll() {
		return repo.findAll();
	}
	
//	FIND BY ID
	
	public ResponseEntity<Skill> findById(int skillId) {
		
		// Check if requested Skill ID exists in the DB
		if (repo.existsById(skillId))
			return ResponseEntity.status(HttpStatus.OK)
							     .body(repo.findById(skillId).get());
		else return ResponseEntity.status(HttpStatus.NOT_FOUND)
				                  .body(null);
	}
	
	
//	CREATE ONE - POST
	public ResponseEntity<Skill> addOne(SkillDTO skillDTO) {
		return ResponseEntity.status(HttpStatus.CREATED)
				             .body(repo.save(new Skill(0, skillDTO.getSkillName(), null)));
	}
	
	
//	PUT
		public ResponseEntity<Skill> updateOne(int skillId, SkillDTO skillDTO) {
			
			// Check if requested Skill ID exists in the DB
		    if (repo.existsById(skillId))
		    	return ResponseEntity.status(HttpStatus.OK)
		    			             .body(repo.save(new Skill(skillId, skillDTO.getSkillName(), null)));
		    	
		    else
		    	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		    			             .body(null);
		        
		}
	
	
//  DELETE
		public ResponseEntity<Void> deleteById(int skillId) {
		        repo.deleteById(skillId);  
		        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		    } 
		
}
