package com.skillstorm.dtos;

import com.skillstorm.models.Captain;

public class CrewDTO {
	
	private String crewName;
	
	private int maxCapacity;
	
	private boolean availability;
	
	private boolean hasCaptain;
	
	private Captain captain;
	
	

	public CrewDTO(String crewName, int maxCapacity, boolean avaiability, boolean hasCaptain, Captain captain) {
		super();
		this.crewName = crewName;
		this.maxCapacity = maxCapacity;
		this.availability = avaiability;
		this.hasCaptain = hasCaptain;
		this.captain = captain;
	}

	public String getCrewName() {
		return crewName;
	}

	public void setCrewName(String crewName) {
		this.crewName = crewName;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean avaiability) {
		this.availability = avaiability;
	}

	public boolean isHasCaptain() {
		return hasCaptain;
	}

	public void setHasCaptain(boolean hasCaptain) {
		this.hasCaptain = hasCaptain;
	}

	public Captain getCaptain() {
		return captain;
	}

	public void setCaptain(Captain captain) {
		this.captain = captain;
	}

	
	@Override
	public String toString() {
		return "CrewDTO [crewName=" + crewName + ", maxCapacity=" + maxCapacity + ", availability=" + availability
				+ ", hasCaptain=" + hasCaptain + ", captain=" + captain + "]";
	}
	

}
