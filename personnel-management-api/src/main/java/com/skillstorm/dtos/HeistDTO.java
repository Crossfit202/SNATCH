package com.skillstorm.dtos;

public class HeistDTO {
	
	private String description;
	
	
	private String location;
	
	private boolean isAssigned;
	
	private String status;
	
//	foreign key - refactor later
	
	private int crewId;

	public HeistDTO(String description, String location, boolean isAssigned, String status, int crewId) {
		super();
		this.description = description;
		this.location = location;
		this.isAssigned = isAssigned;
		this.status = status;
		this.crewId = crewId;
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

	@Override
	public String toString() {
		return "HeistDTO [description=" + description + ", location=" + location + ", isAssigned=" + isAssigned
				+ ", status=" + status + ", crewId=" + crewId + "]";
	}
	
	
}
