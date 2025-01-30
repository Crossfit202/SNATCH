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

import com.skillstorm.dtos.SkillDTO;
import com.skillstorm.models.Skill;
import com.skillstorm.services.SkillService;

@RestController // Marks this class as a REST controller, enabling it to handle HTTP requests
@RequestMapping("/skill") // Sets the base URL path for all endpoints in this controller
public class SkillController {

	private SkillService service; // Injects the service layer dependency

	// Constructor-based dependency injection for SkillService
	public SkillController(SkillService service) {
		super();
		this.service = service;
	}

	// GET ALL - Retrieves all skills from the database
	@GetMapping
	public Iterable<Skill> findAll() {
		return service.findAll(); // Calls the service layer to return all skill entities
	}

	// GET BY ID - Retrieves a specific skill by its ID
	@GetMapping("/{skillId}") // {skillId} is a path variable captured from the URL
	public ResponseEntity<Skill> findById(@PathVariable("skillId") int skillId) {
		return service.findById(skillId); // Calls the service to find skill by ID and returns a ResponseEntity
	}

	// CREATE - Adds a new skill entry to the database
	@PostMapping
	public ResponseEntity<Skill> addOne(@RequestBody SkillDTO skillDTO) {
		// Accepts a JSON request body, maps it to SkillDTO, and sends it to the service
		return service.addOne(skillDTO);
	}

	// UPDATE - Modifies an existing skill record
	@PutMapping("/{skillId}") // Captures the skill ID from the URL
	public ResponseEntity<Skill> updateOne(@PathVariable("skillId") int skillId, @RequestBody SkillDTO skillDTO) {
		// Accepts updated skill details from the request body and sends them to the
		// service
		return service.updateOne(skillId, skillDTO);
	}

	// DELETE - Removes a skill record from the database
	@DeleteMapping("/{skillId}") // Captures the skill ID from the URL
	public ResponseEntity<Void> deleteById(@PathVariable("skillId") int skillId) {
		return service.deleteById(skillId); // Calls the service to delete the specified skill
	}
}
