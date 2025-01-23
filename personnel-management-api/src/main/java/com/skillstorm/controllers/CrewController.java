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


@RestController
@RequestMapping("/crew")
public class CrewController {

	private CrewService service;
	private HeistClient heistClient;
	
	public CrewController(CrewService service, HeistClient heistClient) {
		super();
		this.service = service;
		this.heistClient = heistClient;
	}
	
//	GET ALL
	
	@GetMapping
	public Iterable<Crew> findAll() {
		return service.findAll();
	}
	
//	GET BY ID
	
	@GetMapping("/{crewId}")
	public ResponseEntity<Object> findById(@PathVariable("crewId") int crewId) {
		return service.findById(crewId);
	}
	
//	GET HEIST BY CREW ID
	@GetMapping("/heist/{crewId}")
	public ResponseEntity<HeistDTO> findHeistByCrewId(@PathVariable("crewId") int crewId) {
		return heistClient.findByCrewId(crewId);
	}
	
//	CREATE - POST
	@PostMapping
	public ResponseEntity<Object> addOne(@RequestBody CrewDTO crewDTO) {
		return service.addOne(crewDTO);
	}
	
//	UPDATE - PUT
	@PutMapping("/{crewId}")
	public ResponseEntity<Object> updateOne(@PathVariable("crewId") int crewId, @RequestBody CrewDTO crewDTO) { 
		return service.updateOne(crewId, crewDTO);
	}
	
//	DELETE
	@DeleteMapping("/{crewId}")
	public ResponseEntity<Void> deleteById(@PathVariable("crewId") int crewId) {
		return service.deleteById(crewId);
	}
	
	
}
