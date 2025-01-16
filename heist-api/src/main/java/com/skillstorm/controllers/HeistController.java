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
	public Iterable<Heist> findAll() {
		return service.findAll();
	}
	
//	GET BY ID
	
	@GetMapping("/{heistId}")
	public ResponseEntity<Heist> findById(@PathVariable("heistId") int heistId) {
		return service.findById(heistId);
	}
	
//	CREATE - POST
	@PostMapping
	public ResponseEntity<Heist> addOne(@RequestBody HeistDTO heistDTO) {
		return service.addOne(heistDTO);
	}
	
//	UPDATE - PUT
	@PutMapping("/{heistId}")
	public ResponseEntity<Heist> updateOne(@PathVariable("heistId") int heistId, @RequestBody HeistDTO heistDTO) { 
		return service.updateOne(heistId, heistDTO);
	}
	
//	DELETE
	@DeleteMapping("/{heistId}")
	public ResponseEntity<Void> deleteById(@PathVariable("heistId") int heistId) {
		return service.deleteById(heistId);
	}
	
	
}
