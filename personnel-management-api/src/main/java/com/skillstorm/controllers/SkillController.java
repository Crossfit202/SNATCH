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


@RestController
@RequestMapping("/skill")
public class SkillController {

	private SkillService service;

	public SkillController(SkillService service) {
		super();
		this.service = service;
	}
	
//	GET ALL
	
	@GetMapping
	public Iterable<Skill> findAll() {
		return service.findAll();
	}
	
//	GET BY ID
	
	@GetMapping("/{skillId}")
	public ResponseEntity<Skill> findById(@PathVariable("skillId") int skillId) {
		return service.findById(skillId);
	}
	
//	CREATE - POST
	@PostMapping
	public ResponseEntity<Skill> addOne(@RequestBody SkillDTO skillDTO) {
		return service.addOne(skillDTO);
	}
	
//	UPDATE - PUT
	@PutMapping("/{skillId}")
	public ResponseEntity<Skill> updateOne(@PathVariable("skillId") int skillId, @RequestBody SkillDTO skillDTO) { 
		return service.updateOne(skillId, skillDTO);
	}
	
//	DELETE
	@DeleteMapping("/{skillId}")
	public ResponseEntity<Void> deleteById(@PathVariable("skillId") int skillId) {
		return service.deleteById(skillId);
	}
	
	
}
