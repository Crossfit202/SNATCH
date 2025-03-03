package com.skillstorm.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skillstorm.dtos.PersonnelDTO;
import com.skillstorm.models.Crew;
import com.skillstorm.models.Personnel;
import com.skillstorm.models.Skill;
import com.skillstorm.repositories.CrewRepository;
import com.skillstorm.repositories.PersonnelRepository;
import com.skillstorm.repositories.SkillRepository;

@Service
public class PersonnelService {
	
	private PersonnelRepository repo;
	
	// Inject Crew Repo to retrieve Crew Object
	private CrewRepository crewRepo;
	
	// Inject Skill Repo to retrieve Skill Object
	private SkillRepository skillRepo;

	// Constructor with constructor injection for repositories
	public PersonnelService(PersonnelRepository repo, CrewRepository crewRepo, SkillRepository skillRepo) {
		super();
		this.repo = repo;
		this.crewRepo = crewRepo;
		this.skillRepo = skillRepo;
	}

	
	/*
	 * SERVICE METHODS
	 */

	
// 	GET ALL PERSONNEL
	public Iterable<Personnel> findAll() {
		return repo.findAll();
	}

//  FIND PERSONNEL BY ID
	public ResponseEntity<Object> findById(int personnelId) {
		
		// Check if requested Personnel ID exists in the DB
		if (repo.existsById(personnelId)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(repo.findById(personnelId).get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(String.format("Personnel with ID %d does not exist!", personnelId));
		}
	}

// 	CREATE ONE PERSONNEL - POST
	public ResponseEntity<Object> addOne(PersonnelDTO personnelDTO) {
		
		// Check if input for Personnel Name is a duplicate
		if (repo.existsByPersonnelNameIgnoreCase(personnelDTO.getPersonnelName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
								 .body(String.format("Personnel with name '%s' already exists!", 
										 				personnelDTO.getPersonnelName()));
		}
		
		/*
		 * PERSONNEL-CREW RELATIONSHIP VALIDATIONS
		 */
		
		// Set isAssigned flag to true if Personnel is associated with a Crew
		boolean isAssigned = personnelDTO.getCrew() != null;
		
		// If a Crew is selected
		// Get List of Personnel, else give empty list
		List<Personnel> personnels = isAssigned ? repo.findAllPersonnelsByCrewId(personnelDTO.getCrew().getCrewId()) : new ArrayList<>();

		// Check if selected Crew is already at max capacity
		if(isAssigned && personnels.size() >= repo.findMaxCapByCrewId(personnelDTO.getCrew().getCrewId())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								 .body(String.format("Max capacity reached for Crew. Please assign a different Crew."));
		} else {
			
			// Get Captain Object (set as null if no incoming captain)
		    Crew crew = isAssigned ? crewRepo.findById(personnelDTO.getCrew().getCrewId()).orElse(null) : null;
			
			// Get incoming List of Skill Objects (Stream attempt?)
		    // Convert list to stream
		    List<Skill> skills = personnelDTO.getSkills().stream()
		    		// for each skill in skills, get the full skill object by its id or else set to null (non-terminal)
		    	    .map(skill -> skillRepo.findById(skill.getSkillId()).orElse(null))
		    	    // filter out nulls (non-terminal)
		    	    .filter(skill -> skill != null)
		    	    // collects the skill objects into a list (terminal)
		    	    .collect(Collectors.toList());
		    
		    // Original before trying stream
//			List<Skill> skills = new ArrayList<>();
//			if(personnelDTO.getSkills() != null) {
//				for(Skill skill : personnelDTO.getSkills()) {
//					Skill fullSkill = skillRepo.findById(skill.getSkillId()).orElse(null);
//					if(fullSkill != null) {
//						skills.add(fullSkill);
//					}
//				}
//			}
			
			return ResponseEntity.status(HttpStatus.CREATED)
								 .body(repo.save(new Personnel(0, personnelDTO.getPersonnelName(),
										 						  personnelDTO.getSpecies(),
										 						  personnelDTO.getProfileImg(),
										 						  isAssigned,
										 						  crew,
										 						  skills)));
		}
	}

// 	UPDATE ONE PERSONNEL - PUT
	@Transactional
	public ResponseEntity<Object> updateOne(int personnelId, PersonnelDTO personnelDTO) {
		
		// Check if requested Personnel ID exists in the DB
	    if(!repo.existsById(personnelId)) {
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		             			 .body(String.format("Failed to update! Personnel with ID %d does not exist. Ensure the ID is correct or create a new Personnel.", 
		             					 				personnelId));
	    }
		
	    // Get the current Personnel
	    Personnel currentPersonnel = repo.findById(personnelId).get();
	    
	    /*
	     * PERSONNEL NAME VALIDATION
	     */
	    
		// Check if the Personnel name already exists in the DB
		// and is not the name of this one that is currently being updated
		boolean personnelNameExists = repo.existsByPersonnelNameIgnoreCase(personnelDTO.getPersonnelName()) 
		    						 && !currentPersonnel.getPersonnelName().equalsIgnoreCase(personnelDTO.getPersonnelName());
	    
		if(personnelNameExists) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
       			 .body(String.format("Failed to update! Personnel with with name '%s' already exists!", 
       									personnelDTO.getPersonnelName()));
		}
		
		/*
		 * CREW MAX CAPACITY CHECK
		 */
		boolean isAssigned = personnelDTO.getCrew() != null;
		
		if (isAssigned) {
		    int assignedPersonnelCount = repo.findAllPersonnelsByCrewId(personnelDTO.getCrew().getCrewId()).size();
		    Integer maxCapacity = repo.findMaxCapByCrewId(personnelDTO.getCrew().getCrewId());

		    if (assignedPersonnelCount >= maxCapacity) {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		            .body(String.format("Failed to update! The crew's max capacity (%d) has been reached. Choose a different Crew.",
		                                maxCapacity));
		    }
		}
			
		return ResponseEntity.status(HttpStatus.OK)
							 .body(repo.save(new Personnel(personnelId, personnelDTO.getPersonnelName(),
									 									personnelDTO.getSpecies(),
									 									personnelDTO.getProfileImg(),
									 									isAssigned,
									 									personnelDTO.getCrew(),
									 									personnelDTO.getSkills())));
	}
	

// 	DELETE ONE PERSONNEL
	public ResponseEntity<Void> deleteById(int personnelId) {
		repo.deleteById(personnelId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

}
