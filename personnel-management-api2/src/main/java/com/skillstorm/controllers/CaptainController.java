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

import com.skillstorm.dtos.CaptainDTO;
import com.skillstorm.models.Captain;
import com.skillstorm.services.CaptainService;

@RestController // Marks this class as a REST API controller
@RequestMapping("/captain") // Base URL mapping for this controller
public class CaptainController {

	private CaptainService service; // Service layer dependency for business logic

	// Constructor-based dependency injection for the service
	public CaptainController(CaptainService service) {
		super();
		this.service = service;
	}

	// GET ALL - Retrieves all captains from the database
	@GetMapping
	public Iterable<Captain> findAll() {
		return service.findAll(); // Calls the service layer method to get all captains
	}

	// GET BY ID - Retrieves a single captain by its ID
	@GetMapping("/{captainId}") // URL path variable for captain ID
	public ResponseEntity<Object> findById(@PathVariable int captainId) {
		return service.findById(captainId); // Calls the service layer method to find the captain
	}

	// CREATE ONE - Adds a new captain to the database
	@PostMapping
	public ResponseEntity<Object> addOne(@RequestBody CaptainDTO captainDTO) {
		// Accepts a JSON request body and converts it to CaptainDTO
		return service.addOne(captainDTO); // Calls the service to save the new captain
	}

	// UPDATE ONE - Updates an existing captain's details
	@PutMapping("/{captainId}") // Captures the captain ID from the URL
	public ResponseEntity<Object> updateOne(@PathVariable int captainId, @RequestBody CaptainDTO captainDTO) {
		// Accepts the updated captain data in the request body
		return service.updateOne(captainId, captainDTO); // Calls the service to update the captain
	}

	// DELETE ONE - Deletes a captain by its ID
	@DeleteMapping("/{captainId}") // Captures the captain ID from the URL
	public ResponseEntity<Void> deleteOne(@PathVariable int captainId) {
		return service.deleteOne(captainId); // Calls the service to delete the captain
	}

}
