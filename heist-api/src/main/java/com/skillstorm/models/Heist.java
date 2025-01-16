package com.skillstorm.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "heist")
public class Heist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "heist_id")
	private int heistId;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "is_assigned")
	private boolean isAssigned; 
	
	@Column(name = "status")
	private boolean status; 
	
	@Column(name = "crew_id")
	private int crewId;
	
		
}
