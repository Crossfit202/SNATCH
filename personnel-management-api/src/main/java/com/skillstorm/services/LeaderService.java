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
	public ResponseEntity<Leader> findById(int leaderId) {
		if(repo.existsById(leaderId)) {
			return ResponseEntity.status(HttpStatus.OK)
								 .body(repo.findById(leaderId).get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
						  .body(null);
		}
	}
	
	// CREATE ONE
	public ResponseEntity<Leader> addOne(LeaderDTO leaderDTO) {
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body(repo.save(new Leader(0, leaderDTO.getLeaderName(), null)));
	}
	
	// UPDATE ONE
	public ResponseEntity<Leader> updateOne(int leaderId, LeaderDTO leaderDTO) {
		if(repo.existsById(leaderId)) {
			return ResponseEntity.status(HttpStatus.OK)
			 			 		 .body(repo.save(new Leader(leaderId, leaderDTO.getLeaderName(), null)));
	
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								 .body(null);
		}
	}
	
	// DELETE ONE
	public ResponseEntity<Void> deleteOne(int leaderId) {
		repo.deleteById(leaderId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
							 .body(null);
	}
	
}
