package com.skillstorm.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillstorm.dtos.CaptainDTO;
import com.skillstorm.models.Captain;
import com.skillstorm.repositories.CaptainRepository;

@Service
public class CaptainService {

	private CaptainRepository repo;

	public CaptainService(CaptainRepository repo) {
		super();
		this.repo = repo;
	}
	
	/*
	 * SERVICE METHODS
	 */
	
	// GET ALL
	public Iterable<Captain> findAll() {
		return repo.findAll();
	}
	
	// GET BY ID
	public ResponseEntity<Captain> findById(int captainId) {
		if(repo.existsById(captainId)) {
			return ResponseEntity.status(HttpStatus.OK)
								 .body(repo.findById(captainId).get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					      .header("Error-Message", "This Captain ID does not exist")
						  .body(null);
		}
	}
	
	// CREATE ONE
	public ResponseEntity<Captain> addOne(CaptainDTO captainDTO) {
		if(repo.existsByCaptainNameIgnoreCase(captainDTO.getCaptainName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
								 .header("Error-Message", "A Captain with this name already exists")
					             .body(null);
		}
		
		return ResponseEntity.status(HttpStatus.CREATED)
				 .body(repo.save(new Captain(0, captainDTO.getCaptainName(), null, null)));
		
	}
	
	// UPDATE ONE
	public ResponseEntity<Captain> updateOne(int captainId, CaptainDTO captainDTO) {
		if(repo.existsById(captainId)) {
			return ResponseEntity.status(HttpStatus.OK)
			 			 		 .body(repo.save(new Captain(captainId, captainDTO.getCaptainName(), null, null)));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					             .header("Error-Message", "A Captain with this name already exists")
								 .body(null);
		}
	}
	
	// DELETE ONE
	public ResponseEntity<Void> deleteOne(int captainId) {
		repo.deleteById(captainId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
							 .body(null);
	}
	
}
