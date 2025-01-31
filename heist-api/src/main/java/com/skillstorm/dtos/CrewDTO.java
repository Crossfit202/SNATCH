package com.skillstorm.dtos;


public class CrewDTO {
	
	private String crewName;
	
	private int maxCapacity;
	
	private boolean availability;

	public CrewDTO(String crewName, int maxCapacity, boolean availability) {
		super();
		this.crewName = crewName;
		this.maxCapacity = maxCapacity;
		this.availability = availability;
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

	@Override
	public String toString() {
		return "CrewDTO [crewName=" + crewName + ", maxCapacity=" + maxCapacity + ", availability=" + availability
				+ "]";
	}
	
	

}
