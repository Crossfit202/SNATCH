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

	/*
	 * SERVICE METHODS
	 */

//	GET ALL
	public ResponseEntity<Iterable<HeistWithCrewDTO>> findAll() {
		// Get all Heists
		Iterable<Heist> heists = repo.findAll();

		// Initialize List for Heist-Crew
		List<HeistWithCrewDTO> heistsWithCrew = new ArrayList<>();

		// Loop through each Heist
		for (Heist heist : heists) {
			
			// Fetch associated Crew
			CrewDTO crew = crewClient.findCrewByCrewId(heist.getCrewId()).getBody();

			// If Crew is not found, return Heist only with null for Crew
			if (crew == null) {
				heistsWithCrew.add(new HeistWithCrewDTO(heist, null));
			} else {
				heistsWithCrew.add(new HeistWithCrewDTO(heist, crew));
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(heistsWithCrew);
	}

//	FIND BY ID
	public ResponseEntity<Object> findById(int heistId) {

		// Check if requested Heist ID exists in the DB
		if (repo.existsById(heistId)) {
			
			// Get the current Heist
			Heist heist = repo.findById(heistId).get();

			// Get the Crew associated with current Heist
			CrewDTO crew = crewClient.findCrewByCrewId(heist.getCrewId()).getBody();

			// If Crew is not found, return Heist only with null for Crew
			if (crew == null) {
				return ResponseEntity.status(HttpStatus.OK).body(new HeistWithCrewDTO(heist, null));
			}

			// Return the Heist with the Crew data
			return ResponseEntity.status(HttpStatus.OK).body(new HeistWithCrewDTO(heist, crew));
			
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(String.format("Heist with ID %d does not exist", heistId));
		}
	}

// GET HEIST BY CREW ID (FIX THIS METHOD)
	public ResponseEntity<Heist> findByCrewId(int crewId) {
		
		Heist heist = repo.findByCrewId(crewId);

		if (heist != null) {
			return ResponseEntity.status(HttpStatus.OK).body(heist);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

//	CREATE ONE - POST
	public ResponseEntity<Object> addOne(HeistDTO heistDTO) {
		
		// Use Crew Client to get the Crew Object
		CrewDTO crew = crewClient.findCrewByCrewId(heistDTO.getCrewId()).getBody();

		// If incoming Crew does not exist in the DB
		if (crew == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								 .body(String.format("Crew with ID %d does not exist. Please assign a different Crew.", 
										 				heistDTO.getCrewId()));
		} else {
			
			// Save the newly created Heist
			Heist heist = repo.save(new Heist(0, heistDTO.getDescription(), 
												 heistDTO.getLocation(),
												 heistDTO.isAssigned(), 
												 heistDTO.getStatus(), 
												 heistDTO.getCrewId()
												 ));

			// Wrap the saved Heist and CrewDTO in a HeistWithCrewDTO and return it
			HeistWithCrewDTO heistWithCrew = new HeistWithCrewDTO(heist, crew);

			return ResponseEntity.status(HttpStatus.CREATED).body(heistWithCrew);
		}

	}

//	PUT
	public ResponseEntity<Object> updateOne(int heistId, HeistDTO heistDTO) {
		
		// Check if requested Heist ID exists in the DB
		if (repo.existsById(heistId)) {
			
			// Use Crew Client to get the Crew Object
			CrewDTO crew = crewClient.findCrewByCrewId(heistDTO.getCrewId()).getBody();

			// If incoming Crew does not exist in the DB
			if (crew == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
									 .body(String.format("Crew with ID %d does not exist. Please assign a different Crew.", 
											 				heistDTO.getCrewId()));
			}

			Heist updatedHeist = repo.save(new Heist(heistId, heistDTO.getDescription(), heistDTO.getLocation(),
					heistDTO.isAssigned(), heistDTO.getStatus(), heistDTO.getCrewId()));

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
