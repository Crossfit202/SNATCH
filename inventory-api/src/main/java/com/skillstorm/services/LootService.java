package com.skillstorm.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillstorm.dtos.LootDTO;
import com.skillstorm.models.Loot;
import com.skillstorm.repositories.LootRepository;


@Service
public class LootService {
	private LootRepository repo;

	public LootService(LootRepository repo) {
		super();
		this.repo = repo;
	}

//	SERVICE METHODS
	
//	GET ALL
	public Iterable<Loot> findAll() {
		return repo.findAll();
	}
	
//	FIND BY ID
	
	public ResponseEntity<Loot> findById(int lootId) {
		if (repo.existsById(lootId))
			return ResponseEntity.status(HttpStatus.OK)
							     .body(repo.findById(lootId).get());
		else return ResponseEntity.status(HttpStatus.NOT_FOUND)
				                  .body(null);
	}
	
	
//	CREATE ONE - POST
	public ResponseEntity<Loot> addOne(LootDTO lootDTO) {
		return ResponseEntity.status(HttpStatus.CREATED)
				             .body(repo.save(new Loot(0, lootDTO.getItemName(), lootDTO.getQuantity())));
	}
	
	
//	PUT
		public ResponseEntity<Loot> updateOne(int lootId, LootDTO lootDTO) {
		    if (repo.existsById(lootId))
		    	return ResponseEntity.status(HttpStatus.OK)
		    			             .body(repo.save(new Loot(lootId, lootDTO.getItemName(), lootDTO.getQuantity())));
		    	
		    else
		    	return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		    			             .body(null);
		        
		}
	
	
//  DELETE
		public ResponseEntity<Void> deleteById(int lootId) {
		        repo.deleteById(lootId);  
		        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		    } 
		
}
