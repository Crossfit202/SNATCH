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

import com.skillstorm.dtos.PersonnelDTO;
import com.skillstorm.models.Personnel;
import com.skillstorm.services.PersonnelService;

@RestController // Marks this class as a REST controller to handle HTTP requests
@RequestMapping("/personnel") // Defines the base URL mapping for this controller
public class PersonnelController {

	private PersonnelService service; // Injects the service layer dependency

	// Constructor-based dependency injection for PersonnelService
	public PersonnelController(PersonnelService service) {
		super();
		this.service = service;
	}

	// GET ALL - Retrieves all personnel from the database
	@GetMapping
	public Iterable<Personnel> findAll() {
		return service.findAll(); // Calls the service layer method to return all personnel entities
	}

	// GET BY ID - Retrieves a specific personnel entry by its ID
	@GetMapping("/{personnelId}") // {personnelId} is a path variable captured from the URL
	public ResponseEntity<Object> findById(@PathVariable("personnelId") int personnelId) {
		return service.findById(personnelId); // Calls the service to find personnel by ID
	}

	// CREATE - Adds a new personnel entry to the database
	@PostMapping
	public ResponseEntity<Object> addOne(@RequestBody PersonnelDTO personnelDTO) {
		// Accepts a JSON request body, maps it to PersonnelDTO, and sends it to the
		// service
		return service.addOne(personnelDTO);
	}

	// UPDATE - Modifies an existing personnel record
	@PutMapping("/{personnelId}") // Captures the personnel ID from the URL
	public ResponseEntity<Object> updateOne(@PathVariable("personnelId") int personnelId,
			@RequestBody PersonnelDTO personnelDTO) {
		// Accepts updated personnel details from the request body and sends them to the
		// service
		return service.updateOne(personnelId, personnelDTO);
	}

	// DELETE - Removes a personnel record from the database
	@DeleteMapping("/{personnelId}") // Captures the personnel ID from the URL
	public ResponseEntity<Void> deleteById(@PathVariable("personnelId") int personnelId) {
		return service.deleteById(personnelId); // Calls the service to delete the specified personnel
	}
}
