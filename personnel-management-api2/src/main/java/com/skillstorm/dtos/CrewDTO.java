package com.skillstorm.dtos;

import java.util.List;

import com.skillstorm.models.Captain;
import com.skillstorm.models.Personnel;

public class CrewDTO {
	
	private String crewName;
	
	private int maxCapacity;
	
	private boolean availability;
	
	private boolean hasCaptain;
	
	private Captain captain;
	
	private List<Personnel> personnels;

	public CrewDTO(String crewName, int maxCapacity, boolean availability, boolean hasCaptain, Captain captain,
			List<Personnel> personnels) {
		super();
		this.crewName = crewName;
		this.maxCapacity = maxCapacity;
		this.availability = availability;
		this.hasCaptain = hasCaptain;
		this.captain = captain;
		this.personnels = personnels;
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

	public void setAvailability(boolean availability) {
		this.availability = availability;
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

	public List<Personnel> getPersonnels() {
		return personnels;
	}

	public void setPersonnels(List<Personnel> personnels) {
		this.personnels = personnels;
	}

	@Override
	public String toString() {
		return "CrewDTO [crewName=" + crewName + ", maxCapacity=" + maxCapacity + ", availability=" + availability
				+ ", hasCaptain=" + hasCaptain + ", captain=" + captain + ", personnels=" + personnels + "]";
	}

	
	

}
