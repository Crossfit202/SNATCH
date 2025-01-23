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
	public ResponseEntity<Object> findById(int captainId) {
		if(repo.existsById(captainId)) {
			return ResponseEntity.status(HttpStatus.OK)
								 .body(repo.findById(captainId).get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
						  .body(String.format("Captain with ID %d does not exist!", captainId));
		}
	}
	
	// CREATE ONE
	public ResponseEntity<Object> addOne(CaptainDTO captainDTO) {
		if(repo.existsByCaptainNameIgnoreCase(captainDTO.getCaptainName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					             .body(String.format("'%s' already exists!", captainDTO.getCaptainName()));
		} else {
			return ResponseEntity.status(HttpStatus.CREATED)
					 .body(repo.save(new Captain(0, captainDTO.getCaptainName(), captainDTO.getLeader(), null)));
		}

		
	}
	
	// UPDATE ONE
	public ResponseEntity<Object> updateOne(int captainId, CaptainDTO captainDTO) {
		if(repo.existsById(captainId)) {
			return ResponseEntity.status(HttpStatus.OK)
			 			 		 .body(repo.save(new Captain(captainId, captainDTO.getCaptainName(), captainDTO.getLeader(), null)));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								 .body(String.format("Failed to update! Captain with ID %d does not exist. Ensure the ID is correct or create a new Captain.", captainId));
		}
	}
	
	// DELETE ONE
	public ResponseEntity<Void> deleteOne(int captainId) {
		repo.deleteById(captainId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
							 .body(null);
	}
	
}
