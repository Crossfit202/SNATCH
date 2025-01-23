package com.skillstorm.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.skillstorm.dtos.LeaderDTO;
import com.skillstorm.models.Leader;
import com.skillstorm.repositories.LeaderRepository;

@Service
public class LeaderService {

	private LeaderRepository repo;

	public LeaderService(LeaderRepository repo) {
		super();
		this.repo = repo;
	}
	
	/*
	 * SERVICE METHODS
	 */
	
	// GET ALL
	public Iterable<Leader> findAll() {
		return repo.findAll();
	}
	
	// GET BY ID
	public ResponseEntity<Object> findById(int leaderId) {
		if(repo.existsById(leaderId)) {
			return ResponseEntity.status(HttpStatus.OK)
								 .body(repo.findById(leaderId).get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
						  .body(String.format("Leader with ID %d does not exist!", leaderId));
		}
	}
	
	// CREATE ONE
	public ResponseEntity<Object> addOne(LeaderDTO leaderDTO) {
		if(repo.existsByLeaderNameIgnoreCase(leaderDTO.getLeaderName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
								 .body(String.format("Leader with name '%s' already exists!", leaderDTO.getLeaderName()));
		} else {
			return ResponseEntity.status(HttpStatus.CREATED)
					 .body(repo.save(new Leader(0, leaderDTO.getLeaderName(), null)));
		}
		
		
	}
	
	// UPDATE ONE
	public ResponseEntity<Object> updateOne(int leaderId, LeaderDTO leaderDTO) {
		if(repo.existsById(leaderId)) {
			return ResponseEntity.status(HttpStatus.OK)
			 			 		 .body(repo.save(new Leader(leaderId, leaderDTO.getLeaderName(), null)));
	
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								 .body(String.format("Failed to update! Leader with ID %d does not exist. Ensure the ID is correct or create a new Leader.", leaderId));
		}
	}
	
	// DELETE ONE
	public ResponseEntity<Void> deleteOne(int leaderId) {
		repo.deleteById(leaderId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
							 .body(null);
	}
	
}
