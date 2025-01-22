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

@RestController
@RequestMapping("/captain")
public class CaptainController {

	private CaptainService service;

	public CaptainController(CaptainService service) {
		super();
		this.service = service;
	}

	// GET ALL
	@GetMapping
	public Iterable<Captain> findAll() {
		return service.findAll();
	}

	// GET BY ID
	@GetMapping("/{captainId}")
	public ResponseEntity<Object> findById(@PathVariable int captainId) {
		return service.findById(captainId);
	}

	// CREATE ONE
	@PostMapping
	public ResponseEntity<Captain> addOne(@RequestBody CaptainDTO captainDTO) {
		return service.addOne(captainDTO);
	}

	// UPDATE ONE
	@PutMapping("/{captainId}")
	public ResponseEntity<Captain> updateOne(@PathVariable int captainId, @RequestBody CaptainDTO captainDTO) {
		return service.updateOne(captainId, captainDTO);
	}

	// DELETE ONE
	@DeleteMapping("/{captainId}")
	public ResponseEntity<Void> deleteOne(@PathVariable int captainId) {
		return service.deleteOne(captainId);
	}

}
