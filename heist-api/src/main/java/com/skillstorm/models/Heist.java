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
	private String status; 
	
	// FK TO CREW--USED OPENFEIGN WITH WRAPPER DTO
	@Column(name = "crew_id")
	private int crewId;

	
	// CONSTRUCTORS
	public Heist() {
		super();
	}

	public Heist(int heistId, String description, String location, boolean isAssigned, String status, int crewId) {
		super();
		this.heistId = heistId;
		this.description = description;
		this.location = location;
		this.isAssigned = isAssigned;
		this.status = status;
		this.crewId = crewId;
	}

	
	// GETTERS AND SETTERS
	public int getHeistId() {
		return heistId;
	}

	public void setHeistId(int heistId) {
		this.heistId = heistId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isAssigned() {
		return isAssigned;
	}

	public void setAssigned(boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCrewId() {
		return crewId;
	}

	public void setCrewId(int crewId) {
		this.crewId = crewId;
	}

	// TO STRING
	@Override
	public String toString() {
		return "Heist [heistId=" + heistId + ", description=" + description + ", location=" + location + ", isAssigned="
				+ isAssigned + ", status=" + status + ", crewId=" + crewId + "]";
	}
	
	
		
}
