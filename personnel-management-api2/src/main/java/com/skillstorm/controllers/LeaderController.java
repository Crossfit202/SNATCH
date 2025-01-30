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

import com.skillstorm.dtos.LeaderDTO;
import com.skillstorm.models.Leader;
import com.skillstorm.services.LeaderService;

@RestController // Marks this class as a REST controller, making it handle HTTP requests
@RequestMapping("/leader") // Defines the base URL mapping for this controller
public class LeaderController {

	private LeaderService service; // Injects the service layer dependency

	// Constructor-based dependency injection for LeaderService
	public LeaderController(LeaderService service) {
		super();
		this.service = service;
	}

	// GET ALL - Retrieves all leaders from the database
	@GetMapping
	public Iterable<Leader> findAll() {
		return service.findAll(); // Calls the service layer method to return all leader entities
	}

	// GET BY ID - Retrieves a specific leader by its ID
	@GetMapping("/{leaderId}") // {leaderId} is a path variable captured from the URL
	public ResponseEntity<Object> findById(@PathVariable int leaderId) {
		return service.findById(leaderId); // Calls the service to find a leader by ID
	}

	// CREATE ONE - Adds a new leader to the database
	@PostMapping
	public ResponseEntity<Object> addOne(@RequestBody LeaderDTO leaderDTO) {
		// Accepts a JSON request body, maps it to LeaderDTO, and sends it to the
		// service
		return service.addOne(leaderDTO);
	}

	// UPDATE ONE - Modifies an existing leader record
	@PutMapping("/{leaderId}") // Captures the leader ID from the URL
	public ResponseEntity<Object> updateOne(@PathVariable int leaderId, @RequestBody LeaderDTO leaderDTO) {
		// Accepts updated leader details from the request body and sends them to the
		// service
		return service.updateOne(leaderId, leaderDTO);
	}

	// DELETE ONE - Removes a leader from the database
	@DeleteMapping("/{leaderId}") // Captures the leader ID from the URL
	public ResponseEntity<Void> deleteOne(@PathVariable int leaderId) {
		return service.deleteOne(leaderId); // Calls the service to delete the specified leader
	}
}
