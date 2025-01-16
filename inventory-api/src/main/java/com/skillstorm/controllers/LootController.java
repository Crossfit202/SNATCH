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

import com.skillstorm.dtos.LootDTO;
import com.skillstorm.models.Loot;
import com.skillstorm.services.LootService;

@RestController
@RequestMapping("/loot")
public class LootController {

	private LootService service;

	public LootController(LootService service) {
		super();
		this.service = service;
	}
	
//	GET ALL
	
	@GetMapping
	public Iterable<Loot> findAll() {
		return service.findAll();
	}
	
//	GET BY ID
	
	@GetMapping("/{lootId}")
	public ResponseEntity<Loot> findById(@PathVariable("lootId") int lootId) {
		return service.findById(lootId);
	}
	
//	CREATE - POST
	@PostMapping
	public ResponseEntity<Loot> addOne(@RequestBody LootDTO lootDTO) {
		return service.addOne(lootDTO);
	}
	
//	UPDATE - PUT
	@PutMapping("/{lootId}")
	public ResponseEntity<Loot> updateOne(@PathVariable("lootId") int lootId, @RequestBody LootDTO lootDTO) { 
		return service.updateOne(lootId, lootDTO);
	}
	
//	DELETE
	@DeleteMapping("/{lootId}")
	public ResponseEntity<Void> deleteById(@PathVariable("lootId") int lootId) {
		return service.deleteById(lootId);
	}
	
	
}
