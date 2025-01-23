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


@RestController
@RequestMapping("/personnel")
public class PersonnelController {

	private PersonnelService service;

	public PersonnelController(PersonnelService service) {
		super();
		this.service = service;
	}
	
//	GET ALL
	
	@GetMapping
	public Iterable<Personnel> findAll() {
		return service.findAll();
	}
	
//	GET BY ID
	
	@GetMapping("/{personnelId}")
	public ResponseEntity<Object> findById(@PathVariable("personnelId") int personnelId) {
		return service.findById(personnelId);
	}
	
//	CREATE - POST
	@PostMapping
	public ResponseEntity<Object> addOne(@RequestBody PersonnelDTO personnelDTO) {
		return service.addOne(personnelDTO);
	}
	
//	UPDATE - PUT
	@PutMapping("/{personnelId}")
	public ResponseEntity<Personnel> updateOne(@PathVariable("personnelId") int personnelId, @RequestBody PersonnelDTO personnelDTO) { 
		return service.updateOne(personnelId, personnelDTO);
	}
	
//	DELETE
	@DeleteMapping("/{personnelId}")
	public ResponseEntity<Void> deleteById(@PathVariable("personnelId") int personnelId) {
		return service.deleteById(personnelId);
	}
	
	
}
