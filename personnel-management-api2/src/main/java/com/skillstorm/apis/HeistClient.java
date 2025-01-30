package com.skillstorm.apis;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.skillstorm.dtos.HeistDTO;

@FeignClient(name = "heist-api")
public interface HeistClient {
	
	@GetMapping("/heist/crew/{crewId}")
	public ResponseEntity<HeistDTO> findByCrewId(@PathVariable("crewId") int crewId);
	
}
