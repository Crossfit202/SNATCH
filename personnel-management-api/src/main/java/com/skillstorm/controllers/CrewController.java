package com.skillstorm.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.apis.HeistClient;
import com.skillstorm.dtos.CrewDTO;
import com.skillstorm.dtos.HeistDTO;
import com.skillstorm.models.Crew;
import com.skillstorm.services.CrewService;

@RestController // Marks this class as a REST controller, making it handle HTTP requests
@RequestMapping("/crew") // Base URL mapping for this controller
public class CrewController {

	private CrewService service; // Service layer dependency for handling business logic related to crews
	private HeistClient heistClient; // Client to communicate with the Heist service (microservice communication)

	// Constructor-based dependency injection
	public CrewController(CrewService service, HeistClient heistClient) {
		super();
		this.service = service;
		this.heistClient = heistClient;
	}

	// GET ALL - Retrieves all crews from the database
	@GetMapping
	public Iterable<Crew> findAll() {
		return service.findAll(); // Calls the service layer method to return all crew entities
	}

	// GET BY ID - Retrieves a specific crew by its ID
	@GetMapping("/{crewId}") // {crewId} is a path variable captured from the URL
	public ResponseEntity<Object> findById(@PathVariable("crewId") int crewId) {
		return service.findById(crewId); // Calls the service to find a crew by ID
	}

	// GET HEIST BY CREW ID - Fetches the heist associated with a given crew ID
	@GetMapping("/heist/{crewId}") // Calls an external Heist API (microservice)
	public ResponseEntity<HeistDTO> findHeistByCrewId(@PathVariable("crewId") int crewId) {
		return heistClient.findByCrewId(crewId); // Uses HeistClient to retrieve the heist linked to the crew
	}

	// CREATE - Adds a new crew to the database
	@PostMapping
	public ResponseEntity<Object> addOne(@RequestBody CrewDTO crewDTO) {
		// Accepts a JSON request body, maps it to CrewDTO, and sends it to the service
		return service.addOne(crewDTO);
	}

	// UPDATE - Modifies an existing crew record
	@PutMapping("/{crewId}") // Path variable captures the crew ID
	public ResponseEntity<Object> updateOne(@PathVariable("crewId") int crewId, @RequestBody CrewDTO crewDTO) {
		// Accepts updated crew details from the request body and sends them to the
		// service
		return service.updateOne(crewId, crewDTO);
	}

	// DELETE - Removes a crew from the database
	@DeleteMapping("/{crewId}") // Captures the crew ID from the URL
	public ResponseEntity<Void> deleteById(@PathVariable("crewId") int crewId) {
		return service.deleteById(crewId); // Calls the service to delete the specified crew
	}
}
