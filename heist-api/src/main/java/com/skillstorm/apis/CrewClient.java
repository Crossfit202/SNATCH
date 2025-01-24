package com.skillstorm.apis;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.skillstorm.dtos.CrewDTO;

@FeignClient(name = "personnel-management-api")
public interface CrewClient {
	
	@GetMapping("/crew/{crewId}")
	public ResponseEntity<CrewDTO> findCrewByCrewId(@PathVariable("crewId") int crewId);
	
}
