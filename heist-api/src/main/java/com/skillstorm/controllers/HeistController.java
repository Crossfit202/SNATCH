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

import com.skillstorm.dtos.HeistDTO;
import com.skillstorm.dtos.HeistWithCrewDTO;
import com.skillstorm.models.Heist;
import com.skillstorm.services.HeistService;



@RestController
@RequestMapping("/heist")
public class HeistController {

	private HeistService service;

	public HeistController(HeistService service) {
		super();
		this.service = service;
	}
	
//	GET ALL
	
	@GetMapping
	public ResponseEntity<Iterable<HeistWithCrewDTO>> findAll() {
		return service.findAll();
	}
	
//	GET BY ID
	
	@GetMapping("/{heistId}")
	public ResponseEntity<Object> findById(@PathVariable("heistId") int heistId) {
		return service.findById(heistId);
	}
	
//	GET BY CREW ID
	@GetMapping("/crew/{crewId}")
	public ResponseEntity<Heist> findByCrewId(@PathVariable("crewId") int crewId) {
		return service.findByCrewId(crewId);
	}
	
//	CREATE - POST
	@PostMapping
	public ResponseEntity<Object> addOne(@RequestBody HeistDTO heistDTO) {
		return service.addOne(heistDTO);
	}
	
//	UPDATE - PUT
	@PutMapping("/{heistId}")
	public ResponseEntity<Object> updateOne(@PathVariable("heistId") int heistId, @RequestBody HeistDTO heistDTO) { 
		return service.updateOne(heistId, heistDTO);
	}
	
//	DELETE
	@DeleteMapping("/{heistId}")
	public ResponseEntity<Void> deleteById(@PathVariable("heistId") int heistId) {
		return service.deleteById(heistId);
	}
	
	
}
