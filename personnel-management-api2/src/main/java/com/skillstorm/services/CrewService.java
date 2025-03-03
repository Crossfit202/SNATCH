package com.skillstorm.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skillstorm.apis.HeistClient;
import com.skillstorm.dtos.CrewDTO;
import com.skillstorm.dtos.HeistDTO;
import com.skillstorm.models.Captain;
import com.skillstorm.models.Crew;
import com.skillstorm.models.Personnel;
import com.skillstorm.repositories.CaptainRepository;
import com.skillstorm.repositories.CrewRepository;
import com.skillstorm.repositories.PersonnelRepository;

@Service
public class CrewService {
	private CrewRepository repo;

	// Inject HeistClient to communicate with Heist API
	private HeistClient heistClient;

	// Inject Personnel Repo to retrieve Personnel Object
	private PersonnelRepository personnelRepo;

	// Inject Captain Repo to retrieve Captain Object
	private CaptainRepository captainRepo;

	// Constructor with constructor injection for repositories
	public CrewService(CrewRepository repo, HeistClient heistClient, PersonnelRepository personnelRepo, CaptainRepository captainRepo) {
		super();
		this.repo = repo;
		this.heistClient = heistClient;
		this.personnelRepo = personnelRepo;
		this.captainRepo = captainRepo;
	}

	
	/*
	 * SERVICE METHODS
	 */

	
//	GET ALL CREW
	public Iterable<Crew> findAll() {
		return repo.findAll();
	}

//	FIND CREW BY ID
	public ResponseEntity<Object> findById(int crewId) {

		// Check if requested Crew exists in the DB
		if (repo.existsById(crewId)) {
			return ResponseEntity.status(HttpStatus.OK).body(repo.findById(crewId).get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
								 .body(String.format("Crew with ID %d does not exist!", crewId));
		}
	}

//	GET ASSOCIATED HEIST BY CREW ID
	public ResponseEntity<HeistDTO> findHeistByCrewId(int crewId) {

		// Check if requested Crew exists in the DB
		if (repo.existsById(crewId)) {

			// Use the Heist Client to retrieve associated Heist
			return heistClient.findByCrewId(crewId);

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

//	CREATE ONE CREW - POST
	public ResponseEntity<Object> addOne(CrewDTO crewDTO) {

		// Check if input for Crew Name is a duplicate
		if (repo.existsByCrewNameIgnoreCase(crewDTO.getCrewName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
								 .body(String.format("Crew with name '%s' already exists!", 
										 				crewDTO.getCrewName()));
		}

		// Check if the incoming Captain is already assigned to a Crew
		if (crewDTO.getCaptain() != null && repo.existsByCaptain_CaptainId(crewDTO.getCaptain().getCaptainId())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
								 .body(String.format("Captain with ID '%d' is already assigned to a crew!", 
										 				crewDTO.getCaptain().getCaptainId()));
		} else {

			// Get Captain Object (if supplied)
			Captain captain = captainRepo.findById(crewDTO.getCaptain().getCaptainId()).orElse(null);

			// Save the new Crew with associated Captain Object
			Crew savedCrew = repo.save(new Crew(0, crewDTO.getCrewName(), 
												   crewDTO.getMaxCapacity(),
												   crewDTO.isAvailability(), 
												   crewDTO.isHasCaptain(), 
												   captain, 
												   null));

			// Retrieve the newly saved Crew Object
			Crew completeCrew = repo.findById(savedCrew.getCrewId()).orElse(null);

			// Refactor this into throwing an Exception in the future...Global Exception Handler?
			// Null handling in case the Crew did not save properly
			if (completeCrew != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(completeCrew);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Error occurred while retrieving the saved Crew.");
			}

		}

	}

//	UPDATE ONE CREW - PUT
	@Transactional
	public ResponseEntity<Object> updateOne(int crewId, CrewDTO crewDTO) {

		// Check if the Crew ID exists in DB, else quick exit
		if (!repo.existsById(crewId)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								 .body(String.format("Failed to update! Crew with ID %d does not exist. Ensure the ID is correct or create a new Crew.",
										 				crewId));
		}

		// Get the current Crew
		Crew currentCrew = repo.findById(crewId).get();

		/*
		 * CAPTAIN AND CREW NAME CONFLICT VALIDATION
		 */

		if(crewDTO.getCaptain() != null) {
			// Check if the Captain ID is already assigned to a Crew
			// and is not the one it is currently assigned to
			boolean captainExists = repo.existsByCaptain_CaptainId(crewDTO.getCaptain().getCaptainId())
										&& (currentCrew.getCaptain().getCaptainId() != crewDTO.getCaptain().getCaptainId());

			// Check if the Crew name already exists in the DB
			// and is not the name of this one that is currently being updated
			boolean crewNameExists = repo.existsByCrewNameIgnoreCase(crewDTO.getCrewName())
										&& !currentCrew.getCrewName().equalsIgnoreCase(crewDTO.getCrewName());

			// If both the Captain selected and Crew name conflicts with existing data
			if (captainExists && crewNameExists) {
				return ResponseEntity.status(HttpStatus.CONFLICT)
									 .body(String.format("Failed to update! Crew with with name '%s' already exists, and Captain with ID '%d' is already assigned to a crew!",
											 				crewDTO.getCrewName(), crewDTO.getCaptain().getCaptainId()));

			// If only the captain selected conflicts
			} else if (captainExists) {
				return ResponseEntity.status(HttpStatus.CONFLICT)
									 .body(String.format("Failed to update! Captain with ID '%d' is already assigned to a crew!",
											 				crewDTO.getCaptain().getCaptainId()));

			// If only the name conflicts
			} else if (crewNameExists) {
				return ResponseEntity.status(HttpStatus.CONFLICT)
									 .body(String.format("Failed to update! Crew with with name '%s' already exists!", 
											 				crewDTO.getCrewName()));
			}
			
		} else {
			// To allow null Captains
			currentCrew.setCaptain(null);
			// To flip the boolean to false if Captain is null
			currentCrew.setHasCaptain(false);
		}


		/*
		 * PERSONNEL VALIDATION
		 */

		// Check if the incoming Personnels List exceeds the Crew's max capacity
		if (crewDTO.getPersonnels() != null && crewDTO.getPersonnels().size() > crewDTO.getMaxCapacity()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								 .body(String.format("Failed to update! The number of personnel (%d) exceeds the crew's max capacity (%d).",
														crewDTO.getPersonnels().size(), crewDTO.getMaxCapacity()));

		// If incoming Personnels List is empty, remove all current Personnel from the Crew
		} else if (crewDTO.getPersonnels() == null || crewDTO.getPersonnels().isEmpty()) {
			
			// Remove all personnel from the crew
			repo.removePersonnelNotInList(crewId, null);

		} else {
			// To allow populating of incoming Personnel into a List
			List<Integer> personnelIds = new ArrayList<>();

			// Add all Personnels from the incoming List to the List initialized above
			for (Personnel personnel : crewDTO.getPersonnels()) {
				personnelIds.add(personnel.getPersonnelId());
			}

			// Update Personnels on the Crew using above List
			repo.updateCrewPersonnel(currentCrew.getCrewId(), personnelIds);

			// Remove Personnel currently on the Crew that are not in the incoming List
			repo.removePersonnelNotInList(currentCrew.getCrewId(), personnelIds);
		}

		// If no conflicts, update the Crew!
		return ResponseEntity.status(HttpStatus.OK).body(
				repo.save(new Crew(crewId, crewDTO.getCrewName(), 
										   crewDTO.getMaxCapacity(), 
										   crewDTO.isAvailability(),
										   crewDTO.isHasCaptain(), 
										   crewDTO.getCaptain(), 
										   currentCrew.getPersonnels()
										   )));
	}


//  DELETE ONE CREW
	public ResponseEntity<Void> deleteById(int crewId) {

		// Check if incoming Crew ID exists in the DB
	    if (repo.existsById(crewId)) {
	        Crew crew = repo.findById(crewId).get();

	        // Dissociate all Personnel from the Crew
	        List<Personnel> personnels = personnelRepo.findAllPersonnelsByCrewId(crewId);
	        for (Personnel personnel : personnels) {
	            personnel.setCrew(null);
	            personnelRepo.save(personnel);
	        }
	        
	        // Check if the Crew has an assigned captain and dissociate relationship if so
	        if (crew.getCaptain() != null) {
	        	Captain captain = crew.getCaptain();
	            captain.setCrew(null);
	            captainRepo.save(captain);
	        }

	        repo.deleteById(crewId);
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	        
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
	}

}
