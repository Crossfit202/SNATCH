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

@RestController
@RequestMapping("/leader")
public class LeaderController {

	private LeaderService service;

	public LeaderController(LeaderService service) {
		super();
		this.service = service;
	}
	
	// GET ALL
	@GetMapping
	public Iterable<Leader> findAll() {
		return service.findAll();
	}
	
	// GET BY ID
	@GetMapping("/{leaderId}")
	public ResponseEntity<Object> findById(@PathVariable int leaderId) {
		return service.findById(leaderId);
	}
	
	// CREATE ONE
	@PostMapping
	public ResponseEntity<Object> addOne(@RequestBody LeaderDTO leaderDTO) {
		return service.addOne(leaderDTO);
	}
	
	// UPDATE ONE
	@PutMapping("/{leaderId}")
	public ResponseEntity<Object> updateOne(@PathVariable int leaderId, @RequestBody LeaderDTO leaderDTO) {
		return service.updateOne(leaderId, leaderDTO);
	}
	
	// DELETE ONE
	@DeleteMapping("/{leaderId}")
	public ResponseEntity<Void> deleteOne(@PathVariable int leaderId) {
		return service.deleteOne(leaderId);
	}
	
}
